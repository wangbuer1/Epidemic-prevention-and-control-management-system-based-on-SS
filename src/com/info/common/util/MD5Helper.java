package com.info.common.util;

import java.security.MessageDigest;



public class MD5Helper {

	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}
	
	public static String MD5compute(String inStr)
    {
		try{
	        char[] charArray = inStr.toCharArray();
	
	        byte[] byteArray = new byte[charArray.length];
	
	        for (int i = 0; i < charArray.length; i++)
	            byteArray[i] = (byte) charArray[i];
	
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        byte[] md5Bytes = md5.digest(byteArray);
	
	        StringBuffer hexValue = new StringBuffer();
	
	        for (int i = 0; i < md5Bytes.length; i++)
	        {
	            int val = ((int) md5Bytes[i]) & 0xff;
	            if (val < 16)
	                hexValue.append("0");
	            hexValue.append(Integer.toHexString(val));
	        }
	        return hexValue.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
        
    }

	public static void main(String[] args) {
		System.err.println(MD5Encode("1"));
	}
}
