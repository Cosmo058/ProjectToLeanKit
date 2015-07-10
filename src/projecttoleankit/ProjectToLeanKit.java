package projecttoleankit;

import HTTPLeanKit.HttpRequest;
import MPPHandler.MppH;
import MPPHandler.MpxjFilter;
import net.sf.mpxj.MPXJException;
/**
 *
 * @author angel
 */
public class ProjectToLeanKit {
    public static void main(String[] args) throws MPXJException{
        String filename = "sus.mpp";
        ClassLoader loader = ProjectToLeanKit.class.getClassLoader();
        String path = loader.getResource("projecttoleankit/files/").toString();
        path = path.substring(6);
        System.out.println(path);
        
        HttpRequest hr = new HttpRequest();
        //hr.LeanRequest("unam117","214470572","GetBoardIdentifiers");
        
        MppH mp = new MppH();
        mp.readMPP("sus.mpp");
                
        MpxjFilter mf = new MpxjFilter();
        //mf.filtros(path+filename,"XD");
    }
    
}
