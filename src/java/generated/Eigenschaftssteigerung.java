//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.15 um 03:34:02 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Eigenschaftssteigerung complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Eigenschaftssteigerung">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Eigenschaft" type="{http://www.example.org/Charakter/}Attributskürzel"/>
 *         &lt;element name="Steigerung" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="neuerWert" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Eigenschaftssteigerung", propOrder = {
    "eigenschaft",
    "steigerung",
    "neuerWert"
})
public class Eigenschaftssteigerung {

    @XmlElement(name = "Eigenschaft", required = true)
    @XmlSchemaType(name = "string")
    protected Attributskürzel eigenschaft;
    @XmlElement(name = "Steigerung")
    protected int steigerung;
    protected int neuerWert;

    /**
     * Ruft den Wert der eigenschaft-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attributskürzel }
     *     
     */
    public Attributskürzel getEigenschaft() {
        return eigenschaft;
    }

    /**
     * Legt den Wert der eigenschaft-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributskürzel }
     *     
     */
    public void setEigenschaft(Attributskürzel value) {
        this.eigenschaft = value;
    }

    /**
     * Ruft den Wert der steigerung-Eigenschaft ab.
     * 
     */
    public int getSteigerung() {
        return steigerung;
    }

    /**
     * Legt den Wert der steigerung-Eigenschaft fest.
     * 
     */
    public void setSteigerung(int value) {
        this.steigerung = value;
    }

    /**
     * Ruft den Wert der neuerWert-Eigenschaft ab.
     * 
     */
    public int getNeuerWert() {
        return neuerWert;
    }

    /**
     * Legt den Wert der neuerWert-Eigenschaft fest.
     * 
     */
    public void setNeuerWert(int value) {
        this.neuerWert = value;
    }

}
