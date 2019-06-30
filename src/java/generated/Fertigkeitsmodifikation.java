//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.05.05 um 11:28:12 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Fertigkeitsmodifikation complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Fertigkeitsmodifikation">
 *   &lt;complexContent>
 *     &lt;restriction api.base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Änderung" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="neuerWert" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;choice>
 *           &lt;element name="NeueFertigkeit" type="{http://www.example.org/Charakter/}Fertigkeit" minOccurs="0"/>
 *           &lt;element name="NeueKampftechnik" type="{http://www.example.org/Charakter/}Kampftechnik" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fertigkeitsmodifikation", propOrder = {
    "name",
    "\u00e4nderung",
    "neuerWert",
    "neueFertigkeit",
    "neueKampftechnik"
})
public class Fertigkeitsmodifikation {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "\u00c4nderung")
    protected int änderung;
    protected int neuerWert;
    @XmlElement(name = "NeueFertigkeit")
    protected Fertigkeit neueFertigkeit;
    @XmlElement(name = "NeueKampftechnik")
    protected Kampftechnik neueKampftechnik;

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
     * Ruft den Wert der änderung-Eigenschaft ab.
     * 
     */
    public int getÄnderung() {
        return änderung;
    }

    /**
     * Legt den Wert der änderung-Eigenschaft fest.
     * 
     */
    public void setÄnderung(int value) {
        this.änderung = value;
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

    /**
     * Ruft den Wert der neueFertigkeit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Fertigkeit }
     *     
     */
    public Fertigkeit getNeueFertigkeit() {
        return neueFertigkeit;
    }

    /**
     * Legt den Wert der neueFertigkeit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Fertigkeit }
     *     
     */
    public void setNeueFertigkeit(Fertigkeit value) {
        this.neueFertigkeit = value;
    }

    /**
     * Ruft den Wert der neueKampftechnik-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Kampftechnik }
     *     
     */
    public Kampftechnik getNeueKampftechnik() {
        return neueKampftechnik;
    }

    /**
     * Legt den Wert der neueKampftechnik-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Kampftechnik }
     *     
     */
    public void setNeueKampftechnik(Kampftechnik value) {
        this.neueKampftechnik = value;
    }

}
