/*
 * A Java class that represents a list of books
 * This is an annotated version from which a schema can be generated
 */
package it.polito.dp2.xml.library.annotated;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "books")
public class Books {
	protected List<Book> books;
	
	@XmlElement(required = false)
	public List<Book> getBook() {
        if (books == null) {
            books = new ArrayList<Book>();
        }
        return this.books;
    }
}
