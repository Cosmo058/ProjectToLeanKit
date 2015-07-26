package projecttoleankit;

import java.util.HashMap;
import java.util.Map;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import org.apache.commons.collections4.map.MultiValueMap;

/**
 *
 * @author Angel
 */
public class MppH {
    
    MultiValueMap tareasHijas = new MultiValueMap();
    
    public MultiValueMap readMPP(String path,String filename) throws MPXJException{        
        MPPReader reader = new MPPReader();
        ProjectFile project = reader.read(path+filename);
        
        //System.out.print("\n\n\n");
        for (Task task : project.getAllTasks()){
            //System.out.format("Task: %-47s    ID:%-3s    ParentTask:%-75s    TaskType:%-15s\n",task.getName(),task.getID(),task.getParentTask(),task.getType());
        }
        
        //System.out.print("\n\n\n");
        listHierarchy(project,0,""); //listHierarchy(projectFile,level,trace);
        
        return tareasHijas;
    }
    
    public void listHierarchy(ProjectFile file,int level,String trace){
        for (Task task : file.getChildTasks()){
            //System.out.println(""+level+" Task: " + task.getName());
            listHierarchy(task, "\t",level+1,"");
        }
        //System.out.println();
    }

    private void listHierarchy(Task task, String indent,int level, String trace){
        int contTareasHijas=0;
        
        for (Task child : task.getChildTasks()){
            if(trace!="") trace = trace+":"+task.getName();
            else trace = trace+task.getName();
            
            trace = Cadenas.remvoverAnidados(trace);
            //System.out.println(indent +""+level+" Task: " + child.getName());
            contTareasHijas++;
            listHierarchy(child, indent + "\t",level+1,trace);
        }
        
        if (contTareasHijas==0){
            String tmp = trace.toLowerCase();
            tmp = tmp.substring(tmp.indexOf(':')+1);
            tmp = tmp.substring(tmp.indexOf(':')+1);
            //System.out.println(indent+"La tarea "+task.getName()+" es una tarea 'hoja' con traza: "+tmp);
            tareasHijas.put(tmp,task);
        }
    }
}
