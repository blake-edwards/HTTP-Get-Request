import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpURLConnectionExample {

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		String response = http.sendGet(); // response in the form of a string
		//System.out.println("Here are some random users : \n" + response);
		String prettyResponse = toPrettyFormat(stringToJSON(response));
		System.out.println("\nHere are some random users : \n" + prettyResponse);
	}

	// HTTP GET request
	private String sendGet() throws Exception {
		String url = "https://randomuser.me/api/";
		System.out.println("[?] Sending Http GET request to "+url);
		//System.out.println("debug 1");
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("[!] Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			//System.out.println("debug 2");
		}
		in.close();
		System.out.println("[+] Http GET request made!");
		return response.toString();
	}
	
	public static String toPrettyFormat(JsonObject jsonObject) 
	  {
	      Gson gson = new GsonBuilder().setPrettyPrinting().create();
	      String prettyJson = gson.toJson(jsonObject);
	      return prettyJson;
	  }
	
	public static JsonObject stringToJSON(String jsonString) 
	  {
		JsonObject jsonObj = new JsonParser().parse(jsonString).getAsJsonObject();
		return jsonObj;
	  }

}