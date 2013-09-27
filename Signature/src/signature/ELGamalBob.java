package signature;

import java.io.*;//include
import java.net.*;//include
//import java.security.*;
import java.math.BigInteger;

public class ELGamalBob {
	private static boolean verifySignature(	BigInteger y, BigInteger g, BigInteger p, BigInteger a, BigInteger b, String message)
	{
		BigInteger result1 = (y.modPow(a, p).multiply(a.modPow(b, p))).mod(p);//.modpow
		//((y^a mod p)(a^b mod p)) mod p
		//string => BigInteger
		BigInteger m = new BigInteger(message.getBytes());
		BigInteger result2 = g.modPow(m, p);
		//g^m mod p
		
		return result1.equals(result2);
		//Note "(y^a).(a^b) mod p" is equivalent to "[(y^a) mod p] [(a^b) mod p] mod p"
	}

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();
		ObjectInputStream is = new ObjectInputStream(client.getInputStream());

		// read public key
		BigInteger y = (BigInteger)is.readObject();
		BigInteger g = (BigInteger)is.readObject();
		BigInteger p = (BigInteger)is.readObject();

		// read message
		String message = (String)is.readObject();

		// read signature
		BigInteger a = (BigInteger)is.readObject();
		BigInteger b = (BigInteger)is.readObject();

		boolean flag = verifySignature(y, g, p, a, b, message);

		System.out.println(message);

		if (flag == true)
			System.out.println("Signature verified.");
		else
			System.out.println("Signature verification failed.");

		s.close();
	}
}
