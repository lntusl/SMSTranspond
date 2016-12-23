package com.sms.liangshang.smstranspond.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


/**
 * 通过当前显示的activity的packageName，获取对应的应用程序名
 * @return  返回应用程序名name
 * 
 */
public class GetProgramName{

public static String getProgramNameByPackageName(Context context,
		
		            String packageName) {
		
		        PackageManager pm = context.getPackageManager();
		
		        String name = null;
		
		        try {
		
		            name = pm.getApplicationLabel(
		
		                    pm.getApplicationInfo(packageName,
		
		                            PackageManager.GET_META_DATA)).toString();
		
		        } catch (NameNotFoundException e) {
		
		            e.printStackTrace();
		
		        }
	
		        return name;
		
		    }
}
