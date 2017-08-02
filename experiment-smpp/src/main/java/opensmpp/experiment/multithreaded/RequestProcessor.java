package opensmpp.experiment.multithreaded;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.smpp.Session;
import org.smpp.pdu.EnquireLink;
import org.smpp.pdu.Response;

public class RequestProcessor extends Thread {

	private final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

	private final EsmeQueueOperations esmeQueueOperations;
	private final EsmeSmscOperations esmeSmscOperations;
	private Response response;
	private boolean running = false;

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
		commandMap.put(EsmeClientData.ENQUIRE_LINK, esmeSmscOperations::enquireLink);
	}

	@Override
	public void run() {

		scheduledThreadPool.scheduleAtFixedRate(this::enquireLink, 60, 35, TimeUnit.SECONDS);

		while (running && !Thread.currentThread().isInterrupted()) {
			try {
				final EsmeRequest message = esmeQueueOperations.getFirstMessage();
				process(message);
			} catch (InterruptedException e) {
				// Set interrupted flag.
				Thread.currentThread().interrupt();
			}

			// Thread is getting ready to die, but first,
			// drain remaining elements on the queue and process them.
			// final LinkedList<EsmeRequest> remainingObjects = new
			// LinkedList<>();
			// esmeQueueOperations.drainTo(remainingObjects);
			// for (EsmeRequest complexObject : remainingObjects) {
			// this.process(complexObject);
			// }
		}
	}

	private void enquireLink() {
		EnquireLink enquireLink = new EnquireLink();
		EsmeRequest esmeRequest = new EsmeRequest(enquireLink, EsmeClientData.ENQUIRE_LINK);
		esmeQueueOperations.enquireLink(esmeRequest);
	}

	void process(EsmeRequest request) {
		response = commandMap.get(request.getName()).apply(request);
	}

	public Response getResponse() {
		return response;
	}

	public void drainAndStop() {
		scheduledThreadPool.shutdown();
		running = false;
		this.interrupt();
	}
}
