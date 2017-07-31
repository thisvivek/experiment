package opensmpp.experiment;

import java.io.IOException;

import org.smpp.ServerPDUEventListener;
import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.DeliverSMResp;
import org.smpp.pdu.EnquireLinkResp;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.UnbindResp;
import org.smpp.pdu.ValueNotSetException;

public class SMPPSession extends AbstractClientSession {


	private SessionContext sessionContext;

	public SMPPSession(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	public BindResponse bind(BindRequest bindReq, ServerPDUEventListener pduListener) throws ClientException {
		BindResponse bindResponse = null;
		try {
			bindResponse = sessionContext.bind(bindReq, pduListener);
			
			enquireLinkSender = new EnquireLinkSender();
			enquireLinkSender.start();
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
			
		}
		return bindResponse;
	}

	public UnbindResp unbind() throws ClientException {
		UnbindResp unbindResp = null;
		try {
			unbindResp = sessionContext.unbind();
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
		}
		return unbindResp;
	}

	public SubmitSMResp submit(SubmitSM request) throws ClientException {
		SubmitSMResp submitSMResp = null;
		try {
			submitSMResp = sessionContext.submit(request);
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
			
		}
		return submitSMResp;
	}

	public DeliverSMResp deliver(DeliverSM request) throws ClientException {
		DeliverSMResp deliverSMResp = null;
		try {
			deliverSMResp = sessionContext.deliver(request);
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
			
		}
		return deliverSMResp;
	}

	public EnquireLinkResp enquireLink() throws ClientException {
		EnquireLinkResp enquireLinkResp = null;
		try {
			enquireLinkResp = sessionContext.enquireLink();
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
			
		}
		return enquireLinkResp;
	}

	@Override
	protected SessionContext sessionContext() {
		return sessionContext;
	}
}
