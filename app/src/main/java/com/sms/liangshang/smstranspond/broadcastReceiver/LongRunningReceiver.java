package com.sms.liangshang.smstranspond.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.sms.liangshang.smstranspond.localService.LongRunningService;

public class LongRunningReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, LongRunningService.class);
		context.startService(i);
	}

}
