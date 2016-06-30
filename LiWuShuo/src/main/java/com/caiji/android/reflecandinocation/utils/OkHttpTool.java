package com.caiji.android.reflecandinocation.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangjw on 2016/6/28.
 */
public class OkHttpTool {

    private static OkHttpClient okHttpClient ;

    public static OkHttpTask newInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return new OkHttpTask();
    }

    public static class OkHttpTask extends AsyncTask<String,Integer,String> {

        public static final String URL_REGEX = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
        private IOKCallBack okCallBack;
        private FormBody formBody;


        public OkHttpTask start(String url) {

            //判断URL是否正确，使用正则表达式

            if (url == null) {
                throw new NullPointerException("url is null");
            }

            if (!url.matches(URL_REGEX)) {
                throw new IllegalArgumentException("url is error");
            }

            this.execute(url);
            return this;
        }

        public OkHttpTask post(Map<String,String> params) {

            Set<String> keySet = params.keySet();
            FormBody.Builder builder = new FormBody.Builder();
            for (String key:keySet) {
                String value = params.get(key);
                builder.add(key,value);
            }
            formBody = builder.build();
            return this;
        }

        public void callback(IOKCallBack callBack) {
            this.okCallBack = callBack;
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            //使用OkHttp请求网络
//            Request request = new Request.Builder()
//                    .url(url)
//                    .build();
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            if (formBody != null) {
                builder.post(formBody);
            }
            Request request = builder.build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //接收到网络请求的结果
            if (okCallBack != null) {
                okCallBack.success(s);
            }
        }
    }
}
