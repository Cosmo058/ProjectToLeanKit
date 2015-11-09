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
    //  java -jar ProjectToLeanKit.jar <fileName.extension>
    //
    //El archivo debe estar en el mismo directorio que el archivo jar
    public static void main(String[] args) throws MPXJException, Exception{
        boolean debug = true;
        boolean leanSimple = false;
        Scanner keyboard = new Scanner(System.in);
        
        String domain = "iibi2";
        String user = "jun-a266@hotmail.com";
        String password = "@t1@ng32";
        String boardId = "240886334"; // Test4
        String filename = "sigedda.mpp";
        String toDo = "ToDo";
        int respuesta = 1;
        
        if(args.length!=0){
            debug = false;
            filename = args[0];
        }
        
        if(debug) System.out.println("DEBUG MODE");
        
        System.out.println("Ingrese el dominio de su cuenta");
        System.out.println("[Si la URL de su cuenta es <https://myaccount.leankit.com> su dominio sera \"myaccount\" (sin las comillas)]");
        System.out.print("Dominio: ");
        if(!debug) domain = keyboard.nextLine();
        System.out.println("");
        
        System.out.print("Ingrese el correo electronico asociado a la cuenta de LeanKit: ");
        if(!debug) user = keyboard.nextLine();
        System.out.println("");
        
        System.out.print("Ingrese su contrasenia de LeanKit: ");
        if(!debug) password = keyboard.nextLine();
        System.out.println("");
        
        HttpRequest hr = new HttpRequest(user,password);
        
        
        JSONObject boardsInJSONFormat = hr.getBoards(domain);
        Map boards = JsonManager.getBoardsId(boardsInJSONFormat);
        
        System.out.println("\nSeleccione el numero del tablero(board) donde subir las 'cards': ");
        for(int contador = 0; contador<boards.size();contador++){
            Map temp = (Map)boards.get(contador);
            System.out.println("\t["+(contador+1)+"] Nombre: "+temp.get("Title"));
        }
        
        System.out.print("Tablero numero: ");
        
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
        //System.out.println("Total de etapas en mpp: "+tareasHijas.size()+"\n\n");
        //System.out.println("Treas hijas: "+tareasHijas);
        
        
        JSONObject boardIdentifiers = hr.sendGet(domain,boardId,"GetBoardIdentifiers");
        Map lanesFromHttp = JsonManager.getLanes(boardIdentifiers,toDo); ////////////////////////////////////
        //System.out.println("Total de lanes 'ToDo': "+lanesFromHttp.size());
        //System.out.println(boardIdentifiers.toString(3));
        //System.out.println(lanesFromHttp.toString());
        
        Map cardTypesFromHttp = JsonManager.getCardTypes(boardIdentifiers);
        System.out.println("CardTypes: "+cardTypesFromHttp.toString());
        Map CardType =(Map)cardTypesFromHttp.get(1);
        
        System.out.println("CardType "+cardTypesFromHttp.get(2));
        
        int OtherWorkID = Integer.parseInt(CardType.get("Id").toString());
        //System.out.println("OtherWorkID: "+OtherWorkID);
        
        Map prioritiesFromHttp = JsonManager.getPriorities(boardIdentifiers);
        Map priority = (Map)prioritiesFromHttp.get("Normal");
        int NormalPriorityID = Integer.parseInt(priority.get("Id").toString());
        
        
        if(tareasHijas.size() == lanesFromHttp.size() && lanesFromHttp.size()>1){
            System.out.println("El tablero en LeanKit y el archivo MicrosoftProject parecen tener "+tareasHijas.size()+" etapas principales");
            System.out.println("Que desea hacer?");
            System.out.println("\t[1]Conservar la estructura de "+tareasHijas.size()+" etapas al cargar las tarjetas");
            System.out.println("\t[2]Realizar la carga de tarjetas en un solo Lean");
            System.out.print("Opcion numero: ");
            keyboard.nextLine();
            respuesta = Integer.parseInt(keyboard.nextLine().toString());
            
            if(respuesta == 1)
                leanSimple = false;
            else
                leanSimple = true;
        }if(tareasHijas.size() == lanesFromHttp.size() && lanesFromHttp.size() == 1){
            leanSimple = true;
        }if(tareasHijas.size() > lanesFromHttp.size()){
            leanSimple = true;
        }if(tareasHijas.size() < lanesFromHttp.size()){
            leanSimple = false;
        }
        
        JSONArray cards = new JSONArray();
        List list;
        Iterator iterator = tareasHijas.entrySet().iterator();
        int k=0,j=0;
	while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            list = (List) tareasHijas.get(mapEntry.getKey()); 
            System.out.println("\n\nThe key is: " + mapEntry.getKey() + ",value is :" + mapEntry.getValue()+" "+k++);
            
            for (int l = 0; l < list.size(); l++){
                JSONObject card = new JSONObject();
                if(lanesFromHttp.containsKey(mapEntry.getKey()) || leanSimple){
                    //System.out.println("Found in IF statment: "+j+++"\n");
                    Map lane;
                    
                    if(leanSimple){
                        System.out.println("leanSimple = true");
                        lane = (Map)lanesFromHttp.get(1);
                        System.out.println(lane.toString());
                    }
                    else
                        lane = (Map)lanesFromHttp.get(mapEntry.getKey());
                    
                    Task task = (Task)list.get(l);
                    //System.out.println("TaskName: "+task.getName());

                    SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyy");
                    //System.out.format("Task: %-47s    ID:%-3s    ParentTask:%-75s    TaskType:%-15s\n",task.getName(),task.getID(),task.getParentTask(),ft.format(task.getStart()));

                    if(leanSimple)
                        card.put("LaneId",lane.get("Id"));
                    else
                        card.put("LaneId",lane.get("Id"));
                    
                    card.put("Title",task.getName());
                    
                    CardType =(Map)cardTypesFromHttp.get(Integer.parseInt(mapEntry.getKey().toString()));
                    card.put("TypeID",Integer.parseInt(CardType.get("Id").toString()));
                    card.put("Priority",NormalPriorityID);
                    card.put("IsBlocked","false");
                    card.put("BlockReason","null");
                    card.put("StartDate",ft.format(task.getStart()).toString());
                    card.put("DueDate",ft.format(task.getFinish()).toString());
                    System.out.println(card.toString(2));
                    
                    cards.put(card);
                }
            }
	}
                
        //System.out.println("Cards JSON: "+cards.toString(3));
        
        if(!debug) hr.addCards(domain,boardId,cards);
    }
}
