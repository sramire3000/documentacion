package com.example.demo.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para EcryptBuilder
 * 
 * @author Ejemplo
 * @version 1.2
 */
class EcryptBuilderTest {

    // Clave AES válida de 128 bits (16 bytes) en formato hexadecimal
    private static final String VALID_KEY_128 = "2B7E151628AED2A6ABF7158809CF4F3C";
    
    // Clave AES válida de 256 bits (32 bytes) en formato hexadecimal  
    private static final String VALID_KEY_256 = "2B7E151628AED2A6ABF7158809CF4F3C2B7E151628AED2A6ABF7158809CF4F3C";
    
    // Clave inválida (longitud incorrecta)
    private static final String INVALID_KEY_LENGTH = "2B7E151628AED2A6"; // Solo 8 bytes
    
    // Clave con caracteres no hexadecimales
    private static final String NON_HEX_KEY = "2B7E151628AED2A6ABF7158809CF4F3G";
    
    // Texto de prueba
    private static final String TEST_TEXT = "Hola mundo, esto es una prueba de cifrado AES";
    private static final String EMPTY_TEXT = "";
    private static final String SPECIAL_CHARS_TEXT = "Texto con ñ y acentos: áéíóú";

    @BeforeAll
    static void setUp() {
        System.out.println("=== Configurando tests de EcryptBuilder ===");
        System.out.println(EcryptBuilder.getCipherInfo());
        System.out.println("===========================================");
    }

    @Test
    void testGetCipherInfo() {
        String info = EcryptBuilder.getCipherInfo();
        assertNotNull(info);
        assertTrue(info.contains("Algoritmos a probar"));
    }

    /**
     * Prueba la conversión de array de bytes a hexadecimal y viceversa
     */
    @Test
    void testByteArrayHexStringConversion() {
        System.out.println("Ejecutando testByteArrayHexStringConversion...");
        
        byte[] originalBytes = {0x12, 0x34, 0x56, 0x78, (byte)0xAB, (byte)0xCD, (byte)0xEF};
        
        String hexString = EcryptBuilder.byteArrayToHexString(originalBytes);
        assertNotNull(hexString);
        assertEquals("12345678ABCDEF", hexString);
        
        byte[] convertedBytes = EcryptBuilder.hexStringToByteArray(hexString);
        assertArrayEquals(originalBytes, convertedBytes);
        
        System.out.println("✓ Conversión byte-hex-byte exitosa");
    }
    
    /**
     * Prueba el comportamiento de los métodos de cifrado con entradas inválidas
     */
    @Test
    void testEncryptMethodsWithInvalidHexInput() {
        System.out.println("Ejecutando testEncryptMethodsWithInvalidHexInput...");
        
        // Test con clave que tiene caracteres no hexadecimales
        String result1 = EcryptBuilder.encryptData("INVALID_HEX_KEY", TEST_TEXT);
        assertNull(result1, "encryptData debería retornar null con clave hexadecimal inválida");
        System.out.println("✓ encryptData con clave hexadecimal inválida manejada correctamente");

        // Test con texto cifrado inválido en descifrado
        String result2 = EcryptBuilder.desencryptData(VALID_KEY_128, "INVALID_CIPHER_TEXT");
        assertNull(result2, "desencryptData debería retornar null con texto cifrado hexadecimal inválido");
        System.out.println("✓ desencryptData con texto cifrado hexadecimal inválido manejado correctamente");

        // Test con bytes y clave inválida
        byte[] testBytes = TEST_TEXT.getBytes();
        byte[] result3 = EcryptBuilder.encryptDataBytes("INVALID_HEX_KEY", testBytes);
        assertNull(result3, "encryptDataBytes debería retornar null con clave hexadecimal inválida");
        System.out.println("✓ encryptDataBytes con clave hexadecimal inválida manejada correctamente");

        System.out.println("✓ Todos los métodos manejan correctamente entradas hexadecimales inválidas");
    }


