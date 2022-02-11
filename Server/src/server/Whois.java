package server;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Whois {
	public String domain;
	private String URL = "https://www.whois.com/whois/";
	private String response = "Thông tin domain";
	
	public Whois(String domain) {
		this.domain = domain;
		Crawl();
	}
//	https://www.whois.com/whois/fb.com
	private void Crawl() {
		try {
			String url = URL+domain;
//			Connection.Response res = Jsoup.connect(url)
//					.followRedirects(true)
//					.method(Connection.Method.GET)
//					.execute();
			Document docs = Jsoup.connect(url).get();
			//Document docs = res.parse();
			Elements elms = docs.getElementsByClass("df-value");
			
			Elements row = docs.getElementsByClass("df-row");
			if(row.isEmpty()) {
				response="Không có kết quả cho domain";
				return;
			}
//			System.out.println(row.get(0).text());
//			System.out.println(row.get(1).text());
//			System.out.println(row.get(2).text());
//			System.out.println(row.get(3).text());
//			System.out.println(row.get(4).text());
//			System.out.println(row.get(6).text());
//			System.out.println(row.get(7).text());
//			System.out.println(row.get(8).text());
			response += "\n\t" + row.get(0).text();
			response += "\n\t" + row.get(1).text();
			response += "\n\t" + row.get(2).text();
			response += "\n\t" + row.get(3).text();
			response += "\n\t" + row.get(4).text();
			if(elms.get(6).text() != null) {
				response += "\n\tName Server: " + elms.get(6).text().replace(" ", "\n\t\t\t");
			}
			response += "\n\t" + row.get(7).text();
			response += "\n\t" + row.get(8).text();
			
			
			
			
//			if(elms.isEmpty()) {
//				response="Không có kết quả cho domain";
//				return;
//			}
//			//System.out.println(elms);
//			
////			response
//			response = "Domain: " + elms.get(0).text();
//			response += "\n\t Registrar: " + elms.get(1).text();
//			response += "\n\t Registered On: " + elms.get(2).text();
//			response += "\n\t Expires On: " + elms.get(3).text();
//			response += "\n\t Updated  On: " + elms.get(4).text();
//			response += "\n\t Organization: " + elms.get(8).text();
//			if(elms.get(6).text() != null) {
//				response += "\n\t Name Server: " + elms.get(6).text().replace(" ", "\n\t\t\t");
//			}
			
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			response = "Không tìm thấy thông tin về domain "+this.domain;
		}
	}
	
	public String getResponse() {
		return response;
	}
	public static void main(String[] args) {
		Whois w = new Whois("fb.com");
		System.out.println(w.getResponse());
	}
}
