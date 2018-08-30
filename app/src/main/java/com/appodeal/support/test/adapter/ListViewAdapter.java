package com.appodeal.support.test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appodeal.ads.NativeAd;
import com.appodeal.support.test.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private List<NativeAd> mNativeList;
    private Context mContext;

    public ListViewAdapter(@NonNull Context context, List<NativeAd> list) {
        this.mNativeList = list;
        this.mContext = context;
    }

    public void updateData(List<NativeAd> list) {
        this.mNativeList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mNativeList == null)
            return 0;
        else return mNativeList.size();
    }

    @Override
    public Object getItem(int position) {

        return mNativeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NativeAd nativeAd = mNativeList.get(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_native_ads, parent, false);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            TextView tvDescription = view.findViewById(R.id.tv_description);
            RatingBar ratingBar = view.findViewById(R.id.rb_rating);
            Button ctaButton = view.findViewById(R.id.b_cta);
            ImageView image = view.findViewById(R.id.icon);
            TextView tvAgeRestrictions = view.findViewById(R.id.tv_age_restriction);

            tvTitle.setText(nativeAd.getTitle());
            tvDescription.setText(nativeAd.getDescription());

            if (nativeAd.getRating() == 0) {
                ratingBar.setVisibility(View.INVISIBLE);
            } else {
                ratingBar.setVisibility(View.VISIBLE);
                ratingBar.setRating(nativeAd.getRating());
                ratingBar.setStepSize(0.1f);
            }

            ctaButton.setText(nativeAd.getCallToAction());

            image.setImageBitmap(nativeAd.getIcon());

            if (nativeAd.getAgeRestrictions() != null) {
                tvAgeRestrictions.setText(nativeAd.getAgeRestrictions());
                tvAgeRestrictions.setVisibility(View.VISIBLE);
            } else {
                tvAgeRestrictions.setVisibility(View.GONE);
            }
            nativeAd.registerViewForInteraction((ViewGroup) view);
            view.setVisibility(View.VISIBLE);
        } else {
            view = convertView;
        }
        return view;
    }

}
