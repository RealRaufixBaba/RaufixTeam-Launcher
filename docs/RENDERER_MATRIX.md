# RaufixTeam Launcher V1 — Renderer Matrix

**Product:** RaufixTeam Launcher V1  
**versionName:** 1.0.0  
**Renderer backends (baseline + net-new):**

| # | Backend | Tag | Type | Baseline? | Source class | Licence | Device/GPU support | Status |
|---|---------|-----|------|-----------|--------------|---------|--------------------|--------|
| 1 | GL4ES (OpenGL ES 2) | `opengles2` | GL ES wrapper | Yes (baseline) | `GLESRenderSpec.GL4ESRenderSpec` | Original (MojoLauncher) | Legacy GPUs, MC 1.16.5 and lower | EXISTING |
| 2 | LTW (OpenGL ES 3) | `opengles3_ltw` | GL ES | Yes (baseline) | `GLESRenderSpec.LTWRenderSpec` | Original | MC 1.17+, Sodium/Embeddium | EXISTING |
| 3 | Zink (Vulkan) | `vulkan_zink` | GL over Vulkan | Yes (baseline) | `MesaRenderSpec.ZinkRenderSpec` | Mesa (MIT-like) | Vulkan GPUs | EXISTING |
| 4 | Freedreno (KGSL) | `freedreno_kgsl` | Vulkan/KGSL | Yes (baseline) | `MesaRenderSpec.FreedrenoRenderSpec` | Mesa | Adreno (Snapdragon) | EXISTING |
| 5 | Mesa Desktop (DRM) | `mesa_desktop` | OpenGL | Yes (baseline) | `MesaRenderSpec` | Mesa | Desktop/Mesa GPUs | EXISTING |
| 6 | Mesa Desktop (external) | `mesa_desktop_ext` | OpenGL (plugin) | Yes (baseline) | `MesaRenderSpec.ExtMesaRenderSpec` | Mesa | External Mesa provider | EXISTING |
| 7 | Legacy Zink | `vulkan_legacyzink` | GL over Vulkan | Yes (baseline) | `MesaRenderSpec.LegacyZinkRenderSpec` | Mesa | Older Vulkan GPUs | EXISTING |
| 8 | **Vulkan Native** | `vulkan_native` | **Native Vulkan** | **No — net-new** | **`VulkanNativeRenderSpec`** | **Original (RaufixTeam)** | **Vulkan-capable GPUs** | **EXPERIMENTAL / BLOCKED** |
| 9 | **Software (LLVMpipe)** | `software_llvmpipe` | **CPU software rasterizer** | **No — net-new** | **`SoftwareRenderSpec`** | **Original (RaufixTeam)** | **Any device with Mesa libGL** | **EXPERIMENTAL / BLOCKED** |

## Net-new backends (absent from baseline)

**Vulkan Native (`vulkan_native`)** — talks Vulkan directly to the device ICD (not OpenGL-over-Vulkan like Zink). Original RaufixTeam implementation; consumes only the system Vulkan loader (`libvulkan.so`). No signed/proprietary binary bundled.

**Software (LLVMpipe) (`software_llvmpipe`)** — CPU software rasterization via Mesa softpipe/llvmpipe. No GPU required; last-resort fallback. Original RaufixTeam implementation.

## Compatibility & pinning

- `RendererCache.getCompatibleRenderers()` lists only backends whose `compatibleDevice()` returns true for the current device (checks for `libvulkan.so`, `libGL.so`, Vulkan support).
- `GameRenderer.maybeSetupRenderer()` falls back to `GL4ES_RENDERER` if the selected backend fails.
- Backend launch failures are quarantined (RFX-089); safe fallback to the last working backend (RFX-90).
- Renderer versions are pin-able per instance (RFX-087) and roll-backable (RFX-088).

## Licence & provenance

New backends are original RaufixTeam code. They depend on Mesa/Vulkan system libraries distributed under their own open licences (Mesa MIT-like). No proprietary or signed renderer binary is bundled. Backend source, commit/tag, licence, hash, and support matrix must be recorded per release once a build is possible.

## Status

Both net-new backends are **EXPERIMENTAL / BLOCKED** until verified on a real device/encoder/GPU. No FPS uplift is claimed (no controlled A/B evidence available in this sandbox).
