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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Fertigkeit complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Fertigkeit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Attribut1" type="{http://www.example.org/Charakter/}Attributskürzel"/>
 *         &lt;element name="Attribut2" type="{http://www.example.org/Charakter/}Attributskürzel"/>
 *         &lt;element name="Attribut3" type="{http://www.example.org/Charakter/}Attributskürzel"/>
 *         &lt;element name="Fertigkeitswert" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Steigerungskosten" type="{http://www.example.org/Charakter/}Steigerungskategorie"/>
 *         &lt;element name="Kategorie" type="{http://www.example.org/Charakter/}Fertigkeitskategorie"/>
 *         &lt;element name="Merkmal" type="{http://www.example.org/Charakter/}Merkmal" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fertigkeit", propOrder = {
    "name",
    "attribut1",
    "attribut2",
    "attribut3",
    "fertigkeitswert",
    "steigerungskosten",
    "kategorie",
    "merkmal"
})
public class Fertigkeit {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Attribut1", required = true)
    @XmlSchemaType(name = "string")
    protected Attributskürzel attribut1;
    @XmlElement(name = "Attribut2", required = true)
    @XmlSchemaType(name = "string")
    protected Attributskürzel attribut2;
    @XmlElement(name = "Attribut3", required = true)
    @XmlSchemaType(name = "string")
    protected Attributskürzel attribut3;
    @XmlElement(name = "Fertigkeitswert")
    protected int fertigkeitswert;
    @XmlElement(name = "Steigerungskosten", required = true)
    @XmlSchemaType(name = "string")
    protected Steigerungskategorie steigerungskosten;
    @XmlElement(name = "Kategorie", required = true)
    @XmlSchemaType(name = "string")
    protected Fertigkeitskategorie kategorie;
    @XmlElement(name = "Merkmal", required = true)
    protected List<String> merkmal;

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
     * Ruft den Wert der attribut1-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attributskürzel }
     *     
     */
    public Attributskürzel getAttribut1() {
        return attribut1;
    }

    /**
     * Legt den Wert der attribut1-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributskürzel }
     *     
     */
    public void setAttribut1(Attributskürzel value) {
        this.attribut1 = value;
    }

    /**
     * Ruft den Wert der attribut2-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attributskürzel }
     *     
     */
    public Attributskürzel getAttribut2() {
        return attribut2;
    }

    /**
     * Legt den Wert der attribut2-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributskürzel }
     *     
     */
    public void setAttribut2(Attributskürzel value) {
        this.attribut2 = value;
    }

    /**
     * Ruft den Wert der attribut3-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attributskürzel }
     *     
     */
    public Attributskürzel getAttribut3() {
        return attribut3;
    }

    /**
     * Legt den Wert der attribut3-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributskürzel }
     *     
     */
    public void setAttribut3(Attributskürzel value) {
        this.attribut3 = value;
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
     * Ruft den Wert der kategorie-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Fertigkeitskategorie }
     *     
     */
    public Fertigkeitskategorie getKategorie() {
        return kategorie;
    }

    /**
     * Legt den Wert der kategorie-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Fertigkeitskategorie }
     *     
     */
    public void setKategorie(Fertigkeitskategorie value) {
        this.kategorie = value;
    }

    /**
     * Gets the value of the merkmal property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the merkmal property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMerkmal().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMerkmal() {
        if (merkmal == null) {
            merkmal = new ArrayList<String>();
        }
        return this.merkmal;
    }

}
