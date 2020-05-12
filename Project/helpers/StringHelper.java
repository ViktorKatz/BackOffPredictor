package helpers;

import java.util.regex.Pattern;

public abstract class StringHelper {
	
	static final String notAllowedChars = "([^A-Za-z0-9šđčćžŠĐČĆŽ\\s\\r\\n.!?-])";
	static final Pattern notAllowedPattern = Pattern.compile(notAllowedChars);
	
	public static String removeParentheses(String s) {
		return s.replaceAll("\\[.*?\\]", "");
	}
	
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
		return text.split("\\s");
	}
	
	public static boolean isTextFile(String filename) {
		if(filename.split("\\.").length==0)
			return false;
		return filename.split("\\.")[filename.split("\\.").length-1].contentEquals("txt");
	}
	
	public static String mergeStrings(String[] words) {
		StringBuilder result=new StringBuilder();
		for (String w : words) {
			result.append(w.trim() );
			result.append(' ');
		}
		
		if(result.length() >0 ) {
			result.setLength(result.length()-1);
		}
		
		return result.toString();
	}
	
	public static void main(String[] args) {
		String[] words = {"asd!","aw-da"," 6p"};
		
		words[0] = replaceEOSChars(words[0]);
		words[1] = removeUnwantedChars(words[1]);
		words[2] = replaceNUMChars(words[2]);
		
		System.out.println( mergeStrings(words) );
		
		System.out.println( isTextFile("tralala.txt") );
	}
}
