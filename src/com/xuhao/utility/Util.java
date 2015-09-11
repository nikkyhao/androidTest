package com.xuhao.utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

public class Util {
    public static String TAG="UTIL";
    public static Bitmap getbitmap(String imageUri) {
        Log.v("QQ", "getbitmap:" + imageUri);
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
 
            Log.v(TAG, "image download finished." + imageUri);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "getbitmap bmp fail---");
            return null;
        }
        return bitmap;
    }
    static Toast toast ;
    public static void showToast(Context mContext , String s){
    	toast = new Toast(mContext);
    	toast.setText(s);
    	toast.show();
        }
    
}