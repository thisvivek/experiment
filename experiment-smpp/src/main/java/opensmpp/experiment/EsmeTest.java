package opensmpp.experiment;

import java.io.UnsupportedEncodingException;

import org.smpp.Data;
import org.smpp.ServerPDUEventListener;
import org.smpp.pdu.Address;
import org.smpp.pdu.AddressRange;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.UnbindResp;
import org.smpp.pdu.WrongDateFormatException;
import org.smpp.pdu.WrongLengthOfStringException;

public class EsmeTest {
	private static String smppclient1 = "Test";
	private static  String serviceType = "";
	private static  Address sourceAddress = new Address();
	private static  Address destAddress = new Address();
	private static  String scheduleDeliveryTime = "";
	private static  String validityPeriod = "";
	private static  String shortMessage = "";
	private static  int numberOfDestination = 1;
	private static  String messageId = "";
	private static  byte esmClass = 0;
	private static  byte protocolId = 0;
	private static  byte priorityFlag = 0;
	private static  byte registeredDelivery = 0;
	private static  byte replaceIfPresentFlag = 0;
	private static  byte dataCoding = 0;
	private static  byte smDefaultMsgId = 0;
	private static AddressRange addressRange = new AddressRange();

	
	public static void main(String[] args) {
		ClientConnection esmeConnection = new ClientConnection("localhost", 2775);
		SessionContext esmeSession = new SessionContext(esmeConnection);

		ClientSession esmeOperations = new SMPPSession(esmeSession);
		
		bind(esmeOperations, (event) -> System.out.printf("Handle Event: %s%n", event.getPDU().debugString()));
		//submit_SM(esmeOperations);
		//unBind(esmeOperations);
	}

	private static void bind(ClientSession esmeOperations, ServerPDUEventListener pduListener) {
		BindRequest bindRequest = new BindTransmitter();
		BindResponse bindResponse = null;
		try {
			bindRequest.setSystemId("smppclient2");
			bindRequest.setPassword("password");
			bindRequest.setSystemType(smppclient1);
			bindRequest.setInterfaceVersion((byte) 0x34);
			bindRequest.setAddressRange(addressRange);
		} catch (WrongLengthOfStringException e) {
		}

		try {
			bindResponse = esmeOperations.bind(bindRequest, pduListener);
			System.out.printf("Bind Response : %s%n", bindResponse.debugString());
		} catch (ClientException e) {

		}
	}


	private static void unBind(ClientSession esmeOperations) {
		try {
			UnbindResp unbindResp = esmeOperations.unbind();
			System.out.printf("Unbind Response : %s%n", unbindResp.debugString());
		} catch (ClientException e) {
		}
	}


	private static void submit_SM(ClientSession esmeOperations) {
		SubmitSM sm = new SubmitSM();
		// set values
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
			esmeOperations.submit(sm);
		} catch (ClientException e) {
			
		}
	}
}
