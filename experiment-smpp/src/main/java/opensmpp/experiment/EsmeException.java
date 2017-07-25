package opensmpp.experiment;

public class EsmeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EsmeException() {
		super();
	}
	
	public EsmeException(String msg) {
		super(msg);
	}
	
	public EsmeException(Throwable cause) {
		super(cause);
	}
	
	public EsmeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
