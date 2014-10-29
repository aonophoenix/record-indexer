package server;

public class ServerException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3072518472668097281L;
	
	private int code = -1;
	
	public ServerException()
	{
	}
	
	public ServerException(String msg)
	{
		super(msg);
	}
	
	public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public ServerException(String msg, int code) {
		super(msg);
		this.code = code;
	}
	
	public ServerException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }
	
	public ServerException(Throwable cause) {
		super(cause);
	}
	
	public ServerException(Throwable cause, int code) {
		super(cause);
		this.code = code; 
	}
	
	public int getCode() {
		return code;
	}
	
	
}
