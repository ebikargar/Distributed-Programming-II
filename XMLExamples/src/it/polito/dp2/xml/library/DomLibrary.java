package it.polito.dp2.xml.library;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class DomLibrary {
    private Collection<Book> c = new Vector<Book>();

    public DomLibrary(String fname) 
    {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(fname));
            NodeList nl = doc.getElementsByTagName("book");
            Element e;

            // fill collection with book objects
            for (int i=0; i < nl.getLength(); i++) {
                e = (Element)nl.item(i);
                c.add(new Book(e));
            }
        }
        catch (IOException ioe) {
            System.out.println("Input/Output error: " + ioe.getMessage());
            System.exit(1);
        }
        catch (SAXParseException spe) {
            System.out.println("Parsing exception for entity " + spe.getPublicId() +
                " at line: " + spe.getLineNumber() +
                " column: " + spe.getColumnNumber());
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
		Iterator<Book> i = c.iterator();
		Book book;
		
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
        DomLibrary dl = new DomLibrary(args[0]);
        dl.printBooks();
    }
}

