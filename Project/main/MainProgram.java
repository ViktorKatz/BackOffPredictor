package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import helpers.StringHelper;
import model.NgramDictionary;
import model.Prediction;

public final class MainProgram {

	private static final int N = 3;
	private static final int predictionsPerNgram = 2;
	private static NgramDictionary currentDictionary = new NgramDictionary();
	private static double discounts[] = new double[N];
	
	static {
		for(int i=0;i<N;++i) {
			discounts[i]=0;
		}
	}
	
	public static void setDiscount(int nGram, double discount) {
		discounts[nGram-1] = discount;
	}
	
	public static double getDiscount(int nGram) {
		return discounts[nGram-1];
	}
	
	public static double getCoefficient(int nGram) {
		double result = 1;
		for(int i= N-1;i>=nGram;--i) {
			result *= discounts[i];
		}
		return result  * (1 - getDiscount(nGram));
	}
	
	public static void clearDictionary(){
		currentDictionary = new NgramDictionary();
	}
	
	/**
	 * Cita ranije sacuvan recnik iz .dict fajla
	 * @param filename Ime fajla sa sve ekstenzijom, bez putanje do njega.
	 * @throws FileNotFoundException - ako nema tog fajla, vrsta IOExceptiona.
	 * @throws IOException - Neki drugi IO exception, najcesce ako nema dozvolu od sistema da pristupi.
	 */
	public static void readDictionary(String filename) throws IOException {
		currentDictionary.loadFromFile(filename);
	}
	
	/**
	 * Cuva recnik u fajl. Ako nema odgovarajuceg fajla, pravi novi.
	 * @param filename Ime fajla, bez putanje, sa ekstenzijom.
	 * @throws IOException - Neuspesno cuvanje.
	 */
	public static void saveDictionary(String filename) throws IOException {
		currentDictionary.saveToFile(filename);
	}
	
	public static void makeDictionaryFromDirectories(List<String> paths) throws IOException {
		currentDictionary = new NgramDictionary(paths, N);
	}
	
	public static List<Prediction> getPredictions(String[] words){
		String prefix = Arrays.stream(words).reduce("", (s1,s2) -> s1+" "+s2);
		return getPredictions(prefix);
	}
	
	public static List<Prediction> getPredictions(String prefix){
		String cleanPrefix = StringHelper.removeUnwantedChars(prefix);
		String adjustedPrefix = StringHelper.replaceNUMChars(StringHelper.replaceEOSChars(cleanPrefix));
		String[] words = StringHelper.divideToUnigrams(adjustedPrefix);
		
		List<Prediction> results = new ArrayList<Prediction>();
		
		List<Prediction> gramPredictions;
		for(int i= N-1 < words.length ? N-1 : words.length ;i>=0;--i) {
			String[] lastIWords = StringHelper.getNLastWords(words, i);
			
			gramPredictions=currentDictionary.getPredictions(lastIWords, predictionsPerNgram);
			
			double coefficient = getCoefficient(i+1);
			
			gramPredictions.stream().forEach( newPrediction -> {
					newPrediction.probability *= coefficient;
					Optional<Prediction> existing = results.parallelStream()
							.filter(presentPrediction -> presentPrediction.word.equals(newPrediction.word))
							.findAny();
					
					if(existing.isPresent())
						existing.get().probability+=newPrediction.probability;
					else
						results.add(newPrediction);
				});
		}
		
		results.removeIf(prediction -> prediction.probability == 0);//Ako ne postoji discount
		
		return results;
	}
	
	public static void main(String[] args) throws IOException {
		readDictionary("FirstTestFromWikipedia.dict");
		
		setDiscount(3, 0.2);
		setDiscount(2, 0.3);
		System.out.println(getCoefficient(1));
		System.out.println(getCoefficient(2));
		System.out.println(getCoefficient(3));
		
		System.out.println(getPredictions("se koriste"));
		
		clearDictionary();
		List<String> paths = new ArrayList<String>();
		paths.add("../Tekstovi/ETF");
		makeDictionaryFromDirectories(paths);
		//saveDictionary("TestEtf.dict");
	}

}
