package com.king.mvpwelfare.live.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.king.mvpwelfare.R;
import com.king.mvpwelfare.live.contract.LiveFragmentContract;
import com.king.mvpwelfare.live.model.LiveBean;
import com.king.mvpwelfare.live.presenter.LiveFragmentAdapter;
import com.king.mvpwelfare.live.presenter.LiveFragmentPresenterImpl;
import com.king.mvpwelfare.main.view.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16230 on 2016/11/2.
 */

public class LiveFragmentViewImpl extends Fragment implements LiveFragmentContract.LiveFragmentView,
        SwipeRefreshLayout.OnRefreshListener {
    /**
     * 分页内容，与array中的顺序对应
     */
    public static final int DOUYU = 0;
    public static final int XIONGMAO = 1;
    public static final int ZHANQI = 2;
    public static final int QUANMIN = 3;
    public static final int LONGZHU = 4;
    private LiveFragmentPresenterImpl mLivePresenter;
    private SwipeRefreshLayout mSwipeRefrshLayout;
    private RecyclerView mReclerView;
    private GridLayoutManager mLayoutManger;
    private LiveFragmentAdapter mAdapter;
    private static final int COUNT_LAYOUT = 2;
    private int mType = 0;
    private List<LiveBean> mData;
    private LiveBean liveData;
    public static LiveFragmentViewImpl newInstance(int type) {
        Bundle args = new Bundle();
        LiveFragmentViewImpl fragment = new LiveFragmentViewImpl();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLivePresenter = new LiveFragmentPresenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        mSwipeRefrshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swiperefreshlayout);
        mSwipeRefrshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.main_blue_dark, R.color
                .colorPrimaryDark);
        mSwipeRefrshLayout.setOnRefreshListener(this);

        mReclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
//        mReclerView.setHasFixedSize(true);

        mLayoutManger = new GridLayoutManager(getContext(), COUNT_LAYOUT);
        mReclerView.setLayoutManager(mLayoutManger);

        mReclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new LiveFragmentAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mReclerView.setAdapter(mAdapter);
        onRefresh();
        return view;
    }

    private LiveFragmentAdapter.OnItemClickListener mOnItemClickListener = new LiveFragmentAdapter
            .OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
             liveData = mAdapter.getItemByPosition(position);
            //TODO 点击加载直播链接
            Intent intent = new Intent(MyApplication.context,LiveActivity.class);
            intent.putExtra("data",liveData);
            startActivity(intent);
        }
    };
    @Override
    public void onRefresh() {
        if (mData != null) {
            mData.clear();
        }
        mLivePresenter.loadImag(mType);
    }


    @Override
    public void showProgress() {
        mSwipeRefrshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefrshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFialMsg() {
        View view = getActivity() == null ? mReclerView.getRootView() : getActivity().findViewById(R.id
                .id_drawerlayout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void addLiveList(List<LiveBean> list) {
        if (mData == null) {
            mData = new ArrayList<LiveBean>();
        }
        mData.addAll(list);
        mAdapter.setmData(mData);
        mAdapter.notifyDataSetChanged();
    }
}
