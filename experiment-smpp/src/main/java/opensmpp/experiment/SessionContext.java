package opensmpp.experiment;

import org.smpp.Session;

public class SessionContext extends Session {

	private ClientConnection esmeConnection;

	public SessionContext(ClientConnection esmeConnection) {
		super(esmeConnection);
		this.esmeConnection = esmeConnection;
	}
}
