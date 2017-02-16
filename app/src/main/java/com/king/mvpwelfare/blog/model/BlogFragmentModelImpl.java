package com.king.mvpwelfare.blog.model;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.king.mvpwelfare.API.APIURL;
import com.king.mvpwelfare.blog.contract.BlogFragmentContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.king.mvpwelfare.main.view.MainViewImpl.mQueue;

/**
 * Created by 16230 on 2016/11/2.
 */

public class BlogFragmentModelImpl implements BlogFragmentContract.BlogFragmentModel{
    public static final int CSDN = 0;
    public static final int BOKEYUAN = 1;
    public static final int JIANSHU = 2;
    public static int CSDNNUM = 1;
    public static int JIANSHUNUM = 1;

    @Override
    public void loadBlogList(final int type, final OnloadBlogListListener listener) {
        String url = getURL(type);
        if(type == JIANSHU){
            getJianShu(listener,url);
        }else {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        List list = new ArrayList<blogBean>();
                        if (type == CSDN) {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                blogBean data = new blogBean();
                                data.setNickName(object.getString("nick_name"));
                                data.setTitle(object.getString("title"));
                                data.setViewPeople(object.getString("views"));
                                data.setUrl(object.getString("url"));
                                data.setType(CSDN);
                                list.add(data);
                            }
                        } else if (type == BOKEYUAN) {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                blogBean data = new blogBean();
                                data.setNickName(object.getString("author"));
                                String title = object.getString("title");
                                title = title.replace("<strong>","");
                                title = title.replace("</strong>","");
                                data.setTitle(title);
                                data.setViewPeople(object.getString("hit"));
                                data.setUrl(object.getString("blog_url"));
                                data.setType(BOKEYUAN);
                                list.add(data);
                            }
                        }
                        listener.onSuccess(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    listener.onFailure("加载失败", new Exception());
                }
            });
            mQueue.add(jsonObjectRequest);
        }
    }

    private void getJianShu(final OnloadBlogListListener listener, String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                    System.out.println(response);
                List list = new ArrayList<blogBean>();
                JSONArray array = null;
                try {
                    array = response.getJSONArray("entries");
                for(int i =0;i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    JSONObject user = object.getJSONObject("user");
                    blogBean data = new blogBean();
                    data.setNickName(user.getString("nickname"));
                    String title = object.getString("title");
                    title = title.replace("<em class='search-result-highlight'>","");
                    title = title.replace("</em>","");
                    data.setTitle(title);
                    data.setViewPeople(object.getString("views_count"));
                    data.setUrl(APIURL.JIANSHU_CONTENT + object.getString("slug"));
                    data.setType(JIANSHU);
                    list.add(data);
                }
                listener.onSuccess(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                listener.onFailure("加载失败", new Exception());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Accept","application/json, text/javascript, */*; q=0.3");
                map.put("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
                map.put("X-Requested-With","XMLHttpRequest");

//                map.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
//                map.put("Accept-Encoding","gzip, deflate");
//               map.put("Referer","http://www.jianshu.com/search?q=面试&page=1&type=notes");
//               map.put("X-CSRF-Token","3QDYjsjDRz9RAWRZJh+V/1Vfyiahg/6wGSfvF3Bu/kTbuRXfq/CG1OmSBZHN6MhAlKz8w9+m74qA8UX+vMstVg==");
//               map.put("Connection","keep-alive");
//               map.put("Host","www.jianshu.com");
                return map;
            }
        };
        mQueue.add(jsonObjectRequest);
    }

    public String getURL(int type){
        if(type == CSDN){
            String csdn = APIURL.CSDN_LIST.replace("page=1","page="+CSDNNUM++);
            if(CSDNNUM == 18){
                CSDNNUM = 1;
            }
            return csdn;
        }else if(type == BOKEYUAN){
            return APIURL.BOKEYUAN_LIST;
        }else if(type == JIANSHU){
            String jianshu = APIURL.JIANSHU_LIST.replace("page=1","page="+JIANSHUNUM++);
            return jianshu;
        }
        return APIURL.CSDN_LIST;
    }
    public interface OnloadBlogListListener {
        void onSuccess(List<blogBean> list);

        void onFailure(String msg, Exception e);
    }
}
