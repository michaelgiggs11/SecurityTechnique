package messageDigest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
	public String getSHA(byte[] bytes) {
		// TODO Auto-generated method stub
		String str="";
		try {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(bytes);
				byte[] b = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				str = buf.toString();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

}
