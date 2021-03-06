package it.polito.dp2.NFFG.sol2;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.lab2.ReachabilityTester;
import it.polito.dp2.NFFG.lab2.ReachabilityTesterException;

public class ReachabilityTesterFactory extends it.polito.dp2.NFFG.lab2.ReachabilityTesterFactory {

	// ------------ ReachabilityTesterFactory --------------------------------//
	private NffgVerifier monitor;
	// ----------------------------------------------------------------//

	// ----------------------------------------------------------------//
	@Override
	public ReachabilityTester newReachabilityTester() throws ReachabilityTesterException {

		it.polito.dp2.NFFG.NffgVerifierFactory factory = it.polito.dp2.NFFG.NffgVerifierFactory.newInstance();
		try {
			monitor = factory.newNffgVerifier();
		} catch (NffgVerifierException e) {
			System.out.println("NffgVerifierException: Error during NffgVerifier");
			e.printStackTrace();
		}

		String Url_address = System.getProperty("it.polito.dp2.NFFG.lab2.URL");
		return new MyReachabilityTester(Url_address, monitor);
	}

}
