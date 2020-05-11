package helpers;

import java.util.regex.Pattern;

public abstract class StringHelper {
	
	static final String notAllowedChars = "([^A-Za-z.!?])";
	static final Pattern notAllowedPattern = Pattern.compile(notAllowedChars);
	
	public static String removeUnwantedChars(String s) {
		return s.replaceAll(notAllowedChars, "");
	}
	
	public static String replaceEOSChars(String s) {
		return s.replaceAll("[.?!]", " EOS");
	}
	
	public static String replaceNUMChars(String s) {
		return s.replaceAll(" \\d+\\w+", " NUMBER");
	}
	
	public static String[] divideToUnigrams(String text) {
		return text.split(" ");		
	}
	
	public static String mergeStrings(String[] words) {
		StringBuilder result=new StringBuilder();
		for (String w : words) {
			result.append(w.trim() );
			result.append(' ');
		}
		result.setLength(result.length()-1);
		
		return result.toString();
	}
	
	public static void main(String[] args) {
		String[] words = {"asd","awda","eqwe"};
		
		System.out.println( mergeStrings(words) );
	}
}
