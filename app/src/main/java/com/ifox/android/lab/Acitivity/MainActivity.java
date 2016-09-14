package com.ifox.android.lab.Acitivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ifox.android.lab.R;
import com.ifox.android.lab.fragment.EduFragment;
import com.ifox.android.lab.fragment.NewsFragment;
import com.ifox.android.lab.fragment.VideoFragment;
import com.ifox.android.lab.utils.EduUtils;
import com.ifox.android.lab.utils.NewsUtils;
import com.ifox.android.lab.video.Activity.SearchActivity;

import static com.ifox.android.lab.R.id.menu_info;
import static com.ifox.android.lab.R.id.menu_logoff;
import static com.ifox.android.lab.R.id.toolbar;

/**
 * sher
 * 主活动，管理 fragment
 */
public class MainActivity extends AppCompatActivity {

    private NewsFragment nf;
    private EduFragment ef;
    private VideoFragment vf;
    private int firstSelectedPosition = 0;
    private NavigationView nologin_nav_view;
    private NavigationView nav_view_footer;
    private Context context = this;
    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 将 DrawerLayout 与 Toolbar 关联
        drawerLayout();

        // 顶部标题栏
        toolBar();

        // 侧边菜单
        navMenu();

        // 底部导航栏
        bottomBar();

        // 从服务器获取数据
        date();
    }

    // 接收从loginActivity传回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1){
            View nologin = nologin_nav_view.findViewById(R.id.noLogin);
            View login = nologin_nav_view.findViewById(R.id.login);
            TextView userName = (TextView) login.findViewById(R.id.tv_note);

            MenuItem menuInfo = nologin_nav_view.getMenu().findItem(menu_info);
            MenuItem menuLogOff = nologin_nav_view.getMenu().findItem(menu_logoff);
            String name = data.getStringExtra("userName");

            nologin.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            menuInfo.setVisible(true);
            menuLogOff.setVisible(true);
            userName.setText(name);
        }
    }

    private void drawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
    }

    private void toolBar(){
        mToolbar = (Toolbar) findViewById(toolbar);
        mToolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
        mToolbar.setTitle("lab");
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.inflateMenu(R.menu.menu);

        //action menu操作菜单按钮的点击事件
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.search){
                    Intent it = new Intent(MainActivity.this, SearchActivity.class);
                    MainActivity.this.startActivity(it);
                }
                return false;
            }
        });

        // 导航按钮开启侧边
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void navMenu(){
        nologin_nav_view = (NavigationView) findViewById( R.id.nologin_nav_view);
        View headerView = nologin_nav_view.getHeaderView(0);

        // 跳转登录界面
        ImageView login = (ImageView) headerView.findViewById(R.id.loginpage);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,LoginActivity.class);
                startActivityForResult(intent,1);
            }
        });

        // 设置未登录时隐藏Item
        MenuItem menuInfo = nologin_nav_view.getMenu().findItem(menu_info);
        MenuItem menuLogOff = nologin_nav_view.getMenu().findItem(menu_logoff);
        menuInfo.setVisible(false);
        menuLogOff.setVisible(false);

        // 设置item点击事件
        nologin_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case menu_info:
                        Intent it = new Intent(MainActivity.this, UserInfoActivity.class);
                        startActivity(it);
                        break;

                    case R.id.menu_logoff:
                        View nologin = nologin_nav_view.findViewById(R.id.noLogin);
                        View login = nologin_nav_view.findViewById(R.id.login);
                        nologin.setVisibility(View.VISIBLE);
                        login.setVisibility(View.GONE);
                        MenuItem menuInfo = nologin_nav_view.getMenu().findItem(menu_info);
                        MenuItem menuLogOff = nologin_nav_view.getMenu().findItem(menu_logoff);
                        menuInfo.setVisible(false);
                        menuLogOff.setVisible(false);

                        break;
                }
                return false;
            }
        });

        // 设置侧边底部点击退出应用
        nav_view_footer = (NavigationView) findViewById(R.id.nav_view_footer);
        nav_view_footer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                MainActivity.this.finish();
                return false;
            }
        });
    }

    private void bottomBar(){
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_new, " 公告 ").setActiveColor(R.color.mediumblue))
                .addItem(new BottomNavigationItem(R.drawable.ic_resources, " 教学资源 ").setActiveColor(R.color.purple))
                .addItem(new BottomNavigationItem(R.drawable.ic_video, " 教学视频 ").setActiveColor(R.color.orange))
                .setFirstSelectedPosition(firstSelectedPosition)
                .initialise();
        // 默认页设置
        setDefaultFragment();
        // 设置底部导航栏的切换
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                switch (position) {
                    case 0:
                        nf = new NewsFragment();
                        transaction.replace(R.id.fragment, nf);
                        mToolbarTitle.setText("公告");
                        break;

                    case 1:
                        ef = new EduFragment();
                        transaction.replace(R.id.fragment, ef);
                        mToolbarTitle.setText("教学资源");
                        break;

                    case 2:
                        vf = new VideoFragment();
                        transaction.replace(R.id.fragment, vf);
                        mToolbarTitle.setText("教学视频");
                        break;
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void date() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NewsUtils.getAllNewsForNetWork(getApplicationContext());
                EduUtils.getAllEduForNetWork(getApplicationContext());
            }
        }).start();
    }

    private void setDefaultFragment() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        NewsFragment nf = new NewsFragment();
        transaction.replace(R.id.fragment, nf);
        mToolbarTitle.setText("公告");
        transaction.commit();
    }
}