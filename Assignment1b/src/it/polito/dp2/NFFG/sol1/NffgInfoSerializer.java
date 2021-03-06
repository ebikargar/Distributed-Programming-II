package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.*;
import it.polito.dp2.NFFG.sol1.jaxb.FuncType;
import it.polito.dp2.NFFG.sol1.jaxb.LinkType;
import it.polito.dp2.NFFG.sol1.jaxb.LinksType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgVerifierType;
import it.polito.dp2.NFFG.sol1.jaxb.NodeType;
import it.polito.dp2.NFFG.sol1.jaxb.NodesType;
import it.polito.dp2.NFFG.sol1.jaxb.ObjectFactory;
import it.polito.dp2.NFFG.sol1.jaxb.PoliciesType;
import it.polito.dp2.NFFG.sol1.jaxb.ProviderCatType;
import it.polito.dp2.NFFG.sol1.jaxb.ReachabilityPolicyType;
import it.polito.dp2.NFFG.sol1.jaxb.ResultType;
import it.polito.dp2.NFFG.sol1.jaxb.TraversalPolicyType;

import java.io.File;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.SchemaFactory;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import org.xml.sax.SAXException;

public class NffgInfoSerializer {

	// ------------ NffgInfoSerializer --------------------------------//
	ObjectFactory objFact = new ObjectFactory();
	NffgVerifierType rootElement = objFact.createNffgVerifierType();
	JAXBElement<NffgVerifierType> root = objFact.createNffgVerifier(rootElement);

	private NffgVerifier monitor;
	public static final String SCHEMA_FILE = "xsd" + File.separatorChar + "nffgInfo.xsd";
	public static final String SCHEMA_LOCATION = "http://www.example.org/nffgInfo nffgInfo.xsd";

	// ---------------------------------------------------------//

	/**
	 * Default constructor
	 * 
	 * @throws NffgVerifierException
	 */
	public NffgInfoSerializer() throws NffgVerifierException {
		it.polito.dp2.NFFG.NffgVerifierFactory factory = it.polito.dp2.NFFG.NffgVerifierFactory.newInstance();
		monitor = factory.newNffgVerifier();
	}

	public NffgInfoSerializer(NffgVerifier monitor) {
		super();
		this.monitor = monitor;
	}

	// ---------------------------------------------------------//

	public void marshaller(File output_file) {
		try {
			JAXBContext jct = JAXBContext.newInstance("it.polito.dp2.NFFG.sol1.jaxb");

			Marshaller m = jct.createMarshaller();
			SchemaFactory schemaf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);

			m.setSchema(schemaf.newSchema(new File(SCHEMA_FILE)));
			m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, SCHEMA_LOCATION);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			System.out.println("filename = " + output_file.toString());

			m.marshal(root, output_file);

		} catch (JAXBException e) {
			System.out.println("JAXBException: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (SAXException e) {
			System.out.println("SAXException: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ---------------------------------------------------------//

	public static void main(String[] arg) {

		try {
			NffgInfoSerializer NffgInfoSerializerObj = new NffgInfoSerializer();
			NffgInfoSerializerObj.createNffg();

			File output_file = new File(arg[0]);
			NffgInfoSerializerObj.marshaller(output_file);

		} catch (NffgVerifierException e) {
			System.out.println("NffgVerifierException: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

	}

	// -------------------------NFFG Element--------------------------------//
	private void createNffg() {

		Set<NffgReader> read_nffg_list = monitor.getNffgs();

		for (NffgReader nffg_r : read_nffg_list) {
			NffgType nffgItem = objFact.createNffgType();
			nffgItem.setNffgName(nffg_r.getName());
			nffgItem.setUpTime(convertDate(nffg_r.getUpdateTime()));

			// policy Element
			Set<PolicyReader> read_policy_list = monitor.getPolicies(nffg_r.getName());
			PoliciesType tmpPolicies_list = objFact.createPoliciesType();
			for (PolicyReader policy_r : read_policy_list) {
				if (policy_r instanceof TraversalPolicyReader) {
					TraversalPolicyType policyItem = objFact.createTraversalPolicyType();
					policyItem = (TraversalPolicyType) createPolicy(policy_r);
					tmpPolicies_list.getTraversalPolicy().add(policyItem);

				} else {
					ReachabilityPolicyType policyItem = objFact.createReachabilityPolicyType();
					policyItem = createPolicy(policy_r);
					tmpPolicies_list.getReachabilityPolicy().add(policyItem);

				}
			}
			nffgItem.setPolicies(tmpPolicies_list);

			//// ************* Generate the nodes and add to nffgItem
			Set<NodeReader> read_node_list = nffg_r.getNodes();
			NodesType tmpNodes_list = objFact.createNodesType();
			LinksType tmpLinks_list = objFact.createLinksType();

			for (NodeReader node_r : read_node_list) {
				NodeType nodeItem = objFact.createNodeType();
				nodeItem = createNode(node_r);
				tmpNodes_list.getNode().add(nodeItem);

				Set<LinkReader> read_link_list = node_r.getLinks();
				for (LinkReader link_r : read_link_list) {
					LinkType linkItem = objFact.createLinkType();
					linkItem = createLinks(link_r);
					tmpLinks_list.getLink().add(linkItem);
				}
			}

			nffgItem.setNodes(tmpNodes_list);
			nffgItem.setLinks(tmpLinks_list);
			nffgItem.setPolicies(tmpPolicies_list);

			rootElement.getNffg().add(nffgItem);
		}

	}

	// -------------------------Node Element--------------------------------//
	private NodeType createNode(NodeReader node_r) {

		NodeType nodeTmp = objFact.createNodeType();
		nodeTmp.setNodeName(node_r.getName());

		// Create providerCat element
		ProviderCatType providerCat = objFact.createProviderCatType();
		it.polito.dp2.NFFG.sol1.jaxb.FuncType funcTypeStatus;
		switch (node_r.getFuncType()) {
		case FW:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.FW;
			break;
		case DPI:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.DPI;
			break;
		case NAT:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.NAT;
			break;
		case SPAM:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.SPAM;
			break;
		case CACHE:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.CACHE;
			break;
		case VPN:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.VPN;
			break;
		case WEB_SERVER:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.WEB_SERVER;
			break;
		case WEB_CLIENT:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.WEB_CLIENT;
			break;
		case MAIL_SERVER:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.MAIL_SERVER;
			break;
		case MAIL_CLIENT:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.MAIL_CLIENT;
			break;

		default:
			funcTypeStatus = it.polito.dp2.NFFG.sol1.jaxb.FuncType.FW;
		}

		providerCat.setFuncType((FuncType.fromValue(funcTypeStatus.value())));
		nodeTmp.setProviderCat(providerCat);

		return nodeTmp;
	}

	// -------------------------Link Element--------------------------------//
	private LinkType createLinks(LinkReader link_r) {
		LinkType linkTmp = objFact.createLinkType();

		linkTmp.setLinkName(link_r.getName());
		linkTmp.setSrcNode(link_r.getSourceNode().getName());
		linkTmp.setDestNode(link_r.getDestinationNode().getName());
		return linkTmp;
	}

	// -------------------------Policy Element--------------------------------//
	private ReachabilityPolicyType createPolicy(PolicyReader policy_r) {
		ReachabilityPolicyType policyTmp;
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

	// -------------------------Convert Date--------------------------------//
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
}// end of Serializer