# RaufixTeam Launcher V1 — Known Limits

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Status:** Release BLOCKED

## 1. No build / test execution (primary limit)

The sandbox has **python3/node/npm only** — no JDK, Gradle, Android SDK, or NDK. Therefore:

- No Gradle task (`testFullReleaseUnitTest`, `lintFullRelease`, `assembleFullRelease`, or any variant) could run.
- **No APK was built, signed, or verified.**
- Every instrumentation, renderer, FPS, accessibility, JNI, fuzz/security, and install/upgrade test is **NOT RUN**.
- No PASS, FPS number, or benchmark is claimed or fabricated.

## 2. CurseForge

- No CurseForge API key configured. CurseForge discovery shows **NOT CONFIGURED / BLOCKED** with an official project link.
- Live official CurseForge access requires an **owner-operated HTTPS gateway** (server-side secret management). Not set up in this release.
- `curseforge_api_key` resValue removed from `build.gradle`; no secret in APK.

## 3. Renderer backends

- Two net-new backends added (Vulkan Native, Software LLVMpipe) but **EXPERIMENTAL / BLOCKED** — not verified on a real device/GPU.
- No FPS uplift claimed (no controlled A/B evidence).

## 4. Recording

- Recording system fully specified (RFX-181–200, 4 audio modes, clean surface capture, no floating bubble) but **NOT RUN** — no device/encoder/audio path available.

## 5. Application ID

- `com.raufixteam.launcher` is a **candidate** pending owner-controlled reverse-domain confirmation. Not final for publication.
- Internal Java package migrated to `git.raufixteam.launcher`; JNI/native library lookup and native `.so` names left unchanged (native migration treated as a separate decision per spec).

## 6. Migration

- Test package migration (SAF export-import) was **not executed**. Old sandbox data is **never silently deleted**.

## 7. Localization

- TR/EN/AR supported from baseline; RTL layout present. Full RTL/accessibility audit not executed on device.
