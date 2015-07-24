package projecttoleankit;

import HTTPLeanKit.HttpRequest;
import MPPHandler.MppH;
import MPPHandler.MpxjFilter;
import net.sf.mpxj.MPXJException;
import org.json.JSONObject;
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
        JSONObject jObj = hr.LeanRequest("cosmodev","225790183","GetBoardIdentifiers");
        int spacesToIndentEachLevel = 2;
        System.out.println("\n\nJSON Obj:\n"+jObj.toString(spacesToIndentEachLevel));
        
        MppH mp = new MppH();
        mp.readMPP("sgd.mpp");
                
        //MpxjFilter mf = new MpxjFilter();
        //mf.filtros(path+filename,"XD");
    }
    
}
