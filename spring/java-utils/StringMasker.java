package sv.com.app.util;


public class StringMasker{
	
	public static String repeatChar(char c, int count){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < count;i++){
			sb.append(c);
		}
		return sb.toString();
	}
	
	
	
}