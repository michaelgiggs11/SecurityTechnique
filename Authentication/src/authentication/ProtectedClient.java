package authentication;

import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Date;

public class ProtectedClient {
	public void sendAuthentication(String user, String password, OutputStream outStream) throws IOException, NoSuchAlgorithmException
	{
		DataOutputStream out = new DataOutputStream(outStream);//put data into buffer
		
		Date date = new Date();
		long timestamp1 = date.getTime();
		long timestamp2 = date.getTime();
		//Two Time Stamp
		
		double random_number1 = Math.random();
		double random_number2 = Math.random();
		//Two random_namber
		
		byte[] digestValue1 = Protection.makeDigest(user, password, timestamp1, random_number1);
		byte[] digestValue2 = Protection.makeDigest(digestValue1, timestamp2, random_number2);
		// Two times of digest
		
		out.writeUTF(user); 
		out.writeLong(timestamp1);
		out.writeLong(timestamp2);
		out.writeDouble(random_number1);
		out.writeDouble(random_number2);
		out.writeInt(digestValue1.length); 
		out.write(digestValue2);
		//from hint out.X => DataOutputStream
		
		out.flush();
	}
	
	public static void main(String[] args) throws Exception
	{
		String host = "127.0.0.1";//The IP address of my own computer
		int port = 7999;
		String user = "George";
		String password = "abc123";
		Socket s = new Socket(host, port);
		
		ProtectedClient client = new ProtectedClient();
		client.sendAuthentication(user, password, s.getOutputStream());
		
		s.close();
	}

}
