package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.*;
import it.polito.dp2.NFFG.sol1.jaxb.LinkType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;

public class MyLinkReader implements LinkReader {

	// ------------ Link Element --------------------------------//
	private String linkName;
	private NodeReader srcNode, destNode;
	// ---------------------------------------------------------//

	public MyLinkReader(NffgType nffg, LinkType link_r, NodeReader srcNode_parameter, NodeReader destNode_parameter) {

		if (link_r != null) {
			this.linkName = link_r.getLinkName();
			this.srcNode = srcNode_parameter;
			this.destNode = destNode_parameter;
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
