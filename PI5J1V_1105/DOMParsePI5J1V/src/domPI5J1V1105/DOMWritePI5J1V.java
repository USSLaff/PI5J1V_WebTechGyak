package domPI5J1V1105;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DOMWritePI5J1V {

	public static void main(String[] args) throws ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		//DocumentBuilderFactory osztály példányosítása egy new DocumentBuilder jön létre.
		
		Document doc = builder.newDocument();
		

		Element root = doc.createElementNS("DOMPI5J1V", "hallgatok");
		doc.appendChild(root);
		//Létrehozunk egy gyökérelemet, és hozzáadjuk a dokumentumhoz - appendChild().
		//A gyökérelemhez három gyermekelemet fűzünk.
		root.appendChild(createUser(doc, "1", "Peter", "Nagy", "Web Developer"));
		root.appendChild(createUser(doc, "2", "Piroska", "Vigh", "Java programozo"));
		root.appendChild(createUser(doc, "3", "Ferenc", "Kiss", "Associate professor"));

		//Most következik az XML fájlba való írás.
		//A Java DOM a Transformer objektumot használja az XML-fájl létrehozásához. 
		TransformerFactory transformerFactory = TransformerFactory.newInstance(); Transformer transf = transformerFactory.newTransformer();

		//Beállítjuk a dokumentum kódolását és behúzását. 
		transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); 
		transf.setOutputProperty(OutputKeys.INDENT, "yes"); 
		transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

		//Egy bemeneti forrás létrehozása egy DOM csomóponttal. Ez DOMSource tartalmazza a DOM fát. 
		DOMSource source = new DOMSource(doc);

		File myFile = new File("hallgato1PI5J1V.xml");
		//Írjunk egy konzolba és egy fájlba.
		//StreamResult transzformációs eredménye.
		StreamResult console = new StreamResult (System.out);
		StreamResult file = new StreamResult(myFile);		
	
		//a transform metódus átalakítja a source objektumot a StreamResult objektummá. //Az XML-forrásokat a stream eredményekhez írjuk.
		transf.transform(source, console);
		transf.transform(source, file);
	}
	
	//A createUser() metódusban új felhasználói elemet hozunk létre - createElement().
	private static Node createUser (Document doc, String id, String firstName, String lastName, String profession) {
		Element user = doc.createElement("hallgato");
		//A setAttribute() metódussal beállítjuk az elem attribútumát 
		user.setAttribute("id", id);
		user.appendChild(createUserElement (doc, "keresztnev", firstName)); 
		user.appendChild(createUserElement (doc, "vezeteknev", lastName)); user.appendChild(createUserElement(doc, "foglalkozas", profession));
	
		return user;
	}
	
	//Az elem hozzáadódik a szülőhoz a appendChild() metódussal és szöveges csomópont jön létre - a createTextNode(). 
	private static Node createUserElement (Document doc, String name, String value) {
			Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;

	
	}
}









