# RaufixTeam Launcher V1 — Modrinth & CurseForge

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Status:** Release BLOCKED (no live provider data / device)

## 1. Modrinth (real provider data)

Separate scrollable Modrinth browse/search page:

- Scroll-down pagination / lazy loading; loading/empty/error/retry states; debounce
- Filters/sorting: category, Minecraft version, Fabric/Forge/NeoForge/Quilt loader, author, licence
- Project details: version list, file size/hash, changelog, source links
- Provider IDs persisted; same-name projects never merged
- Installed-mod page: file/loader/instance/version/update state; single/bulk install, enable, disable, update, delete, rollback; change summary/confirmation; before/after hash and snapshot; per-instance mods folder; reversible atomic file changes
- Version diffs and changelogs shown; no auto-update without opt-in
- Provider/project attribution, licence, and author shown; no redistribution where terms forbid it; no unofficial scraping

Implementation baseline present in checkout: `ModrinthProvider`, `ModrinthApi`, `ModrinthIndex`, `ModrinthContentInstaller`, `SearchModFragment`, `ModVersionListFragment`, `ContentManagerFragment`, `InstanceContentManager`.

## 2. CurseForge (gated — no key)

- No CurseForge API key in the APK (`curseforge_api_key` resValue removed from `build.gradle`).
- When no key/gateway is configured: CurseForge shows **NOT CONFIGURED / BLOCKED**, does not pretend the API works, offers official project link.
- Live official CurseForge access only via owner-operated HTTPS gateway (server-side secret, endpoint allowlist, rate limits, caching). Client gets only the gateway URL (user preference).
- `CurseForgeProvider`, `CurseforgeApi`, `CurseManifest`, `ModpackApi` exist in source but are disabled without a gateway.

## 3. Terms

Follow Modrinth and CurseForge terms, attribution, and download permissions. Mock-server / API contract tests planned but **NOT RUN** (no build environment).
