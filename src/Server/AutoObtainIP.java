package Server;

/**
 * Created by Dester on 8/30/2014.
 */

import java.io.BufferedReader;
        import java.io.DataOutputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.URL;

        import javax.net.ssl.HttpsURLConnection;

public class AutoObtainIP {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        AutoObtainIP http = new AutoObtainIP();

        http.create(Inet4Address.getLocalHost().getHostAddress());

    }

    // HTTP GET request
    public void create(String IP) throws Exception {

        String url = "http://macotsolution.com/detectip.php?type=1&ip=" + IP;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response Code : " + responseCode + " " + response.toString());
        if (response.toString().equals("QUERYSUCCESS")) {
            System.out.println("New server is created");
        } else {
            System.out.println("Someone else is running the server");
        }

    }

    public String obtainIP() throws Exception {

        String result = null;
        String url = "http://macotsolution.com/detectip.php?type=2";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response Code : " + responseCode + " " + response.toString());
        if (response.toString() != null){
            result = response.toString();
        }
        return result;

    }

    public void delete(String IP) throws Exception {

        String url = "http://macotsolution.com/detectip.php?type=3&ip=" + IP;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response Code : " + responseCode + " " + response.toString());
        if (response.toString().equals("QUERYSUCCESS")) {
            System.out.println("This server is removed");
        }
    }
}