package projecttoleankit;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.Task;
import org.json.JSONArray;
import org.json.JSONObject;


public class ProjectToLeanKit {
    public static void main(String[] args) throws MPXJException, Exception{
        String domain = "cosmodev";
        String boardId = "226201132"; // Test3
        String filename = "sgd.mpp";
        JSONArray cards = new JSONArray();
        
        ClassLoader loader = ProjectToLeanKit.class.getClassLoader();
        String path = loader.getResource("projecttoleankit/files/").toString();
        path = path.substring(6);
        System.out.println(path);
        
        MppH mp = new MppH();
        Map tareasHijas = mp.readMPP(filename);
        System.out.println("Total de tareas 'hojas': "+tareasHijas.size()+"\n\n");
        
        
        HttpRequest hr = new HttpRequest();
        JSONObject boardIdentifiers = hr.sendGet(domain,boardId,"GetBoardIdentifiers");
        Map lanesFromHttp = JsonManager.distincGetLanes(boardIdentifiers);
        System.out.println("Total de lanes 'hojas': "+lanesFromHttp.size());
        
        Map cardTypesFromHttp = JsonManager.getCardTypes(boardIdentifiers);
        //System.out.println("CardTypes: "+new PrettyPrintingMap<String, String>(cardTypesFromHttp));
        Map CardType =(Map)cardTypesFromHttp.get("Other Work");
        int OtherWorkID = Integer.parseInt(CardType.get("Id").toString());
        //System.out.println("OtherWorkID: "+OtherWorkID);
        
        Map prioritiesFromHttp = JsonManager.getPriorities(boardIdentifiers);
        Map priority = (Map)prioritiesFromHttp.get("Normal");
        int NormalPriorityID = Integer.parseInt(priority.get("Id").toString());
        
        
        Map tareasALean = new HashMap();
        List list;
        Iterator iterator = tareasHijas.entrySet().iterator();
        int k=0,j=0;
	while (iterator.hasNext()) {
            JSONObject card = new JSONObject();
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            list = (List) tareasHijas.get(mapEntry.getKey()); 
            //System.out.println("The key is: " + mapEntry.getKey() + ",value is :" + mapEntry.getValue()+" "+k++);
            
            for (int l = 0; l < list.size(); l++){
                if(lanesFromHttp.containsKey(mapEntry.getKey())){
                    //System.out.println("Found in IF statment: "+j+++"\n");
                    Map lane = (Map)lanesFromHttp.get(mapEntry.getKey());
                    Task task = (Task)list.get(l);
                    Map LaneTask = new HashMap();
                    LaneTask.put(lane.get("Id"),task);
                    tareasALean.put(task.getID(),LaneTask);

                    SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyy");
                    //System.out.format("Task: %-47s    ID:%-3s    ParentTask:%-75s    TaskType:%-15s\n",task.getName(),task.getID(),task.getParentTask(),ft.format(task.getStart()));

                    card.put("LaneId",lane.get("Id"));
                    card.put("Title",task.getName());
                    card.put("TypeID",OtherWorkID);
                    card.put("Priority",NormalPriorityID);
                    card.put("IsBlocked","false");
                    card.put("BlockReason","null");
                    card.put("StartDate",ft.format(task.getStart()).toString());
                    card.put("DueDate",ft.format(task.getFinish()).toString());
                    
                    
                    cards.put(card);
                }
            }
	}
        
        System.out.println("Tareas a Lean: "+tareasALean.size());
        
        System.out.println("Cards JSON: "+cards.toString());
        
        hr.addCards(domain,boardId,cards);
    }
}
