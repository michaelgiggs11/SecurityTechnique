package encryption;

import java.io.*;
import java.net.*;
import java.security.*;
//import java.lang.*;

import javax.crypto.*;

public class CipherClient {
	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "127.0.0.1";
		int port = 7999;
		Socket s = new Socket(host, port);
		//hind
		KeyGenerator k = KeyGenerator.getInstance("DES");
		SecureRandom random = new SecureRandom();
		k.init(random);
		Key key = k.generateKey();
		//Generate a DES key
	    //hind
		File KeyFile = new File("DesKey.txt");
		ObjectOutputStream output1 = new ObjectOutputStream(new FileOutputStream(KeyFile));
		output1.writeObject(key);		
		//Store it in Deskey.txt
	    //ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("KeyFile.xx"));
		//(1) generate a DES key and stores the key in a file
	    
		ObjectOutputStream outSocket = new ObjectOutputStream(s.getOutputStream());
	    outSocket.writeObject(key);
	    outSocket.flush();
	    //Send the DES key to the server
	    //hind
	    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    CipherOutputStream cipherOut = new CipherOutputStream(s.getOutputStream(), cipher);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//This sets the cipher object to Encrypt mode with the specified key k (a Key object)
		//hint
	    byte input[] = message.getBytes();
	    System.out.println("Before encrypted, the plaintext is:" + message);
	    System.out.println("After encrypted, the ciphertext is:" + input);
	    cipherOut.write(input);
	    cipherOut.close();
	    // (2) encrypts the given String object using that key and sends the encrypted object over the socket to the server
	    
	    s.close();
	    output1.close();
		
	}

}
