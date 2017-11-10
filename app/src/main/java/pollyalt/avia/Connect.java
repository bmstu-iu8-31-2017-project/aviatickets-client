package pollyalt.avia;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import javax.net.ssl.HttpsURLConnection;



public class Connect {
   // String url = "http://192.168.1.35:8080";
  // String url = "http://10.50.1.189:8082";
   String url = "http://0.tcp.ngrok.io:14666";
    public String ConnectToServer(String... request)throws Exception
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");

        String urlParameters = request[0];
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        con.disconnect();
        return response.toString().replace('&', ' ');
    }


}
