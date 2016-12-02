package it.polito.dp2.xml;
/* DomWalker.java
 * Tool to walk through and visualize an xml document
 */
import java.io.*;
import javax.xml.parsers.*;
import org.xml.sax.*;  
import org.w3c.dom.*;

public class DomWalker
{
    private Document document;	// the document to visualize
    private PrintStream out;	// the stream to which the document output is visualized
    
    public DomWalker(String fname, PrintStream out)
    {
        this.out = out;
    	//first call the constructor
        DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(fname));   
        } 
        catch (SAXParseException spe) {
           // Parsing error
           out.println("\n** Parsing error"
              + ", line " + spe.getLineNumber()
              + ", uri " + spe.getSystemId());
           out.println("   " + spe.getMessage() );
           // spe.printStackTrace();
           System.exit(1);
        } 
        catch (SAXException sxe) {
        	out.println("\n** Internal error.");
        	out.println("   " + sxe.getMessage() );
        	// Exception  x = sxe;
        	// if (sxe.getException() != null)
        	//     x = sxe.getException();
        	// x.printStackTrace();
        	System.exit(1);
        } 
        catch (ParserConfigurationException pce) {
        	out.println("\n** Internal error.");
        	out.println("Parser with specified options can't be built");
        	//pce.printStackTrace();
        	System.exit(1);
        } 
        catch (IOException ioe) {
        	// I/O error
        	out.println("Unable to read file");
        	// ioe.printStackTrace();
        	System.exit(1);
        }
    }
    
    // Recursive visit of the Document subtree rooted at node
    // level indicates the level of node in the Document tree
    private void followNode(Node node, int level) 
    {
        processNode(node, level); //use this function to print node features
        
        if (node.hasChildNodes()) {
            NodeList children = node.getChildNodes();
            int len = children.getLength();

            for (int i = 0; i < len; i++) {
                followNode(children.item(i), level + 1);
            } 
        }
    }

    // function that processes node (at given level)
    private void processNode(Node node, int level) 
    {
        String name = node.getNodeName();
        String type = getTypeName(node.getNodeType()); //convert codes of nodeType to human readable in getTypeName
        String value = node.getNodeValue();
        
        StringBuffer buf = new StringBuffer();
        for (int i=0; i < level; i++)
            buf.append("  ");
        
        if (level > 0) {
            buf.append("+ ");
        }
        
        buf.append(type).append(": ").append(name);
        
        if (value != null) {
            buf.append(" -> ").append(value);
        }
        
        out.println(buf);
     }        
 
    // returns the string describing the type code
    public static String getTypeName(int type) 
    {
        switch (type) {
            case Node.ELEMENT_NODE: 
                return "ELEMENT";
            case Node.ATTRIBUTE_NODE: 
                return "ATTRIBUTE";
            case Node.TEXT_NODE: 
                return "TEXT";
            case Node.CDATA_SECTION_NODE: 
                return "CDATA SECTION";
            case Node.ENTITY_REFERENCE_NODE: 
                return "ENTITY REFERENCE";
            case Node.ENTITY_NODE: 
                return "ENTITY";
            case Node.PROCESSING_INSTRUCTION_NODE: 
                return "PROCESSING INSTRUCTION";
            case Node.COMMENT_NODE : 
                return "COMMENT";
            case Node.DOCUMENT_NODE: 
                return "DOCUMENT";
            case Node.DOCUMENT_TYPE_NODE: 
                return "DOCUMENT TYPE";
            case Node.DOCUMENT_FRAGMENT_NODE: 
                return "DOCUMENT FRAGMENT";
            case Node.NOTATION_NODE: 
                return "NOTATION";
            default: 
                return "UNKNOWN"; 
        }
    }
  
    // Starts a recursive visit of the whole Document tree
    public void walk ()
    {
        followNode(document, 0); //walk method call recursive function followNode on document
    }
    
    public static void main(String argv[])
    {
        if (argv.length != 1) {
            System.err.println ("Usage: java DomWalker filename");
            System.exit(1);
        }

        DomWalker dw = new DomWalker(argv[0], System.out); //call DomWalker method by passing fname and output
        dw.walk(); //walk method start exploration of dom tree
    }
}
