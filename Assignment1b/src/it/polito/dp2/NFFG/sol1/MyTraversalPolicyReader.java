package it.polito.dp2.NFFG.sol1;

import java.util.Set;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.ReachabilityPolicyType;
import it.polito.dp2.NFFG.sol1.jaxb.TraversalPolicyType;

public class MyTraversalPolicyReader extends MyReachablilityPolicyReader implements TraversalPolicyReader {

	private TraversalPolicyType traversal_policy;
	private Set<FunctionalType> read_travers_list;

	public MyTraversalPolicyReader(NffgType nffg, TraversalPolicyType traversal_policy_r) {
		super(nffg, traversal_policy_r);
		read_travers_list = ((TraversalPolicyReader) traversal_policy_r).getTraversedFuctionalTypes();

	}
	
	@Override
	public Set<FunctionalType> getTraversedFuctionalTypes() {
		return this.read_travers_list;
	}
	
//	public TraversalPolicyType getTraversalPolicyType()
//	{
//		for (FunctionalType travers_r : read_travers_list) {
//			traversal_policy.getTraversComponent().add(FuncType.fromValue(travers_r.name()));
//		}
//		return traversal_policy;
//	}
}
