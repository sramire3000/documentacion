package com.example.demo.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.crypto.BadPaddingException;
import java.security.InvalidKeyException;

/**
 * Clase de prueba para EcryptBuilder
 * 
 * @author Ejemplo
 * @version 1.0
 */
class EcryptBuilderTest {

    // Clave AES válida de 128 bits (16 bytes) en formato hexadecimal
    private static final String VALID_KEY_128 = "2B7E151628AED2A6ABF7158809CF4F3C";
    
    // Clave AES válida de 256 bits (32 bytes) en formato hexadecimal  
    private static final String VALID_KEY_256 = "2B7E151628AED2A6ABF7158809CF4F3C2B7E151628AED2A6ABF7158809CF4F3C";
    
    // Clave inválida (longitud incorrecta)
    private static final String INVALID_KEY = "2B7E151628AED2A6";
    
    // Texto de prueba
    private static final String TEST_TEXT = "Hola mundo, esto es una prueba de cifrado AES";
    private static final String EMPTY_TEXT = "";
    private static final String SPECIAL_CHARS_TEXT = "Texto con ñ y acentos: áéíóú";

    /**
     * Prueba la conversión de array de bytes a hexadecimal y viceversa
     */
    @Test
    void testByteArrayHexStringConversion() {
        byte[] originalBytes = {0x12, 0x34, 0x56, 0x78, (byte)0xAB, (byte)0xCD, (byte)0xEF};
        
        String hexString = EcryptBuilder.byteArrayToHexString(originalBytes);
        assertNotNull(hexString);
        assertEquals("12345678ABCDEF", hexString);
        
        byte[] convertedBytes = EcryptBuilder.hexStringToByteArray(hexString);
        assertArrayEquals(originalBytes, convertedBytes);
    }

    /**
     * Prueba la conversión con cadena hexadecimal inválida
     */
    @Test
    void testHexStringToByteArrayWithInvalidInput() {
        // Cadena con longitud impar
        assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.hexStringToByteArray("123");
        });
        
        // Cadena nula
        assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.hexStringToByteArray(null);
        });
    }

    /**
     * Prueba el cifrado y descifrado con clave válida de 128 bits
     */
    @Test
    void testEncryptDecryptWithValidKey128() {
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());
        assertNotEquals(TEST_TEXT, encrypted);
        
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, encrypted);
        
        assertNotNull(decrypted);
        assertEquals(TEST_TEXT, decrypted);
    }

    /**
     * Prueba el cifrado y descifrado con clave válida de 256 bits
     */
    @Test
    void testEncryptDecryptWithValidKey256() {
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_256, TEST_TEXT);
        
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());
        
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_256, encrypted);
        
        assertNotNull(decrypted);
        assertEquals(TEST_TEXT, decrypted);
    }

    /**
     * Prueba el cifrado y descifrado con texto vacío
     */
    @Test
    void testEncryptDecryptWithEmptyText() {
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, EMPTY_TEXT);
        
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());
        
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, encrypted);
        
        assertNotNull(decrypted);
        assertEquals(EMPTY_TEXT, decrypted);
    }

    /**
     * Prueba el cifrado y descifrado con caracteres especiales
     */
    @Test
    void testEncryptDecryptWithSpecialCharacters() {
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, SPECIAL_CHARS_TEXT);
        
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());
        
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, encrypted);
        
        assertNotNull(decrypted);
        assertEquals(SPECIAL_CHARS_TEXT, decrypted);
    }

    /**
     * Prueba el cifrado con clave inválida
     */
    @Test
    void testEncryptWithInvalidKey() {
        assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.encryptData(INVALID_KEY, TEST_TEXT);
        });
    }

    /**
     * Prueba el cifrado con texto nulo
     */
    @Test
    void testEncryptWithNullText() {
        assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.encryptData(VALID_KEY_128, null);
        });
    }

    /**
     * Prueba el descifrado con clave incorrecta
     */
    @Test
    void testDecryptWithWrongKey() {
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        assertNotNull(encrypted);
        
        // Intentar descifrar con clave diferente
        String differentKey = "3C4F9C088571FB6AA2D8AE2816517E2B";
        String decrypted = EcryptBuilder.desencryptData(differentKey, encrypted);
        
        // Debería fallar silenciosamente y retornar null
        assertNull(decrypted);
    }

    /**
     * Prueba el descifrado con texto cifrado inválido
     */
    @Test
    void testDecryptWithInvalidCipherText() {
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, "INVALIDHEXSTRING");
        
        // Debería fallar silenciosamente y retornar null
        assertNull(decrypted);
    }

    /**
     * Prueba el cifrado y descifrado de bytes
     */
    @Test
    void testEncryptDecryptBytes() {
        byte[] originalBytes = TEST_TEXT.getBytes();
        
        byte[] encryptedBytes = EcryptBuilder.encryptDataBytes(VALID_KEY_128, originalBytes);
        
        assertNotNull(encryptedBytes);
        assertNotEquals(originalBytes.length, encryptedBytes.length); // El cifrado puede cambiar la longitud
        
        byte[] decryptedBytes = EcryptBuilder.desencryptDataBytes(VALID_KEY_128, encryptedBytes);
        
        assertNotNull(decryptedBytes);
        assertArrayEquals(originalBytes, decryptedBytes);
    }

    /**
     * Prueba el cifrado de bytes con parámetros nulos
     */
    @Test
    void testEncryptBytesWithNullParameters() {
        assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.encryptDataBytes(null, TEST_TEXT.getBytes());
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.encryptDataBytes(VALID_KEY_128, null);
        });
    }

    /**
     * Prueba que el mismo texto cifrado con la misma clave produce el mismo resultado
     */
    @Test
    void testEncryptConsistency() {
        String encrypted1 = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        String encrypted2 = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        
        assertEquals(encrypted1, encrypted2, "El cifrado debería ser determinístico con la misma clave");
    }

    /**
     * Prueba el rendimiento con múltiples operaciones
     */
    @Test
    void testPerformanceWithMultipleOperations() {
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 100; i++) {
            String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT + i);
            String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, encrypted);
            assertEquals(TEST_TEXT + i, decrypted);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("100 operaciones de cifrado/descifrado tomó: " + duration + " ms");
        
        // Verificar que no toma demasiado tiempo (menos de 10 segundos)
        assertTrue(duration < 10000, "Las operaciones no deberían tomar más de 10 segundos");
    }

    /**
     * Prueba el formato del texto cifrado
     */
    @Test
    void testCipherTextFormat() {
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        
        assertNotNull(encrypted);
        
        // Verificar que es una cadena hexadecimal válida
        assertTrue(encrypted.matches("^[0-9A-F]+$"), "El texto cifrado debe ser hexadecimal en mayúsculas");
        assertEquals(0, encrypted.length() % 2, "La longitud del texto cifrado debe ser par");
    }
}
