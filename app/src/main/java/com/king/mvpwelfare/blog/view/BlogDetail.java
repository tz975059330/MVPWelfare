package com.king.mvpwelfare.blog.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.king.mvpwelfare.R;
import com.king.mvpwelfare.blog.model.blogBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

import static com.king.mvpwelfare.main.view.MainViewImpl.mQueue;


/**
 * Created by 16230 on 2016/12/8.
 */

public class BlogDetail extends AppCompatActivity {
    //自定义toolbar
    private Toolbar mToolbar;
    private TextView contentTextView;
    private blogBean mdata;
    Handler mHandler;
    private String html;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_detail);
        Intent intent = this.getIntent();
        mdata = (blogBean) intent.getSerializableExtra("blog");
        mToolbar = (Toolbar) findViewById(R.id.toolbar_blog);
        mToolbar.setTitle(mdata.getTitle());
        setSupportActionBar(mToolbar);
        contentTextView = (TextView) findViewById(R.id.blog_text_content);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if((int)msg.obj == 123){
                    contentTextView.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
                    contentTextView.setText(Html.fromHtml(html));
                }
            }
        };
        System.out.println(mdata.getUrl());
       if(mdata.getType() == 0){
           csdnDetail();
       }else if(mdata.getType() == 1){
           bokeyuanDetail();
       }else if(mdata.getType() == 2){
            jianshuDetail();
       }
    }

    private void jianshuDetail(){
        StringRequest request = new StringRequest(mdata.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                Element element = document.select("div.show-content").first();
                if(element==null ){
                    html = "解析失败";
                }else if(element!=null){
                    html = element.html();
                }
                Message msg = new Message();
                msg.obj = 123;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
                return map;
            }
        };
        mQueue.add(request);
    }

    private void bokeyuanDetail() {
        StringRequest request = new StringRequest(mdata.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                Element element = document.getElementById("cnblogs_post_body");
                if(element==null){
                    html = "解析失败";
                }else if(element!=null){
                    html = element.html();
                }
                Message msg = new Message();
                msg.obj = 123;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
                return map;
            }
        };
        mQueue.add(request);
    }

    private void csdnDetail() {
        StringRequest request = new StringRequest(mdata.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                Element element = document.getElementsByAttribute("markdown_views").first();
                Element element1 = document.getElementById("article_content");
                if(element==null && element1==null){
                    html = "解析失败";
                }else if(element!=null){
                    html = element.html();
                    System.out.println("markdown_views-------------------------------------");
                }else if(element1!=null){
                    html = element1.html();
                    System.out.println("article_content-------------------------------------");
                }
                Message msg = new Message();
                msg.obj = 123;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Host","blog.csdn.net");
                map.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
                return map;
            }
        };
        mQueue.add(request);
    }
}
