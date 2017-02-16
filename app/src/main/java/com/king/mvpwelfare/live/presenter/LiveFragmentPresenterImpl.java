package com.king.mvpwelfare.live.presenter;

import com.king.mvpwelfare.API.APIURL;
import com.king.mvpwelfare.live.contract.LiveFragmentContract;
import com.king.mvpwelfare.live.model.LiveBean;
import com.king.mvpwelfare.live.model.LiveFragmentModelImpl;
import com.king.mvpwelfare.live.view.LiveFragmentViewImpl;

import java.util.List;

/**
 * Created by 16230 on 2016/11/2.
 */

public class LiveFragmentPresenterImpl implements LiveFragmentContract.LiveFragmentPresenter,LiveFragmentModelImpl.OnloadLiveListListener {

    private LiveFragmentContract.LiveFragmentView fragmentView;
    private LiveFragmentContract.LiveFragmentModel liveFragmentModel;
    public LiveFragmentPresenterImpl(LiveFragmentViewImpl fragmentView) {
        this.fragmentView = fragmentView;
        this.liveFragmentModel = new LiveFragmentModelImpl();
    }

    @Override
    public void loadImag(int type) {
        String url = getUrl(type);
        fragmentView.showProgress();
        liveFragmentModel.loadLiveList(url,type,this);
    }

    /**
     * 根据type 返回不同的直播主站（获取到的是热门主播的全部信息）
     * 默认返回熊猫的主站信息
     * @param type
     * @return
     */
    private String getUrl(int type) {
        if(type == LiveFragmentViewImpl.DOUYU){
            return APIURL.DOUYU_HOME;
        }else if(type == LiveFragmentViewImpl.XIONGMAO){
            return APIURL.XIONGMAO_HOME;
        }else if(type == LiveFragmentViewImpl.ZHANQI){
            return APIURL.ZHANQI_HOME;
        }else if(type == LiveFragmentViewImpl.QUANMIN){
            return APIURL.QUANMIN_HOME;
        }else if(type == LiveFragmentViewImpl.LONGZHU){
            return APIURL.LONGZHU_HOME;
        }
        return APIURL.XIONGMAO_HOME;
    }

    @Override
    public void onSuccess(List<LiveBean> list) {
        if(null == fragmentView){
            return;
        }
        fragmentView.hideProgress();
        fragmentView.addLiveList(list);
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
