package opensmpp.experiment.multithreaded;

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
	
	public EsmeRequest getFirstMessage() {
		try {
			return processionQueue.takeFirst();
		} catch (InterruptedException e) {
		}
		return null;
	}
	
}
