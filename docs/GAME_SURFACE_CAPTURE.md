# RaufixTeam Launcher V1 — Game Surface Capture Contract

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Covers:** RFX-183, RFX-184, RFX-185  
**Status:** NOT RUN — no device/encoder available.

## Capture source

Recording reads frames from a **second encoder output bound to the game's actual render surface/framebuffer** (`GameView` / `SurfaceProvider`), taken **before** launcher overlays (gear/log toolbar, launcher buttons, virtual gamepad) are composited onto the display.

## What is excluded (game-only guarantee)

The output must never contain:

- Launcher tools / log / gear icons
- Launcher buttons
- Virtual gamepad / touch controls
- Touch / click trails and pointers
- Android navigation bars
- Menus and notification UI

## Backend gating

If a renderer backend has no verified clean frame path to the game surface (i.e., it can only produce frames after overlay composition, or not at all), recording is **`UNAVAILABLE` for that backend**. A full-display MediaProjection capture that includes overlays is **not** accepted as "game-only".

## Verification (not executable)

Frames are verified with an image test to confirm no launcher UI appears. Overlay-capturing output is **not PASS**.

Status: **NOT RUN**.
