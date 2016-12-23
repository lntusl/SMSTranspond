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
                    Log.d ("���Ե绰|����  <===>", msgBody.toString ());
                    String smsToast = "New SMS received from : "
                            + msgAddress + ":\n'"
                            + msgBody.toString () + "'\n"
                            + "MsgDate:" + date;
                    Log.d ("���Ե绰|����  <===>", smsToast);
                    SendMsm.doSend (smsToast);
                }
            }
            Log.d ("���Ե绰|����", "�յ�����");
        }


        if (action.equals (Intent.ACTION_NEW_OUTGOING_CALL)) {
            //ϵͳ����绰 v
            //mIncomingNumber = intent.getStringExtra("incoming_number");
            Log.d ("���Ե绰|����", "�����绰");
        }

        if (action.equals ("android.intent.action.PHONE_STATE")) {
            // ���������
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService (Service.TELEPHONY_SERVICE);
            String incomingNumber = intent.getStringExtra ("incoming_number");

            switch (tManager.getCallState ()) {
                case 1: {
                    if (! rining) {
                        StringBuilder smsString = new StringBuilder ();
                        smsString.append ("You have a comingNum��\n")
                                .append (incomingNumber);
                        SendMsm.doSend (smsString.toString ());
                    }
                    rining = true;
                    Log.d ("���Ե绰|����", "�������� :");
                    break;
                }
                case 2: {
                    if (rining) {
                        Log.d ("���Ե绰|����", "���岢����");
                    }

                    Log.d ("���Ե绰|����", "���ڽ�ͨ :");
                    break;
                }
                case 0: {
                    rining = false;
                    Log.d ("���Ե绰|����", "�Ҷϵ绰��");
                    break;
                }
            }
        }
    }
}
