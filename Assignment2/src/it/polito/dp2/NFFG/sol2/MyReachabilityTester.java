package it.polito.dp2.NFFG.sol2;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.lab2.NoGraphException;
import it.polito.dp2.NFFG.lab2.ReachabilityTester;
import it.polito.dp2.NFFG.lab2.ServiceException;
import it.polito.dp2.NFFG.lab2.UnknownNameException;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class MyReachabilityTester implements ReachabilityTester {

	// ------------ ReachabilityTester --------------------------------//
	private String url;
	private NffgVerifier monitor;
	String nffgName;
	ObjectFactory objFact = new ObjectFactory();
	WebTarget target;
	HashMap<String, String> hm = new HashMap<String, String>();
	// ----------------------------------------------------------------//

	public MyReachabilityTester(String url_address, NffgVerifier monitor) {

		this.url = url_address;
		this.monitor = monitor;

		Client client = ClientBuilder.newClient();

		// make the web targets
		target = client.target(UriBuilder.fromUri(url).build());
		System.out.println("Client is ready to invoke remote operations");
	}

	// ----------------------------------------------------------------//

	@Override
	public void loadNFFG(String name) throws UnknownNameException, ServiceException {

		NffgReader nffg_r = monitor.getNffg(name);
		if (nffg_r == null) {
			System.out.println("nffgName can't be retrieve correctly");
			throw new UnknownNameException();
		}
		nffgName = nffg_r.getName();

		// Delete response each time
		Response delRes = target.path("resource/nodes/").request(MediaType.APPLICATION_XML).delete();
		if (delRes.getStatus() != 200) {
			System.out.println("Error in deleting response...");
			throw new ServiceException();
		}

		// Getting the nodes
		Set<NodeReader> node_set = nffg_r.getNodes();
		for (NodeReader node_r : node_set) {
			Node node = objFact.createNode();
			Property nodeProperty = objFact.createProperty();
			nodeProperty.setName("name");
			nodeProperty.setValue(node_r.getName());
			node.getProperty().add(nodeProperty);

			try {
				Node request = target.path("resource/node/").request(MediaType.APPLICATION_XML)
						.post(Entity.entity(node, MediaType.APPLICATION_XML), Node.class);

				hm.put(node_r.getName(), request.id); // put name and id of node
														// into the HashMap
			} catch (ProcessingException pe) {
				System.out.println("Error during JAX-RS request processing");
				pe.printStackTrace();
			} catch (WebApplicationException wae) {
				System.out.println("Server returned error");
				wae.printStackTrace();
			} catch (Exception e) {
				System.out.println("Unexpected exception");
				e.printStackTrace();
			}
		}

		// Getting the links
		for (NodeReader node_r : node_set) {
			Set<LinkReader> link_set = node_r.getLinks();

			for (LinkReader link_r : link_set) {
				Relationship link = objFact.createRelationship();
				link.setDstNode(hm.get(link_r.getDestinationNode().getName()));
				link.setType("Link");

				try {
					Relationship linkReq = target
							.path("resource/node/" + hm.get(link_r.getSourceNode().getName()) + "/relationship")
							.request(MediaType.APPLICATION_XML)
							.post(Entity.entity(link, MediaType.APPLICATION_XML), Relationship.class);
				} catch (ProcessingException pe) {
					System.out.println("Error during JAX-RS request processing");
					pe.printStackTrace();
				} catch (WebApplicationException wae) {
					System.out.println("Server returned error");
					wae.printStackTrace();
				} catch (Exception e) {
					System.out.println("Unexpected exception");
					e.printStackTrace();
				}

			}
		}
	}

	// Test reachability of src & dest Node
	@Override
	public boolean testReachability(String srcName, String destName)
			throws UnknownNameException, ServiceException, NoGraphException {

		if (nffgName == null) {
			System.out.println("nffgName can't be  retrieve correctly");
			throw new NoGraphException();
		}

		String srcId = hm.get(srcName);
		String destId = hm.get(destName);
		if (srcId == null || destId == null) {
			System.out.println("Source || Destination Node can't be retrieve from HashMap correctly");
			throw new UnknownNameException();
		}

		try {
			Paths pathResp = target.path("resource/node/" + srcId + "/paths").queryParam("dst", destId).request()
					.accept(MediaType.APPLICATION_XML).get(Paths.class);
			if (pathResp.getPath().size() > 0) {
				System.out.println("Path response Successfully retrieved");
				return true;
			} else {
				System.out.println("Path response can't be retrieve correctly");
				return false;
			}
		} catch (ProcessingException pe) {
			System.out.println("Error during JAX-RS request processing");
			pe.printStackTrace();
		} catch (WebApplicationException wae) {
			System.out.println("Server returned error");
			wae.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unexpected exception");
			e.printStackTrace();
		}
		return false;
	}

	// Return name of the graph
	@Override
	public String getCurrentGraphName() {
		if (nffgName != null)
			return nffgName;
		else {
			System.out.println("Can't fill getCurrentGraphNamerg object");
			return null;
		}
	}

}
