package it.polito.dp2.NFFG.sol1;

import java.util.Calendar;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.PolicyReader;

public class MyNffgVerifier implements NffgVerifier {

	// ------------ NffgVerifier Element --------------------------------//

	private Set<NffgReader> nffg_r_set;
	private Set<PolicyReader> policy_r_set;
	
	
	// ---------------------------------------------------------//
	
	
	public MyNffgVerifier() throws NffgVerifierException {
	
	
	
	
	
	
	}

	@Override
	public NffgReader getNffg(String arg0) {

		return null;
	}

	@Override
	public Set<NffgReader> getNffgs() {
		return null;
	}

	@Override
	public Set<PolicyReader> getPolicies() {
		return null;
	}

	@Override
	public Set<PolicyReader> getPolicies(String arg0) {
		return null;
	}

	@Override
	public Set<PolicyReader> getPolicies(Calendar arg0) {
		return null;
	}

}
