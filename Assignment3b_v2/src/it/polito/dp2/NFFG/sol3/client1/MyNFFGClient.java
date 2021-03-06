package it.polito.dp2.NFFG.sol3.client1;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.ReachabilityPolicyReader;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import it.polito.dp2.NFFG.lab3.AlreadyLoadedException;
import it.polito.dp2.NFFG.lab3.NFFGClient;
import it.polito.dp2.NFFG.lab3.ServiceException;
import it.polito.dp2.NFFG.lab3.UnknownNameException;
import it.polito.dp2.NFFG.sol3.service.jaxb.ResultType;
import it.polito.dp2.NFFG.sol3.service.jaxb.ReachabilityPolicyType;
import it.polito.dp2.NFFG.sol3.service.jaxb.TraversalPolicyType;
import it.polito.dp2.NFFG.sol3.service.jaxb.FuncType;
import it.polito.dp2.NFFG.sol3.service.jaxb.ProviderCatType;
import it.polito.dp2.NFFG.sol3.service.jaxb.LinkType;
import it.polito.dp2.NFFG.sol3.service.jaxb.LinksType;
import it.polito.dp2.NFFG.sol3.service.jaxb.Nffg;
import it.polito.dp2.NFFG.sol3.service.jaxb.NodeType;
import it.polito.dp2.NFFG.sol3.service.jaxb.NodesType;
import it.polito.dp2.NFFG.sol3.service.jaxb.ObjectFactory;
import it.polito.dp2.NFFG.sol3.service.jaxb.Policy;

public class MyNFFGClient implements NFFGClient {

	// ------------ MyNFFGClient Element -----------------------//
	private NffgVerifier nffgVerifier;
	private WebTarget target;
	private ObjectFactory objFact;
	// ---------------------------------------------------------//

	public MyNFFGClient(String url_address, NffgVerifier monitor) {
		this.nffgVerifier = monitor;
		this.target = ClientBuilder.newClient().target(url_address);
		this.objFact = new ObjectFactory();
	}
	// ---------------------------------------------------------//

	@Override
	public void loadNFFG(String name) throws UnknownNameException, AlreadyLoadedException, ServiceException {
		NffgReader nffg_r = nffgVerifier.getNffg(name);
		if (nffg_r == null)
			throw new UnknownNameException();
		loadnffgreader(nffg_r);
	}

	private void loadnffgreader(NffgReader nffg_r) throws ServiceException {
		// nffgs
		Nffg nffgItem = objFact.createNffg();
		nffgItem.setNffgName(nffg_r.getName());
		nffgItem.setUpTime(convertDate(nffg_r.getUpdateTime()));

		Set<NodeReader> node_r_list = nffg_r.getNodes();
		NodesType tmpNodes_list = objFact.createNodesType();
		LinksType tmpLinks_list = objFact.createLinksType();

		// nodes
		for (NodeReader node_r : node_r_list) {
			NodeType nodeItem = objFact.createNodeType();
			nodeItem = createNode(node_r);
			tmpNodes_list.getNode().add(nodeItem);
			// links
			Set<LinkReader> link_r_list = node_r.getLinks();
			for (LinkReader link_r : link_r_list) {
				LinkType linkItem = objFact.createLinkType();
				linkItem = createLinks(link_r);
				tmpLinks_list.getLink().add(linkItem);
			}
		}
		nffgItem.setNodes(tmpNodes_list);
		nffgItem.setLinks(tmpLinks_list);

		// post nffgItem
		Response delRes = target.path("nffgservice").request(MediaType.APPLICATION_XML)
				.post(Entity.entity(nffgItem, MediaType.APPLICATION_XML));
		if (delRes.getStatus() != 200) {
			System.out.println("Error in posting nffgItem...");
			throw new ServiceException();
		}
	}

