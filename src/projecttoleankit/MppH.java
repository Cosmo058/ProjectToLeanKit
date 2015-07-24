package projecttoleankit;

import java.util.Iterator;
import java.util.List;
import net.sf.mpxj.Column;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Resource;
import net.sf.mpxj.Table;
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
        
        for (Resource resource : project.getAllResources()){
            //System.out.println("Resource: " + resource.getName() + " (Unique ID=" + resource.getUniqueID() + ")");
        }
        
        System.out.print("\n\n\n");
        for (Task task : project.getAllTasks()){
            //System.out.format("Task: %-47s    ID:%-3s    ParentTask:%-75s    TaskType:%-15s\n",task.getName(),task.getID(),task.getParentTask(),task.getType());
        }
        
        System.out.print("\n\n\n");
        listHierarchy(project);
        
        /*
        try{
            ProjectFile mpp = reader.read(path+filename);

            List tables = mpp.getTables();
            Iterator iter = tables.iterator();
            while (iter.hasNext()){
                Table table = (Table)iter.next();
                
                if (table.getResourceFlag()){
                    List resources = mpp.getAllResources();
                    Iterator resourceIter = resources.iterator();
                    while (resourceIter.hasNext()){
                        Resource resource = (Resource)iter.next();
                        List columns = table.getColumns();
                        Iterator columnIter = columns.iterator();
                        while (columnIter.hasNext()){
                            Column column = (Column)columnIter.next();
                            Object columnValue = resource.getCachedValue(column.getFieldType());
                            System.out.print(columnValue);
                            System.out.print(",");
                        }
                        System.out.println("");
                    }
                }else{
                    List tasks = mpp.getAllTasks();
                    Iterator taskIter = tasks.iterator();
                    
                    while(taskIter.hasNext()){
                        Task task = (Task)iter.next();
                        List columns = table.getColumns();
                        Iterator columnIter = columns.iterator();
                        while(columnIter.hasNext()){
                            Column column = (Column)columnIter.next();
                            Object columnValue = task.getCachedValue(column.getFieldType());
                            System.out.print(columnValue);
                            System.out.print(",");
                        }
                        System.out.println("");
                    }
                }
            }
            //return Json(new { data = "success" }, JsonRequestBehavior.AllowGet);
        }catch(Exception e){
            e.printStackTrace();
            //return Json(new { data = "error" }, JsonRequestBehavior.AllowGet);
        }
        */
    }
    
    public void listHierarchy(ProjectFile file){
        for (Task task : file.getChildTasks()){
            System.out.println("Task: " + task.getName());
            listHierarchy(task, "\t");
        }
        System.out.println();
    }

    private void listHierarchy(Task task, String indent){
        for (Task child : task.getChildTasks()){
            System.out.println(indent + "Task: " + child.getName());
            listHierarchy(child, indent + "\t");
        }
    }
}
