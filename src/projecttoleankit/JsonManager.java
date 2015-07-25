package projecttoleankit;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;


public class JsonManager {
    public static Map JsonIter(JSONObject JObject,String dataToShow) throws Exception{
        Map lanesFromHttp = new HashMap();
        
        //arrays values: "Lanes","ClassesOfService","LaneClassType","BoardId"
        //               "LaneType","Prorities","BoardUsers","BoardStatistics","CardTypes"
        JSONArray jsonArray1 = JObject.getJSONArray("ReplyData");
        JSONObject lanesObject = jsonArray1.getJSONObject(0);
        JSONArray jsonArray = lanesObject.getJSONArray(dataToShow);
        
        //System.out.println("LanesLength: "+jsonArray.length());
        
        for (int i = 0, size = jsonArray.length(); i < size; i++){
            JSONObject objectInArray = jsonArray.getJSONObject(i);
            //Crear el objeto Lane
            Map lane = new HashMap();
            
            String[] elementNames = JSONObject.getNames(objectInArray);
            //System.out.printf("%d ELEMENTS IN CURRENT OBJECT:\n", elementNames.length);
            for (String elementName : elementNames){
                //Setear los atributos de cada Lane
                String value = ""+objectInArray.get(elementName);
                //System.out.printf("name=%s, value=%s\n", elementName, value);
                lane.put(elementName,objectInArray.get(elementName));
            }
            
            lanesFromHttp.put(lane.get("Name"),lane);
            //System.out.println("put numero: "+i);
        }
        
        return lanesFromHttp;
    }
}
