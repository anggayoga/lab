package com.ifox.android.lab;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ifox.android.lab.fragment.EduFragment;
import com.ifox.android.lab.fragment.NewsFragment;
import com.ifox.android.lab.fragment.VideoFragment;

/**
 * 主活动，管理 fragment
 */
public class MainActivity extends AppCompatActivity {

    private NewsFragment nf;

    private EduFragment ef;

    private VideoFragment vf;

    private int firstSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 底部导航栏
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, " 公告 ").setActiveColor(R.color.mediumblue))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, " 教学资源 ").setActiveColor(R.color.purple))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, " 教学视频 ").setActiveColor(R.color.orange))
                .setFirstSelectedPosition(firstSelectedPosition)
                .initialise();

        setDefaultFragment();
        // 设置底部导航栏的切换
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

                FragmentManager fm = getFragmentManager();

                FragmentTransaction transaction = fm.beginTransaction();

                switch (position) {
                    case 0:
                        nf = new NewsFragment();
                        transaction.replace(R.id.fragment, nf);
                        break;
                    case 1:
                        ef = new EduFragment();
                        transaction.replace(R.id.fragment, ef);
                        break;
                    case 2:
                        vf = new VideoFragment();
                        transaction.replace(R.id.fragment, vf);
                        break;
                    default:
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

    // 默认页设置
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        NewsFragment nf = new NewsFragment();
        transaction.replace(R.id.fragment, nf);
        transaction.commit();
    }
}