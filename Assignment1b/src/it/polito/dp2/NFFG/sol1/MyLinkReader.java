package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.*;
import it.polito.dp2.NFFG.sol1.jaxb.LinkType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.NodeType;
import it.polito.dp2.NFFG.sol1.jaxb.NodesType;

public class MyLinkReader implements LinkReader {

	// ------------ Link Element --------------------------------//
	private String linkName;
	private NodeReader srcNode, destNode;
	// ---------------------------------------------------------//

	public MyLinkReader(NffgType nffg, LinkType link_r) {

		if (link_r != null) {
			this.linkName = link_r.getLinkName();

			NodesType nodes = new NodesType();
			nodes = nffg.getNodes();
			for (NodeType node_r : nodes.getNode()) {
				String nodeName;
				nodeName = node_r.getNodeName();

				if (nodeName.equals(link_r.getSrcNode()))
					this.srcNode = new MyNodeReader(node_r, nffg);
				else if (nodeName.equals(link_r.getDestNode()))
					this.destNode = new MyNodeReader(node_r, nffg);
				else
					System.out.println("Node is not recognized");
			}
			System.out.println("My LinkReader fulfilled Correctly");
		}

	}

	// -------------------------------------------------------------------//

	@Override
	public String getName() {
		if (linkName != null)
			return this.linkName;
		else {
			System.out.println("linkName Object is Null");
			return null;
		}
	}

	@Override
	public NodeReader getDestinationNode() {
		if (destNode != null)
			return this.destNode;
		else {
			System.out.println("destNode Object is Null");
			return null;
		}
	}

	@Override
	public NodeReader getSourceNode() {
		if (srcNode != null)
			return this.srcNode;
		else {
			System.out.println("srcNode Object is Null");
			return null;
		}
	}

}
