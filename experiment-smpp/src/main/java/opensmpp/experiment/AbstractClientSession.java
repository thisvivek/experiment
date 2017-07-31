package opensmpp.experiment;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.ValueNotSetException;

public abstract class AbstractClientSession implements ClientSession {

	private static final Random random = new Random();

	private String sessionId = generateSessionId();
	
	protected EnquireLinkSender enquireLinkSender;
	
	private static final int autoLinkCheckTime = 1;

	protected abstract SessionContext sessionContext();
	
	private synchronized static final String generateSessionId() {
		return Util.toHexString(random.nextInt());
	}

	protected synchronized boolean isReadPdu() {
		return sessionContext().isOpened() && sessionContext().isBound();
	}
	
	 

	protected class EnquireLinkSender extends Thread {
		
		private final AtomicBoolean sendingEnquireLink = new AtomicBoolean(false);

		public EnquireLinkSender() {
			super("EnquireLinkSender: " + AbstractClientSession.this);
		}

		@Override
		public void run() {
			System.out.printf("Starting EnquireLinkSender for session %s", sessionId);
			while (isReadPdu()) {
				while (!sendingEnquireLink.compareAndSet(true, false) && !Thread.currentThread().isInterrupted()
						&& isReadPdu()) {
					synchronized (sendingEnquireLink) {
						try {
							sendingEnquireLink.wait(autoLinkCheckTime);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
				}
				if (Thread.currentThread().isInterrupted() || !isReadPdu()) {
					break;
				}

				try {
					//TODO : Need to check with NCL (Positive and Negative scenario)
					sessionContext().enquireLink();
				} catch (ValueNotSetException e) {

				} catch (TimeoutException e) {
					System.out.printf("Response timeout on enquireLink", e.getMessage());

				} catch (PDUException e) {

				} catch (WrongSessionStateException e) {

				} catch (IOException e) {
					System.out.printf("I/O exception on enquireLink", e.getMessage());
				}
			}
			System.out.printf("EnquireLinkSender stopped for session %s", sessionId);
		}

		/**
		 * This method will send enquire link asynchronously.
		 */
		public void enquireLink() {
			if (sendingEnquireLink.compareAndSet(false, true)) {
				synchronized (sendingEnquireLink) {
					sendingEnquireLink.notify();
				}
			} else {
				System.out.printf("Not sending enquire link notify");
			}
		}
	}
}
