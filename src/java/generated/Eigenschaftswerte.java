//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.08.10 um 05:07:24 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Eigenschaftswerte complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Eigenschaftswerte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Mut" type="{http://www.example.org/Charakter/}Attribut"/>
 *         &lt;element name="Klugheit" type="{http://www.example.org/Charakter/}Attribut"/>
 *         &lt;element name="Intuition" type="{http://www.example.org/Charakter/}Attribut"/>
 *         &lt;element name="Charisma" type="{http://www.example.org/Charakter/}Attribut"/>
 *         &lt;element name="Fingerfertigkeit" type="{http://www.example.org/Charakter/}Attribut"/>
 *         &lt;element name="Gewandheit" type="{http://www.example.org/Charakter/}Attribut"/>
 *         &lt;element name="Konstitution" type="{http://www.example.org/Charakter/}Attribut"/>
 *         &lt;element name="Körperkraft" type="{http://www.example.org/Charakter/}Attribut"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Eigenschaftswerte", propOrder = {
    "mut",
    "klugheit",
    "intuition",
    "charisma",
    "fingerfertigkeit",
    "gewandheit",
    "konstitution",
    "k\u00f6rperkraft"
})
public class Eigenschaftswerte {

    @XmlElement(name = "Mut", required = true)
    protected Attribut mut;
    @XmlElement(name = "Klugheit", required = true)
    protected Attribut klugheit;
    @XmlElement(name = "Intuition", required = true)
    protected Attribut intuition;
    @XmlElement(name = "Charisma", required = true)
    protected Attribut charisma;
    @XmlElement(name = "Fingerfertigkeit", required = true)
    protected Attribut fingerfertigkeit;
    @XmlElement(name = "Gewandheit", required = true)
    protected Attribut gewandheit;
    @XmlElement(name = "Konstitution", required = true)
    protected Attribut konstitution;
    @XmlElement(name = "K\u00f6rperkraft", required = true)
    protected Attribut körperkraft;

    /**
     * Ruft den Wert der mut-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attribut }
     *     
     */
    public Attribut getMut() {
        return mut;
    }

    /**
     * Legt den Wert der mut-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attribut }
     *     
     */
    public void setMut(Attribut value) {
        this.mut = value;
    }

    /**
     * Ruft den Wert der klugheit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attribut }
     *     
     */
    public Attribut getKlugheit() {
        return klugheit;
    }

    /**
     * Legt den Wert der klugheit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attribut }
     *     
     */
    public void setKlugheit(Attribut value) {
        this.klugheit = value;
    }

    /**
     * Ruft den Wert der intuition-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attribut }
     *     
     */
    public Attribut getIntuition() {
        return intuition;
    }

    /**
     * Legt den Wert der intuition-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attribut }
     *     
     */
    public void setIntuition(Attribut value) {
        this.intuition = value;
    }

    /**
     * Ruft den Wert der charisma-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attribut }
     *     
     */
    public Attribut getCharisma() {
        return charisma;
    }

    /**
     * Legt den Wert der charisma-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attribut }
     *     
     */
    public void setCharisma(Attribut value) {
        this.charisma = value;
    }

    /**
     * Ruft den Wert der fingerfertigkeit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attribut }
     *     
     */
    public Attribut getFingerfertigkeit() {
        return fingerfertigkeit;
    }

    /**
     * Legt den Wert der fingerfertigkeit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attribut }
     *     
     */
    public void setFingerfertigkeit(Attribut value) {
        this.fingerfertigkeit = value;
    }

    /**
     * Ruft den Wert der gewandheit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attribut }
     *     
     */
    public Attribut getGewandheit() {
        return gewandheit;
    }

    /**
     * Legt den Wert der gewandheit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attribut }
     *     
     */
    public void setGewandheit(Attribut value) {
        this.gewandheit = value;
    }

    /**
     * Ruft den Wert der konstitution-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attribut }
     *     
     */
    public Attribut getKonstitution() {
        return konstitution;
    }

    /**
     * Legt den Wert der konstitution-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attribut }
     *     
     */
    public void setKonstitution(Attribut value) {
        this.konstitution = value;
    }

    /**
     * Ruft den Wert der körperkraft-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attribut }
     *     
     */
    public Attribut getKörperkraft() {
        return körperkraft;
    }

    /**
     * Legt den Wert der körperkraft-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attribut }
     *     
     */
    public void setKörperkraft(Attribut value) {
        this.körperkraft = value;
    }

}
