package com.can.testpreference.preference;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.can.testpreference.R;
import com.can.testpreference.VerticalTextView;

public class CanEditTextPreference extends Preference implements View.OnClickListener {
    private Context mContext;
    private EditText edittext;

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

    VerticalTextView showView;

    public void setShowView(VerticalTextView showView) {
        this.showView = showView;
        if (edittext != null && showView != null) {
            showView.setText(edittext.getText().toString());
        }
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        final EditText edittext = holder.itemView.findViewById(R.id.edit_text);
        holder.itemView.findViewById(R.id.direction).setOnClickListener(this);
        holder.itemView.findViewById(R.id.left).setOnClickListener(this);
        holder.itemView.findViewById(R.id.center).setOnClickListener(this);
        holder.itemView.findViewById(R.id.right).setOnClickListener(this);
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (showView != null) {
                    if (s == null) {
                        showView.setText("");
                    } else {
                        showView.setText(s.toString());
                    }
                }
            }
        });

        if (edittext != null && showView != null) {
            showView.setText(edittext.getText().toString());
        }

        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewParent parent = v.getParent();
                if (v instanceof EditText) {
                    EditText editView = (EditText) v;
                    if (editView.getMaxLines() < editView.getLineCount()) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    } else {
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.center:
                showView.setGravity(Gravity.CENTER);
                break;
            case R.id.left:
                showView.setGravity(Gravity.START);
                break;
            case R.id.right:
                showView.setGravity(Gravity.END);
                break;
            case R.id.direction:
                showView.setHorizontal(!showView.isHorizontal());
                break;
            default:
        }
    }
}
