//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.13 um 01:40:54 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Spezies complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Spezies">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BasisLeben" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BasisSeelenkraft" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BasisZähigkeit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BasisGeschwindigkeit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Kosten" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Spezies", propOrder = {
    "name",
    "basisLeben",
    "basisSeelenkraft",
    "basisZ\u00e4higkeit",
    "basisGeschwindigkeit",
    "kosten"
})
public class Spezies {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "BasisLeben")
    protected int basisLeben;
    @XmlElement(name = "BasisSeelenkraft")
    protected int basisSeelenkraft;
    @XmlElement(name = "BasisZ\u00e4higkeit")
    protected int basisZähigkeit;
    @XmlElement(name = "BasisGeschwindigkeit")
    protected int basisGeschwindigkeit;
    @XmlElement(name = "Kosten")
    protected int kosten;

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
     * Ruft den Wert der basisLeben-Eigenschaft ab.
     * 
     */
    public int getBasisLeben() {
        return basisLeben;
    }

    /**
     * Legt den Wert der basisLeben-Eigenschaft fest.
     * 
     */
    public void setBasisLeben(int value) {
        this.basisLeben = value;
    }

    /**
     * Ruft den Wert der basisSeelenkraft-Eigenschaft ab.
     * 
     */
    public int getBasisSeelenkraft() {
        return basisSeelenkraft;
    }

    /**
     * Legt den Wert der basisSeelenkraft-Eigenschaft fest.
     * 
     */
    public void setBasisSeelenkraft(int value) {
        this.basisSeelenkraft = value;
    }

    /**
     * Ruft den Wert der basisZähigkeit-Eigenschaft ab.
     * 
     */
    public int getBasisZähigkeit() {
        return basisZähigkeit;
    }

    /**
     * Legt den Wert der basisZähigkeit-Eigenschaft fest.
     * 
     */
    public void setBasisZähigkeit(int value) {
        this.basisZähigkeit = value;
    }

    /**
     * Ruft den Wert der basisGeschwindigkeit-Eigenschaft ab.
     * 
     */
    public int getBasisGeschwindigkeit() {
        return basisGeschwindigkeit;
    }

    /**
     * Legt den Wert der basisGeschwindigkeit-Eigenschaft fest.
     * 
     */
    public void setBasisGeschwindigkeit(int value) {
        this.basisGeschwindigkeit = value;
    }

    /**
     * Ruft den Wert der kosten-Eigenschaft ab.
     * 
     */
    public int getKosten() {
        return kosten;
    }

    /**
     * Legt den Wert der kosten-Eigenschaft fest.
     * 
     */
    public void setKosten(int value) {
        this.kosten = value;
    }

}
