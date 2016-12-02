package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.ReachabilityPolicyReader;
import it.polito.dp2.NFFG.VerificationResultReader;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.NodeType;
import it.polito.dp2.NFFG.sol1.jaxb.NodesType;
import it.polito.dp2.NFFG.sol1.jaxb.ReachabilityPolicyType;

public class MyReachablilityPolicyReader implements ReachabilityPolicyReader {

	// ------------ Policy Element-----------------------//
	private String reachability_policyName;
	private NffgReader reachability_policy_nffg_r;
	private VerificationResultReader reachability_policy_verification_r;
	private Boolean reachability_policy_isPositive;
	private NodeReader reachability_srcNode;
	private NodeReader reachability_destNode;
	// ---------------------------------------------------------//

	public MyReachablilityPolicyReader(NffgType nffg, ReachabilityPolicyType reacability_policy_r) {

		if (reacability_policy_r != null) {
			this.reachability_policyName = reacability_policy_r.getPolicyName();
			this.reachability_policy_nffg_r = new MyNffgReader(nffg);
			this.reachability_policy_verification_r = new MyVerificationResultReader(nffg, reacability_policy_r);
			this.reachability_policy_isPositive = reacability_policy_r.isIsPositive();

			// getting srcNode & destNode for reachability policy
			NodesType nodes = nffg.getNodes();
			for (NodeType node_r : nodes.getNode()) {
				node_r.equals(reacability_policy_r.getSrcNode());
				this.reachability_srcNode = new MyNodeReader(node_r, nffg);
			}
			for (NodeType node_r : nodes.getNode()) {
				node_r.equals(reacability_policy_r.getDestNode());
				this.reachability_destNode = new MyNodeReader(node_r, nffg);
			}

		}

		System.out.println("ReachablilityPolicyReader fulfilled Correctly");
	}

	// ---------------------------------------------------------//

	@Override
	public NffgReader getNffg() {
		if (reachability_policy_nffg_r != null)
			return this.reachability_policy_nffg_r;
		else {
			System.out.println("nffg Object in reacablility policy is Null");
			return null;
		}		
		
	}

	@Override
	public VerificationResultReader getResult() {
		if(reachability_policy_verification_r !=null)
		return this.reachability_policy_verification_r;
		else{
			System.out.println("VerificationResultReader object in reachablility poilicy is Null");
			return null;
		}
	}

	@Override
	public Boolean isPositive() {
		if(reachability_policy_isPositive !=null)
			return this.reachability_policy_isPositive;
		else{
			System.out.println("isPositive Object in reachability policy is Null");
			return null;
		}
	}

	@Override
	public String getName() {
		if(reachability_policyName !=null)
		return this.reachability_policyName;
		else{
			System.out.println("policyName Object in reachability Policy is Null");
			return null;
		}
	}

	@Override
	public NodeReader getDestinationNode() {
		if(reachability_destNode !=null)
		return this.reachability_destNode;
		else {
			System.out.println("DestinationNode Object in reachability policy is Null");
			return null;
		}
		
	}

	@Override
	public NodeReader getSourceNode() {
		if(reachability_srcNode !=null)
					return this.reachability_srcNode;
		else{
			System.out.println("SourceNode Object in reachability policy is Null");
			return null;
		}
	}

}
