package org.kenny.bank.common.exception;

//给dao准备的异常类
public class DataAccessException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
    //自定义错误代码
    private String errorCode;

	public DataAccessException()
	{
        super();
    }
    public DataAccessException(String message)
    {
        super(message);
    }
    public DataAccessException(String message, Throwable cause)
    {
        super(message, cause);
    }

    //既有错误代码，又有错误信息
    public DataAccessException(String errorCode,String message)
    {
        super(message); //必须放在第一行
        this.errorCode = errorCode;
    }

    //既有错误代码，又有错误信息，还有原始异常对象
    public DataAccessException(String errorCode,String message,Throwable cause)
    {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
