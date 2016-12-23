package com.sms.liangshang.smstranspond.util;

import android.telephony.SmsManager;
import com.sms.liangshang.smstranspond.localService.LongRunningService;
import com.sms.liangshang.smstranspond.thread.PostThread;

import java.util.ArrayList;

/**
 * Created by liang.shang on 2016-12-20-0020.
 */

public class SendMsm {
    public static void doSend (String message) {
        if (LongRunningService.weChat) {
                            /*
                        wechat
						 */
            PostThread getThread = new PostThread (message, LongRunningService.token);
            getThread.start ();
        } else if (LongRunningService.msmAndWeChat) {
                         /*
                        ¶ÌÐÅ
						 */
            SmsManager sms = SmsManager.getDefault ();
            ArrayList<String> msgs = sms.divideMessage (message);
            sms.sendMultipartTextMessage ("13061764969", null, msgs, null, null);
                            /*
                        wechat
						 */
            PostThread getThread = new PostThread (message, LongRunningService.token);
            getThread.start ();
        }
    }
}
