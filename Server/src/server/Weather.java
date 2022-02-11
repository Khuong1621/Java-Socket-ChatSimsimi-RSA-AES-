package server;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Weather {
	public String local;
	private String baseURL = "https://www.google.com/search?q=weather ";
	public String response = "";
	
	public Weather(String local) {
		this.local = local;
		getWeather();
	}
	private void getWeather() {
		try {
			String url= baseURL + local;
			// https://www.google.com/search?q=weather hcm
			
			System.out.println(url);

			Document html = Jsoup.connect(url).get();
			
			Elements week = html.getElementsByClass("wob_df");
			Element address = html.getElementById("wob_loc");
			Element time = html.getElementById("wob_dts");
			Element thoiTiet = html.getElementById("wob_dcp");
			Element nhietDo = html.getElementById("wob_tm");
			
			response += "Thời tiết " + address.text() +"\n"+ time.text() + "\n";
			response += thoiTiet.text() + " " + ". Nhiệt độ "+ nhietDo.text() + "°C \n";
			
			try {
				Elements toDay = html.getElementsByClass("wtsRwe");
				response += toDay.get(0).child(0).text() + "\n";
				response += toDay.get(0).child(1).text() + "\n";
				response += "Gió: " + toDay.get(0).child(2).child(0).child(0).text() + "\n";
				
				response += "----------------------" + "\n";
				response += "Nhiệt độ 7 ngày tiếp theo" + "\n";

				
			} catch (Exception e) {
				response = "Không tìm thấy địa điểm";
				System.err.println(e);
			}
			for (Element date : week) {
				response += date.child(0).attributes().get("aria-label") + "\n";
				response += "Thời tiết: "+date.child(1).child(0).attributes().get("alt") + "\n";
				response += "Nhiệt độ cao nhất: "+ date.child(2).child(0).child(0).text() + "°C \n";
				response += "Nhiệt độ thấp nhất: "+ date.child(2).child(1).child(0).text() + "°C \n";
				response += "----------------------" + "\n";
				

			}
			
			System.out.println(response);
			
		} catch (Exception e) {
			System.out.println("Error: "+e.getStackTrace());
			response = "Lỗi đường truyền hoặc địa điểm không đúng.";
		}
		
	}
	
	public String getResponse() {
		return response;
	}
	
	public static void main(String[] args) {
		Weather w = new Weather("seoul");
		System.out.println(w.response);
	}
}
