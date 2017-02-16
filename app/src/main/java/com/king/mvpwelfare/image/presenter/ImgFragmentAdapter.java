package com.king.mvpwelfare.image.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.king.mvpwelfare.API.APIURL;
import com.king.mvpwelfare.R;
import com.king.mvpwelfare.image.model.ImageBean;

import org.w3c.dom.ls.LSException;

import java.util.List;

/**
 * Created by 16230 on 2016/10/29.
 */

public class ImgFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private List<ImageBean> mData;
    public LayoutInflater mLayoutInflater;
    public ImgFragmentAdapter(Context applicationContext) {
        this.mContext = applicationContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);

    }
    public void setmData(List<ImageBean> list){
        this.mData = list;
        this.notifyDataSetChanged();
    }
    public ImageBean getItemByPosition(int position){
        return mData == null ?null: mData.get(position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ImageBean images = mData.get(position);
            if(images == null){
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(images.getTitle());
            Glide.with(mContext)
                    .load(APIURL.HUABAN_IMAGE+images.getKey())
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_image_loadfail)
                    .crossFade()
                    .into(((ItemViewHolder) holder).mImage);
        }
    }

    @Override
    public int getItemCount() {
        if(mData==null){
            return 0;
        }
        return mData.size();
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public ImageView mImage;
        public RelativeLayout relativeLayout;
        public ItemViewHolder(View v) {
            super(v);
            mImage = (ImageView) v.findViewById(R.id.id_network_view);
            mTitle = (TextView) v.findViewById(R.id.text_image);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.Relativelaout_msg);
            //设置不可见，同时不占用空间
            relativeLayout.setVisibility(View.GONE);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }
}
