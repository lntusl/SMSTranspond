package com.sms.liangshang.smstranspond.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


/**
 * ͨ����ǰ��ʾ��activity��packageName����ȡ��Ӧ��Ӧ�ó�����
 * @return  ����Ӧ�ó�����name
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
