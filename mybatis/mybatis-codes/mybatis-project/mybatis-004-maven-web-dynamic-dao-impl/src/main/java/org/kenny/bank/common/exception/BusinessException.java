package org.kenny.bank.common.exception;

//给业务层准备的自定义异常类
public class BusinessException extends RuntimeException{

    private String errorCode;

    public BusinessException()
    {

    }
    public BusinessException(String message)
    {
        super(message);
    }
    public BusinessException(String message,Throwable cause)
    {
        super(message,cause);
    }
    public BusinessException(String errorCode,String message)
    {
        super(message);
        this.errorCode = errorCode;
    }
    public BusinessException(String errorCode,String message,Throwable cause)
    {
        super(message,cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode()
    {
        return errorCode;
    }
}
