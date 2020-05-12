package helpers;

import java.util.regex.Pattern;

public abstract class StringHelper {
	
	static final String notAllowedChars = "([^A-Za-z0-9šđčćžŠĐČĆŽ\\s\\r\\n.!?-])";
	static final Pattern notAllowedPattern = Pattern.compile(notAllowedChars);
	
	public static String cyrillicToLatin(String s) {
		String res = s.replaceAll("а", "a");
		res = res.replaceAll("б", "b");
		res = res.replaceAll("в", "v");
		res = res.replaceAll("г", "g");
		res = res.replaceAll("д", "d");
		res = res.replaceAll("ђ", "đ");
		res = res.replaceAll("е", "e");
		res = res.replaceAll("ж", "ž");
		res = res.replaceAll("з", "z");
		res = res.replaceAll("и", "i");
		res = res.replaceAll("ј", "j");
		res = res.replaceAll("к", "k");
		res = res.replaceAll("л", "l");
		res = res.replaceAll("љ", "lj");
		res = res.replaceAll("м", "m");
		res = res.replaceAll("н", "n");
		res = res.replaceAll("њ", "nj");
		res = res.replaceAll("о", "o");
		res = res.replaceAll("п", "p");
		res = res.replaceAll("р", "r");
		res = res.replaceAll("с", "s");
		res = res.replaceAll("т", "t");
		res = res.replaceAll("ћ", "ć");
		res = res.replaceAll("у", "u");
		res = res.replaceAll("ф", "f");
		res = res.replaceAll("х", "h");
		res = res.replaceAll("ц", "c");
		res = res.replaceAll("ч", "č");
		res = res.replaceAll("џ", "dž");
		res = res.replaceAll("ш", "š");
		
		res = res.replaceAll("А", "A");
		res = res.replaceAll("Б", "B");
		res = res.replaceAll("В", "V");
		res = res.replaceAll("Г", "G");
		res = res.replaceAll("Д", "D");
		res = res.replaceAll("Ђ", "Đ");
		res = res.replaceAll("Е", "E");
		res = res.replaceAll("Ж", "Ž");
		res = res.replaceAll("З", "Z");
		res = res.replaceAll("И", "I");
		res = res.replaceAll("Ј", "J");
		res = res.replaceAll("К", "K");
		res = res.replaceAll("Л", "L");
		res = res.replaceAll("Љ", "lj");
		res = res.replaceAll("М", "M");
		res = res.replaceAll("Н", "N");
		res = res.replaceAll("Њ", "Nj");
		res = res.replaceAll("О", "O");
		res = res.replaceAll("П", "P");
		res = res.replaceAll("Р", "R");
		res = res.replaceAll("С", "S");
		res = res.replaceAll("Т", "T");
		res = res.replaceAll("Ћ", "Ć");
		res = res.replaceAll("У", "U");
		res = res.replaceAll("Ф", "F");
		res = res.replaceAll("Х", "H");
		res = res.replaceAll("Ц", "C");
		res = res.replaceAll("Ч", "Č");
		res = res.replaceAll("Џ", "Dž");
		res = res.replaceAll("Ш", "Š");
		
		return res;
	}
	
	public static String removeParentheses(String s) {
		return s.replaceAll("\\[.*?\\]", "");
	}
	
	public static String removeUnwantedChars(String s) {
		return s.replaceAll(notAllowedChars, "");
	}
	
	public static String replaceEOSChars(String s) {
		return s.replaceAll("[.?!]", " EOS ");
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
		
		System.out.println( cyrillicToLatin("Зашто немаш четири ноге?") );
	}
}
