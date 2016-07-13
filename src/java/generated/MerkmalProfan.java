//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.13 um 01:40:54 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MerkmalProfan.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="MerkmalProfan">
 *   &lt;restriction base="{http://www.example.org/Charakter/}Merkmal">
 *     &lt;enumeration value="Körper"/>
 *     &lt;enumeration value="Gesellschaft"/>
 *     &lt;enumeration value="Gesellschaft"/>
 *     &lt;enumeration value="Natur"/>
 *     &lt;enumeration value="Wissen"/>
 *     &lt;enumeration value="Handwerk"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MerkmalProfan")
@XmlEnum
public enum MerkmalProfan {

    @XmlEnumValue("K\u00f6rper")
    KÖRPER("K\u00f6rper"),
    @XmlEnumValue("Gesellschaft")
    GESELLSCHAFT("Gesellschaft"),
    @XmlEnumValue("Natur")
    NATUR("Natur"),
    @XmlEnumValue("Wissen")
    WISSEN("Wissen"),
    @XmlEnumValue("Handwerk")
    HANDWERK("Handwerk");
    private final String value;

    MerkmalProfan(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MerkmalProfan fromValue(String v) {
        for (MerkmalProfan c: MerkmalProfan.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
