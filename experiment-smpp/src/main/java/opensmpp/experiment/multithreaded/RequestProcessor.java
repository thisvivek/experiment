package opensmpp.experiment.multithreaded;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.smpp.Session;
import org.smpp.pdu.Response;

public class RequestProcessor extends Thread {

	private final EsmeQueueOperations esmeQueueOperations;
	private final EsmeSmscOperations esmeSmscOperations;
	private boolean running = false;
	private Response response;

	private Map<String, Function<EsmeRequest, Response>> commandMap = new HashMap<>();
	

	public RequestProcessor(Session session, EsmeQueueOperations esmeQueueOperations) {
		this.esmeQueueOperations = esmeQueueOperations;
		esmeSmscOperations = new EsmeSmscOperations(session);
		initCommand();
		running = true;
	}

	private void initCommand() {
		commandMap.put(EsmeClientData.BIND, esmeSmscOperations::bind);
		commandMap.put(EsmeClientData.SUBMIT_SM, esmeSmscOperations::submit_SM);
		commandMap.put(EsmeClientData.UNBIND, esmeSmscOperations::unbind);
	}

	@Override
	public void run() {
		while (running) {
			process(esmeQueueOperations.getFirstMessage());
		}
	}

	void process(EsmeRequest request) {
		response = commandMap.get(request.getName()).apply(request);
	}

	public Response getResponse() {
		return response;
	}
	
	public void stop(boolean running) {
		this.running = running;
	}
}
