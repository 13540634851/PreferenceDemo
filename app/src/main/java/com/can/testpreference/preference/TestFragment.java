package com.can.testpreference.preference;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

import com.can.testpreference.R;
import com.can.testpreference.view.VerticalTextView;

public class TestFragment extends PreferenceFragmentCompat {
    private static final String COLOR_SLECT_PREF = "key2";
    private static final String KEY_FONT_SIZE = "key_seekbar";
    private SeekBarPreference mFontSizePreference;
    private SeekBarPreference mPaddingPreference;
    private SeekBarPreference mSpacingPreference;
    private VerticalTextView mDrawView;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.menu_list);
    }

    @Override
    protected void onBindPreferences() {
        super.onBindPreferences();

        CanEditTextPreference editTextPreference = findPreference("key1");
        mDrawView = getActivity().findViewById(R.id.show);
        if (mDrawView == null) {
            throw new RuntimeException("====????????????==========");
        }
        editTextPreference.setShowView(mDrawView);


        mFontSizePreference = findPreference(KEY_FONT_SIZE);
        mFontSizePreference.setMin(60);
        mFontSizePreference.setMax(180);
        mFontSizePreference.setShowSeekBarValue(true);
        mFontSizePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (mDrawView != null) {
                    mDrawView.setTextSize((Integer) newValue);
                }
                return true;
            }
        });

        mPaddingPreference = findPreference("key_padding");
        mPaddingPreference.setMin(0);
        mPaddingPreference.setMax(30);
        mPaddingPreference.setShowSeekBarValue(true);
        mPaddingPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (mDrawView != null) {
                    mDrawView.setPadding((Integer) newValue);
                }
                return true;
            }
        });


        mSpacingPreference = findPreference("key_spacing");
        mSpacingPreference.setMin(0);
        mSpacingPreference.setMax(30);
        mSpacingPreference.setShowSeekBarValue(true);
        mSpacingPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (mDrawView != null) {
                    mDrawView.setSpacing((Integer) newValue);
                }
                return true;
            }
        });
    }
}
