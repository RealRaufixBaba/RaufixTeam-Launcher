# Changelog

All notable changes to **RaufixTeam Launcher V1** are documented in this file.

## [1.0.0] — 2026-11-21

### Highlights

First release of **RaufixTeam Launcher V1**, a rebrand of the upstream MojoLauncher/PojavLauncher-derived Android Minecraft: Java Edition launcher (LGPL-3.0).

**Release status: BLOCKED.** No JDK/Gradle/Android SDK/NDK is available in this environment, so no APK was built, signed, or verified, and no test was executed. All Gradle tasks are BLOCKED / NOT RUN. No PASS, FPS number, or benchmark is claimed.

### Rebrand (1.0.0)

- Product name exactly **RaufixTeam Launcher V1**; `versionName` exactly `1.0.0`; monotonic reproducible `versionCode` (`1000001`)
- Namespace `git.artdeell.mojo` → `git.raufixteam.launcher` (Java sources + directories migrated)
- ApplicationId (candidate) `com.zyrox.launcher` → `com.raufixteam.launcher` — pending owner reverse-domain confirmation
- App name `app_short_name`: "RaufixTeam Launcher V1"
- User-facing Zyrox brand text replaced (fatal error, storage, notification permission, driver extra, brand title/subtitle/watermark)
- No Zyrox name/logo/icon/theme asset or Zyrox advertisement/sponsored placement in the user-facing app
- No ad SDKs, banners, interstitials, rewarded ads, or hidden tracking
- CurseForge API key removed from the client build (no `curseforge_api_key` resValue; no secret in APK/BuildConfig/resources/Git/logs)
- Upstream LGPL-3.0 copyright, NOTICE, and MojoLauncher/PojavLauncher/third-party attribution preserved

### Added (specified; not device-verified)

- 200-item RFX catalog in `docs/FEATURE_TRACEABILITY.md` (≥150 net-new capabilities; baseline items marked EXISTING)
- Renderer catalog with two net-new backends absent from baseline: **Vulkan Native** (`vulkan_native`) and **Software (LLVMpipe)** (`software_llvmpipe`) — EXPERIMENTAL / BLOCKED
- Real game-FPS measurement from frame-present timestamps with A/B protocol (RFX-101–120) — no claims without evidence
- Mandatory in-game recording (RFX-181–200): Recording Settings from gear/tools menu, 4 audio modes, clean game-surface capture, 144p–1080p + device-supported 4K+, no floating bubble, MediaStore/SAF library
- Docs suite: BRANDING_MIGRATION, LICENSES_AND_ATTRIBUTION, MODRINTH_CURSEFORGE, API_KEY_SECURITY, RENDERER_MATRIX, FPS_BENCHMARK_PROTOCOL, PRIVACY, API_COMPATIBILITY, QA_PLAN, TEST_RESULTS, KNOWN_LIMITS, GAME_RECORDING, GAME_SURFACE_CAPTURE, RECORDER_ENCODER_MATRIX, RECORDER_AUDIO_MODES, RECORDER_PRIVACY, RECORDER_BENCHMARKS

### Not changed (scoped separately)

- Internal `zyrox_*` resource IDs and "Zyrox"-prefixed class names left in place (references cannot be verified without a build); treated as a separate internal migration decision
- Zyrox scene binary assets not removed (pending owner confirmation)
