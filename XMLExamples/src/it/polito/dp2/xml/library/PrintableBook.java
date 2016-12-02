package it.polito.dp2.xml.library;

import java.util.Iterator;

import it.polito.dp2.xml.library.gen.Book;

public class PrintableBook {
	Book b;

	public PrintableBook(Book b) {
		super();
		this.b = b;
	}

	public void print()
    {
	    System.out.print("Authors:");
	    Iterator<String> i = b.getAuthor().iterator();
	    if (i.hasNext())
	    	System.out.print(" " + i.next());
	    while (i.hasNext()) {
		    System.out.print(", " + i.next());
		}
	    System.out.println();
	    System.out.println("Title: " + b.getTitle());
	    System.out.println("ISBN: " + b.getISBN());
	    System.out.println();
    }
}
