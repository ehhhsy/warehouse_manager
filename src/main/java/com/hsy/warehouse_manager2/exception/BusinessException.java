package com.hsy.warehouse_manager2.exception;
//自定义异常
public class BusinessException extends RuntimeException{
    //创建异常对象
    public BusinessException() {
        super();
    }

    //创建异常对象并指明异常信息
    public BusinessException(String message) {
        super(message);
    }
}
