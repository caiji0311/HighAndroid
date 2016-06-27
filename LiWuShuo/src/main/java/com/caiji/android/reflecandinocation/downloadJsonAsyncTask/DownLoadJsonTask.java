package com.caiji.android.reflecandinocation.downloadJsonAsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by my on 2016/6/20.
 */
public class DownLoadJsonTask extends AsyncTask<String, Void, String> {
    private Context context;
    private CallBack mCallBack;

    public DownLoadJsonTask(Context context, CallBack mCallBack) {
        this.context = context;
        this.mCallBack = mCallBack;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String result = getAllCompany(params[0]);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        mCallBack.callback(result);
    }

    private String getAllCompany(String urlString) throws IOException {
        if (HttpUtils.isNetWorkConn(context)) {
            byte[] buffer = HttpUtils.getNetWorkResult(urlString);
            if (buffer != null) {
                String jsonString = new String(buffer);
                return jsonString;
            }
        }
        return null;
    }

    public interface CallBack {
        public void callback(String result);
    }

}
