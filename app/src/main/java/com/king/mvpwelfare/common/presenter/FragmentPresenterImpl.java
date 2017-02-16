package com.king.mvpwelfare.common.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.king.mvpwelfare.R;
import com.king.mvpwelfare.blog.presenter.BlogFragmentAdapter;
import com.king.mvpwelfare.blog.view.BlogFragmentViewImpl;
import com.king.mvpwelfare.common.contract.FragmentContract;
import com.king.mvpwelfare.common.view.FragmentViewImpl;
import com.king.mvpwelfare.image.view.ImgFragmentViewImpl;
import com.king.mvpwelfare.live.view.LiveFragmentViewImpl;
import com.king.mvpwelfare.main.view.MainViewImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16230 on 2016/10/27.
 */

public class FragmentPresenterImpl implements FragmentContract.FragmentPresenter {
    private FragmentViewImpl fragmentView;
    private String[] mTitles;
    public FragmentPresenterImpl(FragmentViewImpl fragmentView) {
        this.fragmentView = fragmentView;
    }

    @Override
    public String[] loadTitle(int mType) {
        if(mType == MainViewImpl.HOME){
            mTitles = fragmentView.getResources().getStringArray(R.array.tab_home);
        }else if(mType == MainViewImpl.LIVE){
            mTitles = fragmentView.getResources().getStringArray(R.array.tab_live);
        }else if(mType == MainViewImpl.BLOG){
            mTitles = fragmentView.getResources().getStringArray(R.array.tab_blog);
        }
        return mTitles;
    }
    //TODO   这块决定使用哪种格式填充pagerView
    @Override
    public void LoadChildFragment(int mMneu) {
        MyPagerAdapter adapter = new MyPagerAdapter(fragmentView.getChildFragmentManager());
        if(mMneu == MainViewImpl.HOME){
            for(int i = 0,j = mTitles.length;i<j;i++){
                adapter.addFragment(ImgFragmentViewImpl.newInstance(i),mTitles[i]);
            }
        }else if(mMneu == MainViewImpl.LIVE){
            for(int i = 0,j = mTitles.length;i<j;i++){
                adapter.addFragment(LiveFragmentViewImpl.newInstance(i),mTitles[i]);
            }
        }else if(mMneu == MainViewImpl.BLOG){
            for(int i = 0,j = mTitles.length;i<j;i++){
                adapter.addFragment(BlogFragmentViewImpl.newInstance(i),mTitles[i]);
            }
        }

        fragmentView.setAdapter(adapter);
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
