//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.28 um 08:51:00 PM CEST 
//


package generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Charakter_QNAME = new QName("http://www.example.org/Charakter/", "Charakter");
    private final static QName _MetaDate_QNAME = new QName("http://www.example.org/Charakter/", "metaDate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Charakter }
     * 
     */
    public Charakter createCharakter() {
        return new Charakter();
    }

    /**
     * Create an instance of {@link MetaData }
     * 
     */
    public MetaData createMetaData() {
        return new MetaData();
    }

    /**
     * Create an instance of {@link Basistalent }
     * 
     */
    public Basistalent createBasistalent() {
        return new Basistalent();
    }

    /**
     * Create an instance of {@link Liturgien }
     * 
     */
    public Liturgien createLiturgien() {
        return new Liturgien();
    }

    /**
     * Create an instance of {@link Angabe }
     * 
     */
    public Angabe createAngabe() {
        return new Angabe();
    }

    /**
     * Create an instance of {@link Rituale }
     * 
     */
    public Rituale createRituale() {
        return new Rituale();
    }

    /**
     * Create an instance of {@link Ereignis }
     * 
     */
    public Ereignis createEreignis() {
        return new Ereignis();
    }

    /**
     * Create an instance of {@link Talentspezialisierung }
     * 
     */
    public Talentspezialisierung createTalentspezialisierung() {
        return new Talentspezialisierung();
    }

    /**
     * Create an instance of {@link Sonderfertigkeiten }
     * 
     */
    public Sonderfertigkeiten createSonderfertigkeiten() {
        return new Sonderfertigkeiten();
    }

    /**
     * Create an instance of {@link Fertigkeitsmodifikation }
     * 
     */
    public Fertigkeitsmodifikation createFertigkeitsmodifikation() {
        return new Fertigkeitsmodifikation();
    }

    /**
     * Create an instance of {@link Historie }
     * 
     */
    public Historie createHistorie() {
        return new Historie();
    }

    /**
     * Create an instance of {@link MetaDataLine }
     * 
     */
    public MetaDataLine createMetaDataLine() {
        return new MetaDataLine();
    }

    /**
     * Create an instance of {@link Kampftechnik }
     * 
     */
    public Kampftechnik createKampftechnik() {
        return new Kampftechnik();
    }

    /**
     * Create an instance of {@link Eigenschaftswerte }
     * 
     */
    public Eigenschaftswerte createEigenschaftswerte() {
        return new Eigenschaftswerte();
    }

    /**
     * Create an instance of {@link Attribut }
     * 
     */
    public Attribut createAttribut() {
        return new Attribut();
    }

    /**
     * Create an instance of {@link Zauber }
     * 
     */
    public Zauber createZauber() {
        return new Zauber();
    }

    /**
     * Create an instance of {@link Eigenschaftssteigerung }
     * 
     */
    public Eigenschaftssteigerung createEigenschaftssteigerung() {
        return new Eigenschaftssteigerung();
    }

    /**
     * Create an instance of {@link Nachteile }
     * 
     */
    public Nachteile createNachteile() {
        return new Nachteile();
    }

    /**
     * Create an instance of {@link Spezies }
     * 
     */
    public Spezies createSpezies() {
        return new Spezies();
    }

    /**
     * Create an instance of {@link Talente }
     * 
     */
    public Talente createTalente() {
        return new Talente();
    }

    /**
     * Create an instance of {@link Vorteile }
     * 
     */
    public Vorteile createVorteile() {
        return new Vorteile();
    }

    /**
     * Create an instance of {@link Vorteil }
     * 
     */
    public Vorteil createVorteil() {
        return new Vorteil();
    }

    /**
     * Create an instance of {@link Zeremonien }
     * 
     */
    public Zeremonien createZeremonien() {
        return new Zeremonien();
    }

    /**
     * Create an instance of {@link Nachteil }
     * 
     */
    public Nachteil createNachteil() {
        return new Nachteil();
    }

    /**
     * Create an instance of {@link Kampftechniken }
     * 
     */
    public Kampftechniken createKampftechniken() {
        return new Kampftechniken();
    }

    /**
     * Create an instance of {@link Fertigkeit }
     * 
     */
    public Fertigkeit createFertigkeit() {
        return new Fertigkeit();
    }

    /**
     * Create an instance of {@link Sonderfertigkeit }
     * 
     */
    public Sonderfertigkeit createSonderfertigkeit() {
        return new Sonderfertigkeit();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Charakter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/Charakter/", name = "Charakter")
    public JAXBElement<Charakter> createCharakter(Charakter value) {
        return new JAXBElement<Charakter>(_Charakter_QNAME, Charakter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetaData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/Charakter/", name = "metaDate")
    public JAXBElement<MetaData> createMetaDate(MetaData value) {
        return new JAXBElement<MetaData>(_MetaDate_QNAME, MetaData.class, null, value);
    }

}
