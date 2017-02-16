package com.king.mvpwelfare.common.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.king.mvpwelfare.R;
import com.king.mvpwelfare.common.contract.FragmentContract;
import com.king.mvpwelfare.common.presenter.FragmentPresenterImpl;
import com.king.mvpwelfare.main.view.MainViewImpl;


/**
 * Created by 16230 on 2016/10/27.
 * 当一个fragment重新创建时，系统会再次调用Fragment中的    默认构造函数
 * 所以   不能用构造函数传递参数
 */

public class FragmentViewImpl extends Fragment implements FragmentContract.FragmentView {
    private View mView;
    private TabLayout mTablayout;
    //左右切换的页卡
    private ViewPager mViewPager;
    // TabLayout中的tab标题
    private String[] mTitles;
    //表示哪个页面创建的fragment(默认home)
    private int mMenu = MainViewImpl.HOME;
    //presenter
    private FragmentContract.FragmentPresenter mFragmentPresenter;

    FragmentPresenterImpl.MyPagerAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentPresenter = new FragmentPresenterImpl(this);
        mMenu = getArguments().getInt("menu");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_content,container,false);
        mTablayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(1);
        AddTitles();
        mFragmentPresenter.LoadChildFragment(mMenu);
        mViewPager.setAdapter(adapter);
        mTablayout.setupWithViewPager(mViewPager);
        if(mMenu == MainViewImpl.HOME){
            mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        return mView;
    }
    public static FragmentViewImpl newInstance(int menu) {
         Bundle args = new Bundle();
         FragmentViewImpl fragment = new FragmentViewImpl();
         args.putInt("menu",menu);
         fragment.setArguments(args);
         return fragment;
    }

    @Override
    public void AddTitles() {
       mTitles =  mFragmentPresenter.loadTitle(mMenu);
    }

    public void setAdapter(FragmentPresenterImpl.MyPagerAdapter adapter) {
        this.adapter = adapter;
    }

}
