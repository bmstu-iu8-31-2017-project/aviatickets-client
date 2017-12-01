package pollyalt.avia;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Класс для создания подключения к серверу
 */

public class Connect {
    //String url = "http://192.168.1.35:8080";
    String url = "https://whispering-headland-13430.herokuapp.com";

    /**
     * Метод подключения к серверу, отправки запроса и получения данных с сервера
     * @param request Запрос
     * @return Ответ с сервера
     * @throws Exception
     */
    public String ConnectToServer(String... request)throws Exception
    {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
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
        return response.toString();
    }


}
