# RaufixTeam Launcher V1 — API Key & Secret Security

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Status:** Release BLOCKED until a device-verified build confirms no secrets ship.

## 1. CurseForge API key — REMOVED from the client (RFX-153, RFX-049, RFX-054 QA)

The upstream Zyrox/MojoLauncher `app_mojo/build.gradle` embedded the CurseForge secret into the client APK through this path:

```
CURSEFORGE_API_KEY env var  OR  ./curseforge_key.txt  →  getCFApiKey()  →
  resValue 'string', 'curseforge_api_key', ...  →  BuildConfig / resources
```

**That path is removed in RaufixTeam Launcher V1.** `app_mojo/build.gradle` no longer:

- reads `CURSEFORGE_API_KEY` from the environment,
- reads `./curseforge_key.txt`,
- declares `resValue 'string', 'curseforge_api_key', ...`.

No CurseForge secret exists in `BuildConfig`, resources, the APK, Git, logs, or prompt output. If a key was ever exposed, the owner must rotate it.

## 2. Live official CurseForge access — owner-operated HTTPS gateway only

For live official CurseForge API access the client must NOT hold the secret. Instead:

- The owner operates an HTTPS gateway with **server-side secret management**.
- The gateway enforces an **endpoint allowlist** (only official api.curseforge.com endpoints).
- Rate limits, caching, and abuse controls are applied on the gateway.
- Gateway logs are minimal and never contain the secret.
- The client receives **only the gateway URL** (a user-configurable preference, RFX-048).
- The client sends no secret to the gateway unless the owner separately provisions authenticated access; otherwise the client calls the gateway unauthenticated and the gateway applies its own policy.

## 3. No-key / not-configured behavior (RFX-049)

When no CurseForge key/gateway is configured:

- The CurseForge discovery page shows **NOT CONFIGURED / BLOCKED**.
- It does **not** pretend the API works.
- An official project link (curseforge.com) is offered (RFX-050).
- CurseForge-dependent features (RFX-041…047, RFX-072) are locked/disabled gracefully.

## 4. General secret rules (hard bans)

- **No secrets in the APK:** no API keys, signing keys, tokens, or credentials in `resValue`, `BuildConfig`, `assets/`, `src/main/res/`, native libraries, or ProGuard keep rules.
- **No secrets in Git:** nothing committed under `~/specs/`, `app_mojo/`, or `docs/` that is a real key.
- **No secrets in logs:** `LogRedactor` redacts tokens/URIs/accounts; crash/support bundles never contain keys, tokens, UUIDs, email, or API keys (RFX-155).
- **No secrets in prompt output:** this document references no real key value.
- **No DRM / thermal / root bypass:** none implemented.

## 5. Verification (not executable in this sandbox)

A secret-scan / licence-scan test should run on every release build:

```
./gradlew :app_mojo:assembleFullRelease   # BLOCKED: no Gradle/Android SDK
```

Until it runs, a grep-based audit is the only evidence:

- `grep -rn "curseforge_api_key\|CURSEFORGE_API_KEY\|curseforge_key" app_mojo/ docs/` → must return only this document's mention of the removal.
- `grep -rn "sk_live\|sk_test\|ak_.*cf\|AIza\|APP_ID" app_mojo/src` → must return no real keys.

Result: **NOT RUN** (no build environment).
