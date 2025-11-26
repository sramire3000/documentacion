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
     * Constante para el modo de cifrado
     */
    private static final int ENCRYPT_MODE = 1;
    
    /**
     * Constante para el modo de descifrado
     */
    private static final int DECRYPT_MODE = 2;
    
    /**
     * Algoritmo de cifrado a utilizar
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    
    /**
     * Algoritmo de clave a utilizar
     */
    private static final String KEY_ALGORITHM = "AES";
    
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
     * @throws IllegalArgumentException Si la longitud de la cadena no es par
     */
    public static byte[] hexStringToByteArray(String s) {
        if (s == null || s.length() % 2 != 0) {
            throw new IllegalArgumentException("La cadena hexadecimal debe tener longitud par");
        }
        
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
     * @throws IllegalArgumentException Si la clave o el texto son nulos
     */
    public static String encryptData(String key, String text) {
        String encryptText = null;
        if (key != null && text != null) {
            try {
                byte[] bytes = hexStringToByteArray(key.toUpperCase());
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, KEY_ALGORITHM);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                cipher.init(ENCRYPT_MODE, skeySpec);
                byte[] encrypted = cipher.doFinal(text.getBytes());
                encryptText = byteArrayToHexString(encrypted);
            } catch (IllegalArgumentException e) {
                System.err.println("Error en formato de clave o datos: " + e.getMessage());
                throw e;
            } catch (javax.crypto.NoSuchPaddingException e) {
                System.err.println("Error de padding: " + e.getMessage());
            } catch (javax.crypto.NoSuchAlgorithmException e) {
                System.err.println("Algoritmo no disponible: " + e.getMessage());
            } catch (javax.crypto.BadPaddingException e) {
                System.err.println("Error de padding durante cifrado: " + e.getMessage());
            } catch (javax.crypto.IllegalBlockSizeException e) {
                System.err.println("Tamaño de bloque ilegal: " + e.getMessage());
            } catch (java.security.InvalidKeyException e) {
                System.err.println("Clave inválida: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error inesperado durante cifrado: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("La clave y el texto no pueden ser nulos");
        }
        return encryptText;
    }
    
    /**
     * Descifra un texto previamente cifrado utilizando el algoritmo AES.
     * 
     * @param key Clave de descifrado en formato hexadecimal
     * @param text Texto cifrado en formato hexadecimal a descifrar
     * @return Texto descifrado, o null si ocurre un error
     * @throws IllegalArgumentException Si la clave o el texto son nulos
     */
    public static String desencryptData(String key, String text) {
        String desencryptText = null;
        if (key != null && text != null) {
            try {
                byte[] bytes = hexStringToByteArray(key.toUpperCase());
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, KEY_ALGORITHM);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                cipher.init(DECRYPT_MODE, skeySpec);
                byte[] original = cipher.doFinal(hexStringToByteArray(text));
                desencryptText = new String(original);
            } catch (IllegalArgumentException e) {
                System.err.println("Error en formato de clave o datos: " + e.getMessage());
                throw e;
            } catch (javax.crypto.NoSuchPaddingException e) {
                System.err.println("Error de padding: " + e.getMessage());
            } catch (javax.crypto.NoSuchAlgorithmException e) {
                System.err.println("Algoritmo no disponible: " + e.getMessage());
            } catch (javax.crypto.BadPaddingException e) {
                System.err.println("Error de padding durante descifrado - posible clave incorrecta: " + e.getMessage());
            } catch (javax.crypto.IllegalBlockSizeException e) {
                System.err.println("Tamaño de bloque ilegal: " + e.getMessage());
            } catch (java.security.InvalidKeyException e) {
                System.err.println("Clave inválida: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error inesperado durante descifrado: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("La clave y el texto no pueden ser nulos");
        }
        return desencryptText;
    }
    
    /**
     * Cifra un array de bytes utilizando el algoritmo AES con la clave proporcionada.
     * 
     * @param key Clave de cifrado en formato hexadecimal
     * @param text Array de bytes a cifrar
     * @return Array de bytes cifrado, o null si ocurre un error
     * @throws IllegalArgumentException Si la clave o el texto son nulos
     */
    public static byte[] encryptDataBytes(String key, byte[] text) {
        byte[] encryptText = null;
        if (key != null && text != null) {
            try {
                byte[] bytes = hexStringToByteArray(key.toUpperCase());
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, KEY_ALGORITHM);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                cipher.init(ENCRYPT_MODE, skeySpec);
                byte[] encrypted = cipher.doFinal(text);
                encryptText = encrypted;
            } catch (IllegalArgumentException e) {
                System.err.println("Error en formato de clave: " + e.getMessage());
                throw e;
            } catch (javax.crypto.NoSuchPaddingException e) {
                System.err.println("Error de padding: " + e.getMessage());
            } catch (javax.crypto.NoSuchAlgorithmException e) {
                System.err.println("Algoritmo no disponible: " + e.getMessage());
            } catch (javax.crypto.BadPaddingException e) {
                System.err.println("Error de padding durante cifrado: " + e.getMessage());
            } catch (javax.crypto.IllegalBlockSizeException e) {
                System.err.println("Tamaño de bloque ilegal: " + e.getMessage());
            } catch (java.security.InvalidKeyException e) {
                System.err.println("Clave inválida: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error inesperado durante cifrado: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("La clave y el texto no pueden ser nulos");
        }
        return encryptText;
    }
    
    /**
     * Descifra un array de bytes previamente cifrado utilizando el algoritmo AES.
     * 
     * @param key Clave de descifrado en formato hexadecimal
     * @param text Array de bytes cifrado a descifrar
     * @return Array de bytes descifrado, o null si ocurre un error
     * @throws IllegalArgumentException Si la clave o el texto son nulos
     */
    public static byte[] desencryptDataBytes(String key, byte[] text) {
        byte[] data = null;
        if (key != null && text != null) {
            try {
                byte[] bytes = hexStringToByteArray(key.toUpperCase());
                SecretKeySpec skeySpec = new SecretKeySpec(bytes, KEY_ALGORITHM);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                cipher.init(DECRYPT_MODE, skeySpec);
                byte[] original = cipher.doFinal(text);
                data = original;
            } catch (IllegalArgumentException e) {
                System.err.println("Error en formato de clave: " + e.getMessage());
                throw e;
            } catch (javax.crypto.NoSuchPaddingException e) {
                System.err.println("Error de padding: " + e.getMessage());
            } catch (javax.crypto.NoSuchAlgorithmException e) {
                System.err.println("Algoritmo no disponible: " + e.getMessage());
            } catch (javax.crypto.BadPaddingException e) {
                System.err.println("Error de padding durante descifrado - posible clave incorrecta: " + e.getMessage());
            } catch (javax.crypto.IllegalBlockSizeException e) {
                System.err.println("Tamaño de bloque ilegal: " + e.getMessage());
            } catch (java.security.InvalidKeyException e) {
                System.err.println("Clave inválida: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error inesperado durante descifrado: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("La clave y el texto no pueden ser nulos");
        }
        return data;
    }
}
