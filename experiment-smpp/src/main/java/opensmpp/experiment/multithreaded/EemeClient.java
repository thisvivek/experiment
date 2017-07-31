package opensmpp.experiment.multithreaded;

import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.SubmitSM;

public class EemeClient {
	
	private TCPIPConnection connection;
	private Session session;
	private RequestProcessor requestProcessor;
	private EsmeQueueOperations esmeQueueOperations;
	
	public static void main(String[] args) {
		new EemeClient().init();
	}

	public void init() {
		connection = new TCPIPConnection("localhost", 2775);
		session = new Session(connection);
		esmeQueueOperations = new EsmeQueueOperations(1000);
		requestProcessor = new RequestProcessor(session, esmeQueueOperations);
		
		BindRequest bind = new BindTransmitter();
		EsmeRequest esmeRequest = new EsmeRequest(bind, EsmeClientData.BIND);
		requestProcessor.process(esmeRequest);
		System.out.printf("BindResponse %s%n",requestProcessor.getResponse().debugString());
		
		// Till this point, session will binded
		if (session.getState() == Session.STATE_TRANSMITTER) {
			requestProcessor.start();
		}

		SubmitSM sm = new SubmitSM();
		esmeRequest = new EsmeRequest(sm, EsmeClientData.SUBMIT_SM);
		esmeQueueOperations.submit_SM(esmeRequest);
		
		esmeRequest = new EsmeRequest(null, EsmeClientData.UNBIND); 
		requestProcessor.process(esmeRequest);
		System.out.printf("UnBindResponse %s%n",requestProcessor.getResponse().debugString());
	}

}
