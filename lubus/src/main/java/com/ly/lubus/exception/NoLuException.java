package com.ly.lubus.exception;

/**
 * 创建人：luying
 * 创建时间：16/11/28
 * 类说明：
 */

public class NoLuException extends LuBusException {
    public NoLuException(){
        super("No lu found");
    }

    public NoLuException(String msg){
        super(msg);
    }
}
