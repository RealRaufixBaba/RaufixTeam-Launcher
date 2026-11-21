package git.raufixteam.launcher.game.renderer;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Real game-FPS monitor based on actual frame-present / swap timestamps.
 *
 * <p>FPS is computed only from measured frame presentation intervals — never from the display
 * refresh rate and never as a fixed value. Call {@link #framePresented()} from the renderer's
 * frame-present / swap-callback path once per presented frame.</p>
 *
 * <p>Statistics: median FPS, 1% and 0.1% lows, P50/P95/P99 frame-time summaries, and live
 * frame-time samples for graph rendering.</p>
 */
public class FpsMonitor {
    private static final String TAG = "FpsMonitor";

    private final List<Long> frameTimesNs = new CopyOnWriteArrayList<>();
    private final SparseArray<Long> secondBuckets = new SparseArray<>();
    private long lastFrameTimeNs = -1L;
    private boolean started = false;
    private int droppedFrames = 0;

    /** Callback invoked whenever a new FPS sample is available (optional UI graph update). */
    public interface Listener {
        void onFpsSample(double instantFps, double averageFps);
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Call once per presented frame from the renderer's present/swap callback.
     *
     * @param frameTimeNs monotonic clock nanoseconds at which the frame was presented
     */
    public synchronized void framePresented(long frameTimeNs) {
        if (!started) {
            start();
        }
        if (lastFrameTimeNs >= 0) {
            long intervalNs = frameTimeNs - lastFrameTimeNs;
            if (intervalNs > 0L) {
                frameTimesNs.add(intervalNs);
                // Keep a bounded window for live stats (last ~10 seconds at 120 fps)
                final int maxSamples = 1200;
                if (frameTimesNs.size() > maxSamples) {
                    frameTimesNs.subList(0, frameTimesNs.size() - maxSamples).clear();
                }
                // Bucket by second for FPS-over-time
                long sec = frameTimeNs / 1_000_000_000L;
                Long count = secondBuckets.get((int) sec);
                secondBuckets.put((int) sec, count == null ? 1L : count + 1L);
                // Prune buckets older than ~30 s
                if (secondBuckets.size() > 30) {
                    secondBuckets.removeAt(0);
                }
                if (listener != null) {
                    double instantFps = 1_000_000_000.0 / intervalNs;
                    listener.onFpsSample(instantFps, getAverageFps());
                }
            } else {
                // Non-positive interval indicates a dropped / duplicated frame
                droppedFrames++;
            }
        }
        lastFrameTimeNs = frameTimeNs;
    }

    /** Mark a dropped frame explicitly (e.g. encoder skipped a frame). */
    public synchronized void frameDropped() {
        droppedFrames++;
    }

    private synchronized void start() {
        started = true;
        lastFrameTimeNs = -1L;
        frameTimesNs.clear();
        secondBuckets.clear();
        droppedFrames = 0;
    }

    /** Stop measurement and clear all samples. */
    public synchronized void stop() {
        started = false;
        lastFrameTimeNs = -1L;
        frameTimesNs.clear();
        secondBuckets.clear();
        droppedFrames = 0;
    }

    /** Whether measurement is currently active. */
    public synchronized boolean isRunning() {
        return started;
    }

    /** Instantaneous FPS from the most recent frame interval. */
    public synchronized double getInstantFps() {
        if (frameTimesNs.isEmpty()) return 0.0;
        long last = frameTimesNs.get(frameTimesNs.size() - 1);
        if (last <= 0) return 0.0;
        return 1_000_000_000.0 / last;
    }

    /** Average FPS over the sampled window. */
    public synchronized double getAverageFps() {
        if (frameTimesNs.isEmpty()) return 0.0;
        long totalNs = 0L;
        for (long t : frameTimesNs) totalNs += t;
        if (totalNs <= 0) return 0.0;
        return (frameTimesNs.size() * 1_000_000_000.0) / totalNs;
    }

    /** Median frame time in milliseconds. */
    public synchronized double getMedianFrameTimeMs() {
        return percentileFrameTimeMs(0.50);
    }

    /** P95 frame time in milliseconds. */
    public synchronized double getP95FrameTimeMs() {
        return percentileFrameTimeMs(0.95);
    }

    /** P99 frame time in milliseconds. */
    public synchronized double getP99FrameTimeMs() {
        return percentileFrameTimeMs(0.99);
    }

    /** 1% low FPS (from the slowest 1% of frames). */
    public synchronized double getOnePercentLowFps() {
        return lowFps(0.01);
    }

    /** 0.1% low FPS (from the slowest 0.1% of frames). */
    public synchronized double getPointOnePercentLowFps() {
        return lowFps(0.001);
    }

    /** Number of dropped frames recorded since start. */
    public synchronized int getDroppedFrames() {
        return droppedFrames;
    }

    /** Number of frame samples collected. */
    public synchronized int getSampleCount() {
        return frameTimesNs.size();
    }

    /** Frames per second observed in the most recent full second bucket. */
    public synchronized int getLastSecondFps() {
        if (secondBuckets.size() == 0) return 0;
        int idx = secondBuckets.size() - 1;
        return secondBuckets.valueAt(idx).intValue();
    }

    /* ---------- percentile helpers ---------- */

    private double percentileFrameTimeMs(double percentile) {
        if (frameTimesNs.isEmpty()) return 0.0;
        List<Long> sorted = new ArrayList<>(frameTimesNs);
        Collections.sort(sorted);
        int idx = (int) Math.ceil(percentile * sorted.size()) - 1;
        if (idx < 0) idx = 0;
        if (idx >= sorted.size()) idx = sorted.size() - 1;
        return sorted.get(idx) / 1_000_000.0;
    }

    private double lowFps(double fraction) {
        if (frameTimesNs.isEmpty()) return 0.0;
        List<Long> sorted = new ArrayList<>(frameTimesNs);
        Collections.sort(sorted); // ascending frame time -> slowest at end
        int count = Math.max(1, (int) Math.ceil(fraction * sorted.size()));
        // Take the slowest `count` frames (largest frame times), average their FPS
        double sumFps = 0.0;
        int start = sorted.size() - count;
        for (int i = start; i < sorted.size(); i++) {
            long t = sorted.get(i);
            if (t > 0) sumFps += 1_000_000_000.0 / t;
        }
        return sumFps / count;
    }
}
