package it.polito.dp2.xml.parse;
/* DomParse.java
 * Non validating parser with error diagnostics
 */
import java.io.*;
import javax.xml.parsers.*; 

import org.xml.sax.*;

public class DomParse{
    public static void main(String argv[])
    {
        if (argv.length != 1) {
          System.err.println("Usage: java DomParse filename");
          System.exit(1);
        }
        try {
        	DocumentBuilderFactory factory =
        		DocumentBuilderFactory.newInstance();
        	DocumentBuilder builder = factory.newDocumentBuilder();
        	builder.parse( new File(argv[0]) );
        	System.out.println("Parsing successful!");

        } catch (SAXParseException spe) {
        	// Fatal Parsing error (file is not well formed)
        	System.out.println("** Fatal parsing error"
              + ", file " + spe.getSystemId()
              + ", line " + spe.getLineNumber());
        	System.out.println("   " + spe.getMessage() );
           
        	// Print debug info 
        	// spe.printStackTrace();
        	System.exit(1);
           
        } catch (SAXException sxe) {
        	// Fatal error generated during parsing
        	System.out.println("Fatal error encountered during parsing.");

        	// Print debug info 
        	// sxe.printStackTrace();
        	System.exit(1);

        } catch (ParserConfigurationException pce) {
        	// Error in parser configuration
        	System.out.println("Parser configuration error. Unable to proceed.");
            
        	// Print debug info
        	// pce.printStackTrace();
        	System.exit(1);

        } catch (IOException ioe) {
        	// Read error on file 
        	System.out.println("Fatal error: Unable to read file.");
        	
        	// Print debug info
        	// ioe.printStackTrace();
        	System.exit(1);
        }
        // catch (FactoryConfigurationError fce) {
        //    System.out.println("Factory configuration error: " + fce.getMessage());
        //    System.exit(1);
        // }		
    } // main
}
