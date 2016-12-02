/*
 * Library class that works with JAXB programming
 * This class uses the JAXB annotated classes generated from the schema books.xsd 
 * This class uses validation but can work without it either
 * 
 */
package it.polito.dp2.xml.library;
import it.polito.dp2.xml.library.annotated.Book;
import it.polito.dp2.xml.library.annotated.Books;

import java.io.*;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import org.xml.sax.*;

public class JAXBLibraryA {
    private List<Book> c;

    public JAXBLibraryA(String fname) throws SAXException, JAXBException 
    {
        try {
        	// initialize JAXBContext and create unmarshaller
            JAXBContext jc = JAXBContext.newInstance( "it.polito.dp2.xml.library.annotated" );
            Unmarshaller u = jc.createUnmarshaller();
            
            // set validation wrt schema using default validation handler (rises exception with non-valid files)
            // comment out this part to disable validation
            SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("gen-schema/books.xsd"));
            u.setSchema(schema);
                       
            // unmarshal file named fname
            Books books = (Books) u.unmarshal( new File( fname ) );
            
            // initialize specific entities
            c = books.getBook();
   	
        } catch (SAXException se) {
    		System.out.println("Unable to validate file or schema");
    		throw se;
        }
    }

	// Print out library contents
    public void printBooks() 
    {		
		for (Book b :c) {
		    b.print();
		}
    }

    public static void main(String args[]) 
    {
        if (args.length != 1) {
            System.err.println("Usage: java JAXBLibraryA xmlfilename");
            System.exit(1);
        }
		try {
			JAXBLibraryA dl = new JAXBLibraryA(args[0]);
	        dl.printBooks();
		} catch (Exception e) {
			System.out.println("Aborting due to error in file unmarshalling phase");
			// e.printStackTrace();
			System.exit(1);
		}
    }
}

