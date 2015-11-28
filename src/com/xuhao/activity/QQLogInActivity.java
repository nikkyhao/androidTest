package com.xuhao.activity;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

import com.xuhao.R;
import com.xuhao.R.id;
import com.xuhao.R.layout;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xuhao.application.MyApplication;
import com.xuhao.javaBean.User;
import com.xuhao.utility.Util;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
//这个文件处理头像的那个部分还是有问题
public class QQLogInActivity extends Activity {
    MyApplication mApplication = null;
   
    
    Button logButton = null;
    ImageView userLogo = null;
    TextView usernameTextView = null;
    RadioGroup gender,usertype = null;
    RadioButton maleButton,femaleButton = null;
    
    TextView openidTextView = null;
    private Tencent mTencent;

    //下面是用户信息
    Bitmap bitmap = null;//用户头像
    public String openidString, nicknameString,usernameString, genderString, 
    
    portraitURL="http://file.bmob.cn/M01/AA/F2/oYYBAFW8RoSATpIEAAAkoWxDSBc961.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
	setContentView(R.layout.activity_qq_login);
	mApplication = (MyApplication) getApplication();
	userLogo = (ImageView) findViewById(R.id.QQimage);//qq头像显示框
	usernameTextView = (TextView) findViewById(R.id.usernamecontent);//用户名输入框，作为登录的凭据
	gender = (RadioGroup)findViewById(R.id.register_sex);//性别选择框
	usertype = (RadioGroup)findViewById(R.id.register_usertype);//用户身份选择框
	logButton = (Button) findViewById(R.id.login);//登录按钮
	maleButton = (RadioButton)findViewById(R.id.sex_male);
	femaleButton = (RadioButton)findViewById(R.id.sex_female);
	
