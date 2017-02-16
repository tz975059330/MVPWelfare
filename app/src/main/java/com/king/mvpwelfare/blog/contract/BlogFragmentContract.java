package com.king.mvpwelfare.blog.contract;

import com.king.mvpwelfare.blog.model.BlogFragmentModelImpl;
import com.king.mvpwelfare.blog.model.blogBean;

import java.util.List;

/**
 * Created by 16230 on 2016/11/2.
 */

public interface BlogFragmentContract {
    interface BlogFragmentModel{
        void loadBlogList(int type, BlogFragmentModelImpl.OnloadBlogListListener listener);
    }
    interface BlogFragmentView{
        void showProgress();
        void hideProgress();
        void showLoadFialMsg();
        void addBlogList(List<blogBean> list);
    }
    interface BlogFragmentPresenter{
        void loadMsg(int type);
    }
}
