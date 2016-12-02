package com.ly.lubus.exception;

/**
 * 创建人：luying
 * 创建时间：16/11/28
 * 类说明：
 */

public class IncorrectLuException extends LuBusException {
    public IncorrectLuException(){super();}

    public IncorrectLuException(String msg){super(msg);}

    public IncorrectLuException(Throwable throwable){super(throwable);}
}
