package projecttoleankit;

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonManager {
    public static void JsonIter(JSONObject JObject,String array) throws Exception{
        //arrays values: "Lanes","ClassesOfService","LaneClassType","BoardId"
        //               "LaneType","Prorities","BoardUsers","BoardStatistics","CardTypes"
        JSONArray jsonArray1 = JObject.getJSONArray("ReplyData");
        JSONObject lanesObject = jsonArray1.getJSONObject(0);
        JSONArray jsonArray = lanesObject.getJSONArray(array);
        
        System.out.println("LanesLength: "+jsonArray.length());
        
        for (int i = 0, size = jsonArray.length(); i < size; i++){
            JSONObject objectInArray = jsonArray.getJSONObject(i);

            String[] elementNames = JSONObject.getNames(objectInArray);
            System.out.printf("%d ELEMENTS IN CURRENT OBJECT:\n", elementNames.length);
            for (String elementName : elementNames){
                String value = ""+objectInArray.get(elementName);
                System.out.printf("name=%s, value=%s\n", elementName, value);
            }
            System.out.println();
        }
    }
}
