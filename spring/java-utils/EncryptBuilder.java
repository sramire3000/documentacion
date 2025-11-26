package com.example.demo.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase utilitaria para operaciones de cifrado y descifrado utilizando el algoritmo AES.
 * Proporciona métodos para convertir entre diferentes formatos y realizar operaciones criptográficas.
 * 
 * @author Ejemplo
 * @version 1.0
 */
public class EcryptBuilder {
    
    /**
     * Convierte un array de bytes a una representación hexadecimal en formato String.
     * 
     * @param b Array de bytes a convertir
     * @return String que representa los bytes en formato hexadecimal en mayúsculas
     */
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
    
    /**
     * Convierte una cadena hexadecimal a un array de bytes.
     * 
     * @param s Cadena hexadecimal a convertir
     * @return Array de bytes resultante de la conversión
     * @throws NumberFormatException Si la cadena contiene caracteres no hexadecimales
     */
    public static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte)v;
        } 
        return b;
    }
    
    /**
     * Cifra un texto utilizando el algoritmo AES con la clave proporcionada.
     * 
     * @param key Clave de cifrado en formato hexadecimal
     * @param text Texto a cifrar
     * @return Texto cifrado en formato hexadecimal, o null si ocurre un error
     */
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
    
    /**
     * Descifra un texto previamente cifrado utilizando el algoritmo AES.
     * 
     * @param key Clave de descifrado en formato hexadecimal
     * @param text Texto cifrado en formato hexadecimal a descifrar
     * @return Texto descifrado, o null si ocurre un error
     */
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
    
    /**
     * Cifra un array de bytes utilizando el algoritmo AES con la clave proporcionada.
     * 
     * @param key Clave de cifrado en formato hexadecimal
     * @param text Array de bytes a cifrar
     * @return Array de bytes cifrado, o null si ocurre un error
     */
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
    
    /**
     * Descifra un array de bytes previamente cifrado utilizando el algoritmo AES.
     * 
     * @param key Clave de descifrado en formato hexadecimal
     * @param text Array de bytes cifrado a descifrar
     * @return Array de bytes descifrado, o null si ocurre un error
     */
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