	private NodeType createNode(NodeReader node_r) {
		NodeType nodeTmp = objFact.createNodeType();
		nodeTmp.setNodeName(node_r.getName());

		// providerCat
		ProviderCatType providerCat = objFact.createProviderCatType();
		it.polito.dp2.NFFG.sol3.service.jaxb.FuncType funcTypeStatus;
		switch (node_r.getFuncType()) {
		case FW:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.FW;
			break;
		case DPI:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.DPI;
			break;
		case NAT:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.NAT;
			break;
		case SPAM:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.SPAM;
			break;
		case CACHE:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.CACHE;
			break;
		case VPN:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.VPN;
			break;
		case WEB_SERVER:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.WEB_SERVER;
			break;
		case WEB_CLIENT:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.WEB_CLIENT;
			break;
		case MAIL_SERVER:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.MAIL_SERVER;
			break;
		case MAIL_CLIENT:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.MAIL_CLIENT;
			break;

		default:
			funcTypeStatus = it.polito.dp2.NFFG.sol3.service.jaxb.FuncType.FW;
		}
		providerCat.setFuncType((FuncType.fromValue(funcTypeStatus.value())));
		nodeTmp.setProviderCat(providerCat);

		return nodeTmp;
	}

	private LinkType createLinks(LinkReader link_r) {
		LinkType linkTmp = objFact.createLinkType();
		linkTmp.setLinkName(link_r.getName());
		linkTmp.setSrcNode(link_r.getSourceNode().getName());
		linkTmp.setDestNode(link_r.getDestinationNode().getName());
		return linkTmp;
	}
	// ---------------------------------------------------------//

	@Override
	public void loadAll() throws AlreadyLoadedException, ServiceException {
		for (NffgReader nffg_r : nffgVerifier.getNffgs()) {
			loadnffgreader(nffg_r);
		}
		for (PolicyReader policy_r : nffgVerifier.getPolicies()) {
			loadPolicyReader(policy_r);
		}

	}

	// policy
	private void loadPolicyReader(PolicyReader policy_r) throws ServiceException {

		Policy policyItem = objFact.createPolicy();
		if (policy_r instanceof TraversalPolicyReader) {
			TraversalPolicyType policyTmp = objFact.createTraversalPolicyType();
			policyTmp = (TraversalPolicyType) createPolicy(policy_r);
			policyItem.setTraversalPolicy(policyTmp);

		} else {
			ReachabilityPolicyType policyTmp = objFact.createReachabilityPolicyType();
			policyTmp = createPolicy(policy_r);
			policyItem.setReachabilityPolicy(policyTmp);

		}
		// post policy
		Response delRes = target.path("policyservice").request(MediaType.APPLICATION_XML)
				.post(Entity.entity(policyItem, MediaType.APPLICATION_XML));

		if (delRes.getStatus() != 200) {
			System.out.println("Error in posting the policy...");
			throw new ServiceException();
		}
	}

	private ReachabilityPolicyType createPolicy(PolicyReader policy_r) {
		ReachabilityPolicyType policyTmp;
		// policy is instance of TraversalPolicyReader
		if (policy_r instanceof TraversalPolicyReader)
			policyTmp = objFact.createTraversalPolicyType();
		else
			policyTmp = objFact.createReachabilityPolicyType();

		policyTmp.setPolicyName(policy_r.getName());
		policyTmp.setIsPositive(policy_r.isPositive());
		String source = ((ReachabilityPolicyReader) policy_r).getSourceNode().getName();
		policyTmp.setSrcNode(source);
		String destination = ((ReachabilityPolicyReader) policy_r).getDestinationNode().getName();
		policyTmp.setDestNode(destination);
		policyTmp.setNffgName((policy_r.getNffg().getName()));

		// policy is instance of TraversalPolicyReader
		if (policy_r instanceof TraversalPolicyReader) {
			Set<FunctionalType> read_travers_list = ((TraversalPolicyReader) policy_r).getTraversedFuctionalTypes();
			for (FunctionalType travers_r : read_travers_list) {
				((TraversalPolicyType) policyTmp).getTraversComponent().add(FuncType.fromValue(travers_r.name()));
			}
		}

		ResultType result_r = objFact.createResultType();
		if (policy_r.getResult() != null) {
			result_r.setVerificationMessage(policy_r.getResult().getVerificationResultMsg());
			result_r.setVerificationTime(convertDate(policy_r.getResult().getVerificationTime()));
			result_r.setVerificationResult(policy_r.getResult().getVerificationResult());
			policyTmp.setVerificationResult(result_r);
		}
		return policyTmp;
	}
	// ---------------------------------------------------------//

