package it.polito.dp2.NFFG.sol3.client1;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.lab3.NFFGClient;
import it.polito.dp2.NFFG.lab3.NFFGClientException;

public class NFFGClientFactory extends it.polito.dp2.NFFG.lab3.NFFGClientFactory{
	
	// ------------ClientFactory --------------------------------//
	private NffgVerifier monitor;
	// ----------------------------------------------------------------//

	// ----------------------------------------------------------------//
	@Override
	public NFFGClient newNFFGClient() throws NFFGClientException {

		it.polito.dp2.NFFG.NffgVerifierFactory factory = it.polito.dp2.NFFG.NffgVerifierFactory.newInstance();
		try {
			monitor = factory.newNffgVerifier();
		} catch (NffgVerifierException e) {
			System.out.println("NffgVerifierException: Error during NffgVerifier");
			e.printStackTrace();
		}
		
		String Url_address = System.getProperty("it.polito.dp2.NFFG.lab3.URL");
		if (Url_address==null)
			Url_address = "http://localhost:8080/NffgService/rest";
		
		return new MyNFFGClient(Url_address, monitor);
	}
	
	
	
	
	
	
	
	
	
	

}
