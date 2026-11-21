# RaufixTeam Launcher V1 — Recorder Encoder Matrix

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Covers:** RFX-186, RFX-187, RFX-188, RFX-196, REC-QA-02, REC-QA-09  
**Status:** NOT RUN — no device/encoder available.

## Resolution presets

| Preset | Size (16:9) |
|--------|-------------|
| 144p | 256×144 |
| 240p | 426×240 |
| 360p | 640×360 |
| 480p | 854×480 |
| 720p | 1280×720 |
| 1080p | 1920×1080 |
| 1440p | 2560×1440 (device-supported only) |
| 2160p / 4K | 3840×2160 (device-supported only) |
| Custom | user-defined (device-supported only) |

Non-16:9 aspect ratios preserved without stretching; crop/letterbox explicit.

## Capability query (RFX-188)

Encoder size, fps, bitrate, profile, and container support queried via `MediaCodecList` / `VideoCapabilities`. Unsupported choices disabled or a disclosed user-approved fallback offered. Upscale-to-4K labeled as upscale, not native 4K. H.264/AVC MP4 baseline; HEVC/AV1/other containers only when encoder + muxer support them.

## Storage + bitrate preflight (RFX-196)

Estimated file size vs free space reported before recording; recording prevented/confirmed until storage preflight passes. Low storage and long 1080p+ capture tested.

Status: **NOT RUN**.
