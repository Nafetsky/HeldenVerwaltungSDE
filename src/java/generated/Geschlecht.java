//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.12 um 02:15:21 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r Geschlecht.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="Geschlecht">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="m�nnlich"/>
 *     &lt;enumeration value="weiblich"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Geschlecht")
@XmlEnum
public enum Geschlecht {

    @XmlEnumValue("m\u00e4nnlich")
    M�NNLICH("m\u00e4nnlich"),
    @XmlEnumValue("weiblich")
    WEIBLICH("weiblich");
    private final String value;

    Geschlecht(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Geschlecht fromValue(String v) {
        for (Geschlecht c: Geschlecht.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
