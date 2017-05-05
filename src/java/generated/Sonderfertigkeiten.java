//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.05.05 um 11:28:12 PM CEST 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Sonderfertigkeiten complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Sonderfertigkeiten">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sonderfertigkeit" type="{http://www.example.org/Charakter/}Sonderfertigkeit" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Talentspezialisierung" type="{http://www.example.org/Charakter/}Talentspezialisierung" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Sonderfertigkeiten", propOrder = {
    "sonderfertigkeit",
    "talentspezialisierung"
})
public class Sonderfertigkeiten {

    @XmlElement(name = "Sonderfertigkeit")
    protected List<Sonderfertigkeit> sonderfertigkeit;
    @XmlElement(name = "Talentspezialisierung")
    protected List<Talentspezialisierung> talentspezialisierung;

    /**
     * Gets the value of the sonderfertigkeit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sonderfertigkeit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSonderfertigkeit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sonderfertigkeit }
     * 
     * 
     */
    public List<Sonderfertigkeit> getSonderfertigkeit() {
        if (sonderfertigkeit == null) {
            sonderfertigkeit = new ArrayList<Sonderfertigkeit>();
        }
        return this.sonderfertigkeit;
    }

    /**
     * Gets the value of the talentspezialisierung property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the talentspezialisierung property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTalentspezialisierung().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Talentspezialisierung }
     * 
     * 
     */
    public List<Talentspezialisierung> getTalentspezialisierung() {
        if (talentspezialisierung == null) {
            talentspezialisierung = new ArrayList<Talentspezialisierung>();
        }
        return this.talentspezialisierung;
    }

}
