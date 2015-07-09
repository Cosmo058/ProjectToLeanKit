package projecttoleankit;

import projecttoleankit.HTTP.HttpRequest;
/**
 *
 * @author angel
 */
public class ProjectToLeanKit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HttpRequest hr = new HttpRequest();
        
        hr.LeanRequest("unam117","214470572","GetBoardIdentifiers");
        // TODO code application logic here
    }
    
}
