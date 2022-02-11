package server;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Simsimi {
	private String text;
	private String lc = "vn";
	private String API_SIMSIMI = "https://api.simsimi.net/v2/";
	private String response;
	
	public Simsimi(String text) {
		this.text = text;
		getApi();
	}
	
	public void getApi() {
		try {
			String url = API_SIMSIMI+"?text="+ text + "&lc=" + lc + "&cf=false";
			
			Connection.Response res = Jsoup.connect(url)
					.ignoreContentType(true)
					.method(Connection.Method.GET)
					.execute();
			
//			giai ma json object
			JSONObject jsonObject = (JSONObject) JSONValue.parse(res.body());
			
			response = (String) jsonObject.get("success");
//			https://api.simsimi.net/v2/?text=xin chao&lc=vn&cf=false;
			
		} catch (Exception e) {
//			e.getStackTrace();
			System.out.println(e.getStackTrace());
			response = "Không thể kết nối tới api simsimi";
		}
		
	}
	
	public String getResponse() {
		return response;
	}
	
	public static void main(String[] args) {
		Simsimi s = new Simsimi("đi không?");
		s.getApi();
	}
}
