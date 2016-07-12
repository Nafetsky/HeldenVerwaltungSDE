//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.12 um 09:01:02 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Attributskürzel.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="Attributskürzel">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MU"/>
 *     &lt;enumeration value="KL"/>
 *     &lt;enumeration value="IN"/>
 *     &lt;enumeration value="CH"/>
 *     &lt;enumeration value="FF"/>
 *     &lt;enumeration value="GE"/>
 *     &lt;enumeration value="KO"/>
 *     &lt;enumeration value="KK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Attributsk\u00fcrzel")
@XmlEnum
public enum Attributskürzel {

    MU,
    KL,
    IN,
    CH,
    FF,
    GE,
    KO,
    KK;

    public String value() {
        return name();
    }

    public static Attributskürzel fromValue(String v) {
        return valueOf(v);
    }

}
