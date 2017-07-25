package opensmpp.experiment;

import org.smpp.TCPIPConnection;

public class EsmeConnection extends TCPIPConnection {
	
	public EsmeConnection(String address, int port) {
		super(address, port);
	}
}
