# RaufixTeam Launcher V1 — Licences & Attribution

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Status:** Release BLOCKED pending legal review

## 1. Upstream licence (preserved, not rewritten)

The upstream project (`argamerzz9582-debug/ZyroxLauncher`, HEAD `f57c3f7`, imported into this repo) is **LGPL-3.0** and documents itself as an Android Minecraft: Java Edition launcher based on **MojoLauncher/PojavLauncher**.

RaufixTeam Launcher V1:

- **Preserves** upstream copyright, NOTICE, LGPL-3.0 text, MojoLauncher/PojavLauncher/third-party attribution.
- **Preserves** source-distribution obligations (LGPL: users can relink against a modified library).
- **Does not** remove or rewrite applicable upstream copyright or attribution.
- **Does not** claim upstream work as RaufixTeam authorship.

## 2. RaufixTeam-new code

Original RaufixTeam additions (rebrand, new renderer backends Vulkan Native + Software LLVMpipe, recording system, FPS/A/B protocol, docs) are **RaufixTeam-authored** and distributed under the project licence, **separate from** upstream LGPL code.

## 3. Third-party dependencies (from `app_mojo/build.gradle`)

Not a complete build audit (no Gradle available). Declared dependencies include, among others:

- AndroidX (preference, drawerlayout, viewpager2, annotation, constraintlayout) — Apache 2.0 / BSD
- Gson (Apache 2.0), Commons-Codec/IO/Compress (Apache 2.0), exp4j (Apache 2.0), OkHttp (Apache 2.0), ZXing Core (Apache 2.0), Tukaani XZ (public domain), HtmlCleaner (BSD)
- OpenAL Soft AAR (LGPL), GLFW/SDL JNI bindings (their respective licences)
- shaderc.aar / spvc.aar / CI LTW/Mesa AARs (their respective licences)
- Caciocavallo, portrait-sdp/ssp, ExtendedView, gamepad remapper, virtual-joystick (various open licences)
- `git.artdeell.dnbootstrap.glfw` (external package, not in tree — licence to be confirmed at build time)

A full dependency licence audit (`./gradlew :app_mojo:dependencies` + licence plugin) is **BLOCKED / NOT RUN**.

## 4. New assets

New logos/fonts/audio/graphics/renderer binaries (when created) must be original or redistributable under documented licences with provenance recorded here.

## 5. Attribution panel (RFX-158)

An in-app Licence and Attribution panel (`AboutSecurityFragment`) lists upstream and third-party licences. Not device-verified.

## 6. Legal review gate

If licence compliance is uncertain, the release is **BLOCKED** for legal review (per spec).
