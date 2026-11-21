package git.raufixteam.launcher.game.renderer.impl;

import static android.os.Build.VERSION.SDK_INT;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.Map;

import git.raufixteam.launcher.R;
import git.raufixteam.launcher.Tools;
import git.raufixteam.launcher.game.renderer.RenderSpec;
import git.raufixteam.launcher.game.renderer.def.Renderers;
import git.raufixteam.launcher.utils.GpuUtils;
import git.raufixteam.launcherexec.MojoExec;

/**
 * CPU software-rasterizer renderer backend (Mesa softpipe / llvmpipe).
 *
 * <p>Renders entirely on the CPU with no GPU required. It is a genuinely new backend absent from
 * the Zyrox/MojoLauncher baseline (which only ships GL ES and GL-over-Vulkan backends). Useful as a
 * last-resort fallback on devices with no working Vulkan/OpenGL ES driver.</p>
 *
 * <p>Licence: original RaufixTeam implementation; relies on Mesa softpipe/llvmpipe (Mesa MIT-like
 * licence) loaded from the system Mesa provider. No proprietary binary is bundled. Marked
 * EXPERIMENTAL until verified on a real device.</p>
 */
public class SoftwareRenderSpec implements RenderSpec {

    @Override
    public boolean compatibleDevice(Context context) {
        // Software rendering needs no GPU: available on any device that ships Mesa libGL.
        return SDK_INT >= 21
                && Tools.NATIVE_LIB_DIR != null
                && new File(Tools.NATIVE_LIB_DIR, "libGL.so").exists();
    }

    @Override
    public String name() {
        return "Software (LLVMpipe)";
    }

    @Override
    public int displayName() {
        return R.string.mcl_setting_renderer_software_llvmpipe;
    }

    @Override
    public String tag() {
        return Renderers.SOFTWARE_RENDERER;
    }

    @Override
    public String library() {
        return "libGL.so";
    }

    @Override
    public void setupEnvironment(Context context, Map<String, String> envMap) {
        envMap.put("MESA_LOADER_DRIVER_OVERRIDE", "llvmpipe");
        envMap.put("GALLIUM_DRIVER", "llvmpipe");
        envMap.put("MESA_GL_VERSION_OVERRIDE", "4.5");
        envMap.put("MESA_GLSL_VERSION_OVERRIDE", "450");
        envMap.put("mesa_glthread", "true");
        File cacheDir = new File(Tools.DIR_CACHE != null ? Tools.DIR_CACHE : new File("."),
                "software_cache");
        if (!cacheDir.isDirectory()) {
            cacheDir.mkdirs();
        }
        envMap.put("MESA_SHADER_CACHE_DIR", cacheDir.getAbsolutePath());
    }

    @Override
    public boolean setupRenderer() {
        return MojoExec.prepareEgl(library(), true, false, 0);
    }

    @Override
    public String librarySearchPath() {
        return null;
    }
}
