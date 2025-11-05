package domPI5J1V1105;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMModify1PI5J1V {

	public static void main (String argv[])	 {
		
	try {
		
	
		File inputFile = new File("PI5J1V_orarend.xml");
	
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
		Document doc = docBuilder.parse(inputFile);
		
		Node hallgat = doc.getElementsByTagName("ora").item(0);
	
		// hallgat attributumának lekérése
		NamedNodeMap attr = hallgat.getAttributes();
		Node nodeAttr = attr.getNamedItem("id"); 
		nodeAttr.setTextContent("01");
		// loop the hallgat child node 
		NodeList list = hallgat.getChildNodes();
	

		for (int temp = 0; temp < list.getLength(); temp++) { 
			Node node = list.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) { 
				Element eElement = (Element) node;

				if(eElement.getAttribute("id").equals("02")) {
					System.out.println("haeh");
				}
					
		
			
				/*if ("keresztnev".equals(eElement.getNodeName())) { 
					if("Pál".equals (eElement.getTextContent())) {
						Element newElement = doc.createElement("oraado");
						newElement.setTextContent("valaki");
						eElement.appendChild(newElement);
						
						eElement.setTextContent("Olivia");
					}
				}
		
				if ("vezeteknev".equals(eElement.getNodeName())) { 
					if("Kiss".equals(eElement.getTextContent())) {
						eElement.setTextContent("Erős");
					}
				}*/
			}
		}

		// Tartalom konzolra írása
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		//Ez DOMSource tartalmazza a DOM fát. Egy bemeneti forrás létrehozása egy DOM csomóponttal. 
		DOMSource source = new DOMSource (doc);
		System.out.println("---Módosított fájl---");
		StreamResult consoleResult = new StreamResult (System.out); 
		transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}
}