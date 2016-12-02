package it.polito.dp2.xml.fibonacci;
/*
 * A class to serialize a sequence of (big)integers
 */
import java.math.BigInteger;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import org.w3c.dom.*;

public class DomIntSerializer {
	
	protected Document doc;	// document element
	protected Element root;	// document root element
	protected int index;	// progressive index assigned to integers
	
	// Initializes a new document with root element rootname
	public DomIntSerializer(String rootname) throws ParserConfigurationException 
	{
 			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// factory.setNamespaceAware (true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Create the document
			doc = builder.newDocument();

			// Create and append the root element
			root = (Element) doc.createElement(rootname);
			doc.appendChild(root);
	}
	
	// Adds a BigInteger to the document
	public void addBigInteger(BigInteger bi) {
		Element number = doc.createElement("number");
		number.setAttribute("index", Integer.toString(index++));
		Text text = doc.createTextNode(bi.toString());
		number.appendChild(text);
		root.appendChild(number);
	}
	
	// Serializes the document to a PrintStream
	public void serialize(PrintStream out) throws TransformerException {
		TransformerFactory xformFactory = TransformerFactory.newInstance ();
		Transformer idTransform;
		idTransform = xformFactory.newTransformer ();
		idTransform.setOutputProperty(OutputKeys.INDENT, "yes");
		Source input = new DOMSource (doc);
		Result output = new StreamResult (out);
		idTransform.transform (input, output);
	}

	// Test main for the serializer: generates the first 25 Fibonacci numbers
	public static void main(String[] args) {
		try {
			DomIntSerializer slz = new DomIntSerializer("fibonacci");
			Fibonacci s = new Fibonacci();
			for (int i = 1; i <= 25; i++)
				slz.addBigInteger(s.next());
			slz.serialize(System.out);
		}
		catch (ParserConfigurationException pce) {
			System.out.println ("DOM configuration error");			
		}
		catch (TransformerException te) {
			System.out.println("Serialization error");
		}
	}
}
