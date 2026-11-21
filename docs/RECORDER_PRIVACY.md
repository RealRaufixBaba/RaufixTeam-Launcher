# RaufixTeam Launcher V1 — Recorder Privacy

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Covers:** RFX-193, REC-QA-07  
**Status:** NOT RUN — no device available.

## Consent and indicators

- Every MediaProjection session requires **user consent** before capture begins.
- Android's required **screen-capture / microphone indicators** and **foreground-service notification** are shown and **cannot be hidden or bypassed**.
- The OS privacy indicator is **not** an in-game floating icon and is kept out of the game-surface recording file.

## Data handling

- Recorded video/audio is **never uploaded**.
- No private-media logs.
- MediaStore/SAF URIs handled safely, including revoked URIs.

Status: **NOT RUN**.
