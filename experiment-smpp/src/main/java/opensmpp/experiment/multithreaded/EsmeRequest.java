package opensmpp.experiment.multithreaded;

import org.smpp.pdu.Request;

public class EsmeRequest {

	private Request request;
	private String requestName;
	private int count;
	
	public EsmeRequest() {
	}

	public EsmeRequest(Request request, String requestName) {
		this.request = request;
		this.requestName = requestName;
	}

	public Request getRequest() {
		return request;
	}

	public String getName() {
		return requestName;
	}

	public synchronized int getCount() {
		return count;
	}

	public synchronized void increment(int count) {
		this.count++;
	}
}
