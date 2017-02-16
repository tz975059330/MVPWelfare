package com.king.mvpwelfare.image.presenter;


import com.king.mvpwelfare.API.APIURL;
import com.king.mvpwelfare.image.contract.ImgFragmentContract;
import com.king.mvpwelfare.image.model.ImageBean;
import com.king.mvpwelfare.image.model.ImgFragmentModelImpl;
import com.king.mvpwelfare.image.view.ImgFragmentViewImpl;

import java.util.List;

/**
* Created by 16230 on 2016/10/27
*/

public class ImgFragmentPresenterImpl implements ImgFragmentContract.ImgFragmentPresenter, ImgFragmentModelImpl.OnloadImageListener {

    private ImgFragmentContract.ImgFragmentView imgFragmentView;
    private ImgFragmentContract.ImgFragmentModel imgFragmentModel;
    public ImgFragmentPresenterImpl(ImgFragmentViewImpl imgFragmentView) {
        this.imgFragmentView = imgFragmentView;
        this.imgFragmentModel = new ImgFragmentModelImpl();
    }

    @Override
    public void loadImag(int type) {
        String url = null;
        if(type == 0){
            url = APIURL.HUABAN_TRAVEL;
        }else if(type == 1){
            url = APIURL.HUABAN_ANIME;
        }else if(type == 2){
            url = APIURL.HUABAN_FOOD;
        }else if(type == 3){
            url = APIURL.HUABAN_PETS;
        }else if(type == 4){
            url = APIURL.HUABAN_GAME;
        }else if(type == 5){
            url = APIURL.HUABAN_BEAUTY;
        }else if(type == 6){
            url = APIURL.HUABAN_CHILD;
        }else if(type == 7){
            url = APIURL.HUABAN_MEITU;
        }else if(type == 8){
            url = APIURL.HUABAN_ACTOR;
        }else if(type == 9){
            url = APIURL.HUABAN_LIWU;
        }else if(type == 10){
            url = APIURL.HUABAN_CAR;
        }else if(type == 11){
            url = APIURL.HUABAN_funny;
        }else {
            url = APIURL.HUABAN_TRAVEL;
        }
        imgFragmentModel.loadImageList(url,this);
    }


    @Override
    public void onSuccess(List<ImageBean> list) {
        if(null == imgFragmentView){
            return;
        }
        imgFragmentView.hideProgress();
        imgFragmentView.addImage(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        if(null == imgFragmentView){
            return;
        }
        imgFragmentView.hideProgress();
        imgFragmentView.showLoadFialMsg();
    }
}