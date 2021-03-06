package it.polito.dp2.NFFG.sol3.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import it.polito.dp2.NFFG.sol3.service.jaxb.Nffg;

public class NffgDB {

	// ------------ NffgDB --------------------------------------------//
	// this is a database class containing a static Map of Nffg objects
	private static Map<String, Nffg> nffgMap = new ConcurrentHashMap<String, Nffg>();
	private static Map<String, String> nodeIdMap = new ConcurrentHashMap<String, String>();
	// ------------------------------------------------------------------//

	public static Map<String, Nffg> getMap() {
		return nffgMap;
	}

	public static Map<String, String> getNodeIDMap() {
		return nodeIdMap;
	}

	public static void setMap(Map<String, Nffg> nffgmap) {
		NffgDB.nffgMap = nffgmap;
	}

}
