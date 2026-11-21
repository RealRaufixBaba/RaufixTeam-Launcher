package git.raufixteam.launcher.modding.modpacks.api.modloader;

import git.raufixteam.launcher.instances.InstanceInstaller;

import java.io.IOException;

public interface LoaderInstaller {
    boolean requiresGuiInstallation();
    InstanceInstaller createInstaller() throws IOException;
    String installHeadlessly() throws IOException;
}
