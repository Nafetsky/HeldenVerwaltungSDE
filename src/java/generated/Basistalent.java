//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.28 um 08:51:00 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Basistalent complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Basistalent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Fertigkeitswert" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Merkmal" type="{http://www.example.org/Charakter/}MerkmalProfan"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Basistalent", propOrder = {
    "name",
    "fertigkeitswert",
    "merkmal"
})
public class Basistalent {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Fertigkeitswert")
    protected int fertigkeitswert;
    @XmlElement(name = "Merkmal", required = true)
    @XmlSchemaType(name = "string")
    protected MerkmalProfan merkmal;

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Ruft den Wert der fertigkeitswert-Eigenschaft ab.
     * 
     */
    public int getFertigkeitswert() {
        return fertigkeitswert;
    }

    /**
     * Legt den Wert der fertigkeitswert-Eigenschaft fest.
     * 
     */
    public void setFertigkeitswert(int value) {
        this.fertigkeitswert = value;
    }

    /**
     * Ruft den Wert der merkmal-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MerkmalProfan }
     *     
     */
    public MerkmalProfan getMerkmal() {
        return merkmal;
    }

    /**
     * Legt den Wert der merkmal-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MerkmalProfan }
     *     
     */
    public void setMerkmal(MerkmalProfan value) {
        this.merkmal = value;
    }

}
