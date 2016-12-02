package it.polito.dp2.xml.library;
/* A Java class that represents a book
 * Version relying on a dtd
 * 
 */
import org.w3c.dom.*;

public class Book_dtd 
{
    private String authors[];
    private String title;
    private String ISBN;
    
    // Default constructor
    public Book_dtd()
    {
    }

    // Builds a Book object with the specified authors, title and ISBN strings
    public Book_dtd(String authors[], String title, String ISBN) 
    {
        this.authors = authors;
        this.title = title;
        this.ISBN = ISBN;
    }

    // Builds a Book object from a DOM element
    public Book_dtd(Element element) 
    {
    	if (element == null)
    		return;
    	else {
    	    ISBN = element.getAttribute("ISBN");
    	    
    	    NodeList authorNodes = element.getElementsByTagName("author");
    	    int authorNumber = authorNodes.getLength();
    	    authors = new String[authorNumber];
    	    for (int i=0; i < authorNumber; i++) {
    			authors[i] = authorNodes.item(i).getFirstChild().getNodeValue();
    	    }
    	    
			title = element.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
    	}
    }
    
    // Prints a book to std output
    public void print()
    {
	    System.out.print("Authors:");
	    if (authors != null && authors.length>=0) {
	    	System.out.print(" "+authors[0]);
	    	for(int i=1; i<authors.length; i++)
	    		System.out.print(", "+authors[i]);
	    }
	    System.out.println("");
	    System.out.println("Title: " + title);
	    System.out.println("ISBN: " + ISBN);
	    System.out.println();
    }
    
    // get methods
    public String[] getAuthors() 
    {
        return authors;
    }

    public String getTitle() 
    {
        return title;
    }
    
    public String getISBN() 
    {
        return ISBN;
    }

    // set methods
    public void setAuthors(String[] authors) 
    {
        this.authors = authors;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }
  
    public void setISBN(String ISBN) 
    {
        this.ISBN = ISBN;
    }
} 
