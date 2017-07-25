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

public class EsmeOperationsImpl implements EsmeOperations {

	private EsmeSession esmeSession;

	public EsmeOperationsImpl(EsmeSession esmeSession) {
		this.esmeSession = esmeSession;
	}

	public BindResponse bind(BindRequest bindReq, ServerPDUEventListener pduListener) throws EsmeException {
		BindResponse bindResponse = null;
		try {
			bindResponse = esmeSession.bind(bindReq, pduListener);
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
			
		}
		return bindResponse;
	}

	public UnbindResp unbind() throws EsmeException {
		UnbindResp unbindResp = null;
		try {
			unbindResp = esmeSession.unbind();
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
		}
		return unbindResp;
	}

	public SubmitSMResp submit(SubmitSM request) throws EsmeException {
		SubmitSMResp submitSMResp = null;
		try {
			submitSMResp = esmeSession.submit(request);
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
			
		}
		return submitSMResp;
	}

	public DeliverSMResp deliver(DeliverSM request) throws EsmeException {
		DeliverSMResp deliverSMResp = null;
		try {
			deliverSMResp = esmeSession.deliver(request);
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
			
		}
		return deliverSMResp;
	}

	public EnquireLinkResp enquireLink() throws EsmeException {
		EnquireLinkResp enquireLinkResp = null;
		try {
			enquireLinkResp = esmeSession.enquireLink();
		} catch (ValueNotSetException e) {
			
		} catch (TimeoutException e) {
			
		} catch (PDUException e) {
			
		} catch (WrongSessionStateException e) {
			
		} catch (IOException e) {
			
		}
		return enquireLinkResp;
	}
}
