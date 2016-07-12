//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.12 um 02:15:21 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Kampftechnik complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Kampftechnik">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Kampftechnikwert" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Leiteigenschaft" type="{http://www.example.org/Charakter/}Attributskürzel"/>
 *         &lt;element name="Steigerungskosten" type="{http://www.example.org/Charakter/}Steigerungskategorie"/>
 *         &lt;element name="Basistechnik" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kampftechnik", propOrder = {
    "name",
    "kampftechnikwert",
    "leiteigenschaft",
    "steigerungskosten",
    "basistechnik"
})
public class Kampftechnik {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Kampftechnikwert")
    protected int kampftechnikwert;
    @XmlElement(name = "Leiteigenschaft", required = true)
    @XmlSchemaType(name = "string")
    protected Attributskürzel leiteigenschaft;
    @XmlElement(name = "Steigerungskosten", required = true)
    @XmlSchemaType(name = "string")
    protected Steigerungskategorie steigerungskosten;
    @XmlElement(name = "Basistechnik")
    protected Boolean basistechnik;

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
     * Ruft den Wert der kampftechnikwert-Eigenschaft ab.
     * 
     */
    public int getKampftechnikwert() {
        return kampftechnikwert;
    }

    /**
     * Legt den Wert der kampftechnikwert-Eigenschaft fest.
     * 
     */
    public void setKampftechnikwert(int value) {
        this.kampftechnikwert = value;
    }

    /**
     * Ruft den Wert der leiteigenschaft-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attributskürzel }
     *     
     */
    public Attributskürzel getLeiteigenschaft() {
        return leiteigenschaft;
    }

    /**
     * Legt den Wert der leiteigenschaft-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributskürzel }
     *     
     */
    public void setLeiteigenschaft(Attributskürzel value) {
        this.leiteigenschaft = value;
    }

    /**
     * Ruft den Wert der steigerungskosten-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Steigerungskategorie }
     *     
     */
    public Steigerungskategorie getSteigerungskosten() {
        return steigerungskosten;
    }

    /**
     * Legt den Wert der steigerungskosten-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Steigerungskategorie }
     *     
     */
    public void setSteigerungskosten(Steigerungskategorie value) {
        this.steigerungskosten = value;
    }

    /**
     * Ruft den Wert der basistechnik-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBasistechnik() {
        return basistechnik;
    }

    /**
     * Legt den Wert der basistechnik-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBasistechnik(Boolean value) {
        this.basistechnik = value;
    }

}
