import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ping {

	String address;
	
	public Ping (String address) {
		
	}
	
	public Ping () {
		
	}
	
	public String executePing (String addressToPing) {
		String fullCommand;
		
		//Windows uses ping -n 1 IP_ADDRESS
		//OSX and systems with ifconfig (not CentOS, need to add later) uses -c 1 IP_ADDRESS
		
		/*
		 * Sample Unix Output
		 *
		  PING 192.168.1.1 (192.168.1.1) 56(84) bytes of data.
		  64 bytes from 192.168.1.1: icmp_seq=1 ttl=64 time=0.502 ms

		  --- 192.168.1.1 ping statistics ---
		  1 packets transmitted, 1 received, 0% packet loss, time 0ms
		  rtt min/avg/max/mdev = 0.502/0.502/0.502/0.000 ms
		 */
		
		/*
		 * Sample Windows Output
		 * 
		  Pinging 192.168.1.1 with 32 bytes of data:
		  Reply from 192.168.1.1: bytes=32 time<1ms TTL=64

		  Ping statistics for 192.168.1.1:
    		  Packets: Sent = 1, Received = 1, Lost = 0 (0% loss),
		  Approximate round trip times in milli-seconds:
    	      Minimum = 0ms, Maximum = 0ms, Average = 0ms
		 * 
		 */
		String keywordForBreak, successMessage, failMessage;
		String os = (System.getProperty("os.name")); 
		System.out.println(os);
		//TODO Add proper support for finding out if ifconfig exists
		if (os.startsWith("Windows")) {
			fullCommand = "ping" + " -n 1 " + addressToPing;
			keywordForBreak = "Reply";
			successMessage = "time";
			failMessage = "unreachable";
		}
		else {
			fullCommand = "ping" + " -c 1 " + addressToPing;
			keywordForBreak = "packets";
			successMessage = "1 packets received";
			failMessage = "0 packets received";
		}
			
			
		
		try {
			String output = "";
			Process p = Runtime.getRuntime().exec(fullCommand);
			BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
			
			while ((output = inputStream.readLine()) != null) {
				if (output.contains(keywordForBreak)) {
				//	System.out.println(output);
					break;
				}
			}
		
			
			if (output.contains(failMessage))
				//System.out.println(addressToPing + " is Offline");
				return "Offline";
			if (output.contains(successMessage))
				//System.out.println(addressToPing + " is Online");
				return "Online";

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR";
		
	}	

}
