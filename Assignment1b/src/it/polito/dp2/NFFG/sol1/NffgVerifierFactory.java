package it.polito.dp2.NFFG.sol1;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.xml.sax.SAXException;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.sol1.jaxb.NffgVerifierType;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

public class NffgVerifierFactory extends it.polito.dp2.NFFG.NffgVerifierFactory {

	// ------------ NffgVerifier factory --------------------------------//
	private NffgVerifier myNffgVerifier;
	public static final String SCHEMA_FILE = "xsd" + File.separatorChar + "nffgInfo.xsd";
	public static final String SCHEMA_LOCATION = "http://www.example.org/nffgInfo nffgInfo.xsd";

	// ---------------------------------------------------------//
	private NffgVerifierType unmarshal(File inputFile) throws JAXBException, SAXException, NffgVerifierException {
		Unmarshaller u=null;
		String fileName = System.getProperty("it.polito.dp2.NFFG.sol1.NffgInfo.file");

		try {
			JAXBContext jc = JAXBContext.newInstance("it.polito.dp2.NFFG.sol1.jaxb");
			u = jc.createUnmarshaller();

			SchemaFactory schemaf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
			u.setSchema(schemaf.newSchema(new File(SCHEMA_FILE)));

		} catch (JAXBException e) {
			System.out.println("JAXBException: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (SAXException e) {
			System.out.println("SAXException: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

			JAXBElement<NffgVerifierType> nffgType;
			nffgType = (JAXBElement<NffgVerifierType>) u.unmarshal(inputFile);
			NffgVerifierType nffgVerifierType = (NffgVerifierType) nffgType.getValue();
			if (nffgVerifierType != null)
				return nffgVerifierType;
			else
				return null;
	}
	// ---------------------------------------------------------//

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
