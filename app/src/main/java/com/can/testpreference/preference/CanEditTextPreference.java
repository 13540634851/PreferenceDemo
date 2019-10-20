package com.can.testpreference.preference;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;

import com.can.testpreference.R;

public class CanEditTextPreference extends Preference {
    private Context mContext;

    public CanEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayout(context);
    }

    public CanEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public CanEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public CanEditTextPreference(Context context) {
        super(context);
        initLayout(context);
    }

    private void initLayout(Context context) {
        mContext = context;
        setLayoutResource(R.layout.edit_preference_layout);
    }


}
