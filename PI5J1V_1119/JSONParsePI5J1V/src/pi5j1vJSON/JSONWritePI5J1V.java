package pi5j1vJSON;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONWritePI5J1V {

	
	public static void main (String[] args) {
		
		JSONParser JSONparser = new JSONParser();
		
		try (FileReader reader = new FileReader("orarendPI5J1V.json")){
			
			JSONObject JSONObject = new JSONObject();
			JSONArray oraArray = new JSONArray();
			
			oraArray.add(createOra(new String[] {"Szoftvertesztelés","Dr. Hornyák Olivér","Mérnökinformatikus","hétfő","10","12","Inf 103"}));
			oraArray.add(createOra(new String[] {"Szoftvertesztelés","Dr. Hornyák Olivér","Mérnökinformatikus","hétfő","10","12","Inf 103"}));
			oraArray.add(createOra(new String[] {"Web technológiák 1","Agárdi Anita","Mérnökinformatikus","hétfő","10","12","Inf 103"}));
			oraArray.add(createOra(new String[] {"Web technológiák 1","Agárdi Anita","Mérnökinformatikus","kedd","10","12","Inf 103"}));
			oraArray.add(createOra(new String[] {"Mesterséges Intelligencia","Kunné Dr. Tamás Judit","Mérnökinformatikus","kedd","10","12","Inf 103"}));
			oraArray.add(createOra(new String[] {"Adatkezelés XML-ben","Dr. Bednarik László","Mérnökinformatikus","kedd","10","12","Inf 103"}));
			oraArray.add(createOra(new String[] {"Webes alkalmazások (Java)","Selmeci Viktor","Mérnökinformatikus","hétfő","10","12","Inf 103"}));
			oraArray.add(createOra(new String[] {"Webes alkalmazások (Java)","Selmeci Viktor","Mérnökinformatikus","hétfő","10","12","Inf 103"}));
			oraArray.add(createOra(new String[] {"Adatkezelés XML-ben","Dr. Bednarik László","Mérnökinformatikus","hétfő","10","12","Inf 103"}));
			oraArray.add(createOra(new String[] {"Mesterséges Intelligencia","Fazekas Levente","Mérnökinformatikus","hétfő","10","12","Inf 103"}));
			
			for(int i=0; i<oraArray.size();i++) {
				JSONObject localObject = (JSONObject) oraArray.get(i);
				
				System.out.println("\n Óra");
				System.out.println("	Tárgy: "+localObject.get("targy"));
				System.out.println("	Oktató: "+localObject.get("oktato"));
				System.out.println("	Szak: "+localObject.get("szak"));
				System.out.println("	Időpont: ");
				
				JSONObject time = (JSONObject)localObject.get("idopont");
				System.out.println("		Nap: "+time.get("nap"));
				System.out.println("		Tól: "+time.get("tol"));
				System.out.println("		Ig: "+time.get("ig"));
				
				System.out.println("	Helyszín: "+localObject.get("helyszin"));
			
			
			}
			
			JSONObject oraObject = new JSONObject();
			oraObject.put("ora", oraArray);
			JSONObject.put("PI5J1V_orarend", oraObject);
			
			
			FileWriter file = new FileWriter("orarendPI5J1V1.json");
			file.write(JSONObject.toString());
			file.close();
			
			
			
		}
		catch(Exception err) {
			err.printStackTrace();
		}
		
	}
	
	static JSONObject createOra(String[] data) {
		JSONObject localObject = new JSONObject();
		
		localObject.put("targy", data[0]);
		localObject.put("oktato", data[1]);
		localObject.put("szak", data[2]);
		
		JSONObject timeObject = new JSONObject();
		timeObject.put("nap", data[3]);
		timeObject.put("tol", data[4]);
		timeObject.put("ig", data[5]);
		localObject.put("idopont", timeObject);
		
		localObject.put("helyszin",data[6]);
		
		return localObject;
	}
}







