package it.polito.dp2.xml.library;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
//we perform validation in this example
public class DomLibrary_dtd {
    private Collection<Book_dtd> c = new Vector<Book_dtd>();

    public DomLibrary_dtd(String fname) 
    {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(true); //set validation 
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setErrorHandler(new DomLibraryHandler());

            Document doc = db.parse(new File(fname));
            NodeList nl = doc.getElementsByTagName("book");
            Element e;

            // fill collection with book objects
            for (int i=0; i < nl.getLength(); i++) {
                e = (Element)nl.item(i);
                c.add(new Book_dtd(e));
            }
        }
        catch (IOException ioe) {
            System.out.println("Input/Output error: " + ioe.getMessage());
            System.exit(1);
        }
        catch (SAXParseException spe) {
            System.out.println("Parsing error in XML file " + spe.getSystemId());
            System.out.println(" at line: " + spe.getLineNumber() +
                " column: " + spe.getColumnNumber());
            System.out.println(spe.getMessage() );
            System.exit(1);
        }
        catch (SAXException se) {
            System.out.println("General SAX exception: " + se.getMessage());
            System.exit(1);
        }
        catch (ParserConfigurationException pce) {
            System.out.println("Parser configuration exception: " + pce.getMessage());
            System.exit(1);
        }
    }

    // Print out library contents
    public void printBooks() 
    {
		Iterator<Book_dtd> i = c.iterator();
		Book_dtd book;
		
		while (i.hasNext()) {
		    book = i.next();
		    book.print();
		}
    }

    public static void main(String args[]) 
    {
        if (args.length != 1) {
            System.err.println("Usage: java DomLibrary xmlfilename");
            System.exit(1);
        }
        DomLibrary_dtd dl = new DomLibrary_dtd(args[0]);
        dl.printBooks();
    }
}

class DomLibraryHandler extends org.xml.sax.helpers.DefaultHandler {
	
	  // Validation errors are treated as fatal
	  public void error (SAXParseException e) throws SAXParseException
	  {
	    throw e;
	  }

	  // Warnings are displayed (without terminating)
	  public void warning (SAXParseException e) throws SAXParseException
	  {
	    System.out.println("** Warning"
	            + ", file " + e.getSystemId()
	            + ", line " + e.getLineNumber());
	    System.out.println("   " + e.getMessage() );
	  }
}

