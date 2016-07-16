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
 * <p>Java-Klasse für Charakter complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Charakter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Geschlecht" type="{http://www.example.org/Charakter/}Geschlecht"/>
 *         &lt;element name="Spezies" type="{http://www.example.org/Charakter/}Spezies"/>
 *         &lt;element name="Kultur" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Angaben" type="{http://www.example.org/Charakter/}Angabe" minOccurs="0"/>
 *         &lt;element name="Eigenschaftswerte" type="{http://www.example.org/Charakter/}Eigenschaftswerte"/>
 *         &lt;element name="Vorteile" type="{http://www.example.org/Charakter/}Vorteile"/>
 *         &lt;element name="Nachteile" type="{http://www.example.org/Charakter/}Nachteile"/>
 *         &lt;element name="AP" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LeP" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="AsP" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="KaP" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Talente" type="{http://www.example.org/Charakter/}Talente"/>
 *         &lt;element name="Zauber" type="{http://www.example.org/Charakter/}Zauber"/>
 *         &lt;element name="Rituale" type="{http://www.example.org/Charakter/}Rituale"/>
 *         &lt;element name="Liturgien" type="{http://www.example.org/Charakter/}Liturgien"/>
 *         &lt;element name="Zeremonien" type="{http://www.example.org/Charakter/}Zeremonien"/>
 *         &lt;element name="Kampftechniken" type="{http://www.example.org/Charakter/}Kampftechniken"/>
 *         &lt;element name="Sonderfertigkeiten" type="{http://www.example.org/Charakter/}Sonderfertigkeiten"/>
 *         &lt;element name="Steigerungshistorie" type="{http://www.example.org/Charakter/}Historie"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Charakter", propOrder = {
    "name",
    "geschlecht",
    "spezies",
    "kultur",
    "angaben",
    "eigenschaftswerte",
    "vorteile",
    "nachteile",
    "ap",
    "leP",
    "asP",
    "kaP",
    "talente",
    "zauber",
    "rituale",
    "liturgien",
    "zeremonien",
    "kampftechniken",
    "sonderfertigkeiten",
    "steigerungshistorie"
})
public class Charakter {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Geschlecht", required = true)
    @XmlSchemaType(name = "string")
    protected Geschlecht geschlecht;
    @XmlElement(name = "Spezies", required = true)
    protected Spezies spezies;
    @XmlElement(name = "Kultur", required = true)
    protected String kultur;
    @XmlElement(name = "Angaben")
    protected Angabe angaben;
    @XmlElement(name = "Eigenschaftswerte", required = true)
    protected Eigenschaftswerte eigenschaftswerte;
    @XmlElement(name = "Vorteile", required = true)
    protected Vorteile vorteile;
    @XmlElement(name = "Nachteile", required = true)
    protected Nachteile nachteile;
    @XmlElement(name = "AP")
    protected int ap;
    @XmlElement(name = "LeP")
    protected Integer leP;
    @XmlElement(name = "AsP")
    protected Integer asP;
    @XmlElement(name = "KaP")
    protected Integer kaP;
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
    @XmlElement(name = "Steigerungshistorie", required = true)
    protected Historie steigerungshistorie;

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
     * Ruft den Wert der geschlecht-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Geschlecht }
     *     
     */
    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    /**
     * Legt den Wert der geschlecht-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Geschlecht }
     *     
     */
    public void setGeschlecht(Geschlecht value) {
        this.geschlecht = value;
    }

    /**
     * Ruft den Wert der spezies-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Spezies }
     *     
     */
    public Spezies getSpezies() {
        return spezies;
    }

    /**
     * Legt den Wert der spezies-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Spezies }
     *     
     */
    public void setSpezies(Spezies value) {
        this.spezies = value;
    }

    /**
     * Ruft den Wert der kultur-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKultur() {
        return kultur;
    }

    /**
     * Legt den Wert der kultur-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKultur(String value) {
        this.kultur = value;
    }

    /**
     * Ruft den Wert der angaben-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Angabe }
     *     
     */
    public Angabe getAngaben() {
        return angaben;
    }

    /**
     * Legt den Wert der angaben-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Angabe }
     *     
     */
    public void setAngaben(Angabe value) {
        this.angaben = value;
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
     * Ruft den Wert der nachteile-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Nachteile }
     *     
     */
    public Nachteile getNachteile() {
        return nachteile;
    }

    /**
     * Legt den Wert der nachteile-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Nachteile }
     *     
     */
    public void setNachteile(Nachteile value) {
        this.nachteile = value;
    }

    /**
     * Ruft den Wert der ap-Eigenschaft ab.
     * 
     */
    public int getAP() {
        return ap;
    }

    /**
     * Legt den Wert der ap-Eigenschaft fest.
     * 
     */
    public void setAP(int value) {
        this.ap = value;
    }

    /**
     * Ruft den Wert der leP-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLeP() {
        return leP;
    }

    /**
     * Legt den Wert der leP-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLeP(Integer value) {
        this.leP = value;
    }

    /**
     * Ruft den Wert der asP-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAsP() {
        return asP;
    }

    /**
     * Legt den Wert der asP-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAsP(Integer value) {
        this.asP = value;
    }

    /**
     * Ruft den Wert der kaP-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKaP() {
        return kaP;
    }

    /**
     * Legt den Wert der kaP-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKaP(Integer value) {
        this.kaP = value;
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

    /**
     * Ruft den Wert der steigerungshistorie-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Historie }
     *     
     */
    public Historie getSteigerungshistorie() {
        return steigerungshistorie;
    }

    /**
     * Legt den Wert der steigerungshistorie-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Historie }
     *     
     */
    public void setSteigerungshistorie(Historie value) {
        this.steigerungshistorie = value;
    }

}
