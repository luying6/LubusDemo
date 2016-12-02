package com.ly.lubus.exception;

/**
 * 创建人：luying
 * 创建时间：16/11/28
 * 类说明：
 */

public class LuBusException extends RuntimeException {
    public LuBusException(){
        super();
    }

    public LuBusException(String msg){
        super(msg);
    }

    public LuBusException(Throwable throwable){
        super(throwable);
    }
}
