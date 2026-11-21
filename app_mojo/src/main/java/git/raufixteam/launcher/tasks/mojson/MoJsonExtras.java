package git.raufixteam.launcher.tasks.mojson;

import androidx.annotation.Nullable;

import java.io.File;

import git.raufixteam.launcher.JVersionList;
import git.raufixteam.launcher.extra.ExtraConstants;
import git.raufixteam.launcher.extra.ExtraCore;
import git.raufixteam.launcher.metadata.MojangVersionProvider;
import git.raufixteam.launcher.metadata.VersionMetadataRepository;

public class MoJsonExtras {
    @Nullable
    private static JVersionList getActiveManifest() {
        JVersionList inExtra = (JVersionList) ExtraCore.getValue(ExtraConstants.RELEASE_TABLE);
        if (inExtra != null && inExtra.versions != null) {
            return inExtra;
        }
        JVersionList cached = VersionMetadataRepository.getInstance().getCurrentCatalog();
        if (cached != null) {
            ExtraCore.setValue(ExtraConstants.RELEASE_TABLE, cached);
        }
        return cached;
    }

    /**
     * Ensures the official Mojang release manifest is populated on a worker thread so cold-start
     * Play taps, symbolic channels, and Fabric {@code inheritsFrom} lookups always succeed.
     */
    @Nullable
    public static JVersionList ensureActiveManifestSynced() {
        JVersionList current = getActiveManifest();
        if (current != null && current.versions != null && current.versions.length > 0) {
            return current;
        }
        try {
            git.raufixteam.launcher.metadata.LocalMetadataCache.SyncResult<JVersionList> synced =
                    VersionMetadataRepository.getInstance().getMojangProvider().syncManifest(false);
            if (synced != null && synced.data != null) {
                ExtraCore.setValue(ExtraConstants.RELEASE_TABLE, synced.data);
                return synced.data;
            }
        } catch (Throwable t) {
            android.util.Log.w("MoJsonExtras", "Manifest sync before launch failed: " + t.getMessage());
        }
        return current;
    }

    public static String normalizeVersionId(String versionString) {
        if (versionString == null) return null;
        return MojangVersionProvider.resolveSymbolicVersionId(versionString, getActiveManifest());
    }

    public static JVersionList.Version getListedVersion(String normalizedVersionString) {
        return MojangVersionProvider.findVersion(getActiveManifest(), normalizedVersionString);
    }

    public interface DoneListener {
        void onDownloadDone(File[] classpath);
        void onDownloadFailed(Throwable throwable);
    }
}
