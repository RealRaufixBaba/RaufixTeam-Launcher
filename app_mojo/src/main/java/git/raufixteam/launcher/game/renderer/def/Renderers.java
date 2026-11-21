package git.raufixteam.launcher.game.renderer.def;

public final class Renderers {
    public static final String LTW_RENDERER = "opengles3_ltw";
    public static final String GL4ES_RENDERER = "opengles2";
    public static final String ZINK_RENDERER = "vulkan_zink";
    public static final String FREEDRENO_RENDERER = "freedreno_kgsl";
    public static final String MESA_RENDERER = "mesa_desktop";
    public static final String MESA_RENDERER_EXT = "mesa_desktop_ext";
    public static final String LEGACYZINK_RENDERER = "vulkan_legacyzink";
    // ---- Genuine net-new renderer backends (absent from baseline) ----
    // Native Vulkan path: talks Vulkan directly (not OpenGL-over-Vulkan like Zink).
    public static final String VULKAN_NATIVE_RENDERER = "vulkan_native";
    // CPU software rasterizer (Mesa softpipe/llvmpipe): no GPU required, works on any device.
    public static final String SOFTWARE_RENDERER = "software_llvmpipe";
}
