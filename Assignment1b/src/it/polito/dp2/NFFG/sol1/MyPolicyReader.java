package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.VerificationResultReader;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.ReachabilityPolicyType;
//import it.polito.dp2.NFFG.sol1.jaxb.PolicyType;

public class MyPolicyReader implements PolicyReader {

	// ------------ Policy Element-----------------------//
	private String plicyName;
	private NffgReader policy_nffg_r;
	private VerificationResultReader policy_verification_r;
	private Boolean policy_isPositive;
	// ---------------------------------------------------------//

	public MyPolicyReader(NffgType nffg, ReachabilityPolicyType policy_r) {
		if (policy_r != null) {
			this.plicyName = policy_r.getPolicyName();
			this.policy_nffg_r = new MyNffgReader(nffg);
			this.policy_verification_r = new MyVerificationResultReader(nffg, policy_r);
			this.policy_isPositive = policy_r.isIsPositive();
		}
		System.out.println("MyPolicyReader fulfilled Correctly");
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
