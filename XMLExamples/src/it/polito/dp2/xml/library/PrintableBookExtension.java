package it.polito.dp2.xml.library;

import java.util.Iterator;

import it.polito.dp2.xml.library.gen.Book;

public class PrintableBookExtension extends Book {
	public PrintableBookExtension(Book b) {
		setISBN(b.getISBN());
		setTitle(b.getISBN());
		getAuthor().addAll(b.getAuthor());
	}
	
	public void print()
    {
	    System.out.print("Authors:");
	    Iterator<String> i = getAuthor().iterator();
	    if (i.hasNext())
	    	System.out.print(" " + i.next());
	    while (i.hasNext()) {
		    System.out.print(", " + i.next());
		}
	    System.out.println();
	    System.out.println("Title: " + getTitle());
	    System.out.println("ISBN: " + getISBN());
	    System.out.println();
    }
}
