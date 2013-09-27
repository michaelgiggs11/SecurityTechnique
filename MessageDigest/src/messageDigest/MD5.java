package messageDigest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public String getMD5(byte[] bytes) {
		// TODO Auto-generated method stub
		String str="";
		try {
				MessageDigest md = MessageDigest.getInstance("MD5"); //get the instance of md5
				md.update(bytes);									//get the digest updated
				byte[] b = md.digest();								//calculate the final value
				int i;		
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) 
				{
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				str = buf.toString();   //output as strings
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	//when certain algorithm is down, output the abnormal condition
		}
		return str;
	}

}
