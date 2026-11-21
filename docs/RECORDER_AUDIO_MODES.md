# RaufixTeam Launcher V1 — Recorder Audio Modes

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Covers:** RFX-189, RFX-190, RFX-191, RFX-192, RFX-194, REC-QA-03…06  
**Status:** NOT RUN — no device/audio path available.

## Exactly four selectable audio modes

| # | Mode | Source | Consent |
|---|------|--------|---------|
| 1 | Game audio only | Launcher-owned OpenAL/native PCM mix (pre system mixing) | None (game-owned) |
| 2 | Game + capturable media playback | Game PCM + API29+ `AudioPlaybackCapture` | MediaProjection + source app/capture policy |
| 3 | Game + microphone | Game PCM + microphone | `RECORD_AUDIO` (asked only after mic selection) |
| 4 | Game + microphone + capturable media | Game PCM + mic + media | `RECORD_AUDIO` + MediaProjection policy |

## Audio rules

- Other apps captured **only** through authorized `AudioPlaybackCapture` on API29+ when source app + capture policy permit.
- Same-user-profile and MediaProjection consent enforced.
- Never bypass DRM, `ALLOW_CAPTURE_BY_NONE`, or unavailable audio; mode shown **unavailable** when blocked.
- API21–28: never pretend system loopback exists if unsupported.
- `RECORD_AUDIO` requested with clear explanation **only after microphone selection**; if denied, mic not enabled.
- Per-source gain/mute, sample rate/channels, A/V clock sync, drift, and clipping tested; no unauthorized audio access.

## Synchronization (RFX-194)

A/V timestamp alignment, drift, and dropped-frame status measured via `RecorderClock` + `FpsMonitor`.

Status: **NOT RUN**.
