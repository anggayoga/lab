package com.ifox.android.lab.Acitivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ifox.android.lab.R;
import com.ifox.android.lab.bean.UserData;
import com.ifox.android.lab.dao.UserDataManager;

import static com.ifox.android.lab.R.id.toolbar;

/**
 * Created by 10368 on 2016/9/8.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText mAccount;
    private EditText mPwd;
    private Button mRegisterButton;
    private UserDataManager mUserDataManager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 设置标题栏
        toolBar();

        // 寻找控件
        init();

        // 注册点击事件
        click();

        // 开启数据库
        openDb();
    }

    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (mUserDataManager != null) {
            mUserDataManager.closeDataBase();
            mUserDataManager = null;
        }
        super.onPause();
    }

    private void toolBar() {
        mToolbar = (Toolbar) findViewById(toolbar);
        mToolbar.setTitle("注册");
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

    private void init() {
        mAccount = (EditText) findViewById(R.id.register_edit_account);
        mPwd = (EditText) findViewById(R.id.register_edit_pwd);
        mRegisterButton = (Button) findViewById(R.id.register_btn_register);
    }

    private void click() {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void openDb() {
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();
        }
    }

    public void register() {
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            int count = mUserDataManager.findUserByName(userName);
            if (count > 0) {
                Toast.makeText(this, getString(R.string.name_already_exist, userName),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            UserData mUser = new UserData(userName, userPwd);
            mUserDataManager.openDataBase();
            long flag = mUserDataManager.insertUserData(mUser);
            if (flag == -1) {
                Toast.makeText(this, getString(R.string.register_fail),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.register_sucess),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
