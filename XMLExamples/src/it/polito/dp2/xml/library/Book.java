package it.polito.dp2.xml.library;
/* A Java class that represents a book
 * 
 */
import org.w3c.dom.*;

public class Book 
{
    private String authors[];
    private String title;
    private String ISBN;
    
    // Default empty constructor
    public Book()
    {
    }

    // Builds a Book object with the specified authors, title and ISBN strings
    public Book(String authors[], String title, String ISBN) 
    {
        this.authors = authors;
        this.title = title;
        this.ISBN = ISBN;
    }

    // Builds a Book object from a DOM element
    public Book(Element element) 
    {
    	if (element == null)
    		return;
    	else {
    	    ISBN = element.getAttribute("ISBN");
    	    
    	    Node authorTextNode;
    	    NodeList authorNodes = element.getElementsByTagName("author");
    	    int authorNumber = authorNodes.getLength();
    	    if (authorNumber>0)
    	    	authors = new String[authorNumber];
    	    for (int i=0; i < authorNumber; i++) {
    	    	authorTextNode = authorNodes.item(i).getFirstChild();
    	    	if (authorTextNode != null)
    				authors[i] = authorTextNode.getNodeValue();
    	    	else
    	    		authors[i] = null;
    	    }
			
    	    Node titleNode = element.getElementsByTagName("title").item(0);
			if (titleNode != null && titleNode.getFirstChild() != null)
				title = titleNode.getFirstChild().getNodeValue();
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
