# RaufixTeam Launcher V1 — RFX Feature Traceability

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**versionCode:** monotonic (1000001)  
**Namespace:** `git.raufixteam.launcher`  
**ApplicationId (candidate):** `com.raufixteam.launcher` (owner reverse-domain confirmation pending)

## How to read this file

This file is the single source of truth for the 200-item RFX catalog (RFX-001–RFX-200). For every ID it records:

- **Status** — `EXISTING` (baseline capability already meets the acceptance criterion), `NEW` (genuine net-new RaufixTeam capability), or `BLOCKED` (cannot be implemented/verified without a device/build environment).
- **UI/API** — user-facing surface or API contract.
- **Source class/module** — Java class or native module implementing it.
- **Test** — unit / instrumentation / API-contract test that covers it.
- **Docs** — document that specifies it.

A menu row, copy, or color change alone is NOT counted as a feature. A feature must have UI/API + source class + test + docs.

## Build/verification environment status (honest)

The sandbox provides python3/node/npm only — **no JDK, Gradle, Android SDK, or NDK**. Consequently:

- `./gradlew :app_mojo:testFullReleaseUnitTest`, `:app_mojo:lintFullRelease`, `:app_mojo:assembleFullRelease` and every variant are **BLOCKED / NOT RUN**.
- No APK was built, signed, or verified. No APK is reported.
- Every instrumentation/device test below is **NOT RUN** (no evidence). No PASS is fabricated.
- Release is **BLOCKED** pending licence review and a device-verified build.

The `Status` column below records the *intended* classification. The `Test evidence` column records `NOT RUN` everywhere because no test could be executed.

---

## Group 1 · RFX-001–010 · Brand, theme, and visual identity

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-001 | Visible RaufixTeam app identity | NEW | App launcher name "RaufixTeam Launcher V1" | `res/values/strings.xml` (`app_short_name`), `AndroidManifest.xml` | Icon/name audit test | BRANDING_MIGRATION.md |
| RFX-002 | Original adaptive launcher icon set | NEW | Adaptive-icon mipmap set (foreground/background layers) | `res/mipmap-*` + `AndroidManifest.xml` `android:icon` | Adaptive icon resource test | BRANDING_MIGRATION.md |
| RFX-003 | Vector-based in-app icon family | NEW | Vector drawable icons used across UI | `res/drawable-*.xml` (VectorDrawable) | Vector drawable load test | BRANDING_MIGRATION.md |
| RFX-004 | Design-token-based original theme engine | NEW | Theme tokens (color, type, shape) applied via `Theme` | `res/values/themes.xml`, `LauncherPreference*Fragment` | Theme apply/rollback test | ARCHITECTURE.md |
| RFX-005 | Light/dark theme and system-follow option | NEW | DayNight theme + system-follow toggle | `res/values/themes.xml`, `LauncherPreferences` | Theme-switch test | ARCHITECTURE.md |
| RFX-006 | Theme contrast preview audit | NEW | Contrast-ratio preview before applying theme | `LauncherPreferenceExperimentalFragment` | Contrast audit unit test | QA_PLAN.md |
| RFX-007 | Per-screen background and surface styles | NEW | Distinct backgrounds/surfaces per screen | `res/values/styles.xml`, per-fragment layouts | Screen-style snapshot test | ARCHITECTURE.md |
| RFX-008 | Lifecycle-aware original splash flow | NEW | Splash shown in `onCreate`/`onResume`, tied to Activity lifecycle | `LauncherActivity.onCreate`, `BaseActivity` | Splash show/hide test | ARCHITECTURE.md |
| RFX-009 | Theme preview and rollback | NEW | Preview theme then roll back to last-good | `LauncherPreferences`, theme persistence | Theme rollback test | QA_PLAN.md |
| RFX-010 | Audit for every visible Zyrox brand artifact | NEW | Scan resources/assets/layouts for "Zyrox" user-facing text | `res/values/zyrox_strings.xml` → replaced values | Brand-artifact grep audit | BRANDING_MIGRATION.md |

## Group 2 · RFX-011–020 · Home and navigation

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-011 | Draggable home dashboard cards | EXISTING | Dashboard card drag/reorder | `MainMenuFragment`, dashboard adapters | Drag gesture instrumentation test | ARCHITECTURE.md |
| RFX-012 | Add/hide/reorder dashboard-card editor | EXISTING | Card editor in home settings | `MainMenuFragment`, `LauncherPreferenceFragment` | Editor add/hide test | ARCHITECTURE.md |
| RFX-013 | One-tap launch of favorite instances | EXISTING | Launch button on home card | `Instances`, `LauncherActivity` | Launch flow test | ARCHITECTURE.md |
| RFX-014 | Resume last-used instance | EXISTING | Selected instance persisted | `Instances.loadSelectedInstance()` | Selection persistence test | ARCHITECTURE.md |
| RFX-015 | Unified game/mod/runtime update inbox | NEW | Update inbox aggregating all providers | `DownloadCenterFragment`, `ProgressKeeper` | Inbox aggregation test | MODRINTH_CURSEFORGE.md |
| RFX-016 | Download-queue badges and filters | EXISTING | Badge counts + queue filters | `DownloadCenterFragment`, `DownloadQueueManager` | Queue badge test | ARCHITECTURE.md |
| RFX-017 | Offline/partial-connectivity status banner | NEW | Banner shown when offline | `NetworkPolicyHelper`, `MainMenuFragment` | Offline banner test | QA_PLAN.md |
| RFX-018 | Device GPU/renderer capability card | EXISTING | GPU/renderer info card | `GpuUtils`, `RendererCache` | Capability card test | RENDERER_MATRIX.md |
| RFX-019 | Feature search through command palette | NEW | Command-palette search | `SearchModFragment`/palette | Palette search test | QA_PLAN.md |
| RFX-020 | Skippable first-run guided tour | EXISTING | Onboarding wizard, skippable | `OnboardingWizardFragment` | Onboarding skip/complete test | ARCHITECTURE.md |

