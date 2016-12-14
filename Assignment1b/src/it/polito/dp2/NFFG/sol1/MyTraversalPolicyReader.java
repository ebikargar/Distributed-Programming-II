package it.polito.dp2.NFFG.sol1;

import java.util.Set;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.TraversalPolicyType;

public class MyTraversalPolicyReader extends MyReachablilityPolicyReader implements TraversalPolicyReader {

	// ------------ TraversalPolicy-----------------------//
	private Set<FunctionalType> travers_r_set;
	// ---------------------------------------------------------//

	public MyTraversalPolicyReader(NffgType nffg, TraversalPolicyType traversal_policy_r) {
		super(nffg, traversal_policy_r);
		travers_r_set = ((TraversalPolicyReader) traversal_policy_r).getTraversedFuctionalTypes();

	}

	// ---------------------------------------------------------//

	@Override
	public Set<FunctionalType> getTraversedFuctionalTypes() {
		if (travers_r_set != null)
			return this.travers_r_set;
		else {
			System.out.println("Traversalfunction Object is Null in TraversalPolicy");
			return null;
		}
	}

}
