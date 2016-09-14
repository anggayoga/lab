package com.ifox.android.lab.Acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifox.android.lab.R;
import com.ifox.android.lab.bean.NewsBean;
import com.ifox.android.lab.bean.DataHolder;

import static com.ifox.android.lab.R.id.toolbar;

/**
 * sher
 * 公告详情
 */
public class ShowNewsActivity extends AppCompatActivity {

    private TextView n_title;
    private TextView n_content;
    private ImageView n_attachAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        // 设置标题栏
        toolBar();

        // 寻找控件
        init();

        // 为控件设置内容
        setContent();
    }

    private void toolBar() {
        Toolbar mToolbar = (Toolbar) findViewById(toolbar);
        mToolbar.setTitle("公告");
        mToolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init(){
        n_title = (TextView) findViewById(R.id.n_title);
        n_content = (TextView) findViewById(R.id.n_content);
        n_attachAddress = (ImageView) findViewById(R.id.n_attachAddress);
    }

    private void setContent() {
        NewsBean newsBean = DataHolder.getInstance().getNewsBeanData();
        n_title.setText(newsBean.n_title);
        n_content.setText(newsBean.n_content);
        n_attachAddress.setImageBitmap(newsBean.n_attachAddress);
    }
}
