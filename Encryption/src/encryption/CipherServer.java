package encryption;

import java.io.*; 
//import java.math.BigInteger;
import java.net.*;
import java.security.*;

import javax.crypto.*;

public class CipherServer 
{
	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket server = new ServerSocket(port);
		Socket s = server.accept();
		
		//hind
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		Key key = (Key) in.readObject(); 
		//to read from object in which is an ObjectInputStream	
		
		//hind
		Cipher c1 = Cipher.getInstance("DES/ECB/PKCS5Padding");
		c1.init(Cipher.DECRYPT_MODE, key);
		CipherInputStream cipherIn= new CipherInputStream(s.getInputStream(),c1);
		// Decipher with DES algorithm
		
		//Convert to string type
		
		int x = cipherIn.read();
		//if print directly, X is a number;

		StringBuilder plaintext = new StringBuilder();
		// construct a string
		
		while( x != -1)
	     {	
			plaintext.append((char) x);
	    	x=cipherIn.read();
	    //append all x to the plaintext;
	     }
	    // number = > string
		
		System.out.println("The plaintext is: "+ plaintext.toString());
		//Print out the plaintext
		in.close();
		cipherIn.close();
		server.close();
	}
}
