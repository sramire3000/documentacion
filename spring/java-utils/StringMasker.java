package sv.com.app.util;


public class StringMasker{
	
	public static maskString(String input){
		
		if (input==null || input.length() <= 4) {
			return input;
		}
		
		//Si es un correo electonica
		if (input.contains("@")) {
			int atIndex = input.indexOf("@");
			String localPart  = input.substring(0, atIndex);
			String domainPart = input.substring(atIndex);
			
			//Mostrar los ultimos 4 caracteres
			int visibleChars = 2;
			int maskLength = Math.max(0, localPart.length() - visibleChars);
			String maskedLocal = repeatChar('*', maskLength) + localPart.substring(maskLength);
			return maskedLocal + domainPart;
		}
		
		// Si es un nuero de tarjeta o similar
		int visibleChars = 4;
		int maskLength = input.length() - visibleChars;
		String maskedLocal = repeatChar('*', Math.max(0, maskLength));
		String visible = input.substring(maskLength);
		return masked + visible;
	}
	
	public static String repeatChar(char c, int count){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < count;i++){
			sb.append(c);
		}
		return sb.toString();
	}
	
	
	
}