package it.polito.dp2.NFFG.sol3.client2;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;

public class NffgVerifierFactory extends it.polito.dp2.NFFG.NffgVerifierFactory {

	// --------------------------------------------------------------//

	@Override
	public NffgVerifier newNffgVerifier() throws NffgVerifierException {
		String Url_address = System.getProperty("it.polito.dp2.NFFG.lab3.URL");
		if (Url_address == null)
			Url_address = "http://localhost:8080/NffgService/rest/";
		return new MyNffgVerifier(Url_address);
	}

}
