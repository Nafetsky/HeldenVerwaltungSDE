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
 * <p>Java-Klasse für Schablone complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Schablone">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Eigenschaftswerte" type="{http://www.example.org/Charakter/}Eigenschaftswerte"/>
 *         &lt;element name="Kommunikatives" type="{http://www.example.org/Charakter/}Kommunikatives" minOccurs="0"/>
 *         &lt;element name="Vorteile" type="{http://www.example.org/Charakter/}Vorteile"/>
 *         &lt;element name="Talente" type="{http://www.example.org/Charakter/}Talente"/>
 *         &lt;element name="Zauber" type="{http://www.example.org/Charakter/}Zauber"/>
 *         &lt;element name="Rituale" type="{http://www.example.org/Charakter/}Rituale"/>
 *         &lt;element name="Liturgien" type="{http://www.example.org/Charakter/}Liturgien"/>
 *         &lt;element name="Zeremonien" type="{http://www.example.org/Charakter/}Zeremonien"/>
 *         &lt;element name="Kampftechniken" type="{http://www.example.org/Charakter/}Kampftechniken"/>
 *         &lt;element name="Sonderfertigkeiten" type="{http://www.example.org/Charakter/}Sonderfertigkeiten"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Schablone", propOrder = {
    "name",
    "eigenschaftswerte",
    "kommunikatives",
    "vorteile",
    "talente",
    "zauber",
    "rituale",
    "liturgien",
    "zeremonien",
    "kampftechniken",
    "sonderfertigkeiten"
})
public class Schablone {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Eigenschaftswerte", required = true)
    protected Eigenschaftswerte eigenschaftswerte;
    @XmlElement(name = "Kommunikatives")
    protected Kommunikatives kommunikatives;
    @XmlElement(name = "Vorteile", required = true)
    protected Vorteile vorteile;
    @XmlElement(name = "Talente", required = true)
    protected Talente talente;
    @XmlElement(name = "Zauber", required = true)
    protected Zauber zauber;
    @XmlElement(name = "Rituale", required = true)
    protected Rituale rituale;
    @XmlElement(name = "Liturgien", required = true)
    protected Liturgien liturgien;
    @XmlElement(name = "Zeremonien", required = true)
    protected Zeremonien zeremonien;
    @XmlElement(name = "Kampftechniken", required = true)
    protected Kampftechniken kampftechniken;
    @XmlElement(name = "Sonderfertigkeiten", required = true)
    protected Sonderfertigkeiten sonderfertigkeiten;

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
     * Ruft den Wert der eigenschaftswerte-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Eigenschaftswerte }
     *     
     */
    public Eigenschaftswerte getEigenschaftswerte() {
        return eigenschaftswerte;
    }

    /**
     * Legt den Wert der eigenschaftswerte-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Eigenschaftswerte }
     *     
     */
    public void setEigenschaftswerte(Eigenschaftswerte value) {
        this.eigenschaftswerte = value;
    }

    /**
     * Ruft den Wert der kommunikatives-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Kommunikatives }
     *     
     */
    public Kommunikatives getKommunikatives() {
        return kommunikatives;
    }

    /**
     * Legt den Wert der kommunikatives-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Kommunikatives }
     *     
     */
    public void setKommunikatives(Kommunikatives value) {
        this.kommunikatives = value;
    }

    /**
     * Ruft den Wert der vorteile-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Vorteile }
     *     
     */
    public Vorteile getVorteile() {
        return vorteile;
    }

    /**
     * Legt den Wert der vorteile-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Vorteile }
     *     
     */
    public void setVorteile(Vorteile value) {
        this.vorteile = value;
    }

    /**
     * Ruft den Wert der talente-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Talente }
     *     
     */
    public Talente getTalente() {
        return talente;
    }

    /**
     * Legt den Wert der talente-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Talente }
     *     
     */
    public void setTalente(Talente value) {
        this.talente = value;
    }

    /**
     * Ruft den Wert der zauber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Zauber }
     *     
     */
    public Zauber getZauber() {
        return zauber;
    }

    /**
     * Legt den Wert der zauber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Zauber }
     *     
     */
    public void setZauber(Zauber value) {
        this.zauber = value;
    }

    /**
     * Ruft den Wert der rituale-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Rituale }
     *     
     */
    public Rituale getRituale() {
        return rituale;
    }

    /**
     * Legt den Wert der rituale-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Rituale }
     *     
     */
    public void setRituale(Rituale value) {
        this.rituale = value;
    }

    /**
     * Ruft den Wert der liturgien-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Liturgien }
     *     
     */
    public Liturgien getLiturgien() {
        return liturgien;
    }

    /**
     * Legt den Wert der liturgien-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Liturgien }
     *     
     */
    public void setLiturgien(Liturgien value) {
        this.liturgien = value;
    }

    /**
     * Ruft den Wert der zeremonien-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Zeremonien }
     *     
     */
    public Zeremonien getZeremonien() {
        return zeremonien;
    }

    /**
     * Legt den Wert der zeremonien-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Zeremonien }
     *     
     */
    public void setZeremonien(Zeremonien value) {
        this.zeremonien = value;
    }

    /**
     * Ruft den Wert der kampftechniken-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Kampftechniken }
     *     
     */
    public Kampftechniken getKampftechniken() {
        return kampftechniken;
    }

    /**
     * Legt den Wert der kampftechniken-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Kampftechniken }
     *     
     */
    public void setKampftechniken(Kampftechniken value) {
        this.kampftechniken = value;
    }

    /**
     * Ruft den Wert der sonderfertigkeiten-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Sonderfertigkeiten }
     *     
     */
    public Sonderfertigkeiten getSonderfertigkeiten() {
        return sonderfertigkeiten;
    }

    /**
     * Legt den Wert der sonderfertigkeiten-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Sonderfertigkeiten }
     *     
     */
    public void setSonderfertigkeiten(Sonderfertigkeiten value) {
        this.sonderfertigkeiten = value;
    }

}
