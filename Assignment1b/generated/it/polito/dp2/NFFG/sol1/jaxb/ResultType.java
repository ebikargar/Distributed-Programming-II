//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.08 at 01:51:08 PM CET 
//


package it.polito.dp2.NFFG.sol1.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for resultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="verificationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="verificationMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="verificationResult" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultType", propOrder = {
    "verificationTime",
    "verificationMessage",
    "verificationResult"
})
public class ResultType {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar verificationTime;
    protected String verificationMessage;
    protected Boolean verificationResult;

    /**
     * Gets the value of the verificationTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVerificationTime() {
        return verificationTime;
    }

    /**
     * Sets the value of the verificationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVerificationTime(XMLGregorianCalendar value) {
        this.verificationTime = value;
    }

    /**
     * Gets the value of the verificationMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerificationMessage() {
        return verificationMessage;
    }

    /**
     * Sets the value of the verificationMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerificationMessage(String value) {
        this.verificationMessage = value;
    }

    /**
     * Gets the value of the verificationResult property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVerificationResult() {
        return verificationResult;
    }

    /**
     * Sets the value of the verificationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerificationResult(Boolean value) {
        this.verificationResult = value;
    }

}
