package server;


import java.net. *;
import java.util.StringTokenizer;

public class ScanPort {

	private int start;
	private int end;
	private String ip;
	private String response = "";
	private String data;

	public ScanPort(String data) {
		String regScanPort = "^[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+:[0-9]+:[0-9]+$";
		System.out.println(data.trim());
		this.data = data.trim();
		
		if(this.data.matches(regScanPort)) {
			StringTokenizer st = new StringTokenizer(data,":");
			ip = st.nextToken();
			start =	Integer.parseInt(st.nextToken().trim());
			end = Integer.parseInt(st.nextToken().trim());
			response = "Scan ip "+ip+" from port "+start+" to port "+end;
			Scan();
		} else {
			response = "Lệnh không chính xác. Lệnh: /scan [ip]:startPort:endPort";
			System.out.println("Input don't match");
		}
	}

	

	public String Scan() {
		for (int i = start; i <= end; i ++)
	    {
			try{
				Socket socket = new Socket();
				socket.connect(new InetSocketAddress(ip, i), 500);
				socket.close();
				response += "\n\t- "+ip+":"+i+" port is open.";
			}
			catch (Exception e){
				response +=  "\n\t- "+ip+":"+i+" port is closed.";
			}
	    }
		return response;
	}
	
	public String getResponse() {
		return response;
	}
	
	public static void main (String args [])
	{
		ScanPort qp =new ScanPort("8.8.8.8:440:460");
		System.out.println(qp.getResponse());
    }
}