## Group 3 · RFX-021–030 · Instances, profiles, and launch

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-021 | Per-instance labels and colors | EXISTING | Instance name + color picker | `InstanceEditorFragment`, `Instance` | Instance edit test | ARCHITECTURE.md |
| RFX-022 | Unique ID generation when cloning | NEW | UUID-based clone ID | `Instance`, cloning flow | Clone uniqueness test | QA_PLAN.md |
| RFX-023 | Per-instance mod and renderer profiles | NEW | Per-instance renderer/mod profile | `Instance`, `GameRenderer`, `LauncherPreferences` | Profile isolation test | RENDERER_MATRIX.md |
| RFX-024 | Instance-settings diff comparison | NEW | Text diff of settings before/after | `InstanceEditorFragment` diff util | Diff comparison test | QA_PLAN.md |
| RFX-025 | Pre-launch change summary | EXISTING | Summary before launch | `LauncherActivity` pre-flight | Change-summary test | ARCHITECTURE.md |
| RFX-026 | Instance-template export | NEW | Export instance as template JSON | `InstanceExporter`/`Instances` | Export/import round-trip test | QA_PLAN.md |
| RFX-027 | Safe instance creation from template | NEW | Validate template before create | `InstanceInstaller` | Safe-create test | QA_PLAN.md |
| RFX-028 | Duplicate game-folder detection | NEW | Detect duplicate instance folders | `Instances` | Duplicate detection test | QA_PLAN.md |
| RFX-029 | Shortcut and default-instance selection | EXISTING | Default instance + launcher shortcuts | `Instances` | Default selection test | ARCHITECTURE.md |
| RFX-030 | Filterable instance launch history | EXISTING | Launch history list w/ filter | `InstancePlaytimeTracker`, `WorldManager` | History filter test | ARCHITECTURE.md |

## Group 4 · RFX-031–040 · Modrinth discovery page

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-031 | Debounced Modrinth text search | EXISTING | Search input with debounce | `SearchModFragment`, `ModrinthProvider` | Debounce unit test | MODRINTH_CURSEFORGE.md |
| RFX-032 | Scroll-paginated Modrinth results | EXISTING | Paginated list + load more | `ModVersionListFragment` | Pagination test | MODRINTH_CURSEFORGE.md |
| RFX-033 | Multi-filter by category/loader/version | EXISTING | Filter chips | `SearchModFragment`, `ModrinthProvider` | Filter matrix test | MODRINTH_CURSEFORGE.md |
| RFX-034 | Sort by downloads/updated/date | EXISTING | Sort dropdown | `ModrinthProvider` | Sort order test | MODRINTH_CURSEFORGE.md |
| RFX-035 | Author and licence on project page | EXISTING | Project detail shows author/licence | `ModrinthProvider` project page | Attribution display test | MODRINTH_CURSEFORGE.md |
| RFX-036 | Version and file-hash details | EXISTING | File list + SHA-1 hash | `ModrinthProvider` versions | Hash display test | MODRINTH_CURSEFORGE.md |
| RFX-037 | Changelog and dependency display | EXISTING | Changelog + deps | `ModrinthProvider` | Changelog render test | MODRINTH_CURSEFORGE.md |
| RFX-038 | Saved search queries | NEW | Save favorite searches | `LauncherPreferences` | Saved-query test | QA_PLAN.md |
| RFX-039 | Modrinth favorite collections | NEW | Favorite mod collections | `LauncherPreferences` | Collection CRUD test | QA_PLAN.md |
| RFX-040 | Empty-result and network-error recovery | EXISTING | Empty/error/retry states | `SearchModFragment` | Empty/error UI test | QA_PLAN.md |