    /**
     * Prueba byteArrayToHexString con valores null y vacíos
     */
    @Test
    void testByteArrayToHexStringEdgeCases() {
        System.out.println("Ejecutando testByteArrayToHexStringEdgeCases...");
        
        // Array nulo - DEBE retornar null
        assertNull(EcryptBuilder.byteArrayToHexString(null));
        System.out.println("✓ Array nulo manejado correctamente");

        // Array vacío - DEBE retornar cadena vacía
        byte[] emptyArray = new byte[0];
        assertEquals("", EcryptBuilder.byteArrayToHexString(emptyArray));
        System.out.println("✓ Array vacío manejado correctamente");
    }

    /**
     * Prueba el cifrado y descifrado con clave válida de 128 bits
     */
    @Test
    void testEncryptDecryptWithValidKey128() {
        System.out.println("Ejecutando testEncryptDecryptWithValidKey128...");
        
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        
        assertNotNull(encrypted, "El texto cifrado no debería ser nulo");
        assertFalse(encrypted.isEmpty(), "El texto cifrado no debería estar vacío");
        assertNotEquals(TEST_TEXT, encrypted, "El texto cifrado debería ser diferente al original");
        
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, encrypted);
        
        assertNotNull(decrypted, "El texto descifrado no debería ser nulo");
        assertEquals(TEST_TEXT, decrypted, "El texto descifrado debería ser igual al original");
        
