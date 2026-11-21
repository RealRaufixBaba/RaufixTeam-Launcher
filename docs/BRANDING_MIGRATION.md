# RaufixTeam Launcher V1 — Branding Migration

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Status:** Release BLOCKED (visual assets not device-verified)

## 1. Product identity (applied)

| Item | Before (Zyrox) | After (RaufixTeam Launcher V1) |
|------|----------------|--------------------------------|
| Namespace | `git.artdeell.mojo` | `git.raufixteam.launcher` |
| ApplicationId (candidate) | `com.zyrox.launcher` | `com.raufixteam.launcher` |
| versionName | 2.7.0 | **1.0.0** |
| versionCode | date-based (`GITHUB_RUN_NUMBER` or `172009`) | monotonic `1000001` |
| App name (`app_short_name`) | Zyrox Launcher | **RaufixTeam Launcher V1** |
| rootProject.name | MojoLauncher | RaufixTeamLauncher |

## 2. User-facing Zyrox brand text replaced (applied)

- `error_fatal`: "Zyrox Launcher has unexpectedly crashed" → "RaufixTeam Launcher V1 has unexpectedly crashed"
- `storage_required`: "Zyrox Launcher requires external storage…" → "RaufixTeam Launcher V1 requires external storage…"
- `notification_permission_dialog_text`: references to "Zyrox Launcher" → "RaufixTeam Launcher V1"
- `driver_default_extra_name`: "Zyrox Launcher" → "RaufixTeam Launcher V1"
- `zyrox_brand_title`, `zyrox_brand_subtitle`, `zyrox_engine_watermark` values rebranded to RaufixTeam equivalents (resource IDs retained so existing Java/R references stay valid)

## 3. What was NOT changed (scope decision)

- Internal `zyrox_*` **resource IDs** (e.g. `R.string.zyrox_*`, `R.id.zyrox_*`, `R.drawable.zyrox_*`) were **not** renamed. Renaming them would require touching every Java/layout reference and cannot be verified without a build; they are internal, not user-facing. Their *values* were de-branded where user-visible.
- Class names containing "Zyrox" (e.g. `ZyroxCrashAnalyzer`, `ZyroxStudioUi`, `ZyroxShaderBackgroundView`, `ZyroxRoomBridge`, `ZyroxHttpClient`, `ZyroxGifDecoder`, `ZyroxLoopingVideoView`, `ZyroxSeamlessGifView`) were **not** renamed — renaming would break references and JNI/package coupling; this is treated as a separate internal migration decision.
- Zyrox scene assets (`zyrox_*_scene.mp4`, ~23 MB + 12 MB) were **not** removed from the imported tree — removal of large binary assets is scoped separately pending owner confirmation.

## 4. No ads / tracking

- No ad SDKs, banners, interstitials, rewarded ads, or hidden tracking added or shipped.
- `build.gradle` contains no ad or tracking dependencies.

## 5. Pending / owner decisions

- `com.raufixteam.launcher` ApplicationId is a **candidate** pending owner-controlled reverse-domain confirmation.
- A new adaptive launcher icon set, vector in-app icon family, splash, colors, theme, and onboarding visuals are **specified** (RFX-001–010) but not device-verified; original assets must be created or confirmed redistributable.

## 6. Licence obligations preserved

- Upstream LGPL-3.0, MojoLauncher/PojavLauncher/third-party attribution, NOTICE, and source-distribution obligations preserved (see `docs/LICENSES_AND_ATTRIBUTION.md`). No upstream work claimed as RaufixTeam authorship.
