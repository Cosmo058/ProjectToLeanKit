package projecttoleankit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;


public class JsonManager {
    public static Map getLanes(JSONObject boardIdentifiersInJSONFormat) throws Exception{
        Map lanesFromHttp = new HashMap();
        
        JSONArray jsonArray1 = boardIdentifiersInJSONFormat.getJSONArray("ReplyData");
        JSONObject lanesObject = jsonArray1.getJSONObject(0);
        JSONArray jsonArray = lanesObject.getJSONArray("Lanes");
        
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
    
    public static Map getBoardsId(JSONObject boardsInJSONFormat) throws Exception{
        Map boards = new HashMap();
        
        JSONArray ja = (JSONArray) boardsInJSONFormat.get("ReplyData");
        ja = ja.getJSONArray(0);
        //System.out.println(ja.toString(3));
                
        for (int i = 0, size = ja.length(); i < size; i++){
            JSONObject objectInArray = ja.getJSONObject(i);
            
            Map board = new HashMap();
            String[] elementNames = JSONObject.getNames(objectInArray);
            for (String elementName : elementNames){
                board.put(elementName,objectInArray.get(elementName));
            }
            
            boards.put(i,board);
        }
        
        return boards;
    }
    
    public static Map distincGetLanes(JSONObject boardIdentifiersInJSONFormat) throws Exception{
        Map lanesFromHttp = new HashMap();
        
        JSONArray jsonArray1 = boardIdentifiersInJSONFormat.getJSONArray("ReplyData");
        JSONObject lanesObject = jsonArray1.getJSONObject(0);
        JSONArray jsonArray = lanesObject.getJSONArray("Lanes");
        
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
            
            String laneName = (String)lane.get("Name");
            String laneSuperior = "";
            String ultimoNivel ="a";
            String penultimoNivel ="b";
            
            if (laneName.contains(":")){
                ultimoNivel = laneName.substring(laneName.lastIndexOf(':')+1);
                laneSuperior = laneName.substring(0,laneName.lastIndexOf(":"));
                if(laneSuperior.contains(":"))
                    penultimoNivel = laneSuperior.substring(laneSuperior.lastIndexOf(':')+1);
                else
                    penultimoNivel = laneSuperior;
                
                //System.out.println("LaneName: "+laneName+" LaneSup: "+laneSuperior+" ultiNiv: "+ultimoNivel+" penNiv:"+penultimoNivel);
            }
            
            if(ultimoNivel.equals(penultimoNivel)){
                //Es un lane anidado
                lanesFromHttp.put(laneSuperior,lane);
                //System.out.println("Lane repetido");
            }else
                lanesFromHttp.put(lane.get("Name"),lane);
        }
        
        return lanesFromHttp;
    }
    
    public static Map getCardTypes(JSONObject boardIdentifiersInJSONFormat) throws Exception{
        Map lanesFromHttp = new HashMap();
        
        //arrays values: "Lanes","ClassesOfService","LaneClassType","BoardId"
        //               "LaneType","Prorities","BoardUsers","BoardStatistics","CardTypes"
        JSONArray jsonArray1 = boardIdentifiersInJSONFormat.getJSONArray("ReplyData");
        JSONObject lanesObject = jsonArray1.getJSONObject(0);
        JSONArray jsonArray = lanesObject.getJSONArray("CardTypes");
        
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
    
    public static Map getPriorities(JSONObject boardIdentifiersInJSONFormat) throws Exception{
        Map lanesFromHttp = new HashMap();
        
        JSONArray jsonArray1 = boardIdentifiersInJSONFormat.getJSONArray("ReplyData");
        JSONObject lanesObject = jsonArray1.getJSONObject(0);
        JSONArray jsonArray = lanesObject.getJSONArray("Priorities");
        
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
    
    public static String JSONPrettyPrint(String JSONString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(JSONString);
        return gson.toJson(je);
    }
}
