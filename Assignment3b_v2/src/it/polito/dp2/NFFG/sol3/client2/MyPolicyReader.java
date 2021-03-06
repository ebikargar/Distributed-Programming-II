package it.polito.dp2.NFFG.sol3.client2;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.VerificationResultReader;
import it.polito.dp2.NFFG.sol3.service.jaxb.Nffg;
import it.polito.dp2.NFFG.sol3.service.jaxb.ResultType;

public class MyPolicyReader implements PolicyReader {

	// ------------ Policy Element-----------------------//
	private String plicyName;
	protected NffgReader policy_nffg_r;
	protected VerificationResultReader policy_verification_r;
	protected Boolean policy_isPositive;
	// ---------------------------------------------------------//

	public MyPolicyReader(Nffg nffg, String policyName, Boolean isPositive, ResultType verification_result_r,
			NffgReader policy_nffg_r) {
		this.plicyName = policyName;
		this.policy_nffg_r = policy_nffg_r;
		if (verification_result_r != null)
			this.policy_verification_r = new MyVerificationResultReader(nffg, verification_result_r, this);
		this.policy_isPositive = isPositive;
	}
	// ---------------------------------------------------------//

	@Override
	public String getName() {
		if (plicyName != null)
			return this.plicyName;
		else {
			System.out.println("policyName object is null");
			return null;
		}
	}

	@Override
	public NffgReader getNffg() {
		if (policy_nffg_r != null)
			return this.policy_nffg_r;
		else {
			System.out.println("policy_nffg_r object is null");
			return null;
		}
	}

	@Override
	public VerificationResultReader getResult() {
		if (policy_verification_r != null)
			return this.policy_verification_r;
		else {
			System.out.println("policyVerificationResult object is null");
			return null;
		}
	}

	@Override
	public Boolean isPositive() {
		if (policy_isPositive != null)
			return this.policy_isPositive;
		else {
			System.out.println("isPositive object is null");
			return null;
		}
	}

}
