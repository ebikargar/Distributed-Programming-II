
package it.polito.dp2.NFFG.sol3.service.neo4j;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.polito.dp2.NFFG.sol3.service.neo4j package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.polito.dp2.NFFG.sol3.service.neo4j
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Nodes }
     * 
     */
    public Nodes createNodes() {
        return new Nodes();
    }

    /**
     * Create an instance of {@link it.polito.dp2.NFFG.sol3.service.neo4j.Node }
     * 
     */
    public it.polito.dp2.NFFG.sol3.service.neo4j.Node createNode() {
        return new it.polito.dp2.NFFG.sol3.service.neo4j.Node();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link Labels }
     * 
     */
    public Labels createLabels() {
        return new Labels();
    }

    /**
     * Create an instance of {@link Nodes.Node }
     * 
     */
    public Nodes.Node createNodesNode() {
        return new Nodes.Node();
    }

    /**
     * Create an instance of {@link Paths }
     * 
     */
    public Paths createPaths() {
        return new Paths();
    }

    /**
     * Create an instance of {@link Path }
     * 
     */
    public Path createPath() {
        return new Path();
    }

    /**
     * Create an instance of {@link Relationship }
     * 
     */
    public Relationship createRelationship() {
        return new Relationship();
    }

}
