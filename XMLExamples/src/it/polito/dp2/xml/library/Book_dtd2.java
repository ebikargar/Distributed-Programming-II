package it.polito.dp2.xml.library;
/* A Java class that represents a book
 * Version relying on a dtd and extending the basic Book class
 * 
 */
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Book_dtd2 extends Book {

	// Default constructor
	public Book_dtd2() {
	}

	//	 Builds a Book object with the specified authors, title and ISBN strings
    public Book_dtd2(String[] authors, String title, String ISBN) {
		super(authors, title, ISBN);
	}

    // This constructor assumes element has been validated wrt book.dtd
	public Book_dtd2(Element element) {
		if (element == null)
    		return;
    	else {
    	    setISBN(element.getAttribute("ISBN"));
    	    
    	    NodeList authorNodes = element.getElementsByTagName("author");
    	    int authorNumber = authorNodes.getLength();
    	    String[] authorsArray = new String[authorNumber];
    	    for (int i=0; i < authorNumber; i++) {
    			authorsArray[i] = authorNodes.item(i).getFirstChild().getNodeValue();
    	    }
    	    setAuthors(authorsArray);
    	    
			setTitle(element.getElementsByTagName("title").item(0).getFirstChild().getNodeValue());
    	}
	}
}
