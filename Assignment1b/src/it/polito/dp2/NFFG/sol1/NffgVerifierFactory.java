package it.polito.dp2.NFFG.sol1;

import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

import org.xml.sax.SAXException;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;

public class NffgVerifierFactory extends it.polito.dp2.NFFG.NffgVerifierFactory {

	private NffgVerifier myNffgVerifier;

	@Override
	public NffgVerifier newNffgVerifier() throws NffgVerifierException {

		try {
			NffgVerifier myNffgVerifier = new MyNffgVerifier();
			System.out.println("myNffgVerifier created sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myNffgVerifier;
	}
}
