package opensmpp.experiment.multithreaded;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

public class EsmeQueueOperations {

	private final LinkedBlockingDeque<EsmeRequest> processionQueue;

	public EsmeQueueOperations(int maxQueueSize) {
		this.processionQueue = new LinkedBlockingDeque<>(maxQueueSize);
	}

	public void submit_SM(EsmeRequest sm) {
		try {
			processionQueue.putLast(sm);
		} catch (InterruptedException e) {
		}
	}
	
	public void enquireLink(EsmeRequest enquireLink) {
		try {
			processionQueue.putFirst(enquireLink);
		} catch (InterruptedException e) {
		}
	}

	public EsmeRequest getFirstMessage() throws InterruptedException {
		return processionQueue.takeFirst();
	}

	public void drainTo(LinkedList<EsmeRequest> remainingObjects) {
		processionQueue.drainTo(remainingObjects);
	}

}
