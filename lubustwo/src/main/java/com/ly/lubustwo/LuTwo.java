package com.ly.lubustwo;

import android.content.Context;
import android.widget.Toast;

import com.ly.lubus.Lu;
import com.ly.lubus.LuBusManager;
import com.ly.lubus.compoent.AsyncLuCallBack;
import com.ly.lubus.compoent.LuMethod;

import java.util.List;

/**
 * 创建人：luying
 * 创建时间：16/12/2
 * 类说明：
 */

public class LuTwo extends Lu {
    private final static String TAG = "two";

    public LuTwo(){
        super();
    }
    @Override
    public String getModuleName() {
        return TAG;
    }
    @LuMethod(uri = TAG + "/test")
    public void first(Context context, List<Object> params){
        LuBusManager.getInstance().call(context,"one/test", null);
    }
    @LuMethod(uri = TAG + "/test1")
    public void second(Context context, List<Object> params){
        Toast.makeText(context, "you called LuTwo second", Toast.LENGTH_SHORT).show();

    }
    @LuMethod(uri = TAG+ "/test2", needSync = true)
    public void third(Context context, List<Object> params, final AsyncLuCallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (callBack != null){
                    callBack.result(",", null);
                }
            }
        }).start();
    }
}
