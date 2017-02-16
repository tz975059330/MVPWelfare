package com.king.mvpwelfare.main.contract;

/**
 * Created by 16230 on 2016/10/26.
 */

public interface MainContract {


     interface MainModel{

    }
     interface MainView{
        void switchHome();
        void switchLive();
        void switchBlog();
    }
     interface MainPresenter {
        void switchNavigation(int id);
    }
}

