package com.king.mvpwelfare.blog.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.mvpwelfare.R;
import com.king.mvpwelfare.blog.model.blogBean;
import java.util.List;

/**
 * Created by 16230 on 2016/12/8.
 */

public class BlogFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private  OnItemClickListener mOnItemClickListener;
    private List<blogBean> mData;
    private Context mContext;
    public LayoutInflater mLayoutInflater;
    public BlogFragmentAdapter(Context applicationContext) {
        this.mContext = applicationContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_blog,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            blogBean blogdata = mData.get(position);
            if(blogdata==null){
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(blogdata.getTitle());
            ((ItemViewHolder) holder).mName.setText(blogdata.getNickName());
            ((ItemViewHolder) holder).mView.setText(blogdata.getViewPeople());

        }
    }

    @Override
    public int getItemCount() {
        if(mData==null){
            return 0;
        }
        return mData.size();
    }
    public void setData(List<blogBean> list){
        this.mData = list;
    }
    public blogBean getItemByPosition(int position) {
        return mData == null ? null : mData.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(BlogFragmentAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public TextView mName;
        public TextView mView;
        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.text_page);
            mName = (TextView) v.findViewById(R.id.text_author);
            mView = (TextView) v.findViewById(R.id.text_view);
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
