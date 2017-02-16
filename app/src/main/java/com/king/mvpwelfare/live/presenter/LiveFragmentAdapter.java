package com.king.mvpwelfare.live.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.king.mvpwelfare.R;
import com.king.mvpwelfare.live.model.LiveBean;

import java.util.List;

/**
 * Created by 16230 on 2016/11/2.
 */

public class LiveFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private List<LiveBean> mData;
    private Context mContext;
    public LayoutInflater mLayoutInflater;

    public LiveFragmentAdapter(Context applicationContext) {
        this.mContext = applicationContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            final LiveBean liveData = mData.get(position);
            if(liveData == null){
                return;
            }
            Glide.with(mContext)
                    .load(liveData.getRoomImage())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            ((ItemViewHolder) holder).mImage.setImageBitmap(resource);
                            ((ItemViewHolder) holder).mTitle.setText(liveData.getLiveTitle());
                            ((ItemViewHolder) holder).mName.setText(liveData.getLiveName());
                            ((ItemViewHolder) holder).mPeople.setText(liveData.getPeople());
                        }
                    });
        }
    }
    @Override
    public int getItemCount() {
        if(mData == null){
            return 0;
        }
        return mData.size();
    }

    public LiveBean getItemByPosition(int position) {
        return mData == null ? null : mData.get(position);
    }

    public void setmData(List<LiveBean> list) {
        this.mData = list;
    }

    public interface OnItemClickListener {
         void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public ImageView mImage;
        public TextView mName;
        public TextView mPeople;
        public ItemViewHolder(View v) {
            super(v);
            mImage = (ImageView) v.findViewById(R.id.id_network_view);
            mTitle = (TextView) v.findViewById(R.id.text_image);
            mName = (TextView) v.findViewById(R.id.text_name_live);
            mPeople = (TextView) v.findViewById(R.id.text_people_live);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }
}
