//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.29 at 11:13:08 PM CET 
//


package it.polito.dp2.NFFG.sol3.service.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nodeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="providerCat" type="{http://www.example.org/nffgInfo}providerCatType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="nodeName" use="required" type="{http://www.example.org/nffgInfo}myStringType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nodeType", propOrder = {
    "providerCat"
})
public class NodeType {

    @XmlElement(required = true)
    protected ProviderCatType providerCat;
    @XmlAttribute(name = "nodeName", required = true)
    protected String nodeName;

    /**
     * Gets the value of the providerCat property.
     * 
     * @return
     *     possible object is
     *     {@link ProviderCatType }
     *     
     */
    public ProviderCatType getProviderCat() {
        return providerCat;
    }

    /**
     * Sets the value of the providerCat property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderCatType }
     *     
     */
    public void setProviderCat(ProviderCatType value) {
        this.providerCat = value;
    }

    /**
     * Gets the value of the nodeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Sets the value of the nodeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeName(String value) {
        this.nodeName = value;
    }

}
