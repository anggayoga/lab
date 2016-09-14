package com.ifox.android.lab.Acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ifox.android.lab.R;

import static com.ifox.android.lab.R.id.toolbar;

/**
 * Created by 10368 on 2016/9/14.
 */
public class UserInfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        mToolbar = (Toolbar) findViewById(toolbar);
        mToolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
        mToolbar.setTitle("个人资料");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arror);
        mToolbar.inflateMenu(R.menu.userinfo_menu);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //action menu操作菜单按钮的点击事件
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.edit_userinfo){
                    Intent it=new Intent(UserInfoActivity.this, UserInfoEditActivity.class);
                    UserInfoActivity.this.startActivity(it);
                }
                return false;
            }
        });
    }
}
