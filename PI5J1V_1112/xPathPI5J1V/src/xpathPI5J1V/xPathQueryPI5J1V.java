package xpathPI5J1V;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import javax.xml.xpath.*;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathQueryPI5J1V {
	
	
	public static void main(String[] args)  {
		try {
			//File xmlFile = new File("student.xml");
			
			//DocumentBuilder létrehozása
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			Document document = documentBuilder.parse("hallgato.xml");
			
			//Az XML dokumentum normalizálása, azt jelenti, hoyg eltávolítjuk a felesleges whitespace karaktereket.
			// és egyéb normalizálási lépéseket hajtunk végre, hogy egységes legyen a dokumentum szerkezete.
			document.getDocumentElement().normalize();
			
			//az XPath készítése
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			//meg k ell adni az elérési út kifejezést és a csomópont listát:
			//String pi5j1v = "class";
			//String pi5j1v = "class/student";
			String pi5j1v = "class/student[@id=02]";
			
			
			
			
			
			//Készítsen egy listát, majd a xPath kifejezést le kell fordítani és ki kell értékelni.
			NodeList neptunKod = (NodeList)xPath.compile(pi5j1v).evaluate(document,XPathConstants.NODESET);
			
			//A for ciklus segítségével a NodeList csomópontjain végig kell iterálni.
			for (int i=0; i<neptunKod.getLength();i++) {
				
				//KIvesszük a NodeList-ből az aktuális Node elemet.
				Node node = neptunKod.item(i);
				
				System.out.println("\nAktuális elem: "+node.getNodeName());
				
				
				
				//Meg kell vizsgálni a csomópontot, tesztelni kell a subelemet
				if(node.getNodeType()==Node.ELEMENT_NODE && node.getNodeName().equals("hallgato")) {
					
					//A Node elemet Elementté konvertáljuk, hogy hozzáférhessünk az elem specifikus metódusaihoz.
					Element element = (Element)node;
					
					//az id attribútumot ad vissza
					System.out.println("Hallgató ID: "+element.getAttribute("id"));
					
					//keresztnevet ad vissza
					System.out.println("Keresztnév: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
					
					//vezetéknevet ad vissza
					System.out.println("Vezetéknév: "+element.getElementsByTagName("vezeteknev").item(0).getTextContent());
					
					//becenevet ad vissza
					System.out.println("Becenév: "+ element.getElementsByTagName("bevenev").item(0).getTextContent());
					
					//kort ad vissza
					System.out.println("Kor: "+element.getElementsByTagName("kor").item(0).getTextContent());
					
					
				}
				
			}
			
			
			
		}
		catch(ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch(SAXException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(XPathExpressionException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
