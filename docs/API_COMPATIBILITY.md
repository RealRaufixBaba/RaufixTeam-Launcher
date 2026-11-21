# RaufixTeam Launcher V1 — API Compatibility

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Status:** NOT RUN — no device/build environment.

## Platform targets

- `minSdkVersion 21`
- `targetSdkVersion 36`
- `compileSdk 36`
- NDK `29.0.14206865`
- ABI split: `arm64-v8a` only (production APK)
- Flavors: `full`, `noruntime`, `release`
- `multiDexEnabled true`

## Runtime / JVM

- Minecraft/loader/JRE manifests validated statically
- JVM args, heap advice, ABI verified; network/archive/hash/large JSON/JAR work never blocks the main thread
- Per-instance JRE pinning, runtime update/rollback, downloaded-runtime hash verification

## Behavior to validate (API21+)

API21–28: no system loopback audio pretended if unsupported; recorder audio modes appropriately unavailable.
API29+: `AudioPlaybackCapture` only when source app and capture policy permit.

All API-compatibility behavior is **NOT RUN** — no device available.
