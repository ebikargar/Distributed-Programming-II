package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.ReachabilityPolicyReader;
import it.polito.dp2.NFFG.VerificationResultReader;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.ResultType;

public class MyReachablilityPolicyReader extends MyPolicyReader implements ReachabilityPolicyReader {

	// ------------ ReachabilityPolicy Element-----------------------//
	private NodeReader reachability_srcNode;
	private NodeReader reachability_destNode;
	// ---------------------------------------------------------//

	public MyReachablilityPolicyReader(NffgType nffg, String policyName, Boolean isPositive,
			ResultType verification_result_r, NodeReader srcNode, NodeReader destNode,
			NffgReader reachability_policy_r) {

		super(nffg, policyName, isPositive, verification_result_r, reachability_policy_r);

		this.reachability_srcNode = srcNode;
		this.reachability_destNode = destNode;

		System.out.println("ReachablilityPolicyReader fulfilled Correctly");

	}

	// ---------------------------------------------------------//

	@Override
	public NffgReader getNffg() {
		if (policy_nffg_r != null)
			return this.policy_nffg_r;
		else {
			System.out.println("nffg Object in ReachablilityPolicy is Null");
			return null;
		}

	}

	@Override
	public VerificationResultReader getResult() {
		if (policy_verification_r != null)
			return this.policy_verification_r;
		else {
			System.out.println("VerificationResultReader object in ReachablilityPolicy is Null");
			return null;
		}
	}

	@Override
	public Boolean isPositive() {
		if (policy_isPositive != null)
			return this.policy_isPositive;
		else {
			System.out.println("isPositive Object in ReachablilityPolicy is Null");
			return null;
		}
	}

	@Override
	public String getName() {
		if (super.getName() != null)
			return super.getName();
		else {
			System.out.println("policyName Object in ReachablilityPolicy is Null");
			return null;
		}
	}

	@Override
	public NodeReader getDestinationNode() {
		if (reachability_destNode != null)
			return this.reachability_destNode;
		else {
			System.out.println("DestinationNode Object in ReachablilityPolicy is Null");
			return null;
		}

	}

	@Override
	public NodeReader getSourceNode() {
		if (reachability_srcNode != null)
			return this.reachability_srcNode;
		else {
			System.out.println("SourceNode Object in ReachablilityPolicy is Null");
			return null;
		}
	}

}
