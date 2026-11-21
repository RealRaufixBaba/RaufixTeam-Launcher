# RaufixTeam Launcher V1

**RaufixTeam Launcher V1** (`com.raufixteam.launcher`, v`1.0.0` — `arm64-v8a`) is an open-source Android launcher for **Minecraft: Java Edition**, rebranded from the upstream MojoLauncher/PojavLauncher-derived project (LGPL-3.0). It focuses on security transparency, data safety, Modrinth discovery, CurseForge access only through an owner-operated gateway (no API key in the APK), renderer profiles, and an in-game recording system.

> **Product:** RaufixTeam Launcher V1  
> **versionName:** `1.0.0`  
> **versionCode:** monotonic (`1000001`)  
> **Namespace:** `git.raufixteam.launcher`  
> **ApplicationId (candidate):** `com.raufixteam.launcher` — pending owner-controlled reverse-domain confirmation  
> **License:** LGPL-3.0 (see [`LICENSE`](LICENSE)); upstream MojoLauncher/PojavLauncher/third-party attribution preserved  
> **Disclaimer:** Not an official Minecraft product. Not approved by or associated with Mojang AB or Microsoft Corporation.

## Status

**Release BLOCKED.** The sandbox has no JDK, Gradle, Android SDK, or NDK, so no APK was built, signed, or verified, and no test was executed. All Gradle tasks (`:app_mojo:testFullReleaseUnitTest`, `:app_mojo:lintFullRelease`, `:app_mojo:assembleFullRelease`, and all variants) are **BLOCKED / NOT RUN**. No PASS, FPS number, or benchmark is claimed.

See `docs/TEST_RESULTS.md` and `docs/KNOWN_LIMITS.md`.

## Rebranding (1.0.0)

- Product name exactly **RaufixTeam Launcher V1**; `versionName` exactly `1.0.0`; monotonic reproducible `versionCode`.
- No Zyrox name/logo/icon/theme asset or Zyrox advertisement/sponsored placement in the user-facing app.
- No ad SDKs, banners, interstitials, rewarded ads, or hidden tracking.
- CurseForge API key removed from the client build (no `curseforge_api_key` resValue; no secret in APK/BuildConfig/resources/Git/logs).
- Upstream LGPL-3.0 copyright, NOTICE, and MojoLauncher/PojavLauncher/third-party attribution preserved.

> `com.raufixteam.launcher` is a **candidate** app ID pending owner reverse-domain confirmation. Internal Java package migrated to `git.raufixteam.launcher`; JNI/native migration treated as a separate decision.

## Key capabilities (specified; not device-verified)

- Modrinth browse/search with real provider data; pagination, filters, sorting, project details, hashes, changelogs
- CurseForge browse/search **only when a gateway/key is configured**; otherwise NOT CONFIGURED / BLOCKED
- Installed-mod manager: enable/disable, bulk update, snapshot, rollback, quarantine
- Renderer catalog with 9 backends including two net-new (Vulkan Native, Software LLVMpipe); per-instance selection, pin, rollback, fallback
- Real game-FPS measurement from frame-present timestamps with A/B protocol (no claims without evidence)
- Mandatory in-game recording (RFX-181–200): 4 audio modes, clean surface capture, 144p–1080p + device-supported 4K+, no floating bubble, MediaStore/SAF library
- Accessibility/localization: TR/EN/AR, RTL, TalkBack, keyboard/gamepad

See `docs/FEATURE_TRACEABILITY.md` (all 200 RFX items), `docs/RENDERER_MATRIX.md`, `docs/FPS_BENCHMARK_PROTOCOL.md`, `docs/GAME_RECORDING.md`.

## Building & testing from source

### Prerequisites

- JDK 17
- Android SDK Platform 36, Build-Tools, NDK `29.0.0.14206865` (checkout specifies `29.0.14206865`)

> Not available in this sandbox; commands below cannot run here.

### Commands

```bash
# Run automated JUnit 4 unit test suite — BLOCKED (no JDK/Gradle)
./gradlew :app_mojo:testFullReleaseUnitTest

# Run Android Lint static analysis — BLOCKED
./gradlew :app_mojo:lintFullRelease

# Build dedicated arm64-v8a release APK — BLOCKED
./gradlew :app_mojo:assembleFullRelease
```

No APK download link is provided: no APK was built or signed in this release.

## Documentation

- [`docs/FEATURE_TRACEABILITY.md`](docs/FEATURE_TRACEABILITY.md) — all 200 RFX items with status/source/test/docs
- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) — package layout, module boundaries, data flows
- [`docs/BRANDING_MIGRATION.md`](docs/BRANDING_MIGRATION.md) — 1.0.0 rebrand record
- [`docs/LICENSES_AND_ATTRIBUTION.md`](docs/LICENSES_AND_ATTRIBUTION.md) — LGPL-3.0 + third-party attribution
- [`docs/MODRINTH_CURSEFORGE.md`](docs/MODRINTH_CURSEFORGE.md) — provider flows
- [`docs/API_KEY_SECURITY.md`](docs/API_KEY_SECURITY.md) — CurseForge key removed from APK
- [`docs/RENDERER_MATRIX.md`](docs/RENDERER_MATRIX.md) — renderer backends
- [`docs/FPS_BENCHMARK_PROTOCOL.md`](docs/FPS_BENCHMARK_PROTOCOL.md) — A/B FPS protocol
- [`docs/GAME_RECORDING.md`](docs/GAME_RECORDING.md) — recording system (RFX-181–200)
- [`docs/TEST_RESULTS.md`](docs/TEST_RESULTS.md) — all results NOT RUN
- [`docs/KNOWN_LIMITS.md`](docs/KNOWN_LIMITS.md)
- [`docs/QA_PLAN.md`](docs/QA_PLAN.md), [`docs/RELEASE_CHECKLIST.md`](docs/RELEASE_CHECKLIST.md)
- [`PRIVACY.md`](docs/PRIVACY.md), [`SECURITY.md`](SECURITY.md)
- [`PRIVACY_POLICY.md`](PRIVACY_POLICY.md), [`GPLAY_PRIVACY_POLICY.md`](GPLAY_PRIVACY_POLICY.md)
- [`CHANGELOG.md`](CHANGELOG.md) — 1.0.0 changelog

## Open-source credits & attribution

RaufixTeam Launcher V1 builds upon the open-source **MojoLauncher** and **PojavLauncher** projects and their contributors, licensed under **GNU LGPL v3.0**, alongside open-source graphics translators (GL4ES, LTW, Mesa/Zink, Freedreno/Turnip), OpenJDK mobile runtimes, Modrinth API, and CurseForge API. All upstream copyrights and licences remain respected and preserved; no upstream work is claimed as RaufixTeam authorship.
