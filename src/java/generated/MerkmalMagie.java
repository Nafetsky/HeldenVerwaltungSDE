//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.12 um 09:01:02 PM CEST 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r MerkmalMagie.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="MerkmalMagie">
 *   &lt;restriction base="{http://www.example.org/Charakter/}Merkmal">
 *     &lt;enumeration value="Antimagie"/>
 *     &lt;enumeration value="D�monisch"/>
 *     &lt;enumeration value="Einfluss"/>
 *     &lt;enumeration value="Elementar"/>
 *     &lt;enumeration value="Heilung"/>
 *     &lt;enumeration value="Hellsicht"/>
 *     &lt;enumeration value="Illusion"/>
 *     &lt;enumeration value="Sph�ren"/>
 *     &lt;enumeration value="Objekt"/>
 *     &lt;enumeration value="Telekinese"/>
 *     &lt;enumeration value="Verwandlung"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MerkmalMagie")
@XmlEnum
public enum MerkmalMagie {

    @XmlEnumValue("Antimagie")
    ANTIMAGIE("Antimagie"),
    @XmlEnumValue("D\u00e4monisch")
    D�MONISCH("D\u00e4monisch"),
    @XmlEnumValue("Einfluss")
    EINFLUSS("Einfluss"),
    @XmlEnumValue("Elementar")
    ELEMENTAR("Elementar"),
    @XmlEnumValue("Heilung")
    HEILUNG("Heilung"),
    @XmlEnumValue("Hellsicht")
    HELLSICHT("Hellsicht"),
    @XmlEnumValue("Illusion")
    ILLUSION("Illusion"),
    @XmlEnumValue("Sph\u00e4ren")
    SPH�REN("Sph\u00e4ren"),
    @XmlEnumValue("Objekt")
    OBJEKT("Objekt"),
    @XmlEnumValue("Telekinese")
    TELEKINESE("Telekinese"),
    @XmlEnumValue("Verwandlung")
    VERWANDLUNG("Verwandlung");
    private final String value;

    MerkmalMagie(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MerkmalMagie fromValue(String v) {
        for (MerkmalMagie c: MerkmalMagie.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
