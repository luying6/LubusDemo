package com.ly.lubus.util;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * 创建人：luying
 * 创建时间：16/11/30
 * 类说明：
 */

public class ReflectUtil {
    public static List<String> getClassNameListByPackageName(Context context, String packageName) {
        List<String> nameList = new ArrayList<>();
        try {
            String dest = packageName + ".";
            DexFile dexFile = new DexFile(context.getPackageName());
            Enumeration<String> enumeration = dexFile.entries();
            while (enumeration.hasMoreElements()){
                String next = enumeration.nextElement();
                if (next.startsWith(dest) && !next.contains("$")){
                    nameList.add(next);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameList;
    }
}