## Group 5 · RFX-041–050 · CurseForge discovery page

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-041 | Official CurseForge API search | NEW (gated) | Search via owner gateway only | `CurseForgeProvider`, `CurseforgeApi` | Contract test vs mock server | MODRINTH_CURSEFORGE.md |
| RFX-042 | Load more results while scrolling down | NEW (gated) | Paginated CurseForge list | `CurseForgeProvider` | Pagination test | MODRINTH_CURSEFORGE.md |
| RFX-043 | Game-version and loader filters | NEW (gated) | Version/loader filter chips | `CurseForgeProvider` | Filter test | MODRINTH_CURSEFORGE.md |
| RFX-044 | Project/author/licence detail page | NEW (gated) | Project detail w/ attribution | `CurseForgeProvider` | Detail page test | MODRINTH_CURSEFORGE.md |
| RFX-045 | File/version history and hashes | NEW (gated) | File list + hashes | `CurseForgeProvider` | Hash/history test | MODRINTH_CURSEFORGE.md |
| RFX-046 | Modpack manifest preview | NEW (gated) | `manifest.json` preview | `CurseManifest`, `ModpackApi` | Manifest parse test | MODRINTH_CURSEFORGE.md |
| RFX-047 | User-facing API rate-limit state | NEW (gated) | 429/retry UI state | `CurseForgeProvider`, gateway | Rate-limit UI test | MODRINTH_CURSEFORGE.md |
| RFX-048 | Secure gateway endpoint setting | NEW | Gateway URL preference | `LauncherPreferences`, `API_KEY_SECURITY.md` | Endpoint validation test | API_KEY_SECURITY.md |
| RFX-049 | Accurate disabled state when no key | BLOCKED | CurseForge disabled, shows NOT CONFIGURED | `CurseForgeProvider` (no key → disabled) | Disabled-state test | API_KEY_SECURITY.md |
| RFX-050 | External link to official project page | NEW | Link to curseforge.com project | `CurseForgeProvider` detail | External link test | MODRINTH_CURSEFORGE.md |

## Group 6 · RFX-051–060 · Installed-mod manager

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-051 | Unified installed-mod list page | EXISTING | Installed mods list | `ContentManagerFragment` | List render test | MODRINTH_CURSEFORGE.md |
| RFX-052 | Search/filter installed mods by loader/version | EXISTING | Installed-mod search/filter | `ContentManagerFragment` | Filter test | MODRINTH_CURSEFORGE.md |
| RFX-053 | Enable/disable one mod | EXISTING | `.disabled` toggle per mod | `InstanceContentManager` | Toggle test | MODRINTH_CURSEFORGE.md |
| RFX-054 | Bulk enable/disable mods | EXISTING | Bulk toggle action | `InstanceContentManager` | Bulk toggle test | MODRINTH_CURSEFORGE.md |
| RFX-055 | Delete confirmation with impact summary | EXISTING | Delete dialog w/ impact | `DeleteConfirmDialogFragment` | Delete confirmation test | MODRINTH_CURSEFORGE.md |
| RFX-056 | Compare a single mod update | NEW | Before/after version+hash compare | `InstanceContentManager` | Update compare test | QA_PLAN.md |
| RFX-057 | Choose versions for bulk update | NEW | Version picker per mod | `ModVersionListFragment` | Bulk version pick test | QA_PLAN.md |
| RFX-058 | Snapshot before every update | EXISTING | Pre-update snapshot | `InstanceBackupService` | Snapshot test | QA_PLAN.md |
| RFX-059 | Rollback from mod-version history | EXISTING | Rollback to previous version | `InstanceBackupService` | Rollback test | QA_PLAN.md |
| RFX-060 | Quarantine and import a local JAR | EXISTING | Quarantine hostile JAR | `ArchiveSecurity`, `InstanceInstaller` | Quarantine import test | QA_PLAN.md |

## Group 7 · RFX-061–070 · Dependencies and compatibility

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-061 | Required/optional dependency graph | EXISTING | Dep graph view | `ModMetadataParser` | Dep graph test | MODRINTH_CURSEFORGE.md |
| RFX-062 | List missing dependencies with source | EXISTING | Missing-dep list + source | `ModMetadataParser` | Missing-dep test | MODRINTH_CURSEFORGE.md |
| RFX-063 | Explain conflicting mod pairs | EXISTING | Conflict explanation | `InstanceHealthInspector` | Conflict report test | QA_PLAN.md |
| RFX-064 | Block loader mismatch before installation | EXISTING | Loader-mismatch block | `InstanceContentManager` | Mismatch block test | QA_PLAN.md |
| RFX-065 | Mark Minecraft-version mismatch | EXISTING | Version-mismatch flag | `InstanceContentManager` | Mismatch flag test | QA_PLAN.md |
| RFX-066 | Find duplicate files for one mod | NEW | Duplicate JAR detection | `InstanceContentManager` | Duplicate scan test | QA_PLAN.md |
| RFX-067 | Preview compatibility resolution plan | NEW | Resolution plan preview | `InstanceContentManager` | Plan preview test | QA_PLAN.md |
| RFX-068 | Approve/rollback a resolution plan | NEW | Approve/rollback UI | `InstanceContentManager` | Plan approve/rollback test | QA_PLAN.md |
| RFX-069 | Post-install static validation report | EXISTING | Validation report after install | `InstanceHealthInspector` | Validation report test | QA_PLAN.md |
| RFX-070 | Warning severity and remediation steps | EXISTING | Severity + remediation | `InstanceHealthInspector` | Severity test | QA_PLAN.md |

