//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.12 at 06:11:05 PM CET 
//


package it.polito.dp2.NFFG.sol1.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for providerCatType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="providerCatType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="funcType" type="{http://www.example.org/nffgInfo}funcType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "providerCatType", propOrder = {
    "funcType"
})
public class ProviderCatType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected FuncType funcType;

    /**
     * Gets the value of the funcType property.
     * 
     * @return
     *     possible object is
     *     {@link FuncType }
     *     
     */
    public FuncType getFuncType() {
        return funcType;
    }

    /**
     * Sets the value of the funcType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncType }
     *     
     */
    public void setFuncType(FuncType value) {
        this.funcType = value;
    }

}
