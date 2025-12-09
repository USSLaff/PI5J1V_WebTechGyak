package pi5j1v_domparse;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class pi5j1v_DOMRead {
	public static void main(String[] args) {
        try {
            // XML fájl elérési út - a játék könyvtár XML fájljához igazítva
            File xmlFile = new File("src/pi5j1v_domparse/pi5j1v.xml"); // vagy Jatekkonyvtar.xml
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            File outputFile = new File("src/pi5j1v_domparse/read_log.txt");
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                
                String rootNodeName = "Gyökér elem: " + doc.getDocumentElement().getNodeName();
                printAndLog(rootNodeName, writer);
                printAndLog("Játék Könyvtár XML beolvasása", writer);
                printAndLog("=".repeat(50), writer);

                // 1. MŰFAJOK BEOLVASÁSA
                printAndLog("\n1. MŰFAJOK", writer);
                printAndLog("-".repeat(30), writer);
                NodeList mufajList = doc.getElementsByTagName("Mufaj");
                for (int i = 0; i < mufajList.getLength(); i++) {
                    Node nNode = mufajList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String mufajID = elem.getAttribute("MufajID");
                        String mufajNev = elem.getTextContent().trim();
                        printAndLog("Műfaj [" + mufajID + "]: " + mufajNev, writer);
                    }
                }

                // 2. JÁTÉKOK BEOLVASÁSA
                printAndLog("\n\n2. JÁTÉKOK", writer);
                printAndLog("-".repeat(30), writer);
                NodeList jatekList = doc.getElementsByTagName("Jatek");
                for (int i = 0; i < jatekList.getLength(); i++) {
                    Node nNode = jatekList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String jatekID = elem.getAttribute("JatekID");
                        String cim = elem.getElementsByTagName("Cim").item(0).getTextContent();
                        String keszito = elem.getElementsByTagName("Keszito").item(0).getTextContent();
                        String ar = elem.getElementsByTagName("EurAr").item(0).getTextContent();
                        
                        printAndLog("\nJáték [" + jatekID + "]", writer);
                        printAndLog("  Cím: " + cim, writer);
                        printAndLog("  Készítő: " + keszito, writer);
                        printAndLog("  Ár: " + ar + " EUR", writer);
                        
                        // Játék műfajainak keresése
                        printAndLog("  Műfajok:", writer);
                        NodeList jatekMufajList = doc.getElementsByTagName("JatekMufaj");
                        for (int j = 0; j < jatekMufajList.getLength(); j++) {
                            Element jatekMufajElem = (Element) jatekMufajList.item(j);
                            String jmJatekID = jatekMufajElem.getElementsByTagName("JatekID").item(0).getTextContent();
                            
                            if (jmJatekID.equals(jatekID)) {
                                String mufajID = jatekMufajElem.getElementsByTagName("MufajID").item(0).getTextContent();
                                // Műfaj név keresése
                                for (int k = 0; k < mufajList.getLength(); k++) {
                                    Element mufajElem = (Element) mufajList.item(k);
                                    if (mufajElem.getAttribute("MufajID").equals(mufajID)) {
                                        printAndLog("    • " + mufajElem.getTextContent().trim(), writer);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                // 3. FELHASZNÁLÓK BEOLVASÁSA
                printAndLog("\n\n3. FELHASZNÁLÓK", writer);
                printAndLog("-".repeat(30), writer);
                NodeList felhasznaloList = doc.getElementsByTagName("Felhasznalo");
                for (int i = 0; i < felhasznaloList.getLength(); i++) {
                    Node nNode = felhasznaloList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String uid = elem.getAttribute("UID");
                        String email = elem.getElementsByTagName("Email").item(0).getTextContent();
                        
                        printAndLog("\nFelhasználó [" + uid + "]", writer);
                        printAndLog("  Email: " + email, writer);
                        
                        // Becenevek
                        NodeList becenevList = elem.getElementsByTagName("Becenev");
                        if (becenevList.getLength() > 0) {
                            printAndLog("  Becenevek:", writer);
                            for (int k = 0; k < becenevList.getLength(); k++) {
                                printAndLog("    • " + becenevList.item(k).getTextContent(), writer);
                            }
                        }
                        
                        // Pénztárca
                        Element penztarcaElem = (Element) elem.getElementsByTagName("Penztarca").item(0);
                        if (penztarcaElem != null) {
                            String mennyiseg = penztarcaElem.getElementsByTagName("Mennyiseg").item(0).getTextContent();
                            String tipus = penztarcaElem.getElementsByTagName("Tipus").item(0).getTextContent();
                            printAndLog("  Pénztárca: " + mennyiseg + " " + tipus, writer);
                        }
                    }
                }

                // 4. JÁTÉKTULAJDON BEOLVASÁSA
                printAndLog("\n\n4. JÁTÉKTULAJDON", writer);
                printAndLog("-".repeat(30), writer);
                NodeList tulajdonList = doc.getElementsByTagName("JatekTulajdon");
                for (int i = 0; i < tulajdonList.getLength(); i++) {
                    Node nNode = tulajdonList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String vasarlasID = elem.getAttribute("VasarlasID");
                        String jatekID = elem.getElementsByTagName("JatekID").item(0).getTextContent();
                        String felhasznaloID = elem.getElementsByTagName("FelhasznaloID").item(0).getTextContent();
                        String oraszam = elem.getElementsByTagName("Oraszam").item(0).getTextContent();
                        String vasarlasDatum = elem.getElementsByTagName("VasarlasDatum").item(0).getTextContent();
                        
                        printAndLog("\nVásárlás [" + vasarlasID + "]", writer);
                        printAndLog("  JátékID: " + jatekID, writer);
                        printAndLog("  FelhasználóID: " + felhasznaloID, writer);
                        printAndLog("  Összes játszott óra: " + oraszam, writer);
                        printAndLog("  Vásárlás dátuma: " + vasarlasDatum, writer);
                    }
                }

                // 5. TELJESÍTMÉNYEK BEOLVASÁSA
                printAndLog("\n\n5. TELJESÍTMÉNYEK", writer);
                printAndLog("-".repeat(30), writer);
                NodeList teljesitmenyList = doc.getElementsByTagName("Teljesitmeny");
                for (int i = 0; i < teljesitmenyList.getLength(); i++) {
                    Node nNode = teljesitmenyList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String teljesitmenyID = elem.getAttribute("TeljesitmenyID");
                        String cim = elem.getElementsByTagName("Cim").item(0).getTextContent();
                        String leiras = elem.getElementsByTagName("Leiras").item(0).getTextContent();
                        String jatekID = elem.getElementsByTagName("JatekID").item(0).getTextContent();
                        
                        printAndLog("\nTeljesítmény [" + teljesitmenyID + "]", writer);
                        printAndLog("  Cím: " + cim, writer);
                        printAndLog("  Leírás: " + leiras, writer);
                        printAndLog("  JátékID: " + jatekID, writer);
                    }
                }

                // 6. ELÉRT TELJESÍTMÉNYEK BEOLVASÁSA
                printAndLog("\n\n6. ELÉRT TELJESÍTMÉNYEK", writer);
                printAndLog("-".repeat(30), writer);
                NodeList elertTeljesitmenyList = doc.getElementsByTagName("ElertTeljesitmeny");
                for (int i = 0; i < elertTeljesitmenyList.getLength(); i++) {
                    Node nNode = elertTeljesitmenyList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) nNode;
                        String felhasznaloID = elem.getElementsByTagName("FelhasznaloID").item(0).getTextContent();
                        String jatekID = elem.getElementsByTagName("JatekID").item(0).getTextContent();
                        
                        printAndLog("Felhasználó [" + felhasznaloID + "] elérte a játék [" + jatekID + "] teljesítményeit", writer);
                    }
                }
                
                printAndLog("\n" + "=".repeat(50), writer);
                printAndLog("[INFO] Kiírás kész: src/hu/domparse/pi5j1v/read_log.txt", writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printAndLog(String text, PrintWriter writer) {
        System.out.println(text);
        writer.println(text);
    }
}
