package opensmpp.experiment;

import org.smpp.Session;

public class EsmeSession extends Session {

	private EsmeConnection esmeConnection;

	public EsmeSession(EsmeConnection esmeConnection) {
		super(esmeConnection);
		this.esmeConnection = esmeConnection;
	}
}
