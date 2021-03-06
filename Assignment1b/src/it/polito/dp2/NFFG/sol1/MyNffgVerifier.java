package it.polito.dp2.NFFG.sol1;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.ReachabilityPolicyReader;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import it.polito.dp2.NFFG.sol1.jaxb.LinkType;
import it.polito.dp2.NFFG.sol1.jaxb.LinksType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgVerifierType;
import it.polito.dp2.NFFG.sol1.jaxb.NodeType;
import it.polito.dp2.NFFG.sol1.jaxb.NodesType;
import it.polito.dp2.NFFG.sol1.jaxb.ReachabilityPolicyType;
import it.polito.dp2.NFFG.sol1.jaxb.TraversalPolicyType;

public class MyNffgVerifier implements NffgVerifier {

	// ------------ NffgVerifier Element --------------------------------//
	private Set<NffgReader> nffg_r_set;
	private HashSet<PolicyReader> policy_r_set = new HashSet<PolicyReader>();
	private static final String SCHEMA_FILE = "xsd" + File.separatorChar + "nffgInfo.xsd";

	// ---------------------------------------------------------//

	public MyNffgVerifier() throws NffgVerifierException {

		String xmlFileName = System.getProperty("it.polito.dp2.NFFG.sol1.NffgInfo.file");
		Object rootObj;

		try {
			rootObj = unmarshal(xmlFileName, SCHEMA_FILE, "it.polito.dp2.NFFG.sol1.jaxb");
		} catch (Exception e) {
			throw new NffgVerifierException(e);
		}

		if (!(rootObj instanceof JAXBElement<?>))
			throw new NffgVerifierException("The root element is not of type JAXBElement<>");

		Object rootObjValue = ((JAXBElement<?>) rootObj).getValue();
		if (rootObjValue == null)
			throw new NffgVerifierException("The value of the root element is null");

		if (!(rootObjValue instanceof NffgVerifierType))
			throw new NffgVerifierException("The root element is not of type JAXBElement<NffgVerifierType>");

		NffgVerifierType rootElement = (NffgVerifierType) rootObjValue;

		nffg_r_set = new HashSet<NffgReader>();
		for (NffgType nffg_t : rootElement.getNffg()) {

			HashMap<String, NodeReader> node_list = new HashMap<String, NodeReader>();
			HashMap<String, LinkReader> link_list = new HashMap<String, LinkReader>();

			NffgReader nffg_r = new MyNffgReader(nffg_t, node_list);
			nffg_r_set.add(nffg_r);

			NodesType node_set = nffg_t.getNodes();
			for (NodeType node_r : node_set.getNode()) {
				MyNodeReader myNodeReader = new MyNodeReader(node_r, link_list);
				node_list.put(node_r.getNodeName(), myNodeReader);
			}

			LinksType link_set = nffg_t.getLinks();
			for (LinkType link_r : link_set.getLink()) {
				MyLinkReader myLinkReader = new MyLinkReader(nffg_t, link_r, node_list.get(link_r.getSrcNode()),
						node_list.get(link_r.getDestNode()));
				link_list.put(link_r.getLinkName(), myLinkReader);
			}

			List<ReachabilityPolicyType> reachability_policy_set = nffg_t.getPolicies().getReachabilityPolicy();
			for (ReachabilityPolicyType reachability_policy_t : reachability_policy_set) {
				ReachabilityPolicyReader reachability_policy_r = new MyReachablilityPolicyReader(nffg_t,
						reachability_policy_t.getPolicyName(), reachability_policy_t.isIsPositive(),
						reachability_policy_t.getVerificationResult(),
						node_list.get(reachability_policy_t.getSrcNode()),
						node_list.get(reachability_policy_t.getDestNode()), nffg_r);
				policy_r_set.add(reachability_policy_r);
			}
			List<TraversalPolicyType> traversal_policy_set = nffg_t.getPolicies().getTraversalPolicy();
			for (TraversalPolicyType traversal_policy_t : traversal_policy_set) {
				TraversalPolicyReader traversal_policy_r = new MyTraversalPolicyReader(nffg_t, traversal_policy_t,
						node_list.get(traversal_policy_t.getSrcNode()), node_list.get(traversal_policy_t.getDestNode()),
						traversal_policy_t.getVerificationResult(), nffg_r);
				policy_r_set.add(traversal_policy_r);
			}

		}

	}

	// -----------------------------------------------------------------------------------------------------//

	private static Object unmarshal(String xmlFileName, String xsdFileName, String contextPath) throws Exception {
		Unmarshaller u = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(contextPath);
			u = jc.createUnmarshaller();
		} catch (JAXBException e) {
			System.err.println("JAXBException");
			e.printStackTrace();
			throw e;
		}

		SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
		Schema schema = null;
		try {
			schema = sf.newSchema(new File(xsdFileName));
		} catch (org.xml.sax.SAXException se) {
			System.err.println("Unable to validate due to following error");
			se.printStackTrace();
			throw se;
		}

		u.setSchema(schema);
		Object rootObj = null;
		try {
			rootObj = u.unmarshal(new File(xmlFileName));
		} catch (JAXBException e) {
			System.err.println("JAXBException");
			e.printStackTrace();
			throw e;
		}

		if (rootObj == null)
			throw new Exception("The root object is null");

		return rootObj;
	}

	// ----------------------------------------------------------------------//

	@Override
	public NffgReader getNffg(String arg0) {
		for (NffgReader nffg_r : nffg_r_set) {
			if (nffg_r.equals(arg0)) {
				return nffg_r;
			}
		}
		return null;
	}

	@Override
	public Set<NffgReader> getNffgs() {
		if (nffg_r_set != null)
			return nffg_r_set;
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
			System.out.println("Policyreader set object is Null");
			return null;
		}
	}

	@Override
	public Set<PolicyReader> getPolicies(String arg0) {

		HashSet<PolicyReader> policy_r_set = new HashSet<PolicyReader>();
		for (PolicyReader policy_r : policy_r_set) {
			if (policy_r.getNffg().getName().equals(arg0)) {
				policy_r_set.add(policy_r);
			}
		}
		if (policy_r_set.isEmpty())
			return null;
		return policy_r_set;
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
