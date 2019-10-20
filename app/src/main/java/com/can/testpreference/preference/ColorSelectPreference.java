package com.can.testpreference.preference;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.can.testpreference.R;

public class ColorSelectPreference extends Preference {
    private Context mContext;
    private RecyclerView recyclerView;
    private int[] ids;
    private NormalAdapter normalAdapter;
    private int selectedColorId;

    public ColorSelectPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayout(context);
    }

    public ColorSelectPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public ColorSelectPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public ColorSelectPreference(Context context) {
        super(context);
        initLayout(context);
    }


    private void initLayout(Context context) {
        mContext = context;
        setLayoutResource(R.layout.color_layout);
    }


    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        recyclerView = holder.itemView.findViewById(R.id.color_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        normalAdapter = new NormalAdapter();
        recyclerView.setAdapter(normalAdapter);
    }


    public void addColorEntryAndDefaultColor(int[] colorResIds, int selectedColorId) {
        ids = colorResIds;
        this.selectedColorId = selectedColorId;
    }

    public int getSelectedColorId() {
        return this.selectedColorId;
    }

    private class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.Holder> {


        private class Holder extends RecyclerView.ViewHolder {
            private ImageView selectedView;
            private ImageView colorView;
            private int colorid;

            public Holder(View v) {
                super(v);
                selectedView = v.findViewById(R.id.select_view);
                colorView = v.findViewById(R.id.color_view);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedColorId = colorid;
                        NormalAdapter.this.notifyDataSetChanged();
                        getOnPreferenceClickListener().onPreferenceClick(ColorSelectPreference.this);
                    }
                });
            }

            public void setColor(int colorid) {
                this.colorid = colorid;
                Drawable circleDrawable = mContext.getDrawable(R.drawable.ic_circle);
                Drawable ringDrawable = mContext.getDrawable(R.drawable.ic_ring);
                circleDrawable.setTint(mContext.getColor(colorid));
                ringDrawable.setTint(mContext.getColor(colorid));
                colorView.setImageDrawable(circleDrawable);
                selectedView.setImageDrawable(ringDrawable);
            }

            public void updateStatus() {
                if (colorid == selectedColorId) {
                    selectedView.setVisibility(View.VISIBLE);
                } else {
                    selectedView.setVisibility(View.INVISIBLE);
                }
            }
        }


        @Override
        public void onBindViewHolder(Holder holder, int position) {
            if (ids == null || position > ids.length) {
                return;
            }
            holder.setColor(ids[position]);
            holder.updateStatus();
        }

        @Override
        public int getItemCount() {
            if (ids != null) {
                return ids.length;
            } else {
                return 0;
            }
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_layout, parent, false);
            return new Holder(v);
        }
    }

}
