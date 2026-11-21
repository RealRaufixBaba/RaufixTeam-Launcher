# RaufixTeam Launcher V1 — QA Plan

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Status:** NOT RUN — no device/build environment.

## Scope

Every RFX-001–200 acceptance case (RFL-1-01-EN-QA-00001…00846) plus recording acceptance REC-QA-01…12 is an independent case using synthetic/licensed fixtures. Each case is executed through the real UI with cancel/malformed/boundary input; negative paths preserve the last valid instance and never log keys, tokens, mod contents, or private URIs.

## Test matrix dimensions

- API levels: API21 ARM64 Adreno, API36 Vulkan GPU, GLES-only device, Mali mid-range, low-end 4 GB RAM
- Conditions: normal flow, boundary, rollback/recovery, accessibility (TalkBack/keyboard/large text/RTL), thermal throttling, metered mobile data, CurseForge key absent/gateway error/429, offline after cached catalog, nearly full storage, duplicate/renamed JARs, malformed/hostile archive, renderer crash on launch, process death during install, 1000 installed mods, app rebrand/data migration, A/B FPS benchmark fixture
- Contexts: refresh-rate != game frame rate (120 Hz display / 60 FPS game)

## Execution rule

- Each case: perform valid action once, persist expected state, reopen and compare state/hash.
- Negative: network/permission/disk/API failure preserves last valid instance.
- Result per case: PASS / FAIL / BLOCKED / NOT RUN.
- **No plan reported as executed.** All cases NOT RUN in this sandbox.

## Recording acceptance (REC-QA-01…12)

Validated on a real device/encoder/audio path: menu access + game-only frames; resolution/encoder matrix; four audio modes; OS consent + privacy indicator; no-bubble controls/cancel/recovery; storage/bitrate estimate; recording library; FPS + thermal impact; renderer/orientation/API coverage.

## Release gate

Do not claim completion until identity/version (1.0.0), original icon/theme, no-ads audit, licence attribution, Modrinth+CurseForge flows, mod lifecycle, two genuine new renderers, evidenced FPS benchmark, clean game-surface-only output, all four audio modes, capability matrix, 144p–1080p + device-supported 4K+, no floating bubble, and visible Android privacy indicators are accepted.
