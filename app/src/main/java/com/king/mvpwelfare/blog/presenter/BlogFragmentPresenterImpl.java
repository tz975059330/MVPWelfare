package com.king.mvpwelfare.blog.presenter;


import com.king.mvpwelfare.blog.contract.BlogFragmentContract;
import com.king.mvpwelfare.blog.model.BlogFragmentModelImpl;
import com.king.mvpwelfare.blog.model.blogBean;
import com.king.mvpwelfare.blog.view.BlogFragmentViewImpl;

import java.util.List;

/**
 * Created by 16230 on 2016/11/2.
 */

public class BlogFragmentPresenterImpl implements BlogFragmentContract.BlogFragmentPresenter,BlogFragmentModelImpl.OnloadBlogListListener{

    private BlogFragmentContract.BlogFragmentView fragmentView;
    private BlogFragmentContract.BlogFragmentModel blogFragmentModel;
    public BlogFragmentPresenterImpl(BlogFragmentViewImpl fragmentView){
        this.fragmentView = fragmentView;
        this.blogFragmentModel = new BlogFragmentModelImpl();
    }


    @Override
    public void loadMsg(int type) {
        if(null == fragmentView){
            return;
        }
        fragmentView.showProgress();
        blogFragmentModel.loadBlogList(type,this);
    }

    @Override
    public void onSuccess(List<blogBean> list) {
        if(null == fragmentView){
            return;
        }
        fragmentView.hideProgress();
        fragmentView.addBlogList(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        if(null == fragmentView){
            return;
        }
        fragmentView.hideProgress();
        fragmentView.showLoadFialMsg();
    }
}
