package com.king.mvpwelfare.common.contract;

/**
 * Created by 16230 on 2016/10/27.
 */

public interface FragmentContract {

     interface FragmentModel{

    }
     interface FragmentView{
        void  AddTitles();
    }
     interface FragmentPresenter{
        String[] loadTitle(int mType);
        void LoadChildFragment(int mMneu);
    }
}
