package it.polito.dp2.NFFG.sol3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import it.polito.dp2.NFFG.sol3.service.jaxb.Nffg;
import it.polito.dp2.NFFG.sol3.service.neo4j.Node;
import it.polito.dp2.NFFG.sol3.service.neo4j.ObjectFactory;
import it.polito.dp2.NFFG.sol3.service.jaxb.LinkType;

import it.polito.dp2.NFFG.sol3.service.jaxb.NodeType;
import it.polito.dp2.NFFG.sol3.service.jaxb.Policy;
import it.polito.dp2.NFFG.sol3.service.jaxb.ReachabilityPolicyType;
import it.polito.dp2.NFFG.sol3.service.jaxb.TraversalPolicyType;
import it.polito.dp2.NFFG.sol3.service.neo4j.Labels;
import it.polito.dp2.NFFG.sol3.service.neo4j.Property;
import it.polito.dp2.NFFG.sol3.service.neo4j.Relationship;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NffgServiceImp {

	// ------------ NffgServiceImp------------------------------//
	Map<String, Nffg> nffgMap = NffgDB.getMap();
	Map<String, Policy> policyMap = PolicyDB.getMap();
	Map<String, String> nodeMap = NffgDB.getNodeIDMap();
	// ------------------------------------------------------------------//

	String getNodeId(String nodeName) {
		return nodeMap.get(nodeName);
	}

	public void postSingleNffg(Nffg nffg) {

		// check if Nffg already existed or No
		if (NffgDB.getMap().containsKey(nffg.getNffgName())) {
			System.out.println("This Nffg Already Existed");
			throw new ForbiddenException("Nffg already Existed");
		}

		try {
			WebTarget target = createTarget();
			ObjectFactory objFact = new ObjectFactory();

			// nffgs
			Node nffgNode = new ObjectFactory().createNode();
			Property nffgNodeProperty = new ObjectFactory().createProperty();
			nffgNodeProperty.setName("name");
			nffgNodeProperty.setValue(nffg.getNffgName());
			nffgNode.getProperty().add(nffgNodeProperty);

			// Post NffgNode
			Node nffNodeRequest = target.path("resource/node/").request(MediaType.APPLICATION_XML)
					.post(Entity.entity(nffgNode, MediaType.APPLICATION_XML), Node.class);

			// put into the nffgMap
			synchronized(NffgDB.getNodeIDMap()){
			nodeMap.put(nffg.getNffgName(), nffNodeRequest.getId());
			}

			// Post Labels
			Labels nffgLabel = new ObjectFactory().createLabels();
			nffgLabel.getValue().add("NFFG");

			Response labelRequest = target.path("resource/nodes/" + nffNodeRequest.getId() + "/label")
					.request(MediaType.APPLICATION_XML).post(Entity.entity(nffgLabel, MediaType.APPLICATION_XML));
			// nodes
			List<NodeType> node_set = nffg.getNodes().getNode();
			for (NodeType node_r : node_set) {
				Node node = objFact.createNode();
				Property nodeProperty = objFact.createProperty();
				nodeProperty.setName("name");
				nodeProperty.setValue(node_r.getNodeName());
				node.getProperty().add(nodeProperty);

				// Post Node and & put it into the nodeMap
				Node nodeRequest = target.path("resource/node/").request(MediaType.APPLICATION_XML)
						.post(Entity.entity(node, MediaType.APPLICATION_XML), Node.class);
				
				synchronized(NffgDB.getNodeIDMap()){
				nodeMap.put(node_r.getNodeName(), nodeRequest.getId());
				}
				// Set the Relationship
				Relationship nodeRel = objFact.createRelationship();
				nodeRel.setDstNode(nodeRequest.getId());
				nodeRel.setType("belongs");
			}

			// links
			List<LinkType> link_set = nffg.getLinks().getLink();
			for (LinkType link_r : link_set) {
				Relationship link = objFact.createRelationship();
				link.setDstNode(nodeMap.get(link_r.getDestNode()));
				link.setType("Link");

				// Post the link
				Relationship linkReq = target
						.path("resource/node/" + nodeMap.get(link_r.getSrcNode()) + "/relationship")
						.request(MediaType.APPLICATION_XML)
						.post(Entity.entity(link, MediaType.APPLICATION_XML), Relationship.class);
			}
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		// nffg is fulfilled-->put it into the NffgDB
		synchronized(NffgDB.getMap()){
		NffgDB.getMap().put(nffg.getNffgName(), nffg);
		}
	}

	// GET list of Nffg objects
	public List<Nffg> getListOfNffgs() {
		return new ArrayList<Nffg>(nffgMap.values());
	}

	// GET a single Nffg object
	public Nffg getSingleNffg(String nffgId) {
		return nffgMap.get(nffgId);
	}

	// GET list of Policy objects
	public List<Policy> getListOfPolicies() {
		return new ArrayList<Policy>(policyMap.values());
	}

	// GET single Policy object
	public Policy getSinglePolicy(String policyId) {
		return policyMap.get(policyId);
	}

	// Create a new Policy object
	public void postSinglePolicy(Policy policy) {
		Nffg nffg;
		if (policy.getReachabilityPolicy() == null) {
			synchronized(NffgDB.getMap()){
			nffg = nffgMap.get(policy.getTraversalPolicy().getNffgName());
			}
		} else {
			synchronized(NffgDB.getMap()){
			nffg = nffgMap.get(policy.getReachabilityPolicy().getNffgName());
			}
		}
		if (nffg == null) {
			throw new ForbiddenException();
		}

		ReachabilityPolicyType rp;
		TraversalPolicyType tp;

		if (policy.getReachabilityPolicy() != null) {
			rp = policy.getReachabilityPolicy();
			synchronized(NffgDB.getNodeIDMap()){
			String srcNode = nodeMap.get(rp.getSrcNode());
			if (srcNode == null) {
				throw new ForbiddenException();
			}
			}
			synchronized(NffgDB.getNodeIDMap()){
			String destNode = nodeMap.get(rp.getDestNode());
			if (destNode == null) {
				throw new ForbiddenException();
			}
			}
			synchronized(PolicyDB.getMap()){
				policyMap.put(rp.getPolicyName(), policy);
			}
		} else {
			tp = policy.getTraversalPolicy();
			synchronized(NffgDB.getNodeIDMap()){
			String srcNode = nodeMap.get(tp.getSrcNode());
			if (srcNode == null) {
				throw new ForbiddenException();
			}
			}
			synchronized(NffgDB.getNodeIDMap()){
			String destNode = nodeMap.get(tp.getDestNode());
		
			if (destNode == null) {
				throw new ForbiddenException();
			}
			}
			synchronized(PolicyDB.getMap()){
			policyMap.put(tp.getPolicyName(), policy);
			}
		}
	}

	// Remove a single Policy object
	public void deleteSinglePolicy(String policyId) {
		synchronized(PolicyDB.getMap()){
			policyMap.remove(policyId);
		}
	}

	// Remove list of policy object
	public void deleteListOfPolicies() {
		synchronized(PolicyDB.getMap()){
		policyMap.clear();
		}
	}

	private WebTarget createTarget() {
		WebTarget target;
		String property_value = System.getProperty("it.polito.dp2.NFFG.lab3.NEO4JURL");

		Client c = ClientBuilder.newClient();
		if (property_value == null)
			property_value = "http://localhost:8080/Neo4JXML/rest";
		target = c.target(property_value);
		return target;
	}

}