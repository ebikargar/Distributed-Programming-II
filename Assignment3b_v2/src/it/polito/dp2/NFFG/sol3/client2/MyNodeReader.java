package it.polito.dp2.NFFG.sol3.client2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.sol3.service.jaxb.NodeType;

public class MyNodeReader implements NodeReader {

	// ------------ Node Element --------------------------------//
	private String nodeName;
	private FunctionalType functional_r;
	private HashMap<String, LinkReader> link_list;
	// ---------------------------------------------------------//

	public MyNodeReader(NodeType node_r, HashMap<String, LinkReader> link_list_parameter) {
		if (node_r != null) {
			this.nodeName = node_r.getNodeName();
			switch (node_r.getProviderCat().getFuncType()) {
			case FW:
				functional_r = it.polito.dp2.NFFG.FunctionalType.FW;
				break;
			case DPI:
				functional_r = it.polito.dp2.NFFG.FunctionalType.DPI;
				break;
			case NAT:
				functional_r = it.polito.dp2.NFFG.FunctionalType.NAT;
				break;
			case SPAM:
				functional_r = it.polito.dp2.NFFG.FunctionalType.SPAM;
				break;
			case CACHE:
				functional_r = it.polito.dp2.NFFG.FunctionalType.CACHE;
				break;
			case VPN:
				functional_r = it.polito.dp2.NFFG.FunctionalType.VPN;
				break;
			case WEB_SERVER:
				functional_r = it.polito.dp2.NFFG.FunctionalType.WEB_SERVER;
				break;
			case WEB_CLIENT:
				functional_r = it.polito.dp2.NFFG.FunctionalType.WEB_CLIENT;
				break;
			case MAIL_SERVER:
				functional_r = it.polito.dp2.NFFG.FunctionalType.MAIL_SERVER;
				break;
			case MAIL_CLIENT:
				functional_r = it.polito.dp2.NFFG.FunctionalType.MAIL_CLIENT;
				break;

			default:
				functional_r = it.polito.dp2.NFFG.FunctionalType.FW;
			}

			this.link_list = link_list_parameter;
		}

	}

	// -------------------------------------------------------------------//

	@Override
	public String getName() {
		if (nodeName != null)
			return this.nodeName;
		else {
			System.out.println("nodeName Object is Null");
			return null;
		}
	}

	@Override
	public FunctionalType getFuncType() {
		return functional_r;
	}

	@Override
	public Set<LinkReader> getLinks() {
		HashSet<LinkReader> link_r = new HashSet<LinkReader>();
		for (LinkReader myLinkReader : link_list.values()) {
			if (myLinkReader.getSourceNode().getName().equals(this.nodeName)) {
				link_r.add(myLinkReader);
			}
		}
		return link_r;
	}

}
