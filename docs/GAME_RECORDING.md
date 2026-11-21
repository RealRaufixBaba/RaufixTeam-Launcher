# RaufixTeam Launcher V1 — Game Recording System

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Coverage:** RFX-181–200 + recording acceptance matrix REC-QA-01…12  
**Status:** NOT RUN — no device/encoder/build environment available.

## 1. Entry point (RFX-181, RFX-182, RFX-195)

Recording is exposed as a real **Recording Settings** page opened from the existing **landscape game-tools / gear menu** in `GameActivity`.

- **No floating record bubble, no draggable floating icon, no control burned into gameplay.**
- Start / pause / resume / stop are available from that tools menu **or** Android's required persistent foreground-service notification.
- Transport controls are reachable without any in-game floating record icon (RFX-195).

## 2. Clean game-surface capture (RFX-183, RFX-184, RFX-185)

- The saved video contains **Minecraft gameplay only**.
- Captured from a **second encoder output from the game's actual render surface/framebuffer** *before* launcher overlays (gear/log toolbar, launcher buttons, virtual gamepad) are composited.
- **Excluded from video:** launcher gear/log toolbar, launcher buttons, virtual gamepad, touch/click trails and pointers, Android navigation bars, menus, notification UI.
- A full-display MediaProjection recording is **not** labeled "game-only" if it captures overlays. If a backend has no verified clean frame path, recording is `UNAVAILABLE` for that backend.
- Touch input is preserved while touch indicators are never drawn into video.

## 3. Resolutions (RFX-186, RFX-187, RFX-188)

Offered: 144p, 240p, 360p, 480p, 720p and, when the device supports them, 1440p, 2160p/4K, or higher custom.

Example 16:9 sizes: 256×144, 426×240, 640×360, 854×480, 1280×720, 1920×1080, 2560×1440, 3840×2160.

- Non-16:9 aspect ratios preserved without stretching; crop/letterbox made explicit.
- Encoder size/fps/bitrate/profile/container queried via `MediaCodecList` / `VideoCapabilities`; unsupported choices disabled or a disclosed user-approved fallback offered.
- Upscaling 1080p source to 4K is **not** labeled native 4K.
- H.264/AVC MP4 is baseline; HEVC/AV1/other containers shown only when encoder + muxer support them.

## 4. Audio modes — exactly four (RFX-189–192)

1. **Game audio only** — from the launcher's OpenAL/native PCM mix before system mixing.
2. **Game + capturable media playback** — API29+ `AudioPlaybackCapture`, only when source app and capture policy permit.
3. **Game + microphone** — explicit `RECORD_AUDIO` consent requested only after mic selection; if denied, mic not enabled.
4. **Game + microphone + capturable media playback** — independent per-channel gain.

- Game audio captured from launcher-owned OpenAL/PCM mix where possible.
- Other apps only through authorized `AudioPlaybackCapture` on API29+ when source app + capture policy permit.
- Same-user-profile and MediaProjection consent enforced; never bypass DRM, `ALLOW_CAPTURE_BY_NONE`, or unavailable audio; mode shown unavailable when blocked.
- API21–28: never pretend system loopback exists if unsupported.

## 5. Consent, privacy indicators, foreground service (RFX-193)

- Every MediaProjection session requires user consent and Android's required visible screen-capture / microphone indicators and foreground-service notification.
- These **cannot be hidden or bypassed**; the OS indicator is not an in-game floating icon.
- System privacy UI is kept out of the game-surface recording file.

## 6. Preflight & lifecycle (RFX-196, RFX-197, RFX-198)

Before recording: validate encoder/renderer compatibility, permissions, free space, estimated file size, battery/thermal impact, and audio mode.

- Pause/resume/stop are cancellable and idempotent.
- Output atomically finalized; `.part`/pending MediaStore output cleaned on cancel.
- After crash/process death/disk-full: never show a corrupt partial file as complete — finalize a valid MP4 or remove partial output.

## 7. Video library (RFX-199)

MediaStore/SAF video library: list, rename, delete, share. Revoked URIs handled safely. **Never upload recorded video/audio.** No private-media logs.

## 8. Impact reporting (RFX-200)

Reports recording load, temperature, and game frame-time impact (recorder-off vs recorder-on A/B). Never implies recording raises game FPS.

## 9. Related docs

- `docs/GAME_SURFACE_CAPTURE.md` — clean-frame capture contract
- `docs/RECORDER_ENCODER_MATRIX.md` — resolutions/codecs/capabilities
- `docs/RECORDER_AUDIO_MODES.md` — four audio modes
- `docs/RECORDER_PRIVACY.md` — consent/indicators/foreground service
- `docs/RECORDER_BENCHMARKS.md` — overhead/A/B measurement

## 10. Honest status

No recording was executed on a device. All REC-QA-01…12 are **NOT RUN**. No PASS is claimed.