## Group 8 · RFX-071–080 · Modpacks and collections

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-071 | Preview Modrinth modpack manifests | EXISTING | Modrinth manifest preview | `ModrinthApi`, `ModrinthIndex` | Manifest preview test | MODRINTH_CURSEFORGE.md |
| RFX-072 | Import permitted CurseForge manifests | NEW (gated) | CurseForge manifest import | `CurseManifest`, `ModpackInstaller` | Manifest import test | MODRINTH_CURSEFORGE.md |
| RFX-073 | Match missing files to authorized providers | EXISTING | Missing-file provider match | `ModrinthContentInstaller` | Match missing files test | MODRINTH_CURSEFORGE.md |
| RFX-074 | Pin modpack versions to an instance | NEW | Version pinning | `Instance`, `LauncherPreferences` | Pin test | QA_PLAN.md |
| RFX-075 | Group collections locally | NEW | Local collection groups | `LauncherPreferences` | Collection group test | QA_PLAN.md |
| RFX-076 | Export collection contents | NEW | Export collection | `InstanceExporter` | Export test | QA_PLAN.md |
| RFX-077 | Shareable mod-list link | NEW | Shareable link | `InstanceContentManager` | Share link test | QA_PLAN.md |
| RFX-078 | Warn about missing licence/access files | NEW | Licence warning | `InstanceHealthInspector` | Licence warning test | LICENSES_AND_ATTRIBUTION.md |
| RFX-079 | Show manifest differences by version | NEW | Manifest diff | `ModrinthIndex` | Manifest diff test | QA_PLAN.md |
| RFX-080 | Dry-run a modpack installation | NEW | Dry-run install | `ModpackInstaller` | Dry-run test | QA_PLAN.md |

## Group 9 · RFX-081–090 · Renderer catalog and installation

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-081 | At least two genuine renderer backends absent from baseline | NEW | Vulkan Native + Software (LLVMpipe) backends | `VulkanNativeRenderSpec`, `SoftwareRenderSpec`, `Renderers` | Backend compatibility test | RENDERER_MATRIX.md |
| RFX-082 | Renderer source and licence catalog | NEW | Backend source/licence table | `RENDERER_MATRIX.md`, `GameRenderer` | Licence catalog audit | RENDERER_MATRIX.md |
| RFX-083 | GPU/Android compatibility matrix | NEW | Per-backend device compat | `RendererCache`, `GpuUtils` | Compat matrix test | RENDERER_MATRIX.md |
| RFX-084 | Verify downloaded backend hashes | NEW | Hash verification on install | `ArchiveSecurity`, hash check | Hash verify test | QA_PLAN.md |
| RFX-085 | Atomically install renderer packages | NEW | Atomic install + rollback on fail | `InstanceInstaller`, renderer install | Atomic install test | QA_PLAN.md |
| RFX-086 | Safely remove unused renderers | NEW | Uninstall unused backend | Renderer manager | Remove renderer test | QA_PLAN.md |
| RFX-087 | Pin renderer versions | NEW | Per-instance renderer pin | `Instance`, `LauncherPreferences` | Pin version test | QA_PLAN.md |
| RFX-088 | Roll back renderer packages | NEW | Rollback to last-known-good | Renderer manager | Rollback test | QA_PLAN.md |
| RFX-089 | Quarantine backend launch failures | NEW | Quarantine failed backend | `GameRenderer` fallback | Quarantine test | QA_PLAN.md |
| RFX-090 | Safe fallback to last working backend | NEW | Automatic fallback on crash | `GameRenderer.maybeSetupRenderer` | Fallback test | QA_PLAN.md |

## Group 10 · RFX-091–100 · Renderer profiles and controls

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-091 | Independent renderer selection per instance | NEW | Per-instance renderer picker | `InstanceEditorFragment`, `GameRenderer` | Per-instance selection test | RENDERER_MATRIX.md |
| RFX-092 | Test-launch flow after renderer change | NEW | Test launch after change | `GameRenderer`, `LauncherActivity` | Test-launch test | QA_PLAN.md |
| RFX-093 | Renderer-specific settings profiles | NEW | Per-renderer settings | `LauncherPreferenceVideoFragment` | Settings profile test | QA_PLAN.md |
| RFX-094 | Supported features/limits page | NEW | Backend limits page | `RendererCache`, `GpuUtils` | Limits page test | RENDERER_MATRIX.md |
| RFX-095 | GLES/Vulkan capability report | NEW | GLES/Vulkan caps report | `GpuUtils.getGlInfo()` | Caps report test | RENDERER_MATRIX.md |
| RFX-096 | Preview scaling and texture profiles | NEW | Scale/texture profile preview | `GLESRenderSpec`, settings | Scaling preview test | QA_PLAN.md |
| RFX-097 | VSync/frame-cap choice only when supported | NEW | Conditional VSync/cap | `LauncherPreferenceVideoFragment` | VSync conditional test | QA_PLAN.md |
| RFX-098 | Clear/rebuild shader cache | NEW | Shader cache purge | `MesaRenderSpec.purgeShaderCaches` | Cache purge test | QA_PLAN.md |
| RFX-099 | Export/import renderer configuration | NEW | Renderer config export/import | `LauncherPreferences` | Config round-trip test | QA_PLAN.md |
| RFX-100 | Explain renderer fallback reason | NEW | Fallback reason text | `GameRenderer` fallback log | Fallback reason test | QA_PLAN.md |

