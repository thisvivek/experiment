package opensmpp.experiment.multithreaded;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.smpp.Data;
import org.smpp.Session;
import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.Address;
import org.smpp.pdu.AddressRange;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.Response;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.UnbindResp;
import org.smpp.pdu.ValueNotSetException;
import org.smpp.pdu.WrongDateFormatException;
import org.smpp.pdu.WrongLengthOfStringException;

public class EsmeSmscOperations {

	private static String serviceType = "";
	private static Address sourceAddress = new Address();
	private static Address destAddress = new Address();
	private static String scheduleDeliveryTime = "";
	private static String validityPeriod = "";
	private static String shortMessage = "";
	private static byte esmClass = 0;
	private static byte protocolId = 0;
	private static byte priorityFlag = 0;
	private static byte registeredDelivery = 0;
	private static byte replaceIfPresentFlag = 0;
	private static byte dataCoding = 0;
	private static byte smDefaultMsgId = 0;
	
	private final Session session;

	public EsmeSmscOperations(Session session) {
		this.session =  session;
	}

	public Response bind(EsmeRequest req) {
		BindRequest request = (BindRequest)req.getRequest();
		BindResponse response = null;
		AddressRange addressRange = new AddressRange();
		try {
			request.setSystemId("smppclient2");
			request.setPassword("password");
			request.setSystemType(serviceType);
			request.setInterfaceVersion((byte) 0x34);
			request.setAddressRange(addressRange);
		} catch (WrongLengthOfStringException e) {
		}
		try {
			response = session.bind(request, (event) -> System.out.printf("Handle Event: %s%n", event.getPDU().debugString()));
		} catch (ValueNotSetException e) {

		} catch (TimeoutException e) {

		} catch (PDUException e) {

		} catch (WrongSessionStateException e) {

		} catch (IOException e) {

		}
		return response;
	}

	public SubmitSMResp submit_SM(EsmeRequest request) {
		SubmitSM sm = (SubmitSM)request.getRequest();
		SubmitSMResp response = null;
		
		try {
			sm.setServiceType(serviceType);
			sm.setSourceAddr(sourceAddress);
			sm.setDestAddr(destAddress);
			sm.setReplaceIfPresentFlag(replaceIfPresentFlag);
			sm.setShortMessage(shortMessage, Data.ENC_GSM7BIT);
			sm.setScheduleDeliveryTime(scheduleDeliveryTime);
			sm.setValidityPeriod(validityPeriod);
			sm.setEsmClass(esmClass);
			sm.setProtocolId(protocolId);
			sm.setPriorityFlag(priorityFlag);
			sm.setRegisteredDelivery(registeredDelivery);
			sm.setDataCoding(dataCoding);
			sm.setSmDefaultMsgId(smDefaultMsgId);
		} catch (WrongLengthOfStringException e) {

		} catch (UnsupportedEncodingException e) {

		} catch (WrongDateFormatException e) {

		}
		sm.assignSequenceNumber();
		try {
			response = session.submit(sm);
		} catch (ValueNotSetException e) {

		} catch (TimeoutException e) {

		} catch (PDUException e) {

		} catch (WrongSessionStateException e) {

		} catch (IOException e) {

		}
		return response;
	}
	
	public Response unbind(EsmeRequest req) {
		UnbindResp response = null;
		try {
			response = session.unbind();
		} catch (ValueNotSetException e) {
		
		} catch (TimeoutException e) {
		
		} catch (PDUException e) {
		
		} catch (WrongSessionStateException e) {
		
		} catch (IOException e) {
		
		}
		return response;
	}
}
