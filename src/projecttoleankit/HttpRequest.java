
package projecttoleankit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpRequest {
    public JSONObject sendGet(String domain, String boardID,String action){
        try {
            String webPage = "https://"+domain+".leankit.com/kanban/api/board/"+boardID+"/"+action;
            String name = "cosmodevtest@gmail.com";
            String password = "CosmoTest";

            String authString = name + ":" + password;
            System.out.println("auth string: " + authString);
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);
            System.out.println("Base64 encoded auth string: " + authStringEnc);

            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                    sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();

            System.out.println("*** BEGIN ***");
            System.out.println(result);
            System.out.println("*** END ***");
            
            JSONObject jsonObj = new JSONObject(sb.toString());
            return jsonObj;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JSONObject jsonObj = new JSONObject("");
        return jsonObj;
    }
    
    public JSONObject sendPost(String domain, String boardID,String action,JSONArray jsonArray) throws Exception {
        
        String url ="";
                
        if(!action.equals(""))
            url = "https://"+domain+".leankit.com/kanban/api/board/"+boardID+"/"+action;
        
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.addRequestProperty("User-Agent","LeanKit API");

        String urlParameters = jsonArray.toString();

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();        
        
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println(response.toString());
        
        JSONObject jsonObj = new JSONObject(response.toString());
        
        return jsonObj;
    }
    
    public JSONObject sendPost(String domain, String boardID,String action,JSONObject jsonObject) throws Exception {
        JSONObject J_obj = new JSONObject();
        
        String key ="nrIJgvSMlA";
        String url ="";
        
        J_obj.put("key",key);
        
        if(!action.equals(""))
            url = "https://"+domain+".leankit.com/kanban/api/board/"+boardID+"/"+action;
        
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.addRequestProperty("User-Agent","LeanKit API");

        String urlParameters = J_obj.toString();

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();        
        
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println(response.toString());
        
        JSONObject jsonObj = new JSONObject(response.toString());
        
        return jsonObj;
    }
}