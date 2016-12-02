package com.ly.lubus;

import android.content.Context;
import android.text.TextUtils;

import com.ly.lubus.compoent.AsyncLuCallBack;
import com.ly.lubus.exception.IncorrectLuException;
import com.ly.lubus.exception.LuBusException;
import com.ly.lubus.exception.NoLuException;
import com.ly.lubus.util.ReflectUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 创建人：luying
 * 创建时间：16/11/30
 * 类说明：
 */

public class LuBusManager {
    private final static LuBusManager instance = new LuBusManager();
    private HashMap<String, Lu> luMap = new HashMap<>();
    private String mPackageName;

    public LuBusManager() {
    }

    public static LuBusManager getInstance() {
        return instance;
    }

    public Object call(Context context, String uri, List<Object> params) {
        return call(context, uri, params, null);
    }

    /**
     * called by other module;
     * if your dest method runs on different thread,you shall send AsyncLuCallBack;
     * We will check all your param and throw some exception when error occurred
     * @param context
     * @param uri
     * @param params
     * @param callBack
     * @return
     * @throws LuBusException
     */
    public Object call(Context context, String uri, List<Object> params, AsyncLuCallBack callBack) throws LuBusException {
        checkBeforeCall(context, uri);
        String moduleNmae = getModuleNameByuri(uri);
        Lu lu = luMap.get(moduleNmae);
        if (lu == null){
            throw  new NoLuException("Lu" + moduleNmae + "does not exist");
        } else {
            return lu.beCalled(context, uri, params, callBack);
        }
    }

    /**
     * Init lu;
     * You shall call this method while application launched
     * We will do some check before init,see{@link #initLuList(Context context)}
     * @param context
     * @param packageName
     * @throws LuBusException
     */
//    public void init(Context context, String packageName) throws LuBusException{
//        if (TextUtils.isEmpty(packageName)){
//            throw new LuBusException("Empty packageName");
//        }else {
//            this.mPackageName = packageName;
//            initLuList(context);
//        }
//    }

    /**
     *  Init lu;
     *  You shall call this method when you want to load lus by path when you use multidex instant run
     */

    public void init(Context context, String[] pathList) throws LuBusException{
        if (pathList == null || pathList.length <=0){
            throw new LuBusException("Empty pathList");
        }else {
            List<String> nameList = new ArrayList<>();
            Collections.addAll(nameList,pathList);
            loadLu(context, nameList);
        }
    }

    /**
     * check env params before call
     * @param context
     * @param uri
     * @throws LuBusException
     */
    private void checkBeforeCall(Context context, String uri) throws LuBusException {
        String moduleName = getModuleNameByuri(uri);
        if (TextUtils.isEmpty(moduleName)) {
            throw new IncorrectLuException("Empty moduleName");
        }
        checkLuMap(context);
    }

    /**
     * check iwhether lumap saves correctly info. if not, we will init all lu by call{@link #initLuList(Context context)}
     *
     * @param context
     */
    private void checkLuMap(Context context) {
        if (luMap == null || luMap.isEmpty()) {
            initLuList(context);
        }
    }

    /**
     * Init all lu;
     * @param context
     * @throws LuBusException
     */
    private void initLuList(Context context) throws LuBusException{
        List<String> nameList = ReflectUtil.getClassNameListByPackageName(context, mPackageName);
        if (nameList == null || nameList.isEmpty()) {
            throw new NoLuException();
        } else {
            loadLu(context, nameList);
        }
    }

    /**
     * load lu by full package path
     * @param context
     * @param nameList
     */
    private void loadLu(Context context, List<String> nameList) {
        List<Lu> lutemp = new ArrayList<>();

        try {
            for (String temp : nameList) {
                if (!TextUtils.isEmpty(temp)) {
                    Object clzObject = Class.forName(temp).newInstance();
                    if (Lu.class.isInstance(clzObject)) {
                        Lu lu = (Lu) clzObject;
                        lu.initMethod();
                        String verifyLuResult = verifyLu(lu);
                        if (TextUtils.isEmpty(verifyLuResult)){
                            lutemp.add(lu);
                        } else {
                            throw new IncorrectLuException(verifyLuResult);
                        }
                    }
                }
            }
        } catch (InstantiationException e) {
            throw new IncorrectLuException(e);
        } catch (IllegalAccessException e) {
            throw new IncorrectLuException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (lutemp.isEmpty()) {
            throw new NoLuException();
        } else {
            luMap.clear();
            for (Lu temp : lutemp) {
                luMap.put(temp.getModuleName(), temp);
            }
        }

    }

    /**
     * Verify this lu
     *
     * @param lu
     * @return
     */
    private String verifyLu(Lu lu) {
        if (lu == null) {
            return "";
        }
        String errorInfo = "";
        if (TextUtils.isEmpty(lu.getModuleName())) {
            errorInfo = "lu.getModuleName() returns empty";
        } else {

        }

        return errorInfo;
    }

    /**
     * Get module by uri
     * Uri should be formatted like:module/method
     *
     * @param uri String
     * @return String
     */
    private String getModuleNameByuri(String uri) {
        if (TextUtils.isEmpty(uri)) {
            return "";
        }
        String moduleName = "";
        String[] temp = uri.split("/");
        if (temp.length > 1) {
            moduleName = temp[0];
        }
        return moduleName;
    }
}