	openidTextView = (TextView)findViewById(R.id.openid);//显示openid，测试用的
	addLogButtonListener();
	LogIn();
	QQLogIn();
    }

    public void LogIn() {
	String mAppid = "1104708623"; // 第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
	mTencent = Tencent.createInstance(mAppid, getApplicationContext());
	/**
	 * 通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO
	 * 是一个String类型的字符串，表示一些权限 官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE =
	 * “get_user_info,add_t”；所有权限用“all”
	 * 第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类
	 */
	mTencent.login(QQLogInActivity.this, "all", new BaseUiListener());
    }

    private class BaseUiListener implements IUiListener {

	public void onCancel() {
	    // TODO Auto-generated method stub

	}

	public void onComplete(Object response) {
	    // TODO Auto-generated method stub
	    Toast.makeText(getApplicationContext(), "QQ登录成功", 0).show();
	    try {
		// 获得的数据是JSON格式的，获得你想获得的内容
		// 如果你不知道你能获得什么，看一下下面的LOG
		Log.e("QQ", "第一次的response内容：" + response.toString());
		openidString = ((JSONObject) response).getString("openid");// 这个是用户的唯一标识
		Log.e("QQ", "openid:" + openidString);
		openidTextView.setText(openidString);
		// access_token= ((JSONObject)
		// response).getString("access_token"); //expires_in =
		// ((JSONObject) response).getString("expires_in");
	    } catch (org.json.JSONException e) {// 这里注意！，有两个包都有这个Exception类
		e.printStackTrace();
	    }
	    /**
	     * 到此已经获得OpneID以及其他你想获得的内容了
	     * QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
	     * sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
	     * 如何得到这个UserInfo类呢？
	     */
	    UserInfo info = new UserInfo(getApplicationContext(),
		    mTencent.getQQToken());
	    // 这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON

	    info.getUserInfo(new IUiListener() {
		public void onComplete(final Object response) {
		    // TODO Auto-generated method stub
		    Log.e("QQ", "---------------111111" + response.toString());
		    //初始化nickname、头像、openid
		    JSONObject object = (JSONObject)response;
			try {
			    nicknameString = object.getString("nickname");
			    genderString = object.getString("gender");
			} catch (JSONException e) {
			    e.printStackTrace();
			}
		    Message msg = new Message();
		    msg.obj = response;
		    msg.what = 0;
		    mHandler.sendMessage(msg);
		    Log.e("QQ", "-----111---" + response.toString());
		    /**
		     * 由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接 在mHandler里进行操作
		     */
		    new Thread() {
			@Override
			public void run() {
			    // TODO Auto-generated method stub
			    JSONObject json = (JSONObject) response;
			    try {
				portraitURL = json.getString("figureurl_qq_2");//下载用户头像，小头像
				Log.i("figureqq2", portraitURL);
				bitmap = Util.getbitmap(portraitURL);
			    } catch (org.json.JSONException e) {
				e.printStackTrace();
			    }
			    Message msg = new Message();
			    msg.obj = bitmap;
			    msg.what = 1;//1代表图片
			    mHandler.sendMessage(msg);
			}
		    }.start();
		}

		public void onCancel() {
		    Log.e("QQ", "--------------111112");
		    // TODO Auto-generated method stub
		}

		public void onError(UiError arg0) {
		    // TODO Auto-generated method stub
		    Log.e("QQ", "-111113" + ":" + arg0);
		}

	    });

	}

	public void onError(UiError arg0) {
	    // TODO Auto-generated method stub

	}

    }

    Handler mHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    if (msg.what == 0) {//文字部分
		if(genderString.equals("男")){
		    maleButton.setChecked(true);
		}else if (genderString.equals("女")) {
		    maleButton.setChecked(true);
		}
	    
	    }
	   else if (msg.what == 1) {
		Bitmap bitmap = (Bitmap) msg.obj;
		userLogo.setImageBitmap(bitmap);

	    }
	}

    };

    private void addLogButtonListener() {
	logButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		RegisterAndLogin();
	    }

	    private void RegisterAndLogin() {
		// TODO Auto-generated method stub
		// 查询成功，说明是第一次登录
		    Util.showToast(QQLogInActivity.this, "用户第一次登录");
		    User user = new User();
		    File file = null;
		    file = new File("http://file.bmob.cn/M02/C2/C5/oYYBAFZZlDyAIQrTAABIeLd9tDI941.png");
		    Log.d("BmobFile","path："+file.getPath()+"name"+file.getName());
		    user.setQqOpenId(openidString);
		    user.setUserName(usernameTextView.getText().toString());
		    user.setSex(genderString);
		    user.setNickName(nicknameString);
		    user.save(QQLogInActivity.this, new SaveListener() {
			@Override
			public void onSuccess() {
				Util.showToast(QQLogInActivity.this, "添加成功");
			}
			@Override
			public void onFailure(int arg0, String arg1) {
			    Util.showToast(QQLogInActivity.this, "添加失败，错误信息："+arg1);
			}
		});
		    mApplication.setPresentUser(user);
		    Intent intent = new Intent(QQLogInActivity.this,
			    MainActivity.class);
		    startActivity(intent);// 进入主界面
	    }
	});
    }

    private void QQLogIn() {
	String sqlString = "select * from User where qqOpenID ='"
		+ openidString + "'";
	BmobQuery<User> query = new BmobQuery<User>();
	query.doSQLQuery(QQLogInActivity.this, sqlString, new LogInListener());
    }

    class LogInListener extends SQLQueryListener<User> {
	@Override
	public void done(BmobQueryResult<User> result, BmobException e) {
	    List<User> user_list = null;
	    if (e == null) {
		user_list = (List<User>) result.getResults();
		if (user_list != null && user_list.size() > 0) {//// 查询成功，说明以前QQ登录过
		    // 将当前用户作为全局变量存储起来
		    Util.showToast(QQLogInActivity.this, "老用户");
		    mApplication.setPresentUser(user_list.get(0));
		    Util.showToast(QQLogInActivity.this, "登录成功,用户："
			    + user_list.get(0).getUserName());
		    Intent intent = new Intent(QQLogInActivity.this,
			    MainActivity.class);
		    startActivity(intent);// 进入主界面
		} else {//新用户
		    Util.showToast(QQLogInActivity.this, "新用户");
		}
	    } else {// 查询失败，出现异常
		Util.showToast(QQLogInActivity.this, "错误码:" + e.getErrorCode()
			+ "错误描述" + e.getMessage());
	    }

	}
    }
}
