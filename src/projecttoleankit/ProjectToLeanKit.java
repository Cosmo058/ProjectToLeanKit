package projecttoleankit;

import net.sf.mpxj.MPXJException;
import org.json.JSONObject;
/**
 *
 * @author angel
 */
public class ProjectToLeanKit {
    public static void main(String[] args) throws MPXJException, Exception{
        String filename = "sgd.mpp";
        ClassLoader loader = ProjectToLeanKit.class.getClassLoader();
        String path = loader.getResource("projecttoleankit/files/").toString();
        path = path.substring(6);
        System.out.println(path);
        
        HttpRequest hr = new HttpRequest();
        JSONObject jObj = hr.LeanRequest("cosmodev","225790183","GetBoardIdentifiers");
        int spacesToIndentEachLevel = 2;
        //System.out.println("\n\nJSON Obj:\n"+jObj.toString(spacesToIndentEachLevel));
        
        JsonManager.JsonIter(jObj,"ReplyData");
        
        MppH mp = new MppH();
        mp.readMPP(filename);
    }
    
}
