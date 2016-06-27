package com.caiji.android.reflecandinocation.downloadJsonAsyncTask;

/**
 * Created by my on 2016/6/20.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    /**
     * 判断网络是否链接
     */
    public static boolean isNetWorkConn(Context context){
        ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if(info !=null){
            return info.isConnected();
        }else {
            return false;
        }
    }



    /**
     * 获取网路数据
     * @throws IOException
     *
     */

    public static byte[] getNetWorkResult(String urlString) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] result=null;
        InputStream is=null;

        URL url=new URL(urlString);
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5*1000);
        connection.setReadTimeout(5*1000);


        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
            is=connection.getInputStream();
            byte[] buffer=new byte[1024];
            int length=0;

            while((length=is.read(buffer))!=-1){
                baos.write(buffer, 0, length);
                baos.flush();
            }

            result=baos.toByteArray();
        }

        closeStream(is);
        closeStream(baos);
        return result;

    }

    /**
     * 关闭流
     * @param stream
     */
    public static void closeStream(Closeable stream){
        if(stream !=null){
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

