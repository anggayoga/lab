package com.ifox.android.lab.Acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifox.android.lab.R;
import com.ifox.android.lab.bean.EduBean;
import com.ifox.android.lab.bean.DataHolder;

import static com.ifox.android.lab.R.id.toolbar;

/**
 * sher
 * 教学资源详情
 */
public class ShowEduActivity extends AppCompatActivity {

    private TextView et_title;
    private TextView et_content;
    private ImageView et_attachAddress;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_edu);

        // 设置标题栏
        toolBar();

        // 寻找控件
        init();

        // 为控件设置内容
        setContent();
    }

    private void toolBar() {
        mToolbar = (Toolbar) findViewById(toolbar);
        mToolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
        mToolbar.setTitle("教学资源");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        et_title = (TextView) findViewById(R.id.et_title);
        et_content = (TextView) findViewById(R.id.et_content);
        et_attachAddress = (ImageView) findViewById(R.id.et_attachAddress);
    }

    private void setContent() {
        EduBean eduBean = DataHolder.getInstance().getEduBeanData();
        et_title.setText(eduBean.et_title);
        et_content.setText(eduBean.et_content);
        et_attachAddress.setImageBitmap(eduBean.et_attachAddress);
    }

}