package com.king.mvpwelfare.main.presenter;


import com.king.mvpwelfare.R;
import com.king.mvpwelfare.main.contract.MainContract;

/**
* Created by 16230 on 2016/10/26
*/

public class MainPresenterImpl implements MainContract.MainPresenter {
    private MainContract.MainView mMainView;
    public MainPresenterImpl(MainContract.MainView mMainView){
        this.mMainView = mMainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id){
            case R.id.nav_menu_home:
                mMainView.switchHome();
                break;
            case R.id.nav_menu_live:
                mMainView.switchLive();
                break;
            case R.id.nav_menu_blog:
                mMainView.switchBlog();
                break;
            default:
                mMainView.switchHome();
                break;
        }
    }
}