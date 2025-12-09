package pi5j1v_domparse;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class pi5j1v_DOMQuery {
	public static void main(String[] args) {
        try {
            // XML fájl elérési út - a játék könyvtár XML fájljához igazítva
            File xmlFile = new File("src/pi5j1v_domparse/pi5j1v.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("=== JÁTÉK KÖNYVTÁR LEKÉRDEZÉSEK ===\n");

            // 1. LEKÉRDEZÉS: Összes akció (MufajID="1") műfajú játék
            System.out.println("1. LEKÉRDEZÉS: Összes 'Akció' (MufajID=1) műfajú játék");
            NodeList gameMufajList = doc.getElementsByTagName("JatekMufaj");
            for (int i = 0; i < gameMufajList.getLength(); i++) {
                Element gameMufaj = (Element) gameMufajList.item(i);
                String mufajID = gameMufaj.getElementsByTagName("MufajID").item(0).getTextContent();
                
                if (mufajID.equals("1")) {
                    String jatekID = gameMufaj.getElementsByTagName("JatekID").item(0).getTextContent();
                    
                    // Keressük meg a játékot a JatekID alapján
                    NodeList gameList = doc.getElementsByTagName("Jatek");
                    for (int j = 0; j < gameList.getLength(); j++) {
                        Element game = (Element) gameList.item(j);
                        if (game.getAttribute("JatekID").equals(jatekID)) {
                            String cim = game.getElementsByTagName("Cim").item(0).getTextContent();
                            String keszito = game.getElementsByTagName("Keszito").item(0).getTextContent();
                            String ar = game.getElementsByTagName("EurAr").item(0).getTextContent();
                            System.out.println("  • " + cim + " (" + keszito + ") - " + ar + " EUR");
                        }
                    }
                }
            }

            // 2. LEKÉRDEZÉS: Egy adott felhasználó (UID="1") játékai
            System.out.println("\n2. LEKÉRDEZÉS: Felhasználó (UID=1) játékai és óraszámai");
            NodeList ownershipList = doc.getElementsByTagName("JatekTulajdon");
            for (int i = 0; i < ownershipList.getLength(); i++) {
                Element ownership = (Element) ownershipList.item(i);
                String felhasznaloID = ownership.getElementsByTagName("FelhasznaloID").item(0).getTextContent();
                
                if (felhasznaloID.equals("1")) {
                    String jatekID = ownership.getElementsByTagName("JatekID").item(0).getTextContent();
                    String oraszam = ownership.getElementsByTagName("Oraszam").item(0).getTextContent();
                    
                    // Keressük meg a játék nevét
                    NodeList gameList = doc.getElementsByTagName("Jatek");
                    for (int j = 0; j < gameList.getLength(); j++) {
                        Element game = (Element) gameList.item(j);
                        if (game.getAttribute("JatekID").equals(jatekID)) {
                            String cim = game.getElementsByTagName("Cim").item(0).getTextContent();
                            System.out.println("  • " + cim + " - " + oraszam + " óra");
                        }
                    }
                }
            }

            // 3. LEKÉRDEZÉS: Egy adott játék (JatekID="1") teljesítményei
            System.out.println("\n3. LEKÉRDEZÉS: 'Cyberpunk2077' (JatekID=1) teljesítményei");
            NodeList achievementList = doc.getElementsByTagName("Teljesitmeny");
            boolean found = false;
            for (int i = 0; i < achievementList.getLength(); i++) {
                Element achievement = (Element) achievementList.item(i);
                String jatekID = achievement.getElementsByTagName("JatekID").item(0).getTextContent();
                
                if (jatekID.equals("1")) {
                    String cim = achievement.getElementsByTagName("Cim").item(0).getTextContent();
                    String leiras = achievement.getElementsByTagName("Leiras").item(0).getTextContent();
                    System.out.println("  • " + cim + ": " + leiras);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("  Nincs találat.");
            }

            // 4. LEKÉRDEZÉS: Felhasználók beceneveinek listázása
            System.out.println("\n4. LEKÉRDEZÉS: Összes felhasználó és becenevei");
            NodeList userList = doc.getElementsByTagName("Felhasznalo");
            for (int i = 0; i < userList.getLength(); i++) {
                Element user = (Element) userList.item(i);
                String email = user.getElementsByTagName("Email").item(0).getTextContent();
                String uid = user.getAttribute("UID");
                
                System.out.println("  Felhasználó UID=" + uid + " (" + email + "):");
                
                NodeList nicknames = user.getElementsByTagName("Becenev");
                if (nicknames.getLength() > 0) {
                    for (int j = 0; j < nicknames.getLength(); j++) {
                        System.out.println("    • " + nicknames.item(j).getTextContent());
                    }
                } else {
                    System.out.println("    (nincs becenév)");
                }
            }

            // 5. LEKÉRDEZÉS: Műfajok statisztikája - hány játék tartozik minden műfajhoz
            System.out.println("\n5. LEKÉRDEZÉS: Műfaj statisztika (játékok száma műfajonként)");
            NodeList mufajList = doc.getElementsByTagName("Mufaj");
            for (int i = 0; i < mufajList.getLength(); i++) {
                Element mufaj = (Element) mufajList.item(i);
                String mufajID = mufaj.getAttribute("MufajID");
                String mufajNev = mufaj.getTextContent().trim();
                
                int count = 0;
                for (int j = 0; j < gameMufajList.getLength(); j++) {
                    Element gameMufaj = (Element) gameMufajList.item(j);
                    if (gameMufaj.getElementsByTagName("MufajID").item(0).getTextContent().equals(mufajID)) {
                        count++;
                    }
                }
                
                System.out.println("  • " + mufajNev + " (" + mufajID + "): " + count + " játék");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
