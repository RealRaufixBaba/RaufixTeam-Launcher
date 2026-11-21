package git.raufixteam.launcher.tasks;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import git.raufixteam.launcher.JVersionList;
import git.raufixteam.launcher.Tools;
import git.raufixteam.launcher.metadata.LocalMetadataCache;
import git.raufixteam.launcher.metadata.VersionMetadataRepository;

/**
 * Adapter bridging legacy launcher startup callers to {@link VersionMetadataRepository}.
 * Immediately delivers locally cached Minecraft versions and refreshes in the background against
 * Mojang's official {@code version_manifest_v2.json} with ETag conditional caching and offline fallback.
 */
public class AsyncVersionList {
    private static final String TAG = "AsyncVersionList";

    public void getVersionList(@Nullable VersionDoneListener listener) {
        VersionMetadataRepository.getInstance().syncMinecraftVersionsOnStartup(
                new VersionMetadataRepository.CatalogCallback() {
                    @Override
                    public void onCatalogAvailable(@NonNull JVersionList catalog,
                                                   @NonNull LocalMetadataCache.SyncSource source,
                                                   boolean changed) {
                        if (listener != null) {
                            listener.onVersionDone(catalog);
                        }
                    }

                    @Override
                    public void onSyncError(@NonNull Exception error, boolean hasCachedCatalog) {
                        if (hasCachedCatalog) {
                            // Offline or transient network issue with valid cached versions — keep working silently.
                            Log.w(TAG, "Background version manifest sync failed; retaining cached catalog", error);
                            return;
                        }
                        Log.e(TAG, "Initial version manifest sync failed with no cached catalog", error);
                        if (listener != null) {
                            listener.onVersionDone(null);
                        }
                        Tools.showErrorRemote(error);
                    }
                }
        );
    }

    /** Basic listener, acting as a callback */
    public interface VersionDoneListener {
        void onVersionDone(JVersionList versions);
    }
}
