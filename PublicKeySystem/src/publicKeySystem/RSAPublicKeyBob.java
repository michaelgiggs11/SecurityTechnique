package publicKeySystem;

import java.io.*;
//import java.math.BigInteger;
import java.net.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
public class RSAPublicKeyBob {

	public static void main(String[] args) throws Exception {

	    int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();
		

		ObjectInputStream APublicKey = new ObjectInputStream(client.getInputStream());
		ObjectOutputStream PublicKey = new ObjectOutputStream(client.getOutputStream());
		
		/* Generate Bob's key pairs (private key and public key)*/
		KeyPairGenerator genKeyPair = KeyPairGenerator.getInstance("RSA");
	    genKeyPair.initialize(1024, new SecureRandom()); 
	    KeyPair keyPair = genKeyPair.genKeyPair();
	    RSAPublicKey BPublicKey_Bob = (RSAPublicKey) keyPair.getPublic();
	    RSAPrivateKey PrivateKey_Alice = (RSAPrivateKey)keyPair.getPrivate();
	    
	    /* Send his public key to Alice */
	    PublicKey.writeObject(BPublicKey_Bob);
	    
	    /* Get Alice's public key */
	    RSAPublicKey eAlice = (RSAPublicKey) APublicKey.readObject();
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    
	    int choice = APublicKey.readInt();
	    
		    switch(choice)
			{
			  case 1:
				  /*Achieve Confidentiality by using Bob's private key to decipher */ 
				  System.out.println("Confidentiality: encipher by receiver's public  key(Bob)");
				  System.out.println("                 decipher by receiver's private key(Bob)");
				  byte[] in1 = (byte[]) APublicKey.readObject();
				  cipher.init(Cipher.DECRYPT_MODE, PrivateKey_Alice);
   			      byte[] plaintText1 = cipher.doFinal(in1);
				  System.out.println("The plaintext is: " + new String(plaintText1));
				  break;
				    
				  
			  case 2:
				  /*Achieve Integrity/Authentication by using Alice's public key to decipher */ 
				  System.out.println("Integrity/Authentication: encipher by sender's private key (Alice)");
				  System.out.println("                          decipher by sender's private key (Alice)");
				  byte[] in2 = (byte[]) APublicKey.readObject();
				  cipher.init(Cipher.DECRYPT_MODE, eAlice);
   			      byte[] plaintText2 = cipher.doFinal(in2);
				  System.out.println("The plaintext is: " + new String(plaintText2));
				  break;
				  
			
			}
		 
		}
		
	}
 


