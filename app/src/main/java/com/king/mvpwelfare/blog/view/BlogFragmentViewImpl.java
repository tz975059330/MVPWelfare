package com.king.mvpwelfare.blog.view;

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
import com.king.mvpwelfare.blog.contract.BlogFragmentContract;
import com.king.mvpwelfare.blog.model.blogBean;
import com.king.mvpwelfare.blog.presenter.BlogFragmentAdapter;
import com.king.mvpwelfare.blog.presenter.BlogFragmentPresenterImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 16230 on 2016/11/2.
 */

public class BlogFragmentViewImpl extends Fragment implements BlogFragmentContract.BlogFragmentView,SwipeRefreshLayout.OnRefreshListener{


    private BlogFragmentPresenterImpl mPresenter;
    private SwipeRefreshLayout mSwipeRefrshLayout;
    private RecyclerView mReclerView;
    private GridLayoutManager mLayoutManger;
    private BlogFragmentAdapter mAdapter;
    private static final int COUNT_LAYOUT = 1;
    private int mType = 0;
    private List<blogBean> mData;
    private blogBean blogData;

    public static BlogFragmentViewImpl newInstance(int type){
        Bundle args = new Bundle();
        BlogFragmentViewImpl fragment = new BlogFragmentViewImpl();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new BlogFragmentPresenterImpl(this);
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
        mReclerView.setHasFixedSize(true);

        mLayoutManger = new GridLayoutManager(getContext(), COUNT_LAYOUT);
        mReclerView.setLayoutManager(mLayoutManger);

        mReclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new BlogFragmentAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mReclerView.setAdapter(mAdapter);
        onRefresh();
        return view;
    }
    private BlogFragmentAdapter.OnItemClickListener mOnItemClickListener = new BlogFragmentAdapter
            .OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            blogData = mAdapter.getItemByPosition(position);
            //TODO
            Intent intent = new Intent(getActivity(),BlogDetail.class);
            intent.putExtra("blog",blogData);
            startActivity(intent);
        }
    };

    @Override
    public void onRefresh() {
        if(mData!=null){
            mData.clear();
        }
        mPresenter.loadMsg(mType);
        mAdapter.notifyDataSetChanged();
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
    public void addBlogList(List<blogBean> list) {
        if(mData == null){
            mData = new ArrayList<blogBean>();
        }
        mData.addAll(list);
        mAdapter.setData(mData);
        mAdapter.notifyDataSetChanged();
    }
}
