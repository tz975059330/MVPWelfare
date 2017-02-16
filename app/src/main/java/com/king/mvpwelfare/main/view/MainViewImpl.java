package com.king.mvpwelfare.main.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.king.mvpwelfare.R;
import com.king.mvpwelfare.common.view.FragmentViewImpl;
import com.king.mvpwelfare.main.contract.MainContract;
import com.king.mvpwelfare.main.presenter.MainPresenterImpl;


/**
 * Created by 16230 on 2016/10/26.
 */

public class MainViewImpl extends AppCompatActivity implements MainContract.MainView {

    //网络请求队列
    public static final RequestQueue mQueue = Volley.newRequestQueue(MyApplication.context);
    public static Activity mContext;
    public static final int HOME = 0;
    public static final int LIVE = 1;
    public static final int BLOG = 2;
    //侧滑菜单
    private DrawerLayout mDrawerLayout;
    //自定义toolbar
    private Toolbar mToolbar;
    //侧边栏
    private NavigationView mNavigationView;
    //指示器
    private ActionBarDrawerToggle mDrawerToggle;
    //MainPresenter
    private MainContract.MainPresenter mMainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MainViewImpl.this;
        setContentView(R.layout.activity_main);
        //初始化toolbar并设置给activity
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //初始化mDrawerLayout和开关指示器，并将两者关联
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        //mNavigationView初始化
        mNavigationView = (NavigationView) findViewById(R.id.id_navigationview);
        //设置mNavigationView 中的菜单项被点击之后的操作
        setupDrawerContent(mNavigationView);
        //实例化一个MainPresenter接口的实现类
        mMainPresenter = new MainPresenterImpl(this);

        //默认选中博客栏
        switchBlog();
        mNavigationView.setCheckedItem(R.id.nav_menu_blog);
    }
    /**
     * 点击侧滑菜单中的选项，选中然后关闭侧滑菜单
     *
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mMainPresenter.switchNavigation(menuItem.getItemId());
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //清空Volley网络请求队列
                
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_main, FragmentViewImpl.newInstance
                (HOME)).commit();
        mToolbar.setTitle(R.string.home);
    }

    @Override
    public void switchLive() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_main, FragmentViewImpl.newInstance
                (LIVE)).commit();
        mToolbar.setTitle(R.string.live);
    }

    @Override
    public void switchBlog() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_main, FragmentViewImpl.newInstance
                (BLOG)).commit();
        mToolbar.setTitle(R.string.blog);
    }

}
