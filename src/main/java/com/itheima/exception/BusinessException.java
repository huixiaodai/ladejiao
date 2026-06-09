package com.itheima.exception;

//继承 RuntimeException 是为了让 BusinessException 成为一个可以被 throw 的异常，而且不用每层代码都写 try catch
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
