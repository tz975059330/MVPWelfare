package com.king.mvpwelfare.image.model;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.king.mvpwelfare.API.APIURL;
import com.king.mvpwelfare.image.contract.ImgFragmentContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.king.mvpwelfare.main.view.MainViewImpl.mQueue;

/**
 *   图片业务处理类
* Created by 16230 on 2016/10/27
*/

public class ImgFragmentModelImpl implements ImgFragmentContract.ImgFragmentModel {
    /**
     * 加载图片列表
     * @param url
     * @param listener
     */
    @Override
    public void loadImageList(String url, final OnloadImageListener listener) {

//       getTianGouList(url,listener)

        getHuaBanList(url,listener);
    }

    private void getHuaBanList(String url, final OnloadImageListener listener) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //花瓣网页识别
                List<ImageBean> imageBeanList = huaban.getList(response);

                listener.onSuccess(imageBeanList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure("load image failure",error);
            }
        });
        mQueue.add(jsonObjectRequest);
    }

    private void getTianGouList(String url, final OnloadImageListener listener){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //天狗接口
                List<ImageBean> imageBeanList = tiangou.getList(response);

                listener.onSuccess(imageBeanList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure("load image failure",error);
            }
        });
        mQueue.add(jsonObjectRequest);
    }
    public interface OnloadImageListener{
        void onSuccess(List<ImageBean> list);
        void onFailure(String msg,Exception e);
    }

    static class tiangou {
        public static final List<ImageBean> getList(JSONObject response){
            List<ImageBean> imageBeanList = new ArrayList<>();
            try {
                JSONArray tngou = response.getJSONArray("tngou");
                for (int i = 0; i < tngou.length(); i++) {
                    JSONObject data = tngou.getJSONObject(i);
                    ImageBean bean = new ImageBean();
                    bean.setId(data.getString("id"));
                    bean.setSize(data.getString("size"));
                    bean.setUrl(APIURL.IMAGE_ONE + data.getString("img"));
                    bean.setTitle(data.getString("title"));
                    imageBeanList.add(bean);
                }
            }catch (Exception e){

            }
            return imageBeanList;
        }
    }
    static class huaban{
        public static final List<ImageBean> getList(JSONObject response){
            List<ImageBean> imageBeanList = new ArrayList<>();
//            System.out.println(response);
            try {
                JSONArray array = response.getJSONArray("pins");
                for(int i = 0;i<array.length();i++){
                    ImageBean bean = new ImageBean();
//                    System.out.println("---------------------------------------------------------------------------------");
                    JSONObject object = array.getJSONObject(i);
                    JSONObject file =  object.getJSONObject("file");
                    String key = file.getString("key");
//                    System.out.println(key);
                    JSONObject board = object.getJSONObject("board");
                    String title = board.getString("title");
//                    System.out.println(title);
//                    System.out.println("---------------------------------------------------------------------------------");
                    bean.setKey(key);
                    bean.setTitle(title);
                    imageBeanList.add(bean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return imageBeanList;
        }
    }
}