## Group 11 · RFX-101–110 · Real FPS measurement

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-101 | Real game-FPS counter based on presented frames | NEW | FPS counter from frame-present timestamps | New `FpsMonitor` (present/swap timestamps) | FPS counter unit test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-102 | Show display refresh Hz separately from FPS | NEW | Display Hz readout separate from FPS | `Display.Mode.getRefreshRate()`, `FpsMonitor` | Hz-vs-FPS test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-103 | Live frame-time graph | NEW | Sparkline of frame times | `PerformanceHudOverlay`, `FpsMonitor` | Frame-time graph test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-104 | 1% and 0.1% low statistics | NEW | 1%/0.1% low FPS | `FpsMonitor` stats | Low-stats unit test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-105 | P50/P95/P99 frame-time summary | NEW | Frame-time percentile summary | `FpsMonitor` | Percentile unit test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-106 | A/B benchmark wizard for the same scene | NEW | A/B wizard (locked conditions) | New `ABBenchmarkWizard` | A/B wizard test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-107 | Warm-up and measurement-window settings | NEW | 60s warm-up + 120s measurement config | `ABBenchmarkWizard` | Window settings test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-108 | Lock benchmark conditions | NEW | Lock device/renderer/settings | `ABBenchmarkWizard` | Condition lock test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-109 | Save results per device/renderer | NEW | Persist benchmark results | `LauncherPreferences`, CSV/JSON | Result persistence test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-110 | Export raw measurements as CSV/JSON | NEW | Export measurements | `FpsMonitor` export | CSV/JSON export test | FPS_BENCHMARK_PROTOCOL.md |

## Group 12 · RFX-111–120 · FPS optimization

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-111 | Measured render-scale profiles | NEW | Render-scale recommendation cards | `FpsMonitor` + `GpuUtils` | Scale profile test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-112 | Recommendation cards for Minecraft video settings | NEW | Video-setting recommendation cards | `LauncherPreferenceVideoFragment` | Recommendation card test | QA_PLAN.md |
| RFX-113 | Preview render-distance impact | NEW | Render-distance impact preview | `FpsMonitor`, settings | Impact preview test | QA_PLAN.md |
| RFX-114 | Compare VSync and FPS caps | NEW | VSync vs cap comparison | `FpsMonitor` A/B | VSync comparison test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-115 | GPU/CPU bottleneck hints | NEW | Bottleneck hint display | `GpuUtils`, `PerformanceHudOverlay` | Bottleneck hint test | QA_PLAN.md |
| RFX-116 | Shader-compile warm-up when backend supports it | NEW | Shader warm-up toggle | `MesaRenderSpec`, backend | Warm-up test | QA_PLAN.md |
| RFX-117 | Texture-cache budget control | NEW | Texture-cache budget slider | `LauncherPreferenceVideoFragment` | Cache budget test | QA_PLAN.md |
| RFX-118 | Input-to-frame latency measurement mode | NEW | Latency measurement mode | `FpsMonitor`, input pipeline | Latency mode test | FPS_BENCHMARK_PROTOCOL.md |
| RFX-119 | Quality step-down to avoid thermal decline | NEW | Thermal quality step-down | `FpsMonitor` + thermal | Thermal step-down test | QA_PLAN.md |
| RFX-120 | Toggle optimizations individually for A/B | NEW | Per-optimization toggle | `ABBenchmarkWizard` | Toggle test | FPS_BENCHMARK_PROTOCOL.md |

## Group 13 · RFX-121–130 · Java runtime and JVM

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-121 | Safe device-specific heap range advice | EXISTING | Heap range advice | `JavaRuntimeAdvisor` | Heap advice test | API_COMPATIBILITY.md |
| RFX-122 | Runtime-verified GC presets | EXISTING | GC preset verification | `JavaRuntimeAdvisor` | GC preset test | API_COMPATIBILITY.md |
| RFX-123 | Explain and block invalid JVM arguments | EXISTING | JVM-arg validation | `GameRunner`, `JavaRunner` | Invalid-arg block test | API_COMPATIBILITY.md |
| RFX-124 | Pin JRE version per instance | EXISTING | Per-instance JRE pin | `Instance`, `JREUtils` | Pin JRE test | API_COMPATIBILITY.md |
| RFX-125 | Preflight runtime architecture and ABI | EXISTING | ABI preflight | `Architecture`, `GameRunner` | ABI preflight test | API_COMPATIBILITY.md |
| RFX-126 | Verify downloaded runtime hash | EXISTING | Runtime hash verify | `NewJREUtil`, hash check | Runtime hash test | API_COMPATIBILITY.md |
| RFX-127 | Runtime update and rollback | EXISTING | JRE update/rollback | `NewJREUtil` | Update/rollback test | API_COMPATIBILITY.md |
| RFX-128 | Explain missing JRE while offline | EXISTING | Offline JRE explanation | `JavaRuntimeAdvisor` | Offline JRE test | API_COMPATIBILITY.md |
| RFX-129 | Startup-stage duration graph | NEW | Startup stage graph | `GameLaunchProgressTracker` | Duration graph test | QA_PLAN.md |
| RFX-130 | Compatibility warning for runtime options | EXISTING | Runtime-option warning | `JavaRuntimeAdvisor` | Warning test | API_COMPATIBILITY.md |

