package com.sms.liangshang.smstranspond.localService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import com.sms.liangshang.smstranspond.broadcastReceiver.LongRunningReceiver;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by liang.shang on 2016-12-19-0019.
 */

public class LongRunningService extends Service {


    public static String token = "";
    public static Boolean weChat = Boolean.TRUE;
    public static Boolean msmAndWeChat = Boolean.FALSE;
    public static String condition = "";
    public static String expre = "";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        new Thread (new Runnable () {
            @Override
            public void run () {
                Log.d ("TAG", "打印时间: " + new Date ().
                        toString ());
                try {
                    HttpClient httpClient = new DefaultHttpClient ();
                    String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx281640e1fb9786f0&secret=71be8e1305572d3925705bc9a4f5dfe7";
                    HttpGet httpGet = new HttpGet (url);
                    HttpResponse response = httpClient.execute (httpGet);
                    if (response.getStatusLine ().getStatusCode () == 200) {
                        BufferedReader br = new BufferedReader (new InputStreamReader (
                                response.getEntity ().getContent (), "utf-8"));
                        String result = br.readLine ();
                        JSONObject js = new JSONObject (result);
                        LongRunningService.token = js.getString ("access_token");
                        Log.d ("==========HTTP=========", "GET:" + result);
                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        }).start ();
        AlarmManager manager = (AlarmManager) getSystemService (ALARM_SERVICE);
        int twoHour = 1 * 1000 * 60 * 60 * 2; // 这是2h
//        int five = 5;
        long triggerAtTime = SystemClock.elapsedRealtime () + twoHour;
        Intent i = new Intent (this, LongRunningReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast (this, 0, i, 0);
        manager.setExact (AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand (intent, flags, startId);
    }

}
