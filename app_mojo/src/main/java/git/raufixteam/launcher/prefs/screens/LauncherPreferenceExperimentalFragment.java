package git.raufixteam.launcher.prefs.screens;

import android.os.Bundle;

import androidx.preference.SwitchPreferenceCompat;

import git.raufixteam.launcher.utils.GpuUtils;

import git.raufixteam.launcher.R;

public class LauncherPreferenceExperimentalFragment extends LauncherPreferenceBaseFragment {

    @Override
    public void onCreatePreferences(Bundle b, String str) {
        addPreferencesFromResource(R.xml.pref_experimental);
        SwitchPreferenceCompat sysmem = requirePreference("freedrenoSysmem", SwitchPreferenceCompat.class);
        SwitchPreferenceCompat ubwc = requirePreference("ubwcWorkaround", SwitchPreferenceCompat.class);
        boolean hasFreedreno = GpuUtils.getGlInfo().isAdreno();
        sysmem.setVisible(hasFreedreno);
        ubwc.setVisible(hasFreedreno);
    }
}
