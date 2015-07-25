package projecttoleankit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.Task;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author angel
 */
public class ProjectToLeanKit {
    public static void main(String[] args) throws MPXJException, Exception{
        
        HttpRequest hr = new HttpRequest();
        String filename = "sgd.mpp";
        ClassLoader loader = ProjectToLeanKit.class.getClassLoader();
        String path = loader.getResource("projecttoleankit/files/").toString();
        path = path.substring(6);
        System.out.println(path);
        
        JSONObject jObj = hr.sendGet("cosmodev","225790183","GetBoardIdentifiers");
        Map lanesFromHttp = JsonManager.JsonIter(jObj,"Lanes");
        System.out.println("Total de lanes: "+lanesFromHttp.size());
        
        
        MppH mp = new MppH();
        Map tareasHijas = mp.readMPP(filename);
        System.out.println("Total de tareas hijas: "+tareasHijas.size()+"\n\n");
        
        
        Map tareasALean = new HashMap();
        
        Iterator iterator = tareasHijas.entrySet().iterator();
        int k=0,j=0;
	while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            System.out.println("The key is: " + mapEntry.getKey() + ",value is :" + mapEntry.getValue()+" "+k++);
            
            if(lanesFromHttp.containsKey(mapEntry.getKey())){
                System.out.println("Found in IF statment: "+j+++"\n");
                Map lane = (Map)lanesFromHttp.get(mapEntry.getKey());
                Task task = (Task)mapEntry.getValue();
                Map LaneTask = new HashMap();
                LaneTask.put(lane.get("Id"),task);
                tareasALean.put(task.getID(),LaneTask);
            }
	}
        
        System.out.println("Tareas a Lean: "+tareasALean.size());
        
        
        
        Iterator iterator2 = tareasALean.entrySet().iterator();
        k=0;j=0;
	while (iterator2.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator2.next();
            System.out.println("The key is: " + mapEntry.getKey() + ",value is :" + mapEntry.getValue()+" "+k++);
            
            if(lanesFromHttp.containsKey(mapEntry.getKey())){
                System.out.println("Found in IF statment: "+j+++"\n");
                Map lane = (Map)lanesFromHttp.get(mapEntry.getKey());
                Task task = (Task)mapEntry.getValue();
                Map LaneTask = new HashMap();
                LaneTask.put(lane.get("Id"),task);
                tareasALean.put(task.getID(),LaneTask);
            }
	}
        
        JSONArray tareas = new JSONArray();
        
        //hr.sendPost("cosmodev","225790183","AddCards?wipOverrideComment={comment}");
    }
}
