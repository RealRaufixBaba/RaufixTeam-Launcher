# RaufixTeam Launcher V1 — Test Results

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Date:** 2026-11-21  
**Overall release status: BLOCKED**

## Build environment (hard constraint)

The sandbox provides **python3, node, npm only**. There is **no JDK, Gradle, Android SDK, or NDK**. Therefore:

- `./gradlew :app_mojo:testFullReleaseUnitTest` — **BLOCKED / NOT RUN**
- `:app_mojo:lintFullRelease` — **BLOCKED / NOT RUN**
- `:app_mojo:assembleFullRelease` — **BLOCKED / NOT RUN**
- All variant unit/instrumentation tests — **BLOCKED / NOT RUN**

**No APK was built, signed, or verified. No APK is reported.**

## Test-category results

| Category | Planned coverage | Result | Reason |
|----------|-----------------|--------|--------|
| Unit tests (JUnit) | Provider contracts, renderer specs, FPS stats, hash/security | NOT RUN | No JDK/Gradle |
| Instrumentation tests | UI flows, recording, A/B benchmark | NOT RUN | No Android SDK/device |
| API-fake / provider-contract tests | Modrinth + CurseForge (mock server) | NOT RUN | No build |
| Renderer tests | 9 backends incl. 2 net-new | NOT RUN | No device/GPU |
| FPS / A/B benchmark tests | RFX-101–120 | NOT RUN | No device; no evidence |
| Accessibility tests | TalkBack/keyboard/large-text/RTL | NOT RUN | No device |
| JNI / native tests | Native library hooks | NOT RUN | No NDK |
| Fuzz / security tests | ZIP-slip, zip bombs, archive safety | NOT RUN | No build |
| Licence / secret-scan tests | No CurseForge key in APK; no ads | NOT RUN | No build (grep audit only) |
| Install / upgrade tests | Migration, SAF export-import | NOT RUN | No device |
| Recording acceptance (REC-QA-01…12) | RFX-181–200 | NOT RUN | No device/encoder |

## Static verification performed (no build environment required)

The following checks were executed with the available tooling (Python/Node/grep). These are **static verifications, not test executions** — they validate source consistency but do not constitute PASS results for the QA matrix.

| Check | Result | Detail |
|-------|--------|--------|
| Secret scan (no CurseForge key in APK/Git/logs) | PASS | `curseforge_api_key` resValue removed; no `CURSEFORGE_API_KEY` env read; no `curseforge_key.txt`; no secrets in source |
| `R.string.curseforge_api_key` references fixed | PASS | `SearchModFragment` passes `null` → CurseForge correctly NOT CONFIGURED (RFX-049) |
| Package migration `git.artdeell.mojo` → `git.raufixteam.launcher` | PASS | Main + test source trees fully migrated; zero stale refs |
| Namespace `git.raufixteam.launcher` | PASS | `build.gradle` namespace + applicationId consistent |
| `versionName = 1.0.0` | PASS | `getVersionName()` returns `"1.0.0"` |
| Monotonic `versionCode` | PASS | `getVersionCode()` returns `1000001` (not date-based) |
| XML well-formedness | PASS | All `strings.xml` (all locales), `AndroidManifest.xml`, `styles.xml`, `colors.xml`, new `fragment_recorder_settings.xml` parse cleanly |
| Brace balance (all new/modified Java) | PASS | 10 Java files balanced |
| No user-facing Zyrox text | PASS | Zero `>Zyrox`/`>ZYROX` string values across all locale files |
| 200 RFX traceability entries | PASS | `docs/FEATURE_TRACEABILITY.md` has all 200 RFX items (64 EXISTING, 128 NEW incl. 8 gated, 1 BLOCKED) |
| 22 required docs present | PASS | All mandated docs present |
| 2 net-new renderer backends | PASS | `VulkanNativeRenderSpec`, `SoftwareRenderSpec` wired into `GameRenderer` + `RendererCache` |
| FPS monitor from frame-present timestamps | PASS | `FpsMonitor.java` hooked into `GameView.onSurfaceFrameUpdated` |
| Recording system source | PASS | `RecorderController`, `RecorderSettingsFragment`, `RecorderEncoderMatrix`, `RecorderAudioMixer` + layout + gear menu entry |

## Evidence rule

**Writing a scenario is not PASS; no success is claimed without execution evidence.** Every result in the Test-category table above is `NOT RUN` (requires a device/build environment). No PASS, FPS number, or benchmark is fabricated for actual test execution.

## Release gate

Per the final delivery gate, release is **BLOCKED** until: identity/version (1.0.0), original icon/theme, no-ads audit, licence attribution, Modrinth+CurseForge flows, mod lifecycle, two genuine new renderers, and evidenced FPS benchmark are accepted — all requiring a device-verified build that this sandbox cannot produce.

## Unblock path (no build claimed yet)

The repository is now CI-ready so the build can be executed outside this sandbox:

- **Gradle wrapper** added (`gradlew`, `gradle/wrapper/gradle-wrapper.properties` → Gradle 8.14.5). `./gradlew` auto-downloads the Gradle distribution on first run.
- **Four missing submodules added** (`glfw`, `mojoexec`, `sdl`/MojoSDL, `NoBlocklist`) at their upstream commits, providing `:glfw:jni_bindings`, `:mojoexec:jni_bindings`, `:sdl:jni_bindings` and `:NoBlocklist`.
- **GitHub Actions workflow** added at `.github/workflows/android-ci.yml`:
  - `build-test` job: JDK 17 → Android SDK Platform 36 → NDK 29.0.14206865 → `:app_mojo:testFullReleaseUnitTest` → `:app_mojo:lintFullRelease` → `:app_mojo:assembleFullRelease`, uploading test reports, lint reports, and the release APK artifact.
  - `sign-release` job: triggers on `v*` tag push, signs the release APK when `RAUFIXTEAM_*` secrets are configured.
- **Still required to unblock:** run the `build-test` workflow on GitHub Actions (or locally on a PC with JDK 17 + Android SDK Platform 36 + NDK 29.0.14206865), then execute the device tests (2 renderers, recording RFX-181–200, A/B FPS RFX-101–105) and write evidenced results here. Nothing in this section is a PASS — every category above remains `NOT RUN` until executed with evidence.
