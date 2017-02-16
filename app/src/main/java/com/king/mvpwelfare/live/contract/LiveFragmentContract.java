package com.king.mvpwelfare.live.contract;

import com.king.mvpwelfare.live.model.LiveBean;
import com.king.mvpwelfare.live.model.LiveFragmentModelImpl;

import java.util.List;

/**
 * Created by 16230 on 2016/11/2.
 */

public interface LiveFragmentContract {
     interface LiveFragmentModel{
         void loadLiveList(String url,int type, LiveFragmentModelImpl.OnloadLiveListListener listener);
         void getMsg2HTML(String url, int type, LiveFragmentModelImpl.OnloadLiveListListener listener);
     }
     interface LiveFragmentView{
         void showProgress();
         void hideProgress();
         void showLoadFialMsg();
         void addLiveList(List<LiveBean> list);
    }
     interface LiveFragmentPresenter{
         void loadImag(int type);
    }
}
