package projecttoleankit;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
/**
 *
 * @author Angel
 */
public class MppH {
    public void readMPP(String filename) throws MPXJException{
        ClassLoader loader = MppH.class.getClassLoader();
        String path = loader.getResource("MPPHandler/../projecttoleankit/files/").toString();
        path = path.substring(6);
        
        System.out.println("PATH: "+path);
        
        MPPReader reader = new MPPReader();
        ProjectFile project = reader.read(path+filename);
        
        System.out.print("\n\n\n");
        for (Task task : project.getAllTasks()){
            //System.out.format("Task: %-47s    ID:%-3s    ParentTask:%-75s    TaskType:%-15s\n",task.getName(),task.getID(),task.getParentTask(),task.getType());
        }
        
        System.out.print("\n\n\n");
        listHierarchy(project,0,"");
    }
    
    public void listHierarchy(ProjectFile file,int level,String trace){
        for (Task task : file.getChildTasks()){
            System.out.println(""+level+" Task: " + task.getName());
            listHierarchy(task, "\t",level+1,"");
        }
        System.out.println();
    }

    private int listHierarchy(Task task, String indent,int level, String trace){
        int contTareasHijas=0, childFound=0;
        for (Task child : task.getChildTasks()){
            if(trace!="" && childFound==0) trace = trace+":"+task.getName();
            if(trace=="" && childFound==0) trace = trace+task.getName();
            System.out.println(indent +""+level+" Task: " + child.getName());
            contTareasHijas++;
            childFound = listHierarchy(child, indent + "\t",level+1,trace);
        }
        
        if (contTareasHijas==0) System.out.println(indent+"La tarea "+task.getName()+" es una tarea 'hoja' con traza: "+trace);
        return 1;
    }
}
