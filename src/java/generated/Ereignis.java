//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.12 um 02:15:21 PM CEST 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für Ereignis complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Ereignis">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Datum" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Grund" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AP" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="neueAP" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Sonderfertigkeitshinzugewinn" type="{http://www.example.org/Charakter/}Sonderfertigkeit" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Vorteil" type="{http://www.example.org/Charakter/}Vorteil" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Nachteil" type="{http://www.example.org/Charakter/}Nachteil" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Fertigkeitsänderung" type="{http://www.example.org/Charakter/}Fertigkeitsmodifikation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Kampftechnikänderung" type="{http://www.example.org/Charakter/}Fertigkeitsmodifikation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ereignis", propOrder = {
    "datum",
    "grund",
    "ap",
    "neueAP",
    "sonderfertigkeitshinzugewinn",
    "vorteil",
    "nachteil",
    "fertigkeits\u00e4nderung",
    "kampftechnik\u00e4nderung"
})
public class Ereignis {

    @XmlElement(name = "Datum", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datum;
    @XmlElement(name = "Grund", required = true)
    protected String grund;
    @XmlElement(name = "AP")
    protected Integer ap;
    protected Integer neueAP;
    @XmlElement(name = "Sonderfertigkeitshinzugewinn")
    protected List<Sonderfertigkeit> sonderfertigkeitshinzugewinn;
    @XmlElement(name = "Vorteil")
    protected List<Vorteil> vorteil;
    @XmlElement(name = "Nachteil")
    protected List<Nachteil> nachteil;
    @XmlElement(name = "Fertigkeits\u00e4nderung")
    protected List<Fertigkeitsmodifikation> fertigkeitsänderung;
    @XmlElement(name = "Kampftechnik\u00e4nderung")
    protected List<Fertigkeitsmodifikation> kampftechnikänderung;

    /**
     * Ruft den Wert der datum-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatum() {
        return datum;
    }

    /**
     * Legt den Wert der datum-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatum(XMLGregorianCalendar value) {
        this.datum = value;
    }

    /**
     * Ruft den Wert der grund-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrund() {
        return grund;
    }

    /**
     * Legt den Wert der grund-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrund(String value) {
        this.grund = value;
    }

    /**
     * Ruft den Wert der ap-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAP() {
        return ap;
    }

    /**
     * Legt den Wert der ap-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAP(Integer value) {
        this.ap = value;
    }

    /**
     * Ruft den Wert der neueAP-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNeueAP() {
        return neueAP;
    }

    /**
     * Legt den Wert der neueAP-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNeueAP(Integer value) {
        this.neueAP = value;
    }

    /**
     * Gets the value of the sonderfertigkeitshinzugewinn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sonderfertigkeitshinzugewinn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSonderfertigkeitshinzugewinn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sonderfertigkeit }
     * 
     * 
     */
    public List<Sonderfertigkeit> getSonderfertigkeitshinzugewinn() {
        if (sonderfertigkeitshinzugewinn == null) {
            sonderfertigkeitshinzugewinn = new ArrayList<Sonderfertigkeit>();
        }
        return this.sonderfertigkeitshinzugewinn;
    }

    /**
     * Gets the value of the vorteil property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vorteil property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVorteil().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Vorteil }
     * 
     * 
     */
    public List<Vorteil> getVorteil() {
        if (vorteil == null) {
            vorteil = new ArrayList<Vorteil>();
        }
        return this.vorteil;
    }

    /**
     * Gets the value of the nachteil property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nachteil property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNachteil().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Nachteil }
     * 
     * 
     */
    public List<Nachteil> getNachteil() {
        if (nachteil == null) {
            nachteil = new ArrayList<Nachteil>();
        }
        return this.nachteil;
    }

    /**
     * Gets the value of the fertigkeitsänderung property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fertigkeitsänderung property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFertigkeitsänderung().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Fertigkeitsmodifikation }
     * 
     * 
     */
    public List<Fertigkeitsmodifikation> getFertigkeitsänderung() {
        if (fertigkeitsänderung == null) {
            fertigkeitsänderung = new ArrayList<Fertigkeitsmodifikation>();
        }
        return this.fertigkeitsänderung;
    }

    /**
     * Gets the value of the kampftechnikänderung property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kampftechnikänderung property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKampftechnikänderung().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Fertigkeitsmodifikation }
     * 
     * 
     */
    public List<Fertigkeitsmodifikation> getKampftechnikänderung() {
        if (kampftechnikänderung == null) {
            kampftechnikänderung = new ArrayList<Fertigkeitsmodifikation>();
        }
        return this.kampftechnikänderung;
    }

}
