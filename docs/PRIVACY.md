# RaufixTeam Launcher V1 — Privacy

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Status:** Release BLOCKED

## 1. No hidden tracking

- No ad SDKs, banners, interstitials, rewarded ads, or hidden telemetry/analytics.
- No credentials, tokens, UUIDs, email, or API keys in crash/support bundles.
- `LogRedactor` redacts tokens/URIs/accounts from logs (RFX-155).
- Telemetry, if any, is **transparent opt-in, off by default** (RFX-152).

## 2. Permissions and network access

- Permissions and network access explained (RFX-159).
- `RECORD_AUDIO` requested only after microphone selection, with clear explanation; if denied, mic not enabled (RFX-191).
- MediaProjection capture requires user consent; screen-capture/microphone indicators and foreground-service notification cannot be hidden (RFX-193).
- Recorded video/audio never uploaded (RFX-199).

## 3. User data

- User-data export/clear center (RFX-160).
- Cache/backup/restore atomic, checksummed, user-approved.
- Test migration never silently deletes old sandbox data.

## 4. Related

See `PRIVACY_POLICY.md`, `GPLAY_PRIVACY_POLICY.md`, `dataremoval.md`, `SECURITY.md` in the repo root.
