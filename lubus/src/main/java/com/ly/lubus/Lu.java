package com.ly.lubus;

import android.content.Context;
import android.text.TextUtils;

import com.ly.lubus.compoent.AsyncLuCallBack;
import com.ly.lubus.compoent.LuMethod;
import com.ly.lubus.exception.IncorrectLuException;
import com.ly.lubus.exception.LuBusException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * 创建人：luying
 * 创建时间：16/11/30
 * 类说明：使用的类必须继承此类
 */

public abstract class Lu {
    private HashMap<String, Method> methodInfo;

    public Lu() {
    }

    protected void initMethod() {
        methodInfo = new HashMap<>();
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(LuMethod.class);
            if (annotation != null) {
                String uri = ((LuMethod) annotation).uri();
                if (!TextUtils.isEmpty(uri)) {
                    methodInfo.put(uri, method);
                }
            }
        }
    }

    /**
     * format as: module/method
     * this method return module
     *
     * @return String
     */
    public abstract String getModuleName();

    /**
     * called by other module
     * if you dest method runs on different thread,you should send AsyncLuCallBack;
     *
     * @param context   Context
     * @param uri       String
     * @param paramList List<Object> | recommend use only one param, use json String if param is complex
     * @param callBack  AsyncLuCallBack | callback for async call
     * @return Object
     */
    public Object beCalled(Context context, String uri, List<Object> paramList, AsyncLuCallBack callBack)throws LuBusException {
        if (methodInfo != null) {
            try {
                Method method = methodInfo.get(uri);
                Annotation annotation = method.getAnnotation(LuMethod.class);
                if (annotation != null && ((LuMethod) annotation).needSync()) {
                    method.invoke(this, context, paramList, callBack);
                    return null;
                }
                return method.invoke(this, context, paramList);
            } catch (IllegalAccessException e) {
                throw new LuBusException(e);
            } catch (InvocationTargetException e) {
                throw new LuBusException(e);
            }
        } else {
            throw new IncorrectLuException("No Method" + uri);
        }
    }
}
