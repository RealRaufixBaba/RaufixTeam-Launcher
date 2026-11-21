package git.raufixteam.launcher.game.recorder;

import android.media.MediaCodecList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import git.raufixteam.launcher.R;
import git.raufixteam.launcher.Tools;

/**
 * Recording Settings page opened from the in-game gear/tools menu.
 *
 * <p>Provides start / pause / resume / stop controls, resolution presets, audio-mode selection,
 * and encoder-capability queries. No floating record bubble is ever added (RFX-195); transport
 * controls live only in this menu plus Android's required foreground-service notification.</p>
 */
public class RecorderSettingsFragment extends Fragment implements RecorderController.Listener {

    private static final int[] PRESET_WIDTHS = {256, 426, 640, 854, 1280, 1920, 2560, 3840};
    private static final int[] PRESET_HEIGHTS = {144, 240, 360, 480, 720, 1080, 1440, 2160};
    private static final String[] PRESET_LABELS = {
            "144p (256×144)", "240p (426×240)", "360p (640×360)", "480p (854×480)",
            "720p (1280×720)", "1080p (1920×1080)", "1440p (2560×1440)", "2160p / 4K (3840×2160)"
    };

    private RecorderController controller;
    private Spinner resolutionSpinner;
    private Spinner audioModeSpinner;
    private Button startButton;
    private Button pauseButton;
    private Button resumeButton;
    private Button stopButton;
    private TextView statusText;
    private TextView statsText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recorder_settings, container, false);

        controller = new RecorderController(requireContext(), this);

        resolutionSpinner = root.findViewById(R.id.recorder_resolution);
        audioModeSpinner = root.findViewById(R.id.recorder_audio_mode);
        startButton = root.findViewById(R.id.recorder_start);
        pauseButton = root.findViewById(R.id.recorder_pause);
        resumeButton = root.findViewById(R.id.recorder_resume);
        stopButton = root.findViewById(R.id.recorder_stop);
        statusText = root.findViewById(R.id.recorder_status);
        statsText = root.findViewById(R.id.recorder_stats);

        // Resolution presets (RFX-186, RFX-187)
        ArrayAdapter<String> resAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, PRESET_LABELS);
        resAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resolutionSpinner.setAdapter(resAdapter);
        resolutionSpinner.setSelection(4); // default 720p

        // Audio modes — exactly four (RFX-189–192)
        ArrayAdapter<String> audioAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new String[]{
                "Game audio only",
                "Game + capturable media",
                "Game + microphone",
                "Game + microphone + media"
        });
        audioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        audioModeSpinner.setAdapter(audioAdapter);

        startButton.setOnClickListener(v -> startRecording());
        pauseButton.setOnClickListener(v -> {
            controller.pause();
            updateButtons();
        });
        resumeButton.setOnClickListener(v -> {
            controller.resume();
            updateButtons();
        });
        stopButton.setOnClickListener(v -> stopRecording());

        updateButtons();
        return root;
    }

    @Override
    public void onDestroyView() {
        // Never leave a recording running when the settings page is closed; stop it cleanly.
        if (controller != null && controller.isRunning()) {
            controller.stop();
        }
        super.onDestroyView();
    }

    private void startRecording() {
        int pos = resolutionSpinner.getSelectedItemPosition();
        int width = PRESET_WIDTHS[pos];
        int height = PRESET_HEIGHTS[pos];
        int fps = 30;
        int bitrate = estimateBitrate(width, height);

        RecorderController.AudioMode mode = RecorderController.AudioMode.values()
                [audioModeSpinner.getSelectedItemPosition()];

        // Output to app-specific external storage / MediaStore-ready location
        java.io.File out = new java.io.File(Tools.DIR_CACHE != null ? Tools.DIR_CACHE : requireContext().getCacheDir(),
                "recording_" + System.currentTimeMillis() + ".mp4");

        if (controller.prepare(out, mode, width, height, fps, bitrate)) {
            if (controller.start()) {
                updateButtons();
                statusText.setText("Recording: " + width + "×" + height + " @" + fps + "fps");
            } else {
                Toast.makeText(requireContext(), "Failed to start recording", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(requireContext(), "Recording not supported on this device/backend", Toast.LENGTH_LONG).show();
        }
    }

    private void stopRecording() {
        if (controller != null) controller.stop();
        updateButtons();
        statusText.setText("Idle");
    }

    private void updateButtons() {
        boolean running = controller != null && controller.isRunning();
        boolean paused = controller != null && controller.isPaused();
        startButton.setEnabled(!running);
        pauseButton.setEnabled(running && !paused);
        resumeButton.setEnabled(running && paused);
        stopButton.setEnabled(running);
    }

    private int estimateBitrate(int width, int height) {
        // Conservative H.264 bitrate estimate (kbps → bps) for storage preflight (RFX-196)
        long pixels = (long) width * height;
        long kbps;
        if (pixels <= 256L * 144) kbps = 500;
        else if (pixels <= 640L * 360) kbps = 1500;
        else if (pixels <= 1280L * 720) kbps = 3500;
        else if (pixels <= 1920L * 1080) kbps = 6000;
        else kbps = 12000;
        return (int) (kbps * 1000L);
    }

    /* ---------- RecorderController.Listener ---------- */

    @Override
    public void onStateChanged(RecorderController.State state) {
        Tools.runOnUiThread(() -> {
            updateButtons();
            switch (state) {
                case IDLE: statusText.setText("Idle"); break;
                case PREPARING: statusText.setText("Preparing encoder…"); break;
                case RECORDING: statusText.setText("Recording…"); break;
                case PAUSED: statusText.setText("Paused"); break;
                case FINALIZING: statusText.setText("Finalizing…"); break;
                case ERROR: statusText.setText("Error"); break;
            }
        });
    }

    @Override
    public void onError(String message) {
        Tools.runOnUiThread(() ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show());
    }

    @Override
    public void onStats(long durationMs, long frames, long droppedFrames, long bytes) {
        Tools.runOnUiThread(() ->
                statsText.setText(String.format("Frames: %d  Dropped: %d  Duration: %d ms",
                        frames, droppedFrames, durationMs)));
    }
}
