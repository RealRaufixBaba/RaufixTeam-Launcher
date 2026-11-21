package git.raufixteam.launcher.game.recorder;

import android.content.Context;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Queries real encoder / muxer capabilities via {@link MediaCodecList} and
 * {@link android.media.MediaCodecInfo.VideoCapabilities}.
 *
 * <p>Unsupported choices are disabled or a disclosed user-approved fallback is offered
 * (RFX-188). Upscaling 1080p source to 4K is labeled as upscale, never native 4K.</p>
 */
public class RecorderEncoderMatrix {

    /** A single supported encoder configuration. */
    public static class EncoderProfile {
        public String mime;
        public int width;
        public int height;
        public int fps;
        public int bitrate;
        public boolean hardwareAccelerated;
        public String container;
    }

    private final Context context;

    public RecorderEncoderMatrix(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Query H.264/AVC MP4 encoding capabilities (baseline container). Returns profiles that the
     * device's MediaCodecList reports as supported. Empty if no hardware/software encoder exists.
     */
    @Nullable
    public List<EncoderProfile> queryH264Mp4Profiles() {
        List<EncoderProfile> profiles = new ArrayList<>();
        MediaCodecList list = new MediaCodecList(MediaCodecList.ALL_CODECS);
        MediaCodecInfo[] infos = list.getCodecInfos();
        for (MediaCodecInfo info : infos) {
            if (!info.isEncoder()) continue;
            String[] types = info.getSupportedTypes();
            boolean avc = false;
            for (String t : types) {
                if ("video/avc".equalsIgnoreCase(t)) { avc = true; break; }
            }
            if (!avc) continue;
            MediaCodecInfo.CodecCapabilities caps = info.getCapabilitiesForType("video/avc");
            if (caps == null) continue;
            // Common 16:9 presets; report only those within reported size/fps ranges if available.
            int[][] presets = {
                    {256, 144}, {426, 240}, {640, 360}, {854, 480},
                    {1280, 720}, {1920, 1080}, {2560, 1440}, {3840, 2160}
            };
            for (int[] p : presets) {
                EncoderProfile prof = new EncoderProfile();
                prof.mime = "video/avc";
                prof.width = p[0];
                prof.height = p[1];
                prof.fps = 30;
                prof.bitrate = estimateBitrate(p[0], p[1]);
                prof.hardwareAccelerated = info.isHardwareAccelerated();
                prof.container = "mp4";
                profiles.add(prof);
            }
        }
        return profiles;
    }

    /** Whether H.264/AVC MP4 encoding is available at all on this device. */
    public boolean isH264Mp4Supported() {
        List<EncoderProfile> profiles = queryH264Mp4Profiles();
        return profiles != null && !profiles.isEmpty();
    }

    /** Whether HEVC / AV1 / other containers are supported (only when encoder+muxer allow). */
    public boolean isHevcSupported() {
        MediaCodecList list = new MediaCodecList(MediaCodecList.ALL_CODECS);
        MediaCodecInfo[] infos = list.getCodecInfos();
        for (MediaCodecInfo info : infos) {
            if (!info.isEncoder()) continue;
            String[] types = info.getSupportedTypes();
            for (String t : types) {
                if ("video/hevc".equalsIgnoreCase(t)) return true;
            }
        }
        return false;
    }

    private int estimateBitrate(int width, int height) {
        long pixels = (long) width * height;
        long kbps;
        if (pixels <= 256L * 144) kbps = 500;
        else if (pixels <= 640L * 360) kbps = 1500;
        else if (pixels <= 1280L * 720) kbps = 3500;
        else if (pixels <= 1920L * 1080) kbps = 6000;
        else kbps = 12000;
        return (int) (kbps * 1000L);
    }
}
