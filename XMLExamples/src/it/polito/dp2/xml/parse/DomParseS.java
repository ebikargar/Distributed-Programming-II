package it.polito.dp2.xml.parse;
/* DomParseS.java
 * Simple non validating parser
 */
import java.io.*;
import javax.xml.parsers.*;

public class DomParseS{
    public static void main(String argv[])
    {
        if (argv.length != 1) {
          System.err.println("Usage: java DomParseS filename");
          System.exit(1);
        }

        DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();

        try {
           DocumentBuilder builder = factory.newDocumentBuilder();
           builder.parse( new File(argv[0]) );
           System.out.println("Parsing successful!");

        } catch (Exception e) {
        	System.out.println("Fatal Error: parsing not completed.");
        	// e.printStackTrace();
        	System.exit(1);
        }
    } // main
}
