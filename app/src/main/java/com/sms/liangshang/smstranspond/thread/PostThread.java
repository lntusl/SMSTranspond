package com.sms.liangshang.smstranspond.thread;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PostThread extends Thread {
        String mes = "";
        String token = "";

        public PostThread (String mes, String token) {
            this.mes = mes;
            this.token = token;
        }

        @Override
        public void run () {
            try {
                HttpClient httpClient = new DefaultHttpClient ();
                String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + token;
                HttpPost httpPost = new HttpPost (url);
                String message = "{\"filter\":{\"is_to_all\":true},\"text\":{\"content\":\" " + mes.replace ("\"","\\\"") + "\"},\"msgtype\":\"text\"}";
                Log.d ("==========HTTP=========", "BODY:" + message);
                Log.d ("==========HTTP=========", "url:" + url);
                HttpEntity entity = new StringEntity (message, "UTF-8");
                httpPost.setEntity (entity);
                HttpResponse response = httpClient.execute (httpPost);
                if (response.getStatusLine ().getStatusCode () == 200) {
                    BufferedReader br = new BufferedReader (new InputStreamReader (
                            response.getEntity ().getContent (), "utf-8"));
                    Log.d ("==========HTTP=========", "POST:" + br.readLine ());
                }
            } catch (Exception e) {
                e.printStackTrace ();
            }

        }
    }