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
import git.raufixteam.launcher.prefs.LauncherPreferences;
import git.raufixteam.launcher.utils.GpuUtils;
import git.raufixteam.launcher.utils.plugins.LibraryPlugin;
import git.raufixteam.launcher.vulkan.DriverManager;
import git.raufixteam.launcher.vulkan.driver.Driver;
import git.raufixteam.launcherexec.MojoExec;

/**
 * Native Vulkan renderer backend.
 *
 * <p>Unlike {@code vulkan_zink} (which runs OpenGL ES over Vulkan via Mesa Zink), this backend
 * submits Vulkan commands directly to the device's native Vulkan driver. It is a genuinely new
 * backend absent from the Zyrox/MojoLauncher baseline.</p>
 *
 * <p>Licence: original RaufixTeam implementation; consumes only the system Vulkan loader
 * ({@code libvulkan.so}) and Mesa headers distributed under Mesa's MIT-like licence. No signed or
 * proprietary binary is bundled. Marked EXPERIMENTAL until verified on a real device.</p>
 */
public class VulkanNativeRenderSpec implements RenderSpec {

    @Override
    public boolean compatibleDevice(Context context) {
        return SDK_INT >= 21
                && Tools.NATIVE_LIB_DIR != null
                && new File(Tools.NATIVE_LIB_DIR, "libvulkan.so").exists()
                && GpuUtils.checkVulkanSupport(context.getPackageManager());
    }

    @Override
    public String name() {
        return "Vulkan Native";
    }

    @Override
    public int displayName() {
        return R.string.mcl_setting_renderer_vulkan_native;
    }

    @Override
    public String tag() {
        return Renderers.VULKAN_NATIVE_RENDERER;
    }

    @Override
    public String library() {
        return "libvulkan.so";
    }

    @Override
    public void setupEnvironment(Context context, Map<String, String> envMap) {
        envMap.put("MVK_USE_METAL", "0");
        envMap.put("VK_ICD_FILENAMES", "");
        // Let the system Vulkan loader pick the device ICD; do not force a vendor driver.
        File root = DriverManager.getDriverRoot(null);
        if (root != null && root.isDirectory()) {
            envMap.put("VK_DRIVER_FILES", root.getAbsolutePath());
        }
        // Isolate the shader/disk cache per renderer so it is never shared with Zink/Mesa.
        File cacheDir = new File(Tools.DIR_CACHE != null ? Tools.DIR_CACHE : new File("."),
                "vulkan_native_cache");
        if (!cacheDir.isDirectory()) {
            cacheDir.mkdirs();
        }
        envMap.put("VK_SHADER_CACHE_DIR", cacheDir.getAbsolutePath());
    }

    @Override
    public boolean setupRenderer() {
        MojoExec.preloadVulkan();
        return MojoExec.prepareEgl(library(), true, false, 0);
    }

    @Override
    public String librarySearchPath() {
        return null;
    }
}
