package it.polito.dp2.NFFG.sol1;

import java.util.List;
import java.util.Set;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.sol1.jaxb.LinkType;
import it.polito.dp2.NFFG.sol1.jaxb.LinksType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.NodeType;
import it.polito.dp2.NFFG.sol1.jaxb.ProviderCatType;

public class MyNodeReader implements NodeReader {

	// ------------ Node Element --------------------------------//
	private String nodeName;
	private List<ProviderCatType> provider_r;// ??
	private Set<LinkReader> link_list;
	// ---------------------------------------------------------//

	public MyNodeReader(NodeType node_r, NffgType nffg) {
		if (node_r != null) {
			this.nodeName = node_r.getNodeName();
			//this.provider_r = node_r.getProviderCat();//??

			LinksType links = nffg.getLinks();
			for (LinkType link_r : links.getLink()) {
				LinkReader linkReader = new MyLinkReader(nffg, link_r);
				this.link_list.add(linkReader);

			}
			System.out.println("My NodeReader fulfilled Correctly");
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
		// ??????????
		return null;
	}

	@Override
	public Set<LinkReader> getLinks() {
		if (link_list != null)
			return link_list;
		else {
			System.out.println("link_list Object is Null");
			return null;
		}
	}

}
