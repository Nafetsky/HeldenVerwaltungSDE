//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.13 um 11:02:17 AM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Fertigkeitskategorie.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="Fertigkeitskategorie">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Profan"/>
 *     &lt;enumeration value="Zauber"/>
 *     &lt;enumeration value="Ritual"/>
 *     &lt;enumeration value="Liturgie"/>
 *     &lt;enumeration value="Zeremonie"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Fertigkeitskategorie")
@XmlEnum
public enum Fertigkeitskategorie {

    @XmlEnumValue("Profan")
    PROFAN("Profan"),
    @XmlEnumValue("Zauber")
    ZAUBER("Zauber"),
    @XmlEnumValue("Ritual")
    RITUAL("Ritual"),
    @XmlEnumValue("Liturgie")
    LITURGIE("Liturgie"),
    @XmlEnumValue("Zeremonie")
    ZEREMONIE("Zeremonie");
    private final String value;

    Fertigkeitskategorie(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Fertigkeitskategorie fromValue(String v) {
        for (Fertigkeitskategorie c: Fertigkeitskategorie.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
