package server;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class Ip {
	public String ip;
	private String country_name;
//	private String API_IP = "https://api.iplocation.net/";
	private String API_IP = "http://ip-api.com/json/";
	
	public Ip(String ip) {
		this.ip = ip;
		ipLocation();
	}
	
	private void ipLocation() {
		try {
//			String url = API_IP+"?ip="+ip;
			String url = API_IP+ip;
			Connection.Response res = Jsoup.connect(url)
					.ignoreContentType(true)
					.method(Connection.Method.GET)
					.execute();
			
			JSONObject jsonObject = (JSONObject) JSONValue.parse(res.body());
//			http://ip-api.com/json/8.8.8.8
			
			if(((String)jsonObject.get("status")).equals("success")) {
//				ip true
				country_name = (String) "Địa điểm: " + jsonObject.get("city") + ", " + jsonObject.get("country");
				
				country_name += (String) "\n\tTọa độ  Vĩ độ: " + jsonObject.get("lat") + " || Kinh độ: " + jsonObject.get("lon");
			}
			else {
//				ip false
				country_name = "ip không chính xác";
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			country_name= "Không thể kết nối đến API.";
		}
		
	}
	
	public String getResponse() {
		return country_name;
	}
}
