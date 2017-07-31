package opensmpp.experiment;

import org.smpp.ServerPDUEventListener;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.DeliverSMResp;
import org.smpp.pdu.EnquireLinkResp;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.UnbindResp;

public interface ClientSession {

	BindResponse bind(BindRequest bindReq, ServerPDUEventListener pduListener) throws ClientException;

	UnbindResp unbind()  throws ClientException;

	SubmitSMResp submit(SubmitSM request)  throws ClientException;
	
	DeliverSMResp deliver(DeliverSM request) throws ClientException;
	
	EnquireLinkResp enquireLink() throws ClientException;
}
