package com.dcl.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
 
public class ThreeDESTest {
 
private static String password = "xiehuaxin";
	
	public static void main(String[] args) {
		jdkAES();
		
	}
	
	public static void jdkAES() {
		try {
			//1.����KEY
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(new SecureRandom());
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] byteKey = secretKey.getEncoded();
 
			//2.ת��KEY
			Key key = new SecretKeySpec(byteKey,"AES");
			
			//3.����
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(password.getBytes());
			System.out.println("���ܺ�" + Hex.encodeHexString(result));
			
			//4.����
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(result);
			System.out.println("���ܺ�" + new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

 
}

