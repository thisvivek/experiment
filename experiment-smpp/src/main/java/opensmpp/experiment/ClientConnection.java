package opensmpp.experiment;

import org.smpp.TCPIPConnection;

public class ClientConnection extends TCPIPConnection {
	
	public ClientConnection(String address, int port) {
		super(address, port);
	}
}
