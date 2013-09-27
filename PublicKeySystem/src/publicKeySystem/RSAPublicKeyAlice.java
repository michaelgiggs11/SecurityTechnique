package publicKeySystem;
import java.io.*;
//import java.math.BigInteger;
import java.net.*;
import java.security.*;
import java.util.Scanner;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


import javax.crypto.Cipher;


public class RSAPublicKeyAlice {
	
	
	public static void main(String[] args) throws Exception {
        
		System.out.println("Welcome to the RSA Public Key System");
		System.out.println("Which kind of service do you want?");
		System.out.println("Confidentiality           : Press 1");
	    System.out.println("integrity & Authentication: Press 2");
	    
		Scanner input = new Scanner(System.in);
		
	    String host = "127.0.0.1";
		int port = 7999;
		Socket s = new Socket(host, port);
		
		ObjectOutputStream BPublicKey = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream APublicKey = new ObjectInputStream(s.getInputStream());
		
		/* Generate Alice's key pairs (private key and public key)*/
		KeyPairGenerator genKeyPair = KeyPairGenerator.getInstance("RSA");
		genKeyPair.initialize(1024 ,new SecureRandom());
	    KeyPair keyPair = genKeyPair.genKeyPair();
	    RSAPublicKey PublicKey_Alice = (RSAPublicKey) keyPair.getPublic();
	    RSAPrivateKey PrivateKey_Alice = (RSAPrivateKey)keyPair.getPrivate();
	    
	    /* Send her public key to Bob */
	    BPublicKey.writeObject(PublicKey_Alice);
	    
	    /* Get Bob's public key */
	    RSAPublicKey eBob = (RSAPublicKey) APublicKey.readObject();
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    
	    int choice= input.nextInt();
	   
		    switch(choice)
			{
			  case 1:
				  //Confidentiality: encipher by receiver's public key(Bob)

				  System.out.println("Confidentiality: encipher by receiver's public key (Bob) ");
				  Scanner input1 = new Scanner(System.in);
				  System.out.println("Please enter the plaintext you want to encipner");
				  String in1= input1.nextLine();
				  cipher.init(Cipher.ENCRYPT_MODE, eBob);
   			      byte[] cipherText1 = cipher.doFinal(in1.getBytes());
				  System.out.println("The ciphertext is: " + cipherText1);
				  BPublicKey.writeInt(1);
				  BPublicKey.writeObject(cipherText1);
				  BPublicKey.flush();
				  BPublicKey.close();
				  break;
				  
			  case 2:
				  //Integrity/Authentication: encipher by sender's private key (Alice)

				  System.out.println("Integrity/Authentication: encipher by sender's private key (Alice)");
				  Scanner input2 = new Scanner(System.in);
				  System.out.println("Please enter the plaintext you want to encipner");
				  String in2= input2.nextLine();
				  cipher.init(Cipher.ENCRYPT_MODE, PrivateKey_Alice);
   			      byte[] cipherText2 = cipher.doFinal(in2.getBytes());
				  System.out.println("The ciphertext is: " + cipherText2);
				  BPublicKey.writeInt(2);
				  BPublicKey.writeObject(cipherText2);
				  BPublicKey.flush();
				  BPublicKey.close();
				  break;
				  
			}
		    s.close();
		    input.close();
		}

	    
	    
	}

