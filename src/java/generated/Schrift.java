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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Schrift complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Schrift">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Komplexität" type="{http://www.example.org/Charakter/}Steigerungskategorie"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Schrift", propOrder = {
    "name",
    "komplexit\u00e4t"
})
public class Schrift {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Komplexit\u00e4t", required = true)
    @XmlSchemaType(name = "string")
    protected Steigerungskategorie komplexität;

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
     * Ruft den Wert der komplexität-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Steigerungskategorie }
     *     
     */
    public Steigerungskategorie getKomplexität() {
        return komplexität;
    }

    /**
     * Legt den Wert der komplexität-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Steigerungskategorie }
     *     
     */
    public void setKomplexität(Steigerungskategorie value) {
        this.komplexität = value;
    }

}
