package it.polito.dp2.NFFG.sol1;

import java.util.Calendar;
import java.util.Set;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.HashSet;

import it.polito.dp2.NFFG.*;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.NodeType;
import it.polito.dp2.NFFG.sol1.jaxb.NodesType;

public class MyNffgReader implements NffgReader {

	// ------------ Nffg Element --------------------------------//
	private String nffgName;
	private Calendar upTime;
	private XMLGregorianCalendar upTimeTmp;
	private Set<NodeReader> node_r_set;
	// ---------------------------------------------------------//

	public MyNffgReader(NffgType nffg) {
		if (nffg != null) {
			this.nffgName = nffg.getNffgName();

			this.upTimeTmp = nffg.getUpTime();
			this.upTime = upTimeTmp.toGregorianCalendar();

			node_r_set = new HashSet<NodeReader>();
			NodesType nodes = nffg.getNodes();
			for (NodeType node : nodes.getNode()) {
				NodeReader node_r = new MyNodeReader(node, nffg);
				this.node_r_set.add(node_r);

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
		// to check if the read node is the one on the set of nodes has been
		// read
		for (NodeReader node_r : node_r_set) {
			if (node_r.equals(arg0)) {
				return node_r;
			}
		}
		System.out.println("NodeReader Object is Null");
		return null;
	}

	@Override
	public Set<NodeReader> getNodes() {

		if (node_r_set != null)
			return this.node_r_set;
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
