package it.polito.dp2.NFFG.sol2;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.lab2.NoGraphException;
import it.polito.dp2.NFFG.lab2.ReachabilityTester;
import it.polito.dp2.NFFG.lab2.ServiceException;
import it.polito.dp2.NFFG.lab2.UnknownNameException;

public class MyReachabilityTester implements ReachabilityTester {

	private String url;
	private NffgVerifier monitor;
	String nffgName;
	
	public MyReachabilityTester(String url_address, NffgVerifier monitor) {
		
	this.url = url_address;
	this.monitor= monitor;
	
	}

	@Override
	public void loadNFFG(String name) throws UnknownNameException, ServiceException {
		
		NffgReader nffg_r = monitor.getNffg(name);
		nffgName = nffg_r.getName();
		
		//upload on neo4j ,the nodes and the links
		//1.
		//first: append the resource
		// append then nodes 
		//do DELETE localhost:8080/Neo4JXML/rest/resource/nodes,based  ;
		
		//second,for each node,create the node element and set property
		//post on current endl point : localhost:8080/Neo4JXML/rest/resource/node
		
		
		
		//do the same 3 step of work for loading the links
		// you need store Id of the node from prveious step to be used here
		
		//3. store the name of nffg -->done
	}

	@Override
	public boolean testReachability(String srcName, String destName)
			throws UnknownNameException, ServiceException, NoGraphException {
		// call GET localhost:8080/Neo4JXML/rest/resource/node/0/paths?dst=2 (0=srcId,2=destId from previous step)
		// check the number of path from src to dest, if it's >0 we return: true  if there is no return false

		return false;
	}

	@Override
	public String getCurrentGraphName() {
		return nffgName;
	}

}
