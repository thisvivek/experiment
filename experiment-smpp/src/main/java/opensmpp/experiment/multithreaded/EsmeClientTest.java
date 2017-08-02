package opensmpp.experiment.multithreaded;

import java.util.Scanner;

public class EsmeClientTest {
	public static void main(String[] args) {
		EsmeClient esmeClient = new EsmeClient();
		
		esmeClient.init();
		
		esmeClient.process();
		
		Scanner input = new Scanner(System.in);
		System.out.printf("Press any key to cleanup.");
		input.nextLine();
		input.close();
		
		esmeClient.cleanup();
	}
}
