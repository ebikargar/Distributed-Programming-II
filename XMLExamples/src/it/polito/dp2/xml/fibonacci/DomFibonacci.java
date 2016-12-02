package it.polito.dp2.xml.fibonacci;
/* 
 * An example showing how to create an XML file with DOM:
 * a file is created containing the first 25 Fibonacci numbers
 */
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class DomFibonacci
{
	public static void main (String[]args)
	{
		try {
      		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
			// factory.setNamespaceAware (true);
			DocumentBuilder builder = factory.newDocumentBuilder ();

			// Create the document
			Document doc = builder.newDocument ();

			// Create and append the root element
			Element root = (Element) doc.createElement ("fibonacci");
			doc.appendChild (root);

			// Create a Fibonacci generator
			Fibonacci s = new Fibonacci();
			
			// Create and append the number elements
			for (int i = 1; i <= 25; i++)
			{
				Element number = doc.createElement("number");
				number.setAttribute ("index", Integer.toString(i));
				Text text = doc.createTextNode(s.next().toString());
				number.appendChild(text); //append text to number
				root.appendChild(number); //append number element to root element
			}

			// Serialize the document onto System.out
			TransformerFactory xformFactory = TransformerFactory.newInstance ();
			Transformer idTransform = xformFactory.newTransformer ();
			idTransform.setOutputProperty(OutputKeys.INDENT, "yes");
			Source input = new DOMSource (doc);
			Result output = new StreamResult (System.out);
			idTransform.transform (input, output);
		}
		// catch (FactoryConfigurationError e)
		// {
		// 	System.out.println ("Could not locate a JAXP factory class");
		// }
		catch (ParserConfigurationException e)
		{
			System.out.println ("Could not locate a JAXP DocumentBuilder class");
		}
		// catch (DOMException e)
		// {
		// 	System.out.println("Error while building the DOM tree");
		//	System.out.println (e);
		// }
		catch (TransformerException e)
		{
			System.out.println("Unexpected Error during serialization");
			System.out.println (e);
		}
	}
}
