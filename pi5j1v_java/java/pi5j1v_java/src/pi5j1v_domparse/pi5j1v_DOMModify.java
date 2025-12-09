package pi5j1v_domparse;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class pi5j1v_DOMModify {
	public static void main(String[] args) {
        try {
            // XML fájl elérési út - a játék könyvtár XML fájljához igazítva
            File xmlFile = new File("src/pi5j1v_domparse//pi5j1v.xml");
            File outputFile = new File("src/pi5j1v_domparse/Jatekkonyvtar_modified.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            System.out.println("XML módosítás kezdete...\n");
            
            // 1. MÓDOSÍTÁS: Játék ár módosítása (Cyberpunk 2077 ára)
            NodeList games = doc.getElementsByTagName("Jatek");
            for (int i = 0; i < games.getLength(); i++) {
                Element game = (Element) games.item(i);
                NodeList titles = game.getElementsByTagName("Cim");
                if (titles.getLength() > 0) {
                    String title = titles.item(0).getTextContent();
                    if (title.equals("Cyberpunk2077")) {
                        Node priceNode = game.getElementsByTagName("EurAr").item(0);
                        System.out.println("1. Cyberpunk2077 régi ára: " + priceNode.getTextContent() + " EUR");
                        priceNode.setTextContent("39.99");
                        System.out.println("   Cyberpunk2077 új ára: 39.99 EUR\n");
                    }
                }
            }

            // 2. MÓDOSÍTÁS: Felhasználó pénztárca összeg módosítása (UID="1")
            NodeList users = doc.getElementsByTagName("Felhasznalo");
            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                if (user.getAttribute("UID").equals("1")) {
                    Node walletNode = user.getElementsByTagName("Penztarca").item(0);
                    if (walletNode != null) {
                        Element wallet = (Element) walletNode;
                        Node amountNode = wallet.getElementsByTagName("Mennyiseg").item(0);
                        System.out.println("2. Felhasználó (UID=1) régi egyenlege: " + amountNode.getTextContent() + " EUR");
                        amountNode.setTextContent("200.00");
                        System.out.println("   Felhasználó (UID=1) új egyenlege: 200.00 EUR\n");
                    }
                }
            }

            // 3. MÓDOSÍTÁS: Új felhasználó beszúrása
            Element newUser = doc.createElement("Felhasznalo");
            newUser.setAttribute("UID", "3");

            Element email = doc.createElement("Email");
            email.setTextContent("new_player@email.com");
            
            Element nickname1 = doc.createElement("Becenev");
            nickname1.setTextContent("playerX");
            
            Element nickname2 = doc.createElement("Becenev");
            nickname2.setTextContent("gamerZ");
            
            Element wallet = doc.createElement("Penztarca");
            
            Element amount = doc.createElement("Mennyiseg");
            amount.setTextContent("75.50");
            
            Element currency = doc.createElement("Tipus");
            currency.setTextContent("EUR");
            
            wallet.appendChild(amount);
            wallet.appendChild(currency);
            
            newUser.appendChild(email);
            newUser.appendChild(nickname1);
            newUser.appendChild(nickname2);
            newUser.appendChild(wallet);

            doc.getDocumentElement().appendChild(newUser);
            System.out.println("3. Új felhasználó hozzáadva (UID=3)\n");

            // 4. MÓDOSÍTÁS: Felhasználó törlése (UID="2")
            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                if (user.getAttribute("UID").equals("2")) {
                    String emailText = user.getElementsByTagName("Email").item(0).getTextContent();
                    user.getParentNode().removeChild(user);
                    System.out.println("4. Felhasználó törölve (UID=2, Email: " + emailText + ")\n");
                    break;
                }
            }

            // 5. MÓDOSÍTÁS: Játék óraszám módosítása (VasarlasID="1")
            NodeList purchases = doc.getElementsByTagName("JatekTulajdon");
            for (int i = 0; i < purchases.getLength(); i++) {
                Element purchase = (Element) purchases.item(i);
                if (purchase.getAttribute("VasarlasID").equals("1")) {
                    Node hoursNode = purchase.getElementsByTagName("Oraszam").item(0);
                    System.out.println("5. JátékTulajdon (VasarlasID=1) régi óraszáma: " + hoursNode.getTextContent());
                    hoursNode.setTextContent("100");
                    System.out.println("   JátékTulajdon (VasarlasID=1) új óraszáma: 100\n");
                    break;
                }
            }

            // Módosított XML mentése
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);
            
            System.out.println("Módosítások sikeresen elmentve: " + outputFile.getPath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
