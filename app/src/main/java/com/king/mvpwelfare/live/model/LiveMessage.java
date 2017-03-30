package com.king.mvpwelfare.live.model;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.king.mvpwelfare.API.APIURL;
import com.king.mvpwelfare.live.view.LiveFragmentViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.king.mvpwelfare.main.view.MainViewImpl.mQueue;

/**
 * Created by 16230 on 2016/11/4.
 */

public class LiveMessage {

    public void DouYuList(String response, final LiveFragmentModelImpl.OnloadLiveListListener listener) {
        List<LiveBean> list = new ArrayList<>();
        Document html = Jsoup.parse(response);
        Element parent = html.getElementById("live-list-contentbox");
        Elements childen = parent.children();
        for (int i = 0; i < 20; i++) {
            LiveBean bean = new LiveBean();
            Element li = childen.get(i);
            Element a = li.child(0);
            Element span = a.select("span.imgbox").first();
            Element p = a.select("div.mes").first().select("p").first();
            bean.setIdRoom(li.attr("data-rid"));
            bean.setLiveTitle(a.attr("title"));
            bean.setRoomImage(span.select("img").first().attr("data-original"));
            bean.setLiveName(p.children().get(0).text());
            bean.setPeople(p.children().get(1).text());
            bean.setType(LiveFragmentViewImpl.DOUYU);
            list.add(bean);
        }
        DouYuTwoRequest(list, listener);
    }

