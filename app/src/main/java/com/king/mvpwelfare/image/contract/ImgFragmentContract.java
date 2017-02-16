package com.king.mvpwelfare.image.contract;

import com.king.mvpwelfare.image.model.ImageBean;
import com.king.mvpwelfare.image.model.ImgFragmentModelImpl;

import java.util.List;

/**
 * Created by 16230 on 2016/10/27.
 */

public interface ImgFragmentContract {
     interface ImgFragmentModel{
        void loadImageList(String url, ImgFragmentModelImpl.OnloadImageListener listener);
    }
     interface ImgFragmentPresenter {
        void loadImag(int type);
    }

     interface ImgFragmentView{
         void showProgress();
         void addImage(List<ImageBean> list);
         void hideProgress();
         void showLoadFialMsg();
    }
}
