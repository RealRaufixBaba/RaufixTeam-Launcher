# RaufixTeam Launcher V1 — FPS Benchmark Protocol (A/B)

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Status:** NOT RUN — no device/build environment available. No FPS number is claimed.

## 1. Measurement principle (RFX-101–105)

Game FPS is measured **only from actual game frame-present / swap / frame timestamps** produced by the renderer backend, never from the display refresh rate and never as a fixed value.

- Display refresh Hz (60/90/120 Hz) is shown **separately** from FPS (RFX-102) via `Display.Mode.getRefreshRate()`.
- A live frame-time graph (RFX-103), 1%/0.1% lows (RFX-104), and P50/P95/P99 frame-time summaries (RFX-105) are computed from measured frame times.
- If a backend cannot measure presented frames, FPS is `UNAVAILABLE` for that backend.

## 2. Controlled A/B conditions (RFX-106–108, RFX-120)

FPS gains are claimed **only with controlled A/B evidence**, all identical between A and B:

- Same device, Android version, GPU, thermal starting state
- Same Minecraft version, seed/world/scene
- Same loader/mod list, same JRE, same renderer
- Same video settings, same duration
- At least **five runs**, **60 s warm-up + 120 s measurement** per run (RFX-107)
- Report **median FPS, 1% low, P50/P95/P99 frame time**, temperature, battery, memory

## 3. Go/no-go target

- At least **10% median FPS uplift** (or equivalent frame-time reduction) in **two reference devices/scenarios**
- Without **>5% 1%-low regression** or sustained thermal regression

If hardware/traces are unavailable → `BLOCKED/NOT RUN`. Fabricated benchmarks, fixed FPS, refresh-rate-as-FPS, and "boosts every device" claims are **forbidden**.

## 4. What this sandbox can and cannot do

| Item | Status | Reason |
|------|--------|--------|
| FPS counter from frame-present timestamps | Source written (`FpsMonitor`) | Not compiled/run |
| A/B benchmark wizard | Source planned (`ABBenchmarkWizard`) | Not run |
| Perfetto/GPU-CPU traces | Not available | No device |
| Median FPS / 1% low / P95/P99 | Not measured | No runs |
| Go/no-go 10% uplift claim | **Not claimed** | No evidence |

## 5. Honest result

**No benchmark was executed.** Every FPS-related test in `docs/TEST_RESULTS.md` is `NOT RUN`. No PASS, no FPS number, no uplift claim appears anywhere in this release.
