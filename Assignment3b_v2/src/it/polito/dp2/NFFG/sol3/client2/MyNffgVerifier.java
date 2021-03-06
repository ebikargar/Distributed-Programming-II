package it.polito.dp2.NFFG.sol3.client2;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.ReachabilityPolicyReader;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import it.polito.dp2.NFFG.sol3.service.jaxb.LinkType;
import it.polito.dp2.NFFG.sol3.service.jaxb.LinksType;
import it.polito.dp2.NFFG.sol3.service.jaxb.Nffg;
import it.polito.dp2.NFFG.sol3.service.jaxb.NodeType;
import it.polito.dp2.NFFG.sol3.service.jaxb.NodesType;
import it.polito.dp2.NFFG.sol3.service.jaxb.Policy;
import it.polito.dp2.NFFG.sol3.service.jaxb.ReachabilityPolicyType;
import it.polito.dp2.NFFG.sol3.service.jaxb.TraversalPolicyType;

public class MyNffgVerifier implements NffgVerifier {

	// ------------ NffgVerifier Element --------------------------------//
	private Map<String, NffgReader> nffg_r_set;
	private HashSet<PolicyReader> policy_r_set = new HashSet<PolicyReader>();
	// ------------------------------------------------------------------//

	public MyNffgVerifier(String url_address) throws NffgVerifierException {
		// make the webTarget
		WebTarget target;
		target = ClientBuilder.newClient().target(url_address);
		// Get Nffgs -->put into nffgMap
		Response getNffg = target.path("nffgservice/").request(MediaType.APPLICATION_XML).get();
		if (getNffg.getStatus() != 200)
			throw new NffgVerifierException();
		List<Nffg> nffgs = getNffg.readEntity(new GenericType<List<Nffg>>() {
		});

		HashMap<String, Nffg> nffgMap = new HashMap<String, Nffg>();
		for (Nffg nffg : nffgs) {
			nffgMap.put(nffg.getNffgName(), nffg);
		}
		// get policies
		Response getpolicy = target.path("policyservice/").request(MediaType.APPLICATION_XML).get();
		if (getpolicy.getStatus() != 200)
			throw new NffgVerifierException();

		List<Policy> policies = getpolicy.readEntity(new GenericType<List<Policy>>() {
		});

		nffg_r_set = new HashMap<>();
		for (Nffg nffgItem : nffgs) {

			HashMap<String, NodeReader> node_list = new HashMap<String, NodeReader>();
			HashMap<String, LinkReader> link_list = new HashMap<String, LinkReader>();

			NffgReader nffg_r = new MyNffgReader(nffgItem, node_list);
			nffg_r_set.put(nffg_r.getName(), nffg_r);

			// Get the Nodes -->Put them into the Nodes Hashmap
			NodesType node_set = nffgItem.getNodes();
			for (NodeType node_r : node_set.getNode()) {
				MyNodeReader myNodeReader = new MyNodeReader(node_r, link_list);
				node_list.put(node_r.getNodeName(), myNodeReader);
			}
			// Get the Links -->Put them into the Links Hashmap
			LinksType link_set = nffgItem.getLinks();
			for (LinkType link_r : link_set.getLink()) {
				MyLinkReader myLinkReader = new MyLinkReader(nffgItem, link_r, node_list.get(link_r.getSrcNode()),
						node_list.get(link_r.getDestNode()));
				link_list.put(link_r.getLinkName(), myLinkReader);
			}

		}
		// Get the Policies -->Put them into the Policy HashSet
		for (Policy policyItem : policies) {
			NffgReader nffg_r;
			Nffg nffgItem;
			// Policy is instance of Reachability
			if (policyItem.getReachabilityPolicy() != null) {
				nffg_r = nffg_r_set.get(policyItem.getReachabilityPolicy().getNffgName());
				nffgItem = nffgMap.get(policyItem.getReachabilityPolicy().getNffgName());

				ReachabilityPolicyType reachability_policy_t = policyItem.getReachabilityPolicy();
				ReachabilityPolicyReader reachability_policy_r = new MyReachablilityPolicyReader(nffgItem,
						reachability_policy_t.getPolicyName(), reachability_policy_t.isIsPositive(),
						reachability_policy_t.getVerificationResult(),
						nffg_r.getNode(reachability_policy_t.getSrcNode()),
						nffg_r.getNode(reachability_policy_t.getDestNode()), nffg_r);
				policy_r_set.add(reachability_policy_r);
			} else {
				// Policy is instance of Traversal
				nffg_r = nffg_r_set.get(policyItem.getTraversalPolicy().getNffgName());
				nffgItem = nffgMap.get(policyItem.getTraversalPolicy().getNffgName());

				TraversalPolicyType traversal_policy_t = policyItem.getTraversalPolicy();
				TraversalPolicyReader traversal_policy_r = new MyTraversalPolicyReader(nffgItem, traversal_policy_t,
						nffg_r.getNode(traversal_policy_t.getSrcNode()),
						nffg_r.getNode(traversal_policy_t.getDestNode()), traversal_policy_t.getVerificationResult(),
						nffg_r);
				policy_r_set.add(traversal_policy_r);

			}
		}
	}
	// ------------------------------------------------------------------//

	@Override
	public NffgReader getNffg(String arg0) {
		for (NffgReader nffg_r : nffg_r_set.values()) {
			if (nffg_r.equals(arg0)) {
				return nffg_r;
			}
		}
		return null;
	}

	@Override
	public Set<NffgReader> getNffgs() {
		if (nffg_r_set != null)
			return new HashSet<NffgReader>(nffg_r_set.values());
		else {
			System.out.println("nffg_r_set Object is Null");
			return null;
		}
	}

	@Override
	public Set<PolicyReader> getPolicies() {
		if (policy_r_set != null)
			return policy_r_set;
		else {
			System.out.println("policy_r_set object is Null");
			return null;
		}
	}

	@Override
	public Set<PolicyReader> getPolicies(String arg0) {
		Set<PolicyReader> result = new HashSet<PolicyReader>();
		for (PolicyReader policy_r : policy_r_set) {
			if (policy_r.getNffg().getName().equals(arg0)) {
				result.add(policy_r);
			}
		}
		if (result.isEmpty()) {
			return null;
		}
		return result;
	}

	@Override
	public Set<PolicyReader> getPolicies(Calendar arg0) {
		HashSet<PolicyReader> policy_r_set = new HashSet<PolicyReader>();
		for (PolicyReader policy_r : policy_r_set) {
			if (policy_r.getResult().getVerificationTime().after(arg0)) {
				policy_r_set.add(policy_r);
			}
		}
		if (policy_r_set.isEmpty())
			return null;
		return policy_r_set;
	}

}
