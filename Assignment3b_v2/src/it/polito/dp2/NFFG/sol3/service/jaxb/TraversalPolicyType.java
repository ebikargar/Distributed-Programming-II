//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.29 at 11:13:08 PM CET 
//


package it.polito.dp2.NFFG.sol3.service.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for traversalPolicyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="traversalPolicyType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.org/nffgInfo}reachabilityPolicyType">
 *       &lt;sequence>
 *         &lt;element name="traversComponent" type="{http://www.example.org/nffgInfo}funcType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "traversalPolicyType", propOrder = {
    "traversComponent"
})
public class TraversalPolicyType
    extends ReachabilityPolicyType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected List<FuncType> traversComponent;

    /**
     * Gets the value of the traversComponent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the traversComponent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTraversComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FuncType }
     * 
     * 
     */
    public List<FuncType> getTraversComponent() {
        if (traversComponent == null) {
            traversComponent = new ArrayList<FuncType>();
        }
        return this.traversComponent;
    }

}
