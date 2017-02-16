package com.king.mvpwelfare.API;

/**
 * Created by 16230 on 2016/10/26.
 *
 *
 * 直播链接需要将$替换为直播房间号
 */

public class APIURL {
    //天狗图片链接需要补全的内容
    public static final String IMAGE_ONE = "http://tnfs.tngou.net/img";
    //天狗分类图片请求地址  返回同一分类下的所有封面
    public static final String IMAGE = "http://www.tngou.net/tnfs/api/list?id=";
    //天狗图片列表请求地址  返回一个图片集合
    public static final String IMAGE_LIST = "http://www.tngou.net/tnfs/api/show?id=";

    //花瓣列表地址
    public static final String HUABAN_TRAVEL = "http://api.huaban.com/favorite/travel_places";
    public static final String HUABAN_ANIME = "http://api.huaban.com/favorite/anime";
    public static final String HUABAN_FOOD = "http://api.huaban.com/favorite/food_drink";
    public static final String HUABAN_PETS = "http://api.huaban.com/favorite/pets";
    public static final String HUABAN_GAME = "http://api.huaban.com/favorite/games";
    public static final String HUABAN_BEAUTY = "http://api.huaban.com/favorite/beauty";
    public static final String HUABAN_CHILD = "http://api.huaban.com/favorite/kids";
    public static final String HUABAN_MEITU = "http://api.huaban.com/favorite/quotes";
    public static final String HUABAN_ACTOR = "http://api.huaban.com/favorite/people";
    public static final String HUABAN_LIWU = "http://api.huaban.com/favorite/desire";
    public static final String HUABAN_CAR = "http://api.huaban.com/favorite/cars_motorcycles";
    public static final String HUABAN_funny = "http://api.huaban.com/favorite/funny";

    //花瓣图片地址
    public static final String HUABAN_IMAGE = "http://img.hb.aicdn.com/";


    //直播链接
    public static final String LIVE_DOUYU = "https://m.douyu.com/html5/live?roomId=$";
    public static final String LIVE_XIONGMAO = "http://room.api.m.panda.tv/index.php?method=room.shareapi&roomid=$";
    public static final String LIVE_ZHANQI = "http://wshls.cdn.zhanqi.tv/zqlive/$/playlist.m3u8";
    public static final String LIVE_QUANMIN = "http://www.quanmin.tv/json/rooms/$/info.json";
    public static final String LIVE_LONGZHU = "http://livestreamcdn.plu.cn/live/GetLivePlayUrl?roomId=$";
    /**
     * 各个直播平台主站热门
     */
    public static final String DOUYU_HOME = "https://www.douyu.com/directory/columnRoom/game";
    public static final String XIONGMAO_HOME = "http://www.panda.tv/all";
    public static final String ZHANQI_HOME = "http://www.zhanqi.tv/api/static/v2.1/live/list/20/1.json";
    public static final String QUANMIN_HOME = "http://www.quanmin.tv/json/play/list.json";
    public static final String LONGZHU_HOME = "http://longzhu.com/channels/all";

    /**
     * 各个博客主站搜索内容json接口
     */
    public static final String CSDN_LIST = "http://ms.csdn.net/api/v2/search/blog?page=1&pagesize=20&key=面试";

    public static final String BOKEYUAN_LIST = "http://cnblogs.davismy.com/Handler.ashx?op=BlogSearch&key=面试";

    public static final String JIANSHU_LIST = "http://www.jianshu.com/search/do?q=面试&page=1&type=notes";

    public static final String JIANSHU_CONTENT = "http://www.jianshu.com/p/";
}