        System.out.println("✓ Cifrado/descifrado con clave 128 bits exitoso");
    }

    /**
     * Prueba el cifrado con clave inválida (longitud incorrecta)
     */
    @Test
    void testEncryptWithInvalidKeyLength() {
        System.out.println("Ejecutando testEncryptWithInvalidKeyLength...");
        
        // Esto debería retornar null porque la longitud de clave es incorrecta
        String result = EcryptBuilder.encryptData(INVALID_KEY_LENGTH, TEST_TEXT);
        assertNull(result, "Debería retornar null con clave de longitud incorrecta");
        
        System.out.println("✓ Clave con longitud incorrecta manejada correctamente");
    }

    /**
     * Prueba el cifrado con clave que contiene caracteres no hexadecimales
     */
    @Test
    void testEncryptWithNonHexKey() {
        System.out.println("Ejecutando testEncryptWithNonHexKey...");
        
        // Verificar que hexStringToByteArray lanza NumberFormatException con caracteres no hexadecimales
        assertThrows(NumberFormatException.class, () -> {
            EcryptBuilder.hexStringToByteArray(NON_HEX_KEY);
        });
        
        // El método encryptData debería capturar cualquier excepción y retornar null
        String result = EcryptBuilder.encryptData(NON_HEX_KEY, TEST_TEXT);
        assertNull(result, "encryptData debería retornar null con clave que contiene caracteres no hexadecimales");
        
        System.out.println("✓ Clave con caracteres no hexadecimales manejada correctamente");
    }

    /**
     * Prueba el cifrado con texto nulo
     */
    @Test
    void testEncryptWithNullText() {
        System.out.println("Ejecutando testEncryptWithNullText...");
        
        // Esto DEBE lanzar IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.encryptData(VALID_KEY_128, null);
        });
        assertTrue(exception.getMessage().contains("no pueden ser nulos"));
        
        System.out.println("✓ Texto nulo manejado correctamente");
    }

    /**
     * Prueba el cifrado con clave nula
     */
    @Test
    void testEncryptWithNullKey() {
        System.out.println("Ejecutando testEncryptWithNullKey...");
        
        // Esto DEBE lanzar IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.encryptData(null, TEST_TEXT);
        });
        assertTrue(exception.getMessage().contains("no pueden ser nulos"));
        
        System.out.println("✓ Clave nula manejada correctamente");
    }

    /**
     * Prueba el descifrado con clave incorrecta
     */
    @Test
    void testDecryptWithWrongKey() {
        System.out.println("Ejecutando testDecryptWithWrongKey...");
        
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        assertNotNull(encrypted, "El cifrado debería funcionar con clave válida");
        
        // Intentar descifrar con clave diferente - DEBE retornar null
        String differentKey = "3C4F9C088571FB6AA2D8AE2816517E2B";
        String decrypted = EcryptBuilder.desencryptData(differentKey, encrypted);
        
        assertNull(decrypted, "Debería retornar null cuando la clave de descifrado es incorrecta");
        
        System.out.println("✓ Clave incorrecta en descifrado manejada correctamente");
    }

    /**
     * Prueba el descifrado con texto cifrado inválido (caracteres no hexadecimales)
     */
    @Test
    void testDecryptWithInvalidCipherText() {
        System.out.println("Ejecutando testDecryptWithInvalidCipherText...");
        
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, "INVALIDHEXSTRING");
        
        // DEBE retornar null porque el texto cifrado no es hexadecimal válido
        assertNull(decrypted, "Debería retornar null con texto cifrado que contiene caracteres no hexadecimales");
        
        System.out.println("✓ Texto cifrado inválido manejado correctamente");
    }

    /**
     * Prueba el descifrado con texto cifrado de longitud impar
     */
    @Test
    void testDecryptWithOddLengthCipherText() {
        System.out.println("Ejecutando testDecryptWithOddLengthCipherText...");
        
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, "123");
        
        // DEBE retornar null porque la longitud es impar
        assertNull(decrypted, "Debería retornar null con texto cifrado de longitud impar");
        
        System.out.println("✓ Texto cifrado con longitud impar manejado correctamente");
    }

    /**
     * Prueba el descifrado con texto cifrado nulo
     */
    @Test
    void testDecryptWithNullCipherText() {
        System.out.println("Ejecutando testDecryptWithNullCipherText...");
        
        // Esto DEBE lanzar IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.desencryptData(VALID_KEY_128, null);
        });
        assertTrue(exception.getMessage().contains("no pueden ser nulos"));
        
        System.out.println("✓ Texto cifrado nulo manejado correctamente");
    }

    /**
     * Prueba el descifrado con clave nula
     */
    @Test
    void testDecryptWithNullKey() {
        System.out.println("Ejecutando testDecryptWithNullKey...");
        
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        assertNotNull(encrypted, "El cifrado debería funcionar con clave válida");
        
        // Esto DEBE lanzar IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.desencryptData(null, encrypted);
        });
        assertTrue(exception.getMessage().contains("no pueden ser nulos"));
        
        System.out.println("✓ Clave nula en descifrado manejada correctamente");
    }

    /**
     * Prueba el cifrado y descifrado de bytes
     */
    @Test
    void testEncryptDecryptBytes() {
        System.out.println("Ejecutando testEncryptDecryptBytes...");
        
        byte[] originalBytes = TEST_TEXT.getBytes();
        
        byte[] encryptedBytes = EcryptBuilder.encryptDataBytes(VALID_KEY_128, originalBytes);
        assertNotNull(encryptedBytes, "El cifrado de bytes no debería retornar null");
        
        byte[] decryptedBytes = EcryptBuilder.desencryptDataBytes(VALID_KEY_128, encryptedBytes);
        assertNotNull(decryptedBytes, "El descifrado de bytes no debería retornar null");
        
        assertArrayEquals(originalBytes, decryptedBytes, "Los bytes descifrados deberían ser iguales a los originales");
        
        System.out.println("✓ Cifrado/descifrado de bytes exitoso");
    }

    /**
     * Prueba el cifrado de bytes con parámetros nulos
     */
    @Test
    void testEncryptBytesWithNullParameters() {
        System.out.println("Ejecutando testEncryptBytesWithNullParameters...");
        
        byte[] testBytes = TEST_TEXT.getBytes();
        
        // Clave nula - DEBE lanzar IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.encryptDataBytes(null, testBytes);
        });
        assertTrue(exception.getMessage().contains("no pueden ser nulos"));
        
        // Bytes nulos - DEBE lanzar IllegalArgumentException
        exception = assertThrows(IllegalArgumentException.class, () -> {
            EcryptBuilder.encryptDataBytes(VALID_KEY_128, null);
        });
        assertTrue(exception.getMessage().contains("no pueden ser nulos"));
        
        System.out.println("✓ Parámetros nulos en cifrado de bytes manejados correctamente");
    }

    /**
     * Prueba que el mismo texto cifrado con la misma clave produce el mismo resultado
     */
    @Test
    void testEncryptConsistency() {
        System.out.println("Ejecutando testEncryptConsistency...");
        
        String encrypted1 = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        String encrypted2 = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        
        assertNotNull(encrypted1, "Primer cifrado no debería ser null");
        assertNotNull(encrypted2, "Segundo cifrado no debería ser null");
        assertEquals(encrypted1, encrypted2, "El cifrado debería ser determinístico con la misma clave");
        
        System.out.println("✓ Consistencia del cifrado verificada");
    }

    /**
     * Prueba el formato del texto cifrado
     */
    @Test
    void testCipherTextFormat() {
        System.out.println("Ejecutando testCipherTextFormat...");
        
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        
        assertNotNull(encrypted);
        
        // Verificar que es una cadena hexadecimal válida
        assertTrue(encrypted.matches("^[0-9A-F]+$"), "El texto cifrado debe ser hexadecimal en mayúsculas");
        assertEquals(0, encrypted.length() % 2, "La longitud del texto cifrado debe ser par");
        
        System.out.println("✓ Formato del texto cifrado verificado");
    }

    /**
     * Prueba básica de funcionalidad
     */
    @Test
    void testBasicFunctionality() {
        System.out.println("Ejecutando testBasicFunctionality...");
        
        int iterations = 5;
        for (int i = 0; i < iterations; i++) {
            String testData = TEST_TEXT + " - Iteración " + i;
            String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, testData);
            assertNotNull(encrypted, "Cifrado no debería ser null en iteración " + i);
            
            String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, encrypted);
            assertNotNull(decrypted, "Descifrado no debería ser null en iteración " + i);
            
            assertEquals(testData, decrypted, "Datos deberían coincidir en iteración " + i);
        }
        
        System.out.println("✓ Funcionalidad básica verificada (" + iterations + " iteraciones)");
    }

    /**
     * Prueba integración completa del flujo
     */
    @Test
    void testCompleteIntegrationFlow() {
        System.out.println("Ejecutando testCompleteIntegrationFlow...");
        
        // Paso 1: Cifrar
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, TEST_TEXT);
        assertNotNull(encrypted);
        
        // Paso 2: Descifrar
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, encrypted);
        assertNotNull(decrypted);
        
        // Paso 3: Verificar
        assertEquals(TEST_TEXT, decrypted);
        
        // Paso 4: Verificar que el texto original y cifrado son diferentes
        assertNotEquals(TEST_TEXT, encrypted);
        
        // Paso 5: Verificar formato hexadecimal
        assertTrue(encrypted.matches("^[0-9A-F]+$"));
        
        System.out.println("✓ Flujo de integración completo verificado");
    }

    /**
     * Prueba con texto vacío
     */
    @Test
    void testWithEmptyText() {
        System.out.println("Ejecutando testWithEmptyText...");
        
        String encrypted = EcryptBuilder.encryptData(VALID_KEY_128, EMPTY_TEXT);
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());
        
        String decrypted = EcryptBuilder.desencryptData(VALID_KEY_128, encrypted);
        assertNotNull(decrypted);
        assertEquals(EMPTY_TEXT, decrypted);
        
        System.out.println("✓ Texto vacío manejado correctamente");
    }
}
