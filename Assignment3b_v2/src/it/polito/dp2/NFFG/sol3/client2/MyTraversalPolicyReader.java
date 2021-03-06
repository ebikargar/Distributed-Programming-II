package it.polito.dp2.NFFG.sol3.client2;

import java.util.HashSet;
import java.util.Set;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import it.polito.dp2.NFFG.sol3.service.jaxb.FuncType;
import it.polito.dp2.NFFG.sol3.service.jaxb.Nffg;
import it.polito.dp2.NFFG.sol3.service.jaxb.ResultType;
import it.polito.dp2.NFFG.sol3.service.jaxb.TraversalPolicyType;

public class MyTraversalPolicyReader extends MyReachablilityPolicyReader implements TraversalPolicyReader {

	// ------------ TraversalPolicy Element--------------------------------//
	private HashSet<FunctionalType> travers_r_set = new HashSet<FunctionalType>();
	// --------------------------------------------------------------------//

	public MyTraversalPolicyReader(Nffg nffg, TraversalPolicyType traversal_policy_r, NodeReader srcNode,
			NodeReader destNode, ResultType result_r, NffgReader traversal_policy_nffg_r) {
		super(nffg, traversal_policy_r.getPolicyName(), traversal_policy_r.isIsPositive(), result_r, srcNode, destNode,
				traversal_policy_nffg_r);

		for (FuncType travers_r_item : traversal_policy_r.getTraversComponent()) {
			switch (travers_r_item) {
			case FW:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.FW);
				break;
			case DPI:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.DPI);
				break;
			case NAT:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.NAT);
				break;
			case SPAM:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.SPAM);
				break;
			case CACHE:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.CACHE);
				break;
			case VPN:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.VPN);
				break;
			case WEB_SERVER:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.WEB_SERVER);
				break;
			case WEB_CLIENT:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.WEB_CLIENT);
				break;
			case MAIL_SERVER:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.MAIL_SERVER);
				break;
			case MAIL_CLIENT:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.MAIL_CLIENT);
				break;

			default:
				travers_r_set.add(it.polito.dp2.NFFG.FunctionalType.FW);
			}
		}
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
