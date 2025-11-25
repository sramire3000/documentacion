package com.example.demo.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EcryptBuilder {
	  public static String byteArrayToHexString(byte[] b) {
		    StringBuffer sb = new StringBuffer(b.length * 2);
		    for (int i = 0; i < b.length; i++) {
		      int v = b[i] & 0xFF;
		      if (v < 16)
		        sb.append('0'); 
		      sb.append(Integer.toHexString(v));
		    } 
		    return sb.toString().toUpperCase();
		  }
		  
		  public static byte[] hexStringToByteArray(String s) {
		    byte[] b = new byte[s.length() / 2];
		    for (int i = 0; i < b.length; i++) {
		      int index = i * 2;
		      int v = Integer.parseInt(s.substring(index, index + 2), 16);
		      b[i] = (byte)v;
		    } 
		    return b;
		  }
		  
		  public static String encryptData(String key, String text) {
		    String encryptText = null;
		    if (key != null && text != null)
		      try {
		        byte[] bytes = hexStringToByteArray(key.toUpperCase());
		        SecretKeySpec skeySpec = new SecretKeySpec(bytes, "AES");
		        Cipher cipher = Cipher.getInstance("AES");
		        cipher.init(1, skeySpec);
		        byte[] encrypted = cipher.doFinal(text.getBytes());
		        encryptText = byteArrayToHexString(encrypted);
		      } catch (Exception e) {
		        System.out.println("Error Message" + e);
		      }  
		    return encryptText;
		  }
		  
		  public static String desencryptData(String key, String text) {
		    String desencryptText = null;
		    if (key != null && text != null)
		      try {
		        byte[] bytes = hexStringToByteArray(key.toUpperCase());
		        SecretKeySpec skeySpec = new SecretKeySpec(bytes, "AES");
		        Cipher cipher = Cipher.getInstance("AES");
		        cipher.init(2, skeySpec);
		        byte[] original = cipher.doFinal(hexStringToByteArray(text));
		        desencryptText = new String(original);
		      } catch (Exception e) {
		        System.out.println("Error Message" + e);
		      }  
		    return desencryptText;
		  }
		  
		  public static byte[] encryptDataBytes(String key, byte[] text) {
		    byte[] encryptText = null;
		    if (key != null && text != null)
		      try {
		        byte[] bytes = hexStringToByteArray(key.toUpperCase());
		        SecretKeySpec skeySpec = new SecretKeySpec(bytes, "AES");
		        Cipher cipher = Cipher.getInstance("AES");
		        cipher.init(1, skeySpec);
		        byte[] encrypted = cipher.doFinal(text);
		        encryptText = encrypted;
		      } catch (Exception e) {
		        System.out.println("Error Message" + e);
		      }  
		    return encryptText;
		  }
		  
		  public static byte[] desencryptDataBytes(String key, byte[] text) {
		    byte[] data = null;
		    if (key != null && text != null)
		      try {
		        byte[] bytes = hexStringToByteArray(key.toUpperCase());
		        SecretKeySpec skeySpec = new SecretKeySpec(bytes, "AES");
		        Cipher cipher = Cipher.getInstance("AES");
		        cipher.init(2, skeySpec);
		        byte[] original = cipher.doFinal(text);
		        data = original;
		      } catch (Exception e) {
		        System.out.println("Error Message" + e);
		      }  
		    return data;
		  }
}
