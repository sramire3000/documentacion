package com.example.demo.util;

/**
 * Utilidad para enmascarar cadenas sensibles mostrando solo una parte de los caracteres.
 * Proporciona funcionalidades para enmascarar direcciones de correo electrónico y números
 * como tarjetas de crédito, manteniendo visibles solo los últimos caracteres.
 * 
 * @author Ejemplo
 * @version 1.0
 */
public class StringMasker {
    
    /**
     * Enmascara una cadena sensible según su formato.
     * 
     * <p>Para direcciones de correo electrónico:
     * <ul>
     *   <li>Mantiene visibles los últimos 2 caracteres de la parte local (antes de @)</li>
     *   <li>Muestra completo el dominio (después de @)</li>
     *   <li>Ejemplo: "usuario@dominio.com" → "******io@dominio.com"</li>
     * </ul>
     * 
     * <p>Para otros tipos de cadenas (como números de tarjeta):
     * <ul>
     *   <li>Mantiene visibles los últimos 4 caracteres</li>
     *   <li>Ejemplo: "1234567890123456" → "************3456"</li>
     * </ul>
     * 
     * <p>Si la cadena es nula o tiene 4 caracteres o menos, se retorna sin modificar.
     *
     * @param input La cadena a enmascarar. Puede ser nula.
     * @return La cadena enmascarada según las reglas especificadas, o la cadena original
     *         si es nula o tiene longitud menor o igual a 4 caracteres.
     */
    public static String maskString(String input){
        
        if (input == null || input.length() <= 4) {
            return input;
        }
        
        // Si es un correo electrónico (contiene @ y tiene formato válido)
        if (isValidEmail(input)) {
            int atIndex = input.indexOf("@");
            String localPart  = input.substring(0, atIndex);
            String domainPart = input.substring(atIndex);
            
            // Si la parte local tiene 2 caracteres o menos, no enmascarar
            if (localPart.length() <= 2) {
                return input;
            }
            
            // Mostrar los últimos 2 caracteres, enmascarar el resto
            int visibleChars = 2;
            int maskLength = localPart.length() - visibleChars;
            String maskedLocal = repeatChar('*', maskLength) + localPart.substring(maskLength);
            return maskedLocal + domainPart;
        }
        
        // Si es un número de tarjeta o similar
        int visibleChars = 4;
        int maskLength = input.length() - visibleChars;
        String masked = repeatChar('*', maskLength);
        String visible = input.substring(maskLength);
        return masked + visible;
    }
    
    /**
     * Valida si una cadena tiene formato de email básico.
     * Debe contener @ y tener caracteres tanto antes como después del @.
     *
     * @param input La cadena a validar.
     * @return true si tiene formato de email básico, false en caso contrario.
     */
    private static boolean isValidEmail(String input) {
        int atIndex = input.indexOf("@");
        // Debe tener @ y caracteres antes y después del @
        return atIndex > 0 && atIndex < input.length() - 1;
    }
    
    /**
     * Crea una cadena compuesta por la repetición de un carácter específico.
     * 
     * <p>Si el conteo es 0 o negativo, retorna una cadena vacía.
     *
     * @param c El carácter a repetir.
     * @param count El número de veces que se debe repetir el carácter.
     * @return Una cadena compuesta por el carácter repetido el número especificado de veces,
     *         o una cadena vacía si count es menor o igual a 0.
     */
    private static String repeatChar(char c, int count){
        if (count <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; i++){
            sb.append(c);
        }
        return sb.toString();
    }
}
