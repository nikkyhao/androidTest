package com.example.androidtestproject;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class QQActivity extends Activity  {
    //QQ头像
    Bitmap bitmap = null; 
    Button logButton = null;
    ImageView userLogo = null;
    private Tencent mTencent;
    public static String openidString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		logButton = (Button)findViewById(R.id.QQLogButton);
		userLogo = (ImageView)findViewById(R.id.QQimage);
	}
	public void LogIn(View v){
	    System.out.println("Login Button");
	    String mAppid = "1104708623";				//第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录       
	    mTencent = Tencent.createInstance(mAppid,getApplicationContext()); /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限          官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”           第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */       
	    mTencent.login(QQActivity.this,"all", new BaseUiListener()); 
	}
	
	private class BaseUiListener implements IUiListener {
	    
	        public void onCancel() {
	            // TODO Auto-generated method stub
	             
	        }
	        public void onComplete(Object response) {
	            // TODO Auto-generated method stub
	            Toast.makeText(getApplicationContext(), "QQ登录成功", 0).show();
	            try {
	                //获得的数据是JSON格式的，获得你想获得的内容
	                //如果你不知道你能获得什么，看一下下面的LOG
	                Log.e("QQ", "-------------"+response.toString());
	                openidString = ((JSONObject) response).getString("openid");
	                Log.e("QQ", "-------------"+openidString);
	                //access_token= ((JSONObject) response).getString("access_token");              //expires_in = ((JSONObject) response).getString("expires_in");
	            } catch (org.json.JSONException e) {//这里注意！，有两个包都有这个Exception类
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            /**到此已经获得OpneID以及其他你想获得的内容了
	            QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？ 
	            sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息 
	                      如何得到这个UserInfo类呢？  */
	            QQToken qqToken = mTencent.getQQToken();
	            UserInfo info = new UserInfo(getApplicationContext(), qqToken);
	            //这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON      
	          
	            info.getUserInfo(new IUiListener() {
	        	 
	                public void onComplete(final Object response) {
	                    // TODO Auto-generated method stub
	                    Log.e("QQ", "---------------111111");
	                    Message msg = new Message();
	                    msg.obj = response;
	                    msg.what = 0;
	                    mHandler.sendMessage(msg);
	                    Log.e("QQ", "-----111---"+response.toString());
	                    /**由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接
	                     * 在mHandler里进行操作
	                     * 
	                     */
	                    new Thread(){
	 
	                        @Override
	                        public void run() {
	                            // TODO Auto-generated method stub
	                            JSONObject json = (JSONObject)response;
	                            try {
	                        	String s =json.getString("figureurl_qq_2");
	                                bitmap = Util.getbitmap(s);
	                               
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
	                    Log.e("QQ", "-111113"+":"+arg0);
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
	                    
	                        String nicknameString = null;;
				try {
				    nicknameString = response.getString("nickname");
				} catch (org.json.JSONException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				}
	                        Log.e("QQ", "--"+nicknameString);
	                   
	                }
	            }else if(msg.what == 1){
	                Bitmap bitmap = (Bitmap)msg.obj;
	                userLogo.setImageBitmap(bitmap);
	                 
	            }
	        }
	 
	    };
}
