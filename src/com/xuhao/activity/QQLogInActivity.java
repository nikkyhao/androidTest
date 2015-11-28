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
import android.widget.TextView;
import android.widget.Toast;
//这个文件处理头像的那个部分还是有问题
public class QQLogInActivity extends Activity {
    MyApplication mApplication = null;

    // QQ头像
    Bitmap bitmap = null;
    Button logButton = null;
    ImageView userLogo = null;
    TextView openidTextView = null;
    TextView nicknameTextView = null;
    TextView genderTextView = null;
    private Tencent mTencent;
    public String openidString, nicknameString, genderString, 
    portraitURL="http://file.bmob.cn/M01/AA/F2/oYYBAFW8RoSATpIEAAAkoWxDSBc961.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
	setContentView(R.layout.activity_qq_login);
	mApplication = (MyApplication) getApplication();
	userLogo = (ImageView) findViewById(R.id.QQimage);
//	openidTextView = (TextView) findViewById(R.id.openidcontent);
	nicknameTextView = (TextView) findViewById(R.id.nicknamecontent);
//	genderTextView = (TextView) findViewById(R.id.gendercontent);
	logButton = (Button) findViewById(R.id.login);
	addLogButtonListener();
	LogIn();
    }

    public void LogIn() {
	System.out.println("Login Button");
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
				portraitURL = json.getString("figureurl_qq_2");
				bitmap = Util.getbitmap(portraitURL);
				Log.i("figureqq1", portraitURL);
			    } catch (org.json.JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
			    Message msg = new Message();
			    msg.obj = bitmap;
			    msg.what = 1;
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
	    if (msg.what == 0) {
		JSONObject response = (JSONObject) msg.obj;
		if (response.has("nickname")) {
		    try {
			nicknameString = response.getString("nickname");
			nicknameTextView.setText(nicknameString);
		    } catch (org.json.JSONException e) {
			e.printStackTrace();
		    }
		    Log.e("QQ", "nickname" + nicknameString);
		}
		if (response.has("gender")) {
		    try {
			genderString = response.getString("gender");
			genderTextView.setText(genderString);
		    } catch (org.json.JSONException e) {
			e.printStackTrace();
		    }
		    Log.e("QQ", "gender:" + genderString);

		}
	    } else if (msg.what == 1) {
		Bitmap bitmap = (Bitmap) msg.obj;
		userLogo.setImageBitmap(bitmap);

	    }
	}

    };

    private void addLogButtonListener() {
	logButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		QQLogIn();
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
	    if (e == null) {// 查询成功，说明以前QQ登录过
		// showToast("查询成功");
		user_list = (List<User>) result.getResults();
		if (user_list != null && user_list.size() > 0) {// 查询成功，返回结果不空
		    // 将当前用户作为全局变量存储起来
		    mApplication.setPresentUser(user_list.get(0));
		    Util.showToast(QQLogInActivity.this, "登录成功,用户："
			    + user_list.get(0).getUserName());
		    Intent intent = new Intent(QQLogInActivity.this,
			    MainActivity.class);
		    startActivity(intent);// 进入主界面
		} else {// 查询成功，说明是第一次登录
		    Util.showToast(QQLogInActivity.this, "用户第一次登录");
		    User user = new User();
		    File file = null;
			file = new File("http://file.bmob.cn/M01/AA/F2/oYYBAFW8RoSATpIEAAAkoWxDSBc961.jpg");
//		        if(!file.exists()){//判断文件是否存在  
//		            try {  
//		                file.createNewFile();  //创建文件  
//		                  
//		            } catch (IOException e1) {  
//		                e.printStackTrace();  
//		            }  }
		    Log.d("BmobFile","path："+file.getPath()+"name"+file.getName());
		    Util.showToast(QQLogInActivity.this,"path："+file.getPath()+"name"+file.getName() );
		    user.setPortrait(new BmobFile(file));
		    user.setQqOpenId(openidString);
		    user.setUserName(openidString);
		    user.setSex(genderString);
		    user.setNickName(nicknameString);
		    user.save(QQLogInActivity.this, new SaveListener() {
			@Override
			public void onSuccess() {
				Util.showToast(QQLogInActivity.this, "添加成功");
			}
			@Override
			public void onFailure(int arg0, String arg1) {
			    Util.showToast(QQLogInActivity.this, "错误信息："+arg1);
			}
		});
		    mApplication.setPresentUser(user);
		    Intent intent = new Intent(QQLogInActivity.this,
			    MainActivity.class);
		    startActivity(intent);// 进入主界面
		}
	    } else {// 查询失败，出现异常
		Util.showToast(QQLogInActivity.this, "错误码:" + e.getErrorCode()
			+ "错误描述" + e.getMessage());
	    }

	}
    }
}
