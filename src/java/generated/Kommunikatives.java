//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.08.10 um 05:36:22 PM CEST 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Kommunikatives complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Kommunikatives">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sprachen" type="{http://www.example.org/Charakter/}Sprache" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Schriften" type="{http://www.example.org/Charakter/}Schrift" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kommunikatives", propOrder = {
    "sprachen",
    "schriften"
})
public class Kommunikatives {

    @XmlElement(name = "Sprachen")
    protected List<Sprache> sprachen;
    @XmlElement(name = "Schriften")
    protected List<Schrift> schriften;

    /**
     * Gets the value of the sprachen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sprachen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSprachen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sprache }
     * 
     * 
     */
    public List<Sprache> getSprachen() {
        if (sprachen == null) {
            sprachen = new ArrayList<Sprache>();
        }
        return this.sprachen;
    }

    /**
     * Gets the value of the schriften property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schriften property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchriften().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Schrift }
     * 
     * 
     */
    public List<Schrift> getSchriften() {
        if (schriften == null) {
            schriften = new ArrayList<Schrift>();
        }
        return this.schriften;
    }

}
