package git.raufixteam.launcher.utils.lifecycle;

import static git.raufixteam.launcher.game.GameActivity.INTENT_LAUNCH_CLASSPATH;
import static git.raufixteam.launcher.game.GameActivity.INTENT_LAUNCH_VERSION;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import git.raufixteam.launcher.game.GameActivity;
import git.raufixteam.launcher.R;
import git.raufixteam.launcher.Tools;
import git.raufixteam.launcher.progresskeeper.ProgressKeeper;
import git.raufixteam.launcher.tasks.mojson.MoJsonExtras;
import git.raufixteam.launcher.utils.NotificationUtils;

import java.io.File;

public class ContextAwareDoneListener implements MoJsonExtras.DoneListener, ContextExecutorTask {
    private final String mErrorString;
    private final String mNormalizedVersionid;
    private File[] classpath;

    public ContextAwareDoneListener(Context baseContext, String versionId) {
        this.mErrorString = baseContext.getString(R.string.mc_download_failed);
        this.mNormalizedVersionid = versionId;
    }

    private Intent createGameStartIntent(Context context) {
        Intent mainIntent = new Intent(context, GameActivity.class);
        mainIntent.putExtra(INTENT_LAUNCH_VERSION, mNormalizedVersionid);
        mainIntent.putExtra(INTENT_LAUNCH_CLASSPATH, classpath);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return mainIntent;
    }

    @Override
    public void onDownloadDone(File[] classpath) {
        this.classpath = classpath;
        ProgressKeeper.waitUntilDone(()->ContextExecutor.execute(this));
    }

    @Override
    public void onDownloadFailed(Throwable throwable) {
        Tools.showErrorRemote(mErrorString, throwable);
    }

    @Override
    public void executeWithActivity(Activity activity) {
        try {
            // Strictly release all launcher background GIF bitmaps, MP4 decoders & shader surfaces
            // BEFORE starting GameActivity / GameService so maximum RAM is free for the JVM.
            if (activity instanceof git.raufixteam.launcher.LauncherActivity) {
                ((git.raufixteam.launcher.LauncherActivity) activity).releaseAllLauncherBackgroundMedia(true);
            } else {
                git.raufixteam.launcher.ui.ZyroxShaderBackgroundView.releaseAllForGameStart();
                git.raufixteam.launcher.ui.ZyroxLoopingVideoView.suspendInactiveDecoders(true);
                git.raufixteam.launcher.ui.ZyroxSeamlessGifView.purgeAllActiveInstances(true);
            }
            git.raufixteam.launcher.servers.ZyroxRoomBridge.stopClientTunnel();
            Intent gameStartIntent = createGameStartIntent(activity);
            activity.startActivity(gameStartIntent);
            activity.finish();
            activity.overridePendingTransition(0, 0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Throwable e) {
            Tools.showError(activity.getBaseContext(), e);
        }
    }

    @Override
    public void executeWithApplication(Context context) {
        Intent gameStartIntent = createGameStartIntent(context);
        // Since the game is a separate process anyway, it does not matter if it gets invoked
        // from somewhere other than the launcher activity.
        // The only problem may arise if the launcher starts doing something when the user starts the notification.
        // So, the notification is automatically removed once there are tasks ongoing in the ProgressKeeper
        NotificationUtils.sendBasicNotification(context,
                R.string.notif_download_finished,
                R.string.notif_download_finished_desc,
                gameStartIntent,
                NotificationUtils.PENDINGINTENT_CODE_GAME_START,
                NotificationUtils.NOTIFICATION_ID_GAME_START
        );
        // You should keep yourself safe, NOW!
        // otherwise android does weird things...
    }
}
