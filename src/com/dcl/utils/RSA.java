package com.dcl.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSA {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		Map<String,String> map = genKeyPair();
		String str1 = "{\"path\":\"123\",\"fileName\":\"��2018.9.12��ƶ�����϶���������־��ѧ�𡢹��ҽ�ѧ��.ppt\",\"password\":\"8888\",\"id\":3,\"account\":\"123\"}";
		str1 = URLEncoder.encode(str1,"UTF-8");
		List<String> s = cutString(str1);
		String res = "";
		for(String ss : s) {
			String s1 = encrypt( URLEncoder.encode(ss,"UTF-8"), map.get("public_key"));
			System.out.println(s1);
			res += decrypt( s1, map.get("private_key"));
		}
		System.out.println(res);
		System.out.println(URLDecoder.decode(res,"UTF-8"));
	}
	/**
	 * �ַ�����Ƭ
	 * @param context
	 * @return
	 */
	public static List<String> cutString(String context) {
		int max = 50;
		List<String> res = new ArrayList<String>();
		byte[] strs = context.getBytes();
		int len = strs.length;
		if(len > max) {
			int size = (int) Math.ceil((float) len / max);
			for(int i = 0;i < size; i++) {
				int toIndex = (i + 1) * max;
				if(toIndex >= len) {
					toIndex = len;
				}
				byte[] s = Arrays.copyOfRange(strs, i * max, toIndex);
				res.add(new String(s));
			}
		}else {
			res.add(context);
		}
		return res;
	}
	/**
	 * ��Կ��˽Կ 
	 * @return
	 */
	public static Map<String,String> genKeyPair(){ 
		Map<String,String> map = new HashMap<String,String>();
		try {
			// KeyPairGenerator���������ɹ�Կ��˽Կ�ԣ�����RSA�㷨���ɶ���  
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
			// ��ʼ����Կ������������Կ��СΪ96-1024λ  
			keyPairGen.initialize(1024,new SecureRandom());  
			// ����һ����Կ�ԣ�������keyPair��  
			KeyPair keyPair = keyPairGen.generateKeyPair();  
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // �õ�˽Կ  
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // �õ���Կ  
			String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));  
			// �õ�˽Կ�ַ���  
			String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));  
			// ����Կ��˽Կ���浽Map
			map.put("public_key", publicKeyString);
			map.put("private_key", privateKeyString);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}  
	/**
	 * RSA��Կ����   
	 * @param str
	 * @param publicKey
	 * @return
	 */
	public static String encrypt( String str, String publicKey ){
		try {
			//base64����Ĺ�Կ
			byte[] decoded = Base64.decodeBase64(publicKey);
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
			//RSA����
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
			return outStr;
		}catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * RSA˽Կ����  
	 * @param str
	 * @param privateKey
	 * @return
	 */
	public static String decrypt(String str, String privateKey){
		try {
			//64λ������ܺ���ַ���
			byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
			//base64�����˽Կ
			byte[] decoded = Base64.decodeBase64(privateKey);  
	        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
			//RSA����
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			String outStr = new String(cipher.doFinal(inputByte));
			return outStr;
		}catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * byteתString
	 * @param res
	 * @return
	 */
	public static String byteToString(byte[] res) {
		StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < res.length; i++) {
    		if (i == res.length-1) {
    			sb.append(Byte.toString(res[i]));
			}else{
				sb.append(Byte.toString(res[i]));	
				sb.append(" ");
			}			
		}
    	return sb.toString();
	}
	/**
	 * Stringתbyte
	 * @param res
	 * @return
	 */
	public static byte[] stringToByte(String res) {
		String[] strArr = res.split(" ");
    	int len = strArr.length;
    	byte[] clone = new byte[len];
    	for (int i = 0; i < len; i++) {
			clone[i] = Byte.parseByte(strArr[i]);
    	}
    	return clone;
	}
}