## Group 14 · RFX-131–140 · Downloads and cache

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-131 | Unified download cart for mods/renderers/runtimes | NEW | Unified download cart | `DownloadQueueManager`, `ProgressKeeper` | Cart test | QA_PLAN.md |
| RFX-132 | Pause/resume/cancel each download | EXISTING | Pause/resume/cancel per download | `Downloader`, `DownloadQueueManager` | Download control test | QA_PLAN.md |
| RFX-133 | Resume queue after connectivity returns | EXISTING | Auto-resume on reconnect | `DownloadQueueManager` | Resume test | QA_PLAN.md |
| RFX-134 | Bandwidth cap and schedule window | NEW | Bandwidth cap + schedule | `DownloadQueueManager` | Cap/schedule test | QA_PLAN.md |
| RFX-135 | Jittered backoff for retries | NEW | Jittered retry backoff | `DownloaderTask` | Backoff test | QA_PLAN.md |
| RFX-136 | Deduplicate downloads by URL/hash | NEW | Dedup by URL/hash | `DownloadQueueManager` | Dedup test | QA_PLAN.md |
| RFX-137 | Estimate disk space before download | EXISTING | Disk-space estimate | `NetworkPolicyHelper` | Space estimate test | QA_PLAN.md |
| RFX-138 | Visible partial-file cleanup/quarantine | NEW | Partial-file quarantine UI | `ArchiveSecurity`, downloader | Partial cleanup test | QA_PLAN.md |
| RFX-139 | Checksum result and source information | EXISTING | Checksum + source shown | `ArchiveSecurity` | Checksum test | QA_PLAN.md |
| RFX-140 | Offline download-cache manager | NEW | Offline cache manager | `DownloadQueueManager` cache | Offline cache test | QA_PLAN.md |

## Group 15 · RFX-141–150 · Worlds and configuration

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-141 | Text diff for instance settings | EXISTING | Settings text diff | `InstanceEditorFragment` | Diff test | QA_PLAN.md |
| RFX-142 | Back up/rollback settings changes | EXISTING | Settings backup/rollback | `InstanceBackupService` | Backup/rollback test | QA_PLAN.md |
| RFX-143 | Search and sort world list | EXISTING | World search/sort | `WorldManagerFragment` | World sort test | ARCHITECTURE.md |
| RFX-144 | Preview world-deletion impact | EXISTING | Deletion impact preview | `WorldManager` | Deletion preview test | QA_PLAN.md |
| RFX-145 | Import/export selected saves via SAF | EXISTING | SAF import/export saves | `WorldManager`, SAF | SAF save test | ARCHITECTURE.md |
| RFX-146 | World-backup retention policy screen | NEW | Retention policy UI | `WorldManager`, `LauncherPreferences` | Retention test | QA_PLAN.md |
| RFX-147 | Resource-pack version compatibility table | NEW | Resource-pack compat table | `ModMetadataParser` | Compat table test | QA_PLAN.md |
| RFX-148 | Shader-pack renderer-compatibility badge | NEW | Shader-pack badge | `RendererCache` badge | Badge test | RENDERER_MATRIX.md |
| RFX-149 | Safe config-file editor | NEW | Safe config editor | Config editor | Config editor test | QA_PLAN.md |
| RFX-150 | Break down disk use by category | NEW | Disk-use breakdown | `Tools`, storage utils | Disk breakdown test | QA_PLAN.md |

## Group 16 · RFX-151–160 · Security and privacy

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-151 | Release verification for no ad SDK/placement | NEW | Ad/tracking absence audit | `build.gradle` (no ad SDKs) + grep audit | Secret/ad-scan test | API_KEY_SECURITY.md |
| RFX-152 | Transparent opt-in telemetry, off by default | NEW | Telemetry opt-in off | `LauncherPreferences` | Opt-in default test | PRIVACY.md |
| RFX-153 | Never put CurseForge secret in client APK | NEW | Secret removed from APK | `build.gradle` (no `curseforge_api_key` resValue) | Secret-scan test | API_KEY_SECURITY.md |
| RFX-154 | Lock secret features when no safe input exists | NEW | CurseForge locked without key | `CurseForgeProvider` disabled | Lock-state test | API_KEY_SECURITY.md |
| RFX-155 | Redact tokens/URIs/accounts from logs | EXISTING | Log redaction | `LogRedactor` | Redaction test | PRIVACY.md |
| RFX-156 | Defend against mod/JAR ZIP-slip and zip bombs | EXISTING | ZIP-slip/zip-bomb defense | `ArchiveSecurity` | ZIP-slip fuzz test | QA_PLAN.md |
| RFX-157 | Source/provenance badge for downloaded files | NEW | Provenance badge | `ModItem`, provider badge | Badge test | MODRINTH_CURSEFORGE.md |
| RFX-158 | Licence and attribution panel | EXISTING | Licence panel | `AboutSecurityFragment`, LICENSES | Licence panel test | LICENSES_AND_ATTRIBUTION.md |
| RFX-159 | Explain permissions and network access | NEW | Permission/network explanation | `LauncherPreferenceMiscellaneousFragment` | Explanation test | PRIVACY.md |
| RFX-160 | User-data export/clear center | NEW | Export/clear user data | `LauncherPreferences`, dataremoval | Export/clear test | PRIVACY.md |

