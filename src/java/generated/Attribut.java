//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.08.10 um 05:36:22 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Attribut complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Attribut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Attributswert" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="Kürzel" type="{http://www.example.org/Charakter/}Attributskürzel" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Attribut")
public class Attribut {

    @XmlAttribute(name = "Attributswert")
    protected Integer attributswert;
    @XmlAttribute(name = "K\u00fcrzel")
    protected Attributskürzel kürzel;

    /**
     * Ruft den Wert der attributswert-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAttributswert() {
        return attributswert;
    }

    /**
     * Legt den Wert der attributswert-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAttributswert(Integer value) {
        this.attributswert = value;
    }

    /**
     * Ruft den Wert der kürzel-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attributskürzel }
     *     
     */
    public Attributskürzel getKürzel() {
        return kürzel;
    }

    /**
     * Legt den Wert der kürzel-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributskürzel }
     *     
     */
    public void setKürzel(Attributskürzel value) {
        this.kürzel = value;
    }

}
