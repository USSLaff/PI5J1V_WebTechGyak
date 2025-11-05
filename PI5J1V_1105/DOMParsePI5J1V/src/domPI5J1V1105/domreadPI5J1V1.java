package domPI5J1V1105;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class domreadPI5J1V1 {

    public static void main(String argv[]) throws SAXException,
            IOException, ParserConfigurationException {

        // XML file megnyitása
        File xmlFile = new File("hallgatoPI5J1V.xml");

        // példányosítás a DocumentBuilderFactory osztályt a statikus newInstance()
        // metódussal. DocumentBuilderFactory factory
        // DocumentBuilderFactory.newInstance();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        // A DocumentBuilderFactory-ból megkapjuk a DocumentBuildert.
        // A DocumentBuilder tartalmazza az API-t a DOM-dokumentum példányok
        // XML-dokumentumból való beszerzéséhez.

        // DOM fa előállítása
        Document neptunkod = dBuilder.parse(xmlFile);
        // A parse() metódus elemzi az XML fájlt a Document.

        neptunkod.getDocumentElement().normalize();
        // A dokumentum normalizálása segít a helyes eredmények elérésében.
        // eltávolítja az üres szövegcsomópontokat, és összekapcsolja a szomszédos
        // szövegcsomópontokat.

        System.out.println("Gyökér elem: " + neptunkod.getDocumentElement().getNodeName());
        // Kiíratjuk a dokumentum gyökérelemét

        // a fa megadott névvel (hallgato) rendelkező csomópontjainak összegyűjtése
        // listába.
        // A getElementsByTagName() metódus segítségével megkapjuk a hallgato elem
        // NodeListjét a dokumentumban. NodeList nlist
        // neptunkod.getElementsByTagName("hallgato"); //gyerekelemek mentése listába
        NodeList nList = neptunkod.getElementsByTagName("hallgato");

        for (int i = 0; i < nList.getLength(); i++) {
            // A listán for ciklussal megyünk végig.

            // Lekérjük a lista aktuális elemét
            Node nNode = nList.item(i);
            System.out.println("\nAktuális elem: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                // Elementté konvertáljuk az aktuális elemet
                Element elem = (Element) nNode;

                String hid = elem.getAttribute("id");
                // Az elem attribútumot a getAttridbute() segítségével kapjuk meg.

                // lekérjük az aktuális elem gyerekelemeit és annak tartalmát
                Node node1 = elem.getElementsByTagName("keresztnev").item(0);
                String kname = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("vezeteknev").item(0);
                String vname = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("foglalkozas").item(0);
                String fname = node3.getTextContent();
                // megkapjuk a hallgato elem járom gyerekelemének tartalmát.

                // formázva kiírjuk:
                System.out.println("Hallgató id: " + hid);
                System.out.println("Keresztnév: " + kname);
                System.out.println("Vezetéknév: " + vname);
                System.out.println("Foglalkozás: " + fname);

            }
        }
    }
}