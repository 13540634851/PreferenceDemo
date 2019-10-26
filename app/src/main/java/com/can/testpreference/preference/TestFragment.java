package com.can.testpreference.preference;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.can.testpreference.R;
import com.can.testpreference.VerticalTextView;

public class TestFragment extends PreferenceFragmentCompat {
    private static final String COLOR_SLECT_PREF = "key2";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.menu_list);
    }

    @Override
    protected void onBindPreferences() {
        super.onBindPreferences();
        ColorSelectPreference colorSelectPreference = findPreference(COLOR_SLECT_PREF);
        int[] colors = new int[]{
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorBlue,
                R.color.colorYellow
        };
        colorSelectPreference.addColorEntryAndDefaultColor(colors, R.color.colorAccent);
        colorSelectPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (preference instanceof ColorSelectPreference) {
                    ColorSelectPreference colorSelectPreference1 = (ColorSelectPreference) preference;
                }
                return false;
            }
        });


        CanEditTextPreference editTextPreference = findPreference("key1");
        VerticalTextView view = getActivity().findViewById(R.id.show);
        if (view == null) {
            throw new RuntimeException("====????????????==========");
        }
        editTextPreference.setShowView(view);
    }
}