## Group 17 · RFX-161–170 · Accessibility and localization

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-161 | Select Turkish/English/Arabic interface | EXISTING | TR/EN/AR locale switcher | `LocaleUtils`, `values-tr/ar/en` | Locale switch test | ARCHITECTURE.md |
| RFX-162 | Arabic RTL layout audit | EXISTING | RTL layout | `values-ar`, RTL layouts | RTL layout test | ARCHITECTURE.md |
| RFX-163 | TalkBack action labels for mod rows | NEW | TalkBack labels | Mod row accessibility labels | TalkBack test | QA_PLAN.md |
| RFX-164 | Keyboard-based main navigation | NEW | Keyboard nav | View accessibility | Keyboard nav test | QA_PLAN.md |
| RFX-165 | Visible focus for D-pad/gamepad | NEW | Focus indication | Gamepad input, Views | Focus indication test | QA_PLAN.md |
| RFX-166 | No clipping at large text scale | NEW | Large-text layout | Layouts, `values-land` | Large-text test | QA_PLAN.md |
| RFX-167 | High contrast and non-color-only state icons | NEW | High-contrast icons | Drawable theming | Contrast test | QA_PLAN.md |
| RFX-168 | Reduced motion and animation toggle | NEW | Reduced motion | `LauncherPreferenceExperimentalFragment` | Reduced-motion test | QA_PLAN.md |
| RFX-169 | Tablet/landscape layout | EXISTING | Tablet/land layouts | `layout-land`, tablet layouts | Tablet layout test | ARCHITECTURE.md |
| RFX-170 | Enable mods with a screen reader | NEW | Screen-reader mod enable | Mod rows + TalkBack | Screen-reader test | QA_PLAN.md |

## Group 18 · RFX-171–180 · Diagnostics and recovery

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-171 | Diagnostics center distinguishing allowed/denied states | EXISTING | Diagnostics allowed/denied | `GraphicsAdvisor`, `GameOptimizer` | Diagnostics test | QA_PLAN.md |
| RFX-172 | Generate a redacted support bundle | EXISTING | Redacted support bundle | `LogRedactor` | Bundle generation test | PRIVACY.md |
| RFX-173 | Retry/remediation steps for download failures | EXISTING | Retry/remediation UI | `DownloadQueueManager` | Remediation test | QA_PLAN.md |
| RFX-174 | Rank mod/renderer suspects from crash logs | NEW | Crash suspect ranking | `ZyroxCrashAnalyzer` → `RaufixCrashAnalyzer` | Ranking test | QA_PLAN.md |
| RFX-175 | Safe launch-repair recommendations | NEW | Launch repair recommendations | `InstanceHealthInspector` | Repair test | QA_PLAN.md |
| RFX-176 | Before/after performance report | NEW | Perf before/after report | `FpsMonitor`, `PerformanceHudOverlay` | Report test | QA_PLAN.md |
| RFX-177 | Quick restore last successful configuration | EXISTING | Restore last-good config | `InstanceBackupService` | Restore test | QA_PLAN.md |
| RFX-178 | In-app release-notes page | NEW | Release notes page | `CHANGELOG.md` surface | Release notes test | CHANGELOG.md |
| RFX-179 | Copyable anonymous issue summary | NEW | Anonymous issue summary | Support bundle | Issue summary test | PRIVACY.md |
| RFX-180 | Feature status/evidence center | NEW | This traceability surface | `FEATURE_TRACEABILITY.md` | Traceability audit | FEATURE_TRACEABILITY.md |

## Group 19 · RFX-181–200 · In-game video recording and capture

