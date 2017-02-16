package com.king.mvpwelfare.live.model;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.king.mvpwelfare.live.contract.LiveFragmentContract;
import com.king.mvpwelfare.live.view.LiveFragmentViewImpl;

import java.util.List;

import static com.king.mvpwelfare.main.view.MainViewImpl.mQueue;

/**
 * Created by 16230 on 2016/11/2.
 */

public class LiveFragmentModelImpl implements LiveFragmentContract.LiveFragmentModel {


    @Override
    public void loadLiveList(String url, final int type, final OnloadLiveListListener listener) {
        final StringRequest String = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getMsg2HTML(response, type, listener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure("加载数据失败", error);
            }
        });
        mQueue.add(String);
    }

    @Override
    public void getMsg2HTML(String response, int type, final OnloadLiveListListener listener) {
        LiveMessage liveMessage = new LiveMessage();
        if (type == LiveFragmentViewImpl.DOUYU) {
            liveMessage.DouYuList(response, listener);
        } else if (type == LiveFragmentViewImpl.XIONGMAO) {
            liveMessage.XiongMaoList(response, listener);
        } else if (type == LiveFragmentViewImpl.ZHANQI) {
            liveMessage.ZhanQiList(response, listener);
        } else if (type == LiveFragmentViewImpl.QUANMIN) {
            liveMessage.QuanMinList(response, listener);
        } else if (type == LiveFragmentViewImpl.LONGZHU) {
            liveMessage.LongZhuList(response, listener);
        }
    }

    public interface OnloadLiveListListener {
        void onSuccess(List<LiveBean> list);

        void onFailure(String msg, Exception e);
    }


}
