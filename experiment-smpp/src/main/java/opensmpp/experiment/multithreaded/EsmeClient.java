package opensmpp.experiment.multithreaded;

import java.util.Objects;

import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.Response;
import org.smpp.pdu.SubmitSM;

public class EsmeClient {
	
	private TCPIPConnection connection;
	private Session session;
	private RequestProcessor requestProcessor;
	private EsmeQueueOperations esmeQueueOperations;
	boolean canProcess = false;
	
	public void init() {
		connection = new TCPIPConnection("localhost", 2775);
		session = new Session(connection);
		esmeQueueOperations = new EsmeQueueOperations(1000);
		requestProcessor = new RequestProcessor(session, esmeQueueOperations);
		
		BindRequest bind = new BindTransmitter();
		EsmeRequest esmeRequest = new EsmeRequest(bind, EsmeClientData.BIND);
		requestProcessor.process(esmeRequest);
		Response response = requestProcessor.getResponse();
		System.out.printf("BindResponse %s%n",Objects.nonNull(response)? response.debugString() : "No Bind Response");
		
		// Till this point, session will binded
		if (session.getState() == Session.STATE_TRANSMITTER) {
			requestProcessor.start();
		}
		canProcess = true;
	}

	public void process() {
		if(!canProcess)
			return;
		SubmitSM sm = new SubmitSM();
		EsmeRequest esmeRequest = new EsmeRequest(sm, EsmeClientData.SUBMIT_SM);
		esmeQueueOperations.submit_SM(esmeRequest);
	}
	
	public void cleanup() {
		canProcess = false;
		requestProcessor.drainAndStop();
		
		EsmeRequest esmeRequest = new EsmeRequest(null, EsmeClientData.UNBIND); 
		requestProcessor.process(esmeRequest);
		Response response = requestProcessor.getResponse();
		System.out.printf("UnBindResponse %s%n", Objects.nonNull(response)? response.debugString() : "No Unbind Response");
	}

}