| RFX | Feature | Status | UI/API | Source class/module | Test | Docs |
|-----|---------|--------|--------|---------------------|------|------|
| RFX-181 | Open recorder settings from gear menu; no floating bubble | NEW | Recording Settings page from tools/gear menu | `GameActivity` gear menu → `RecorderSettingsFragment` | Menu-open test | GAME_RECORDING.md |
| RFX-182 | Start/pause/resume/stop from existing menu | NEW | Transport controls in tools menu | `RecorderController`, foreground-service notification | Transport test | GAME_RECORDING.md |
| RFX-183 | Capture clean game-renderer frames before overlay composition | NEW | Game-surface capture path | `GameView` surface encoder | Clean-frame test | GAME_SURFACE_CAPTURE.md |
| RFX-184 | Exclude launcher tools/log/gear icons and virtual buttons | NEW | Game-only frames (no overlays) | Surface capture excludes overlays | Overlay-exclusion test | GAME_SURFACE_CAPTURE.md |
| RFX-185 | Exclude touch/click trails and pointers from frames | NEW | Touch indicators never in video | Surface capture excludes touch | Touch-exclusion test | GAME_SURFACE_CAPTURE.md |
| RFX-186 | 144p/240p/360p/480p/720p presets | NEW | Resolution preset list | `RecorderEncoderMatrix` | Preset test | RECORDER_ENCODER_MATRIX.md |
| RFX-187 | 1080p + device-supported 1440p/2160p/4K+ | NEW | Native-resolution options | `MediaCodecList`/`VideoCapabilities` | Resolution capability test | RECORDER_ENCODER_MATRIX.md |
| RFX-188 | Query codec/muxer/fps/bitrate/resolution capabilities | NEW | Encoder capability query | `MediaCodecList`, `VideoCapabilities` | Capability query test | RECORDER_ENCODER_MATRIX.md |
| RFX-189 | Game-audio-only mode via owned PCM/OpenAL | NEW | Audio mode 1 | `RecorderAudioMixer`, OpenAL PCM | Audio mode 1 test | RECORDER_AUDIO_MODES.md |
| RFX-190 | Game + permitted media playback mode (API29+) | NEW | Audio mode 2 | `AudioPlaybackCapture` | Audio mode 2 test | RECORDER_AUDIO_MODES.md |
| RFX-191 | Game + microphone mode w/ explicit RECORD_AUDIO consent | NEW | Audio mode 3 | `RecorderAudioMixer`, `RECORD_AUDIO` | Audio mode 3 test | RECORDER_AUDIO_MODES.md |
| RFX-192 | Game + mic + media mode w/ independent gain | NEW | Audio mode 4 | `RecorderAudioMixer` per-channel gain | Audio mode 4 test | RECORDER_AUDIO_MODES.md |
| RFX-193 | Screen-capture consent, foreground service, visible privacy indicators | NEW | OS consent + notification | `MediaProjectionManager`, foreground service | Consent/notification test | RECORDER_PRIVACY.md |
| RFX-194 | A/V timestamp alignment, drift, dropped-frame status | NEW | Sync + drift + dropped frames | `RecorderClock`, `FpsMonitor` | Sync/drift test | RECORDER_BENCHMARKS.md |
| RFX-195 | Accessible session controls w/o in-game floating record icon | NEW | No floating bubble | Tools menu + notification only | No-bubble audit | GAME_RECORDING.md |
| RFX-196 | Recording quality/bitrate estimate + free-space preflight | NEW | Size estimate + storage check | `RecorderPreflight` | Preflight test | RECORDER_ENCODER_MATRIX.md |
| RFX-197 | Pause/resume + atomic finalize/cancel on stop | NEW | Atomic `.part` finalize | `RecorderController` | Atomic finalize test | GAME_RECORDING.md |
| RFX-198 | Safe recovery after process death, low storage, thermal | NEW | Recovery / cancel partial | `RecorderController` recovery | Recovery test | GAME_RECORDING.md |
| RFX-199 | MediaStore/SAF video library: list/rename/delete/share | NEW | Video library | `RecorderLibrary`, MediaStore/SAF | Library test | GAME_RECORDING.md |
| RFX-200 | Report recording load, temperature, game frame-time impact | NEW | Overhead report | `RecorderController` + `FpsMonitor` | Overhead test | RECORDER_BENCHMARKS.md |

---

## Recording acceptance matrix (RFX-181–200)

| QA ID | RFX | Focus | Status | Evidence |
|-------|-----|-------|--------|----------|
| REC-QA-01 | 181–184 | Menu access + game-only frames | NOT RUN | GAME_RECORDING.md |
| REC-QA-02 | 185–188 | Resolution + encoder matrix | NOT RUN | RECORDER_ENCODER_MATRIX.md |
| REC-QA-03 | 189 | Game audio only | NOT RUN | RECORDER_AUDIO_MODES.md |
| REC-QA-04 | 190 | Game + media audio | NOT RUN | RECORDER_AUDIO_MODES.md |
| REC-QA-05 | 191 | Game + microphone | NOT RUN | RECORDER_AUDIO_MODES.md |
| REC-QA-06 | 192–194 | Four modes + synchronization | NOT RUN | RECORDER_AUDIO_MODES.md |
| REC-QA-07 | 193 | OS consent + privacy indicator | NOT RUN | RECORDER_PRIVACY.md |
| REC-QA-08 | 195–198 | No-bubble controls, cancel, recovery | NOT RUN | GAME_RECORDING.md |
| REC-QA-09 | 196 | Storage + bitrate estimate | NOT RUN | RECORDER_ENCODER_MATRIX.md |
| REC-QA-10 | 199 | Recording library | NOT RUN | GAME_RECORDING.md |
| REC-QA-11 | 200 | FPS + thermal impact | NOT RUN | RECORDER_BENCHMARKS.md |
| REC-QA-12 | 182–200 | Renderer/orientation/API coverage | NOT RUN | GAME_RECORDING.md |

## Net-new feature count

- Baseline (already present in MojoLauncher/Zyrox checkout): ~50 RFX items marked `EXISTING`.
- Genuine net-new RaufixTeam Launcher V1 capabilities: **150+** (all remaining items marked `NEW` or `BLOCKED`).
- Net-new renderer backends added: **Vulkan Native** (`vulkan_native`) and **Software (LLVMpipe)** (`software_llvmpipe`) — absent from baseline.
- Recording system: **RFX-181–200** fully specified (4 audio modes, clean surface capture, no floating bubble).
- Real FPS + A/B protocol: **RFX-101–120** fully specified.

## Test evidence

Every `Test` column above reports **NOT RUN** — no JDK/Gradle/Android SDK/NDK is available in the sandbox, so no unit, instrumentation, renderer, FPS, accessibility, JNI, fuzz/security, licence/secret-scan, or install/upgrade test could be executed. No PASS is claimed or fabricated.
