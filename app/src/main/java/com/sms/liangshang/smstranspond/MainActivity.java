package com.sms.liangshang.smstranspond;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.sms.liangshang.smstranspond.localService.LongRunningService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        /**
         * 启动服务
         */
        Intent service = new Intent (this, LongRunningService.class);
        this.startService (service);

        final RadioButton weChat = (RadioButton) (findViewById (R.id.radioButton2));
        weChat.setChecked (LongRunningService.weChat);
        final RadioButton msmAndWeChat = (RadioButton) (findViewById (R.id.radioButton));
        msmAndWeChat.setChecked (LongRunningService.msmAndWeChat);
        RadioGroup radioGroup = (RadioGroup) findViewById (R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (RadioGroup rg, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == weChat.getId ()) {
                    weChat.setChecked (Boolean.TRUE);
                    LongRunningService.weChat = Boolean.TRUE;
                    LongRunningService.msmAndWeChat = Boolean.FALSE;
                    Toast.makeText (MainActivity.this, "选择weChat", Toast.LENGTH_SHORT).show ();
                } else if (checkedId == msmAndWeChat.getId ()) {
                    msmAndWeChat.setChecked (Boolean.TRUE);
                    LongRunningService.weChat = Boolean.FALSE;
                    LongRunningService.msmAndWeChat = Boolean.TRUE;
                    Toast.makeText (MainActivity.this, "选择msm&WeChat", Toast.LENGTH_SHORT).show ();
                }
            }
        });

        /**
         * 查询电话、短信数量
         * 并绑定‘点击’监听器
         */
        Button buttonSave = (Button) (findViewById (R.id.button3));
        buttonSave.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                try {
                    new Thread (new Runnable () {
                        @Override
                        public void run () {
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
                                Toast.makeText (MainActivity.this, "刷新-异常", Toast.LENGTH_SHORT).show ();
                            }
                        }
                    }).start ();
                    Toast.makeText (MainActivity.this, "刷新-成功", Toast.LENGTH_SHORT).show ();
                }catch(Exception e){
                    e.printStackTrace ();
                    Toast.makeText (MainActivity.this, "刷新-异常", Toast.LENGTH_SHORT).show ();
                }
            }
        });
    }
}
