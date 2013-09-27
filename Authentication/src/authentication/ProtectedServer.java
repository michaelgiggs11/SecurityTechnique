package authentication;

import java.io.*;
import java.net.*;
import java.security.*;

public class ProtectedServer {
	public boolean authenticate(InputStream inStream) throws IOException, NoSuchAlgorithmException
	{
		DataInputStream in = new DataInputStream(inStream);//get data from buffer
		//ByteInputSteam vs DataInputStream?
		
		String user = in.readUTF();
		String password = lookupPassword(user);
		
		long timestamp1 = in.readLong();
		long timestamp2 = in.readLong();
		//Tow time stamp read from client
		
		double random_number1 = in.readDouble();
		double random_number2 = in.readDouble();
		//Tow random number read from client

		int length = in.readInt();
		byte[] receivedDigest = new byte [length];
		//read and put into array protect
		in.readFully(receivedDigest);
		//put in protecetDigest buffer
		
		boolean flag = true;
		byte[] Result1 = Protection.makeDigest(user, password, timestamp1, random_number1);
		byte[] recomputedDigest = Protection.makeDigest(Result1, timestamp2, random_number2);
		//The same with client; generate at the server to make authentication
		
		flag = MessageDigest.isEqual(receivedDigest, recomputedDigest); 
		//if the results in the client and server are the same
		return flag;
		//ture or fulse
	}
	
	protected String lookupPassword(String user)
	{
		return "abc123";
	}
	
	public static void main(String[] args) throws Exception
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();//accept the socket of client
		
		ProtectedServer server = new ProtectedServer();
		
		if (server.authenticate(client.getInputStream()))
			System.out.println("Client logged in.");
		else
			System.out.println("Client failed to log in.");
		
		s.close();
	}
}
