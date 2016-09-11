package com.ifox.android.lab.Acitivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ifox.android.lab.R;
import com.ifox.android.lab.dao.UserDataManager;

import static com.ifox.android.lab.R.id.toolbar;

public class LoginActivity extends AppCompatActivity {

	private Context context = this;
	private TextView mRegister;
	private EditText mAccount;
	private EditText mPwd;
	private Button mLoginButton;
	private UserDataManager mUserDataManager;
	private Toolbar mToolbar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mAccount = (EditText) findViewById(R.id.login_edit_account);
		mPwd = (EditText) findViewById(R.id.login_edit_pwd);
		mLoginButton = (Button) findViewById(R.id.login_btn_login);
		mRegister = (TextView) findViewById(R.id.register);

		Toolbar mToolbar = (Toolbar) findViewById(toolbar);
		mToolbar.setTitle("登录");
		mToolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		mRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,RegisterActivity.class);
				startActivity(intent);
			}
		});

		//登录点击事件
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});

		//开启数据库
		if (mUserDataManager == null) {
			mUserDataManager = new UserDataManager(this);
			mUserDataManager.openDataBase();
        }
	}

	//判断用户名和密码是否与数据库一致，设置控件是否可见
	public void login() {
		if (isUserNameAndPwdValid()) {
			String userName = mAccount.getText().toString().trim();
			String userPwd = mPwd.getText().toString().trim();
			int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
			if(result==1){
				//login success
				Toast.makeText(this, getString(R.string.login_sucess),Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("dl",1);
				intent.putExtra("userName", userName);
				setResult(1, intent);
				finish();
			}else if(result==0){
				//login failed,user does't exist
				Toast.makeText(this, getString(R.string.login_fail),
						Toast.LENGTH_SHORT).show();
			}
		}
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
}