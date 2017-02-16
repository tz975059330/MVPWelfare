package com.king.mvpwelfare.image.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.king.mvpwelfare.R;
import com.king.mvpwelfare.image.contract.ImgFragmentContract;
import com.king.mvpwelfare.image.model.ImageBean;
import com.king.mvpwelfare.image.presenter.ImgFragmentAdapter;
import com.king.mvpwelfare.image.presenter.ImgFragmentPresenterImpl;
import com.king.mvpwelfare.main.view.MainViewImpl;
import com.king.mvpwelfare.main.view.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16230 on 2016/10/27.
 */

public class ImgFragmentViewImpl extends Fragment implements ImgFragmentContract.ImgFragmentView, SwipeRefreshLayout
        .OnRefreshListener {


    private SwipeRefreshLayout mSwipeRefrshLayout;
    private RecyclerView mReclerView;
    private StaggeredGridLayoutManager mLayoutManger;
    private int mType = 0;
    private ImgFragmentAdapter mAdapter;
    private ImgFragmentContract.ImgFragmentPresenter mImgPresenter;
    private static final int COUNT_LAYOUT = 2;
    private List<ImageBean> mData;
    private static boolean firstFlag = true;
    public static ImgFragmentViewImpl newInstance(int type) {
        Bundle args = new Bundle();
        ImgFragmentViewImpl fragment = new ImgFragmentViewImpl();
        args.putInt("type", type);
        if(!firstFlag){
            cleanGlideMemory();
        }
        firstFlag = false;
        fragment.setArguments(args);
        return fragment;
    }

    private static void cleanGlideMemory() {
        Glide.get(MyApplication.context).clearMemory();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImgPresenter = new ImgFragmentPresenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item,container,false);

        mSwipeRefrshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swiperefreshlayout);
        mSwipeRefrshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.main_blue_dark, R.color
                .colorPrimaryDark);
        mSwipeRefrshLayout.setOnRefreshListener(this);

        mReclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mReclerView.setHasFixedSize(true);

        mLayoutManger = new StaggeredGridLayoutManager(COUNT_LAYOUT,StaggeredGridLayoutManager.VERTICAL);
        mReclerView.setLayoutManager(mLayoutManger);

        mReclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ImgFragmentAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mReclerView.setAdapter(mAdapter);
        //动态加载，
//        mReclerView.addOnScrollListener(mOnScrollListener);
        showProgress();
        onRefresh();
        return view;
    }
    private ImgFragmentAdapter.OnItemClickListener mOnItemClickListener = new ImgFragmentAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            ImageBean Image = mAdapter.getItemByPosition(position);
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view
                    .getHeight() / 2, 0, 0);
            Intent intent = new Intent(MyApplication.context, DetailActivity.class);
//            view.buildDrawingCache();
//            Bitmap bt = Bitmap.createBitmap(view.getDrawingCache());
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bt.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] byteArray = stream.toByteArray();
            intent.putExtra("image", Image);
            ActivityCompat.startActivity(MainViewImpl.mContext, intent, compat.toBundle());
        }
    };
//    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
//
//        private int lastVisibleItem;
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//        }
//
//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//        }
//    };
    @Override
    public void onRefresh() {
        if(mData!=null){
            mData.clear();
        }
        mImgPresenter.loadImag(mType);
    }

    @Override
    public void showProgress() {
        mSwipeRefrshLayout.setRefreshing(true);
    }

    @Override
    public void addImage(List<ImageBean> list) {
        if(mData == null){
            mData  = new ArrayList<ImageBean>();
        }
        mData.addAll(list);
        mAdapter.setmData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgress() {
        mSwipeRefrshLayout.setRefreshing(false);

    }

    @Override
    public void showLoadFialMsg() {
        View view = getActivity() == null ? mReclerView.getRootView() : getActivity().findViewById(R.id.id_drawerlayout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }
}
