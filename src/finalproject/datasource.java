package finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.json.JSONObject;

public class datasource {

	public static double[][] getdata(int nov) throws IOException  {
		HashMap<Integer, String> currencyNameMap = new HashMap<>();
        currencyNameMap.put(0, "INR");
        currencyNameMap.put(1, "USD");
        currencyNameMap.put(2, "EUR");
        currencyNameMap.put(3, "GBP");
        currencyNameMap.put(4, "JPY");
        double arr[][] = new double[5][5];
        
        for(int j=0;j<5;j++)
        {
        	String GET_URL = "https://v6.exchangerate-api.com/v6/e23936ab293c7ff14f2bd8fa/latest/"+currencyNameMap.get(j);
            URL url = new URL(GET_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) !=null){
            	response.append(inputLine);
            }in.close();
            for (int i=0;i<5;i++)
            {
            	JSONObject obj= new JSONObject(response.toString());
                Double exchangeRate = obj.getJSONObject("conversion_rates").getDouble(currencyNameMap.get(i));
                arr[j][i]=exchangeRate;
            }
        }
        return arr;
        
    }
	
	

}
