package com.sms.liangshang.smstranspond.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;


/**
 * 
 * @author LNTUSL
 *
 */
public class PackagesInfo {  
    private List<ApplicationInfo> appList;  
      
    public PackagesInfo(Context context){  
        //ͨ�������������������е�Ӧ�ó�������ж�صģ�������Ŀ¼  
        PackageManager pm = context.getApplicationContext().getPackageManager();  
        appList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);  
    }  
      
      
    /** 
     * ͨ��һ�����������ظó����һ��Application���� 
     * @param name  ������ 
     * @return  ApplicationInfo  
     */  
      
    public ApplicationInfo getInfo(String name){  
        if(name == null){  
            return null;  
        }  
        for(ApplicationInfo appinfo : appList){  
            if(name.equals(appinfo.processName)){  
                return appinfo;  
            }  
        }  
        return null;  
    }  
      
}  