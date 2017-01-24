package it.polito.dp2.NFFG.sol1;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.HashSet;

import it.polito.dp2.NFFG.*;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;

public class MyNffgReader implements NffgReader {

	// ------------ Nffg Element --------------------------------//
	private String nffgName;
	private Calendar upTime;
	private XMLGregorianCalendar upTimeTmp;
	private HashMap<String, NodeReader> node_r_set;
	private Set<LinkReader> link_r_set;
	// ---------------------------------------------------------//

	public MyNffgReader(NffgType nffg, HashMap<String, NodeReader> node_r_set) {
		if (nffg != null) {

			this.nffgName = nffg.getNffgName();
			this.upTimeTmp = nffg.getUpTime();
			this.upTime = upTimeTmp.toGregorianCalendar();
			this.node_r_set = node_r_set;

			link_r_set = new HashSet<LinkReader>();
			for (NodeReader node_r : node_r_set.values()) {
				for (LinkReader link_r : node_r.getLinks()) {
					this.link_r_set.add(link_r);
				}
			}

			System.out.println("MyNffgReader fulfilled Correctly");
		}
	}

	// ----------------------------------------------------------------------//

	@Override
	public String getName() {
		if (nffgName != null)
			return this.nffgName;
		else {
			System.out.println("nffgName Object is Null");
			return null;
		}
	}

	@Override
	public NodeReader getNode(String arg0) {
		for (NodeReader node_r : node_r_set.values()) {
			if (node_r.getName().equals(arg0)) {
				return node_r;
			}
		}
		System.out.println("NodeReader Object is Null");
		return null;
	}

	@Override
	public Set<NodeReader> getNodes() {
		if (node_r_set != null)
			return new HashSet<NodeReader>(node_r_set.values());
		else {
			System.out.println("Set<NodeReader> Object is Null");
			return null;
		}
	}

	@Override
	public Calendar getUpdateTime() {
		if (upTime != null)
			return this.upTime;
		else {
			System.out.println("upTime Object is Null");
			return null;
		}

	}

}