    public void DouYuTwoRequest(final List<LiveBean> list, final LiveFragmentModelImpl.OnloadLiveListListener
            listener) {
        final List<LiveBean> MyList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String url = APIURL.LIVE_DOUYU.replace("$", list.get(i).getIdRoom());
            final int finalI = i;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        LiveBean bean = list.get(finalI);
                        JSONObject data = response.getJSONObject("data");
                        bean.setLiveUrl(data.getString("hls_url"));
                        MyList.add(bean);
                        if (finalI == list.size() - 1) {
                            listener.onSuccess(MyList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(jsonObjectRequest);
        }
    }

    public void XiongMaoList(String response, final LiveFragmentModelImpl.OnloadLiveListListener listener) {
        List<LiveBean> list = new ArrayList<>();
        Document html = Jsoup.parse(response);
        Element parent = html.getElementById("later-play-list");
        Elements childen = parent.children();
        for (int i = 0; i < 20; i++) {
            LiveBean bean = new LiveBean();
            Element li = childen.get(i);
            bean.setIdRoom(li.attr("data-id"));
            list.add(bean);
        }
        XiongMaoTworequest(list, listener);
    }

    public void XiongMaoTworequest(final List<LiveBean> list, final LiveFragmentModelImpl.OnloadLiveListListener
            listener) {
        final List<LiveBean> MyList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String url = APIURL.LIVE_XIONGMAO.replace("$", list.get(i).getIdRoom());
            final int finalI = i;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        LiveBean bean = new LiveBean();
                        JSONObject data = response.getJSONObject("data");
                        JSONObject hostInfo = data.getJSONObject("hostinfo");
                        JSONObject roomInfo = data.getJSONObject("roominfo");
                        JSONObject pictures = roomInfo.getJSONObject("pictures");
                        JSONObject videoInfo = data.getJSONObject("videoinfo");
                        String url = videoInfo.getString("address");
                        if (url.contains("_small")) {
                            url = url.replace("_small", "");
                        }
                        bean.setLiveName(hostInfo.getString("name"));
                        bean.setIdRoom(roomInfo.getString("id"));
                        bean.setLiveTitle(roomInfo.getString("name"));
                        bean.setPeople(roomInfo.getString("person_num"));
                        bean.setRoomImage(pictures.getString("img"));
                        bean.setLiveUrl(url);
                        bean.setType(LiveFragmentViewImpl.XIONGMAO);
                        MyList.add(bean);
                        if (finalI == list.size() - 1) {
                            listener.onSuccess(MyList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(jsonObjectRequest);
        }
    }

    public void ZhanQiList(String response, final LiveFragmentModelImpl.OnloadLiveListListener listener) {
                try {
                    List<LiveBean> list = new ArrayList<>();
                    JSONObject myJson = new JSONObject(response);
                    JSONObject data = myJson.getJSONObject("data");
                    JSONArray rooms = data.getJSONArray("rooms");
                    for(int i = 0;i<rooms.length();i++){
                        LiveBean bean = new LiveBean();
                        JSONObject one  = (JSONObject) rooms.get(i);
                        String url  = APIURL.LIVE_ZHANQI.replace("$",one.getString("videoId"));
                        bean.setType(LiveFragmentViewImpl.ZHANQI);
                        bean.setLiveUrl(url);
                        bean.setPeople(one.getString("online"));
                        bean.setIdRoom(one.getString("code"));
                        bean.setLiveName(one.getString("nickname"));
                        bean.setLiveTitle(one.getString("title"));
                        bean.setRoomImage(one.getString("bpic"));
                        list.add(bean);
//                        bean.toString();
                    }
                    listener.onSuccess(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
    }

    public void QuanMinList(String response, final LiveFragmentModelImpl.OnloadLiveListListener listener) {
        try {
            List<LiveBean> list = new ArrayList<>();
            JSONObject MyJsonobject = new JSONObject(response);
            JSONArray data = MyJsonobject.getJSONArray("data");
            for(int i = 0;i<20;i++){
                LiveBean bean = new LiveBean();
                JSONObject room = (JSONObject) data.get(i);
                bean.setPeople(room.getString("view"));
                bean.setRoomImage(room.getString("thumb"));
                bean.setLiveTitle(room.getString("title"));
                bean.setLiveName(room.getString("nick"));
                bean.setIdRoom(room.getString("uid"));
                bean.setType(LiveFragmentViewImpl.QUANMIN);
                list.add(bean);
            }
            QuanMinTwoRequest(list,listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取单个房间播放详细
     * @param list
     * @param listener
     */
    public void QuanMinTwoRequest(final List<LiveBean> list, final LiveFragmentModelImpl.OnloadLiveListListener
            listener) {
        final List<LiveBean> MyList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String url = APIURL.LIVE_QUANMIN.replace("$", list.get(i).getIdRoom());
            final int finalI = i;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        LiveBean bean = list.get(finalI);
                        JSONArray array = response.getJSONArray("room_lines");
                        JSONObject live = (JSONObject) array.get(0);
                        JSONObject select = live.getJSONObject("flv");
                        JSONObject url = select.getJSONObject("5");
                        bean.setLiveUrl(url.getString("src"));
                        MyList.add(bean);
                        if (finalI == list.size() - 1) {
                            listener.onSuccess(MyList);
                        }
                    } catch (JSONException e) {
                        System.out.println("发生错误的id"+list.get(finalI).getIdRoom());
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(jsonObjectRequest);
        }

    }

    public void LongZhuList(String response, final LiveFragmentModelImpl.OnloadLiveListListener listener) {
        List<LiveBean> list = new ArrayList<>();
        Document html = Jsoup.parse(response);
        Element parent = html.getElementById("list-con");
        Elements childen = parent.children();
        for (int i = 0; i < 20; i++) {
            LiveBean bean = new LiveBean();
            Element a = childen.get(i);
            String[] array = a.attr("id").split("-");
            Element img = a.select("img.livecard-thumb").first();
            Element ul = a.select("ul.livecard-meta").first();
            Element span = a.select("span.livecard-modal").first();
            Element li = ul.children().first();
            bean.setPeople(li.child(1).text());
            bean.setType(LiveFragmentViewImpl.LONGZHU);
            bean.setIdRoom(array[1]);
            bean.setLiveName(span.child(0).text());
            bean.setLiveTitle(img.attr("alt"));
            bean.setRoomImage(img.attr("src"));
            list.add(bean);
        }
        LongZhuTwoRequest(list,listener);
    }

    public void LongZhuTwoRequest(final List<LiveBean> list, final LiveFragmentModelImpl.OnloadLiveListListener
            listener) {
        final List<LiveBean> MyList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String url = APIURL.LIVE_LONGZHU.replace("$", list.get(i).getIdRoom());
            final int finalI = i;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        LiveBean bean = list.get(finalI);
                        JSONArray playLines = response.getJSONArray("playLines");
                        JSONObject one = (JSONObject) playLines.get(0);
                        JSONArray urls = one.getJSONArray("urls");
                        JSONObject url = (JSONObject) urls.get(0);
                        bean.setLiveUrl(url.getString("securityUrl"));
                        MyList.add(bean);
                        if (finalI == list.size() - 1) {
                            listener.onSuccess(MyList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(jsonObjectRequest);
        }
    }

}
