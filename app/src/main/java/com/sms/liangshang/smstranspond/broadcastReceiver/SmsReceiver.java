package com.sms.liangshang.smstranspond.broadcastReceiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.sms.liangshang.smstranspond.util.SendMsm;

import java.text.SimpleDateFormat;

public class SmsReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    public static boolean rining = Boolean.FALSE;

    @Override
    public void onReceive (Context context, Intent intent) {
        String action = intent.getAction ().toString ();
        if (action.equals (SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras ();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get ("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu ((byte[]) pdus[i]);
                }
                StringBuilder msgBody = new StringBuilder ();
                if(messages!=null){
                    for(SmsMessage sms : messages){
                        msgBody.append (sms.getMessageBody ());
                    }
                }

                if (messages.length > 0) {
                    String msgAddress = messages[0].getOriginatingAddress ();
                    long msgDate = messages[0].getTimestampMillis ();
                    SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                    String date = format.format (msgDate);
                    Log.d ("测试电话|短信  <===>", msgBody.toString ());
                    String smsToast = "New SMS received from : "
                            + msgAddress + ":\n'"
                            + msgBody.toString () + "'\n"
                            + "MsgDate:" + date;
                    Log.d ("测试电话|短信  <===>", smsToast);
                    SendMsm.doSend (smsToast);
                }
            }
            Log.d ("测试电话|短信", "收到短信");
        }


        if (action.equals (Intent.ACTION_NEW_OUTGOING_CALL)) {
            //系统拨打电话 v
            //mIncomingNumber = intent.getStringExtra("incoming_number");
            Log.d ("测试电话|短信", "播出电话");
        }

        if (action.equals ("android.intent.action.PHONE_STATE")) {
            // 如果是来电
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService (Service.TELEPHONY_SERVICE);
            String incomingNumber = intent.getStringExtra ("incoming_number");

            switch (tManager.getCallState ()) {
                case 1: {
                    if (! rining) {
                        StringBuilder smsString = new StringBuilder ();
                        smsString.append ("You have a comingNum：\n")
                                .append (incomingNumber);
                        SendMsm.doSend (smsString.toString ());
                    }
                    rining = true;
                    Log.d ("测试电话|短信", "来电响铃 :");
                    break;
                }
                case 2: {
                    if (rining) {
                        Log.d ("测试电话|短信", "响铃并接听");
                    }

                    Log.d ("测试电话|短信", "正在接通 :");
                    break;
                }
                case 0: {
                    rining = false;
                    Log.d ("测试电话|短信", "挂断电话！");
                    break;
                }
            }
        }
    }
}
