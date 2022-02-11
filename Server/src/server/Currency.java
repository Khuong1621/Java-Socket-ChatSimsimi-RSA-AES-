package server;

import java.io.IOException;
import java.util.StringTokenizer;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Connection;
import org.jsoup.Jsoup;


public class Currency {
	private String APIKEY ="21e115f0469b1e33b4b6c3b990e0d994";
	private String url = "http://api.exchangeratesapi.io/v1/latest?access_key="+APIKEY+"&format=1";
	
	private String from;
	private String to; 
	private Long money;
	private String response;
	private JSONObject rates;
	private String data = "VND:EUR:100000";
	
//	http://api.exchangeratesapi.io/v1/latest?access_key=21e115f0469b1e33b4b6c3b990e0d994&format=1
	public Currency(String data) throws IOException {
		try {
			this.data= data;
			
			StringTokenizer st = new StringTokenizer(data,":");
			this.from = st.nextToken().toUpperCase();
			this.to = st.nextToken().toUpperCase();
			this.money = Long.parseLong(st.nextToken().trim());
			
			System.out.println(from + to + money);
			Change(data);
		} catch (Exception e) {
			System.out.println("Lỗi số tiền");
			response = "Số tiền không hợp lệ.";
		}
	}
	
	public void APi() throws IOException {

		try {
			Connection.Response res = Jsoup.connect(url)
					.ignoreContentType(true)
					.method(Connection.Method.GET)
					.execute();
			
			JSONObject jsonObject = (JSONObject) JSONValue.parse(res.body());
			
			this.rates = (JSONObject) jsonObject.get("rates");
		} catch (Exception e) {
			response = "Không thể kết nối tới api.";
		}

//		Truong hop 2 ngoai te khac EUR thi lam binh thuong

		
		
	}
	public String Change(String s) throws IOException {
		try {
			APi();
			
			if(!(from.equals("EUR"))&&!(to.equals("EUR")) ) {
				Double ratesFrom = (Double) rates.get(from);
				Double ratesTo = (Double) rates.get(to);
		
				float result = (float) (money / (ratesFrom/ratesTo));
				result = (float) (Math.ceil(result *100) /100);
				
				response = money + from +" = "+ result + to;
				
			} else {
				if(from.equals("EUR")) {
					Double ratesTo = (Double) rates.get(to);
					
					float result = (float) (money * ratesTo);
					result = (float) (Math.ceil(result *100) /100);
					
					response = money + from +" = "+ result + to;
				}
				
				if(to.equals("EUR")) {
					Double ratesFrom = (Double) rates.get(from);
					
					float result = (float) (money / ratesFrom);
					result = (float) (Math.ceil(result *100) /100);
					
					response = money + from +" = "+ result + to;
				}
			}
			
			return response;
		} catch (Exception e) {
			System.out.println("Lỗi đơn vị tiền.");
			response = "Đơn vị tiền không hợp lệ";
		}
		
		return response;
		
	}
	
	public String getResponse() {
		return response;
	}
	
	public static void main(String[] args) throws IOException {
		Currency c = new Currency("VND:EUR:aaaa");
		
		System.out.println(c.getResponse());
	}
}
