package projecttoleankit;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.Task;
import org.apache.commons.collections4.map.MultiValueMap;
import org.json.JSONArray;
import org.json.JSONObject;


public class ProjectToLeanKit {
    
    //Uso del programa:
    //  java.jar -jar ProjectToLeanKit.jar <fileName.extension>
    //
    //El archivo debe estar en el mismo directorio que el archivo jar
    public static void main(String[] args) throws MPXJException, Exception{
        boolean debug = true;
        Scanner keyboard = new Scanner(System.in);
        
        String domain = "cosmodev";
        String user = "cosmodevtest@gmail.com";
        String password = "CosmoTest";
        String boardId = "226196690"; // Test4
        String filename = "sgd.mpp";
        
        if(args.length!=0){
            debug = false;
            filename = args[0];
        }
        
        System.out.println("Ingrese el dominio de su cuenta");
        System.out.println("[Si la URL de su cuenta es <https://myaccount.leankit.com> su dominio sera \"myaccount\" (sin las comillas)]");
        System.out.print("Dominio: ");
        //domain = keyboard.nextLine();
        System.out.println("");
        
        System.out.print("Ingrese su usuario de LeanKit: ");
        //user = keyboard.nextLine();
        System.out.println("");
        
        System.out.print("Ingrese su contrasenia de LeanKit:");
        //password = keyboard.nextLine();
        System.out.println("");
        
        HttpRequest hr = new HttpRequest(user,password);
        
        
        JSONObject boardsInJSONFormat = hr.getBoards(domain);
        Map boards = JsonManager.getBoardsId(boardsInJSONFormat);
        
        System.out.println("Seleccione el numero del tablero(board) donde subir las 'cards': ");
        for(int contador = 0; contador<boards.size();contador++){
            Map temp = (Map)boards.get(contador);
            System.out.println("\t["+(contador+1)+"] Nombre: "+temp.get("Title"));
        }
        
        int indice = keyboard.nextInt() - 1;
        Map board = (Map)boards.get(indice);
        boardId = board.get("Id").toString();
        
        
        String path = "";
        if(debug){
            ClassLoader loader = ProjectToLeanKit.class.getClassLoader();
            path = loader.getResource("projecttoleankit/files/").toString();
            path = path.substring(6);
            //System.out.println("PATH:"+path);
        }else{
            path = ProjectToLeanKit.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            path = path.substring(0,path.lastIndexOf("/")+1);
        }
        
        MppH mp = new MppH();
        MultiValueMap tareasHijas = mp.readMPP(path,filename);
        //System.out.println("Total de tareas 'hojas': "+tareasHijas.size()+"\n\n");
        //System.out.println("Treas hijas: "+tareasHijas);
        
        
        JSONObject boardIdentifiers = hr.sendGet(domain,boardId,"GetBoardIdentifiers");
        Map lanesFromHttp = JsonManager.distincGetLanes(boardIdentifiers);
        //System.out.println("Total de lanes 'hojas': "+lanesFromHttp.size());
        
        Map cardTypesFromHttp = JsonManager.getCardTypes(boardIdentifiers);
        //System.out.println("CardTypes: "+new PrettyPrintingMap<String, String>(cardTypesFromHttp));
        Map CardType =(Map)cardTypesFromHttp.get("Other Work");
        int OtherWorkID = Integer.parseInt(CardType.get("Id").toString());
        //System.out.println("OtherWorkID: "+OtherWorkID);
        
        Map prioritiesFromHttp = JsonManager.getPriorities(boardIdentifiers);
        Map priority = (Map)prioritiesFromHttp.get("Normal");
        int NormalPriorityID = Integer.parseInt(priority.get("Id").toString());
        
        JSONArray cards = new JSONArray();
        List list;
        Iterator iterator = tareasHijas.entrySet().iterator();
        int k=0,j=0;
	while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            list = (List) tareasHijas.get(mapEntry.getKey()); 
            //System.out.println("\n\nThe key is: " + mapEntry.getKey() + ",value is :" + mapEntry.getValue()+" "+k++);
            
            for (int l = 0; l < list.size(); l++){
                JSONObject card = new JSONObject();
                if(lanesFromHttp.containsKey(mapEntry.getKey())){
                    //System.out.println("Found in IF statment: "+j+++"\n");
                    Map lane = (Map)lanesFromHttp.get(mapEntry.getKey());
                    Task task = (Task)list.get(l);
                    //System.out.println("TaskName: "+task.getName());

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
                    //System.out.println(card.toString(2));
                    
                    cards.put(card);
                }
            }
	}
                
        System.out.println("Cards JSON: "+cards.toString(3));
        
        //hr.addCards(domain,boardId,cards);
    }
}
