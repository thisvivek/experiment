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

public interface EsmeOperations {

	BindResponse bind(BindRequest bindReq, ServerPDUEventListener pduListener) throws EsmeException;

	UnbindResp unbind()  throws EsmeException;

	SubmitSMResp submit(SubmitSM request)  throws EsmeException;
	
	DeliverSMResp deliver(DeliverSM request) throws EsmeException;
	
	EnquireLinkResp enquireLink() throws EsmeException;
}
