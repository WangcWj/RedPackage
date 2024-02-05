package xyz.monkeytong.hongbao.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import xyz.monkeytong.hongbao.R;

import xyz.monkeytong.hongbao.utils.AccessibilityUtils;
import xyz.monkeytong.hongbao.utils.BatterySetting;
import xyz.monkeytong.hongbao.utils.SettingUtils;


/**
 * Created by Zhongyi on 2/4/16.
 */
public class GeneralSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.general_preferences);

        Preference whitePref = findPreference("pref_keep_screen_on");
        whitePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                BatterySetting.open(getActivity());
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        setPrefListeners();
    }

    private void setPrefListeners() {
        //add white
        Preference whitePref = findPreference("pref_accessibility");
        boolean accessibilitySettingsOn = AccessibilityUtils.isAccessibilitySettingsOn(getActivity());
        if(accessibilitySettingsOn){
            whitePref.setTitle("无障碍权限已开启");
        }else {
            whitePref.setTitle("开启无障碍权限");
        }
        whitePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(AccessibilityUtils.isAccessibilitySettingsOn(getActivity())){
                    return true;
                }
                AccessibilityUtils.goAccessibilityPage(getActivity());
                return false;
            }
        });
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof OnPreferenceDisplayDialogCallback && ((OnPreferenceDisplayDialogCallback) preference).onPreferenceDisplayDialog(this, preference)) {
            return;
        }
        super.onDisplayPreferenceDialog(preference);
    }
}
