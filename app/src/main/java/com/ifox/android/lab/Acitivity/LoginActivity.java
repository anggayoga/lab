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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// 设置标题栏
		toolBar();

		// 寻找控件
		init();

		// 注册、登录点击事件
		click();

		// 开启数据库
		openDb();
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

	private void toolBar() {mToolbar = (Toolbar) findViewById(toolbar);
		mToolbar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);
		mToolbar.setTitle("登录");
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
		mAccount = (EditText) findViewById(R.id.login_edit_account);
		mPwd = (EditText) findViewById(R.id.login_edit_pwd);
		mLoginButton = (Button) findViewById(R.id.login_btn_login);
		mRegister = (TextView) findViewById(R.id.register);
	}

	private void click() {
		mRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,RegisterActivity.class);
				startActivity(intent);
			}
		});
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//判断用户名和密码是否与数据库一致，设置控件是否可见
				login();
			}
		});
	}

	private void openDb() {
		if (mUserDataManager == null) {
			mUserDataManager = new UserDataManager(this);
			mUserDataManager.openDataBase();
		}
	}

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

}