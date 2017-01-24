package it.polito.dp2.NFFG.sol3.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import it.polito.dp2.NFFG.sol3.service.jaxb.Policy;

public class PolicyDB {

	// this is a database class containing a static Map of Policy objects
	private static Map<String, Policy> policymap = new ConcurrentHashMap<String, Policy>();

	public static Map<String, Policy> getMap() {
		return policymap;
	}

	public static void setMap(Map<String, Policy> map) {
		PolicyDB.policymap = map;
	}

}