	@Override
	public void loadReachabilityPolicy(String name, String nffgName, boolean isPositive, String srcNodeName,
			String dstNodeName) throws UnknownNameException, ServiceException {
		// check srcNode & destNode
		NffgReader nffgChk = nffgVerifier.getNffg(nffgName);
		if (nffgChk == null)
			throw new UnknownNameException();
		NodeReader srcNodeChk = nffgChk.getNode(srcNodeName);
		if (srcNodeChk == null)
			throw new UnknownNameException();
		NodeReader destNodeChk = nffgChk.getNode(srcNodeName);
		if (destNodeChk == null)
			throw new UnknownNameException();

		ReachabilityPolicyType reachability_policy_r = objFact.createReachabilityPolicyType();
		reachability_policy_r.setPolicyName(name);
		reachability_policy_r.setSrcNode(srcNodeName);
		reachability_policy_r.setDestNode(dstNodeName);
		reachability_policy_r.setNffgName(nffgName);
		reachability_policy_r.setIsPositive(isPositive);

		Policy policy_r = objFact.createPolicy();
		policy_r.setReachabilityPolicy(reachability_policy_r);
		// post ReachabilityPolicy
		Response delRes = target.path("policyservice").request(MediaType.APPLICATION_XML)
				.post(Entity.entity(policy_r, MediaType.APPLICATION_XML));
		if (delRes.getStatus() != 200) {
			System.out.println("Error in posting ReachabilityPolicy infos...");
			throw new ServiceException();
		}
	}
	// ---------------------------------------------------------//

	@Override
	public void unloadReachabilityPolicy(String name) throws UnknownNameException, ServiceException {
		// delete a Policy
		Response delRes = target.path("policyservice/" + name).request(MediaType.APPLICATION_XML).delete();
		if (delRes.getStatus() == 404) {
			System.out.println("Not found the policy to be delete ...");
			throw new UnknownNameException();
		}
		if (delRes.getStatus() != 204)
			throw new ServiceException();
	}
	// ---------------------------------------------------------//

	@Override
	public boolean testReachabilityPolicy(String name) throws UnknownNameException, ServiceException {
		// update verificationResult of policy
		Response delRes = target.path("policyservice/" + name + "/verification").request(MediaType.APPLICATION_XML)
				.post(Entity.entity(null, MediaType.APPLICATION_XML));
		if (delRes.getStatus() == 404) {
			System.out.println("Not found policy to be updated ...");
			throw new UnknownNameException();
		}
		if (delRes.getStatus() != 200)
			throw new ServiceException();

		Response getpolicy = target.path("policyservice/" + name).request(MediaType.APPLICATION_XML).get();
		if (getpolicy.getStatus() == 404) {
			System.out.println("Not found policy to update verficationResult...");
			throw new UnknownNameException();
		}
		if (getpolicy.getStatus() != 200)
			throw new ServiceException();

		Policy policyTmp = getpolicy.readEntity(Policy.class);
		Boolean policyItem;
		if (policyTmp.getReachabilityPolicy() != null) {
			policyItem = policyTmp.getReachabilityPolicy().getVerificationResult().isVerificationResult();
		} else {
			policyItem = policyTmp.getTraversalPolicy().getVerificationResult().isVerificationResult();
		}
		return policyItem;
	}
	// ---------------------------------------------------------//

	private static XMLGregorianCalendar convertDate(Calendar calendar) {
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(calendar.getTime());
			gregorianCalendar.setTimeZone(calendar.getTimeZone());
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		} catch (DatatypeConfigurationException e) {
			System.err.println("convertDate - DatatypeConfigurationException");
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

}