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
        String domain = "cosmodev";
        String boardId = "225790183";
        String filename = "sgd.mpp";
        JSONArray cards = new JSONArray();
        
        ClassLoader loader = ProjectToLeanKit.class.getClassLoader();
        String path = loader.getResource("projecttoleankit/files/").toString();
        path = path.substring(6);
        System.out.println(path);
        
        MppH mp = new MppH();
        Map tareasHijas = mp.readMPP(filename);
        System.out.println("Total de tareas hijas: "+tareasHijas.size()+"\n\n");
        
        
        HttpRequest hr = new HttpRequest();
        JSONObject jObj = hr.sendGet(domain,boardId,"GetBoardIdentifiers");
        Map lanesFromHttp = JsonManager.JsonIter(jObj,"Lanes");
        System.out.println("Total de lanes: "+lanesFromHttp.size());
        
        
        Map tareasALean = new HashMap();
        Iterator iterator = tareasHijas.entrySet().iterator();
        int k=0,j=0;
	while (iterator.hasNext()) {
            JSONObject card = new JSONObject();
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            System.out.println("The key is: " + mapEntry.getKey() + ",value is :" + mapEntry.getValue()+" "+k++);
            
            if(lanesFromHttp.containsKey(mapEntry.getKey())){
                System.out.println("Found in IF statment: "+j+++"\n");
                Map lane = (Map)lanesFromHttp.get(mapEntry.getKey());
                Task task = (Task)mapEntry.getValue();
                Map LaneTask = new HashMap();
                LaneTask.put(lane.get("Id"),task);
                tareasALean.put(task.getID(),LaneTask);
                
                card.put("LaneId",lane.get("Id"));
                card.put("Title",task.getName());
                        
                cards.put(card);
            }
	}
        
        System.out.println("Tareas a Lean: "+tareasALean.size());
        
        System.out.println("Cards JSON: "+cards.toString());
        //hr.sendPost("cosmodev","225790183","AddCards?wipOverrideComment={comment}",cards);
    }
}
