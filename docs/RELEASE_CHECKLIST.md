# RaufixTeam Launcher V1 — Production Release & Signing Checklist

## 1. GitHub Repository Secrets (Release Signing)

Configure these four encrypted repository secrets (preferably scoped to a protected `production-release` environment in **Settings → Secrets and variables → Actions**):

| Secret Name | Description |
| :--- | :--- |
| `RAUFIXTEAM_KEYSTORE_BASE64` | Base64-encoded production `.jks` / `.keystore` (`base64 -w 0 release.jks`). Never commit the keystore to Git. |
| `RAUFIXTEAM_KEYSTORE_PASSWORD` | Keystore password. |
| `RAUFIXTEAM_KEY_ALIAS` | Signing key alias inside the keystore. |
| `RAUFIXTEAM_KEY_PASSWORD` | Private key password. |

These map to the `RAUFIXTEAM_*` environment variables read by `app_mojo/build.gradle` (`releaseKeystorePath`, `releaseKeystorePassword`, `releaseKeyAlias`, `releaseKeyPassword`). When any of them is missing, the release variant builds **unsigned** (per `app_mojo/build.gradle` signing logic).

### Keystore Safety Rules

- **Back up your production keystore offline** in a secure encrypted vault. Losing it prevents seamless in-place Android upgrades for existing users.
- **Signing continuity:** If upgrading users from an earlier certificate, use the same release keystore for seamless updates. If rotating to a new release keystore, clearly state in the release notes that users should export/back up instances and worlds before uninstalling the older build and installing the new build.
- Pull requests never receive signing secrets; CI only signs on trusted `v*` tag pushes or explicit manual `workflow_dispatch` when all four secrets are configured, and wipes temporary decoded keystore files in an `if: always()` step.

---

## 2. Pre-Release Verification Gate

- [ ] `app_mojo/build.gradle` `versionName` and `versionCode` match the target release (`1.0.0` / `1000001`).
- [ ] `CHANGELOG.md`, `README.md`, `SECURITY.md`, and `PRIVACY_POLICY.md` are up to date.
- [ ] Zero secrets, keystores, `.jks`, `.keystore`, tokens, or private keys exist in `git status` or git history.
- [ ] CI job `Security & Manifest Static Audit` is green (no cleartext HTTP, no dangerous permissions, no leaked tokens).
- [ ] CI job `Build, JUnit Tests & Android Lint` is green (all JUnit 4 unit tests pass, Android Lint report uploaded, release APKs built with `debuggable=false`).
- [ ] Upgrade compatibility verified (existing instances, worlds, mods, accounts, and `default.json` controls load cleanly without data loss).

---

## 3. Signed Release Verification Gate

- [ ] Push annotated tag `v1.0.0` (only after explicit maintainer approval) to trigger `sign-release` & `publish-release`.
- [ ] Confirm `apksigner verify --verbose --print-certs` reports `Verified using v1 scheme: true`, `v2 scheme: true`, `v3 scheme: true` and matches your expected production certificate SHA-256.
- [ ] Confirm `SHA256SUMS.txt` is attached to the GitHub Release and matches every uploaded APK (`arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86`, and `universal`).
- [ ] Publish the public signing certificate SHA-256 in the GitHub Release body so users can cross-check inside **RaufixTeam Launcher → TRUST → Security & Trust**.
