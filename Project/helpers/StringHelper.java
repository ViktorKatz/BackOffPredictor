package helpers;

import java.util.regex.Pattern;

class StringHelper {
	
	static final String notAllowedChars = "([^A-Za-z.!?])";
	static final Pattern notAllowedPattern = Pattern.compile(notAllowedChars);
	
	String removeUnwantedChars(String s) {
		return s.replaceAll(notAllowedChars, "");
	}
	
	String replaceEOSChars(String s) {
		return s.replaceAll("[.?!]", " EOS");
	}
	
	String replaceNUMChars(String s) {
		return s.replaceAll(" \\d+\\w+", " NUMBER");
	}
	
	String[] divideToUnigrams(String text) {
		return text.split(" ");		
	}
}
