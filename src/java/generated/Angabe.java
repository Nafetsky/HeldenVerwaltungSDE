//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.08.01 um 07:53:25 PM CEST 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Angabe complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Angabe">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Größe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Gewicht" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Haarfarbe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Augenfarbe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Geburtstag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Alter" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sonstiges" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Angabe", propOrder = {
    "gr\u00f6\u00dfe",
    "gewicht",
    "haarfarbe",
    "augenfarbe",
    "geburtstag",
    "alter",
    "sonstiges"
})
public class Angabe {

    @XmlElement(name = "Gr\u00f6\u00dfe")
    protected int größe;
    @XmlElement(name = "Gewicht")
    protected int gewicht;
    @XmlElement(name = "Haarfarbe", required = true)
    protected String haarfarbe;
    @XmlElement(name = "Augenfarbe", required = true)
    protected String augenfarbe;
    @XmlElement(name = "Geburtstag", required = true)
    protected String geburtstag;
    @XmlElement(name = "Alter")
    protected int alter;
    @XmlElement(name = "Sonstiges")
    protected List<String> sonstiges;

    /**
     * Ruft den Wert der größe-Eigenschaft ab.
     * 
     */
    public int getGröße() {
        return größe;
    }

    /**
     * Legt den Wert der größe-Eigenschaft fest.
     * 
     */
    public void setGröße(int value) {
        this.größe = value;
    }

    /**
     * Ruft den Wert der gewicht-Eigenschaft ab.
     * 
     */
    public int getGewicht() {
        return gewicht;
    }

    /**
     * Legt den Wert der gewicht-Eigenschaft fest.
     * 
     */
    public void setGewicht(int value) {
        this.gewicht = value;
    }

    /**
     * Ruft den Wert der haarfarbe-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaarfarbe() {
        return haarfarbe;
    }

    /**
     * Legt den Wert der haarfarbe-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaarfarbe(String value) {
        this.haarfarbe = value;
    }

    /**
     * Ruft den Wert der augenfarbe-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAugenfarbe() {
        return augenfarbe;
    }

    /**
     * Legt den Wert der augenfarbe-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAugenfarbe(String value) {
        this.augenfarbe = value;
    }

    /**
     * Ruft den Wert der geburtstag-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeburtstag() {
        return geburtstag;
    }

    /**
     * Legt den Wert der geburtstag-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeburtstag(String value) {
        this.geburtstag = value;
    }

    /**
     * Ruft den Wert der alter-Eigenschaft ab.
     * 
     */
    public int getAlter() {
        return alter;
    }

    /**
     * Legt den Wert der alter-Eigenschaft fest.
     * 
     */
    public void setAlter(int value) {
        this.alter = value;
    }

    /**
     * Gets the value of the sonstiges property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sonstiges property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSonstiges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSonstiges() {
        if (sonstiges == null) {
            sonstiges = new ArrayList<String>();
        }
        return this.sonstiges;
    }

}
