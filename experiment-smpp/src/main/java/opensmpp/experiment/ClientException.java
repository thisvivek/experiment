package opensmpp.experiment;

public class ClientException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ClientException() {
		super();
	}
	
	public ClientException(String msg) {
		super(msg);
	}
	
	public ClientException(Throwable cause) {
		super(cause);
	}
	
	public ClientException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
