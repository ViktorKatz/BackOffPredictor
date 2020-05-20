package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import helpers.StringHelper;
import model.NgramDictionary;
import model.Prediction;

public final class MainProgram {

	private static final int N = 3;
	private static final int predictionsPerNgram = 5;
	private static NgramDictionary currentDictionary = new NgramDictionary();
	private static double discounts[] = new double[N];
	
	static {
		for(int i=0;i<N;++i) {
			discounts[i]=0;
		}
	}
	
	public void setDiscount(int nGram, double discount) {
		discounts[nGram-1] = discount;
	}
	
	public double getDiscount(int nGram) {
		return discounts[nGram-1];
	}
	
	public double getCombinedDiscount(int nGram) {
		double result = 1;
		for(int i=N-1;i>=nGram-1;--i) {
			result*=discounts[i];
		}
		return result;
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
	
	public static List<Prediction> getPredictions(String prefix){
		String cleanPrefix = StringHelper.removeUnwantedChars(prefix);
		String adjustedPrefix = StringHelper.replaceNUMChars(StringHelper.replaceEOSChars(cleanPrefix));
		String[] words = StringHelper.divideToUnigrams(adjustedPrefix);
		
		List<Prediction> results = new ArrayList<Prediction>();
		
		List<Prediction> gramPredictions;
		for(int i=N-1;i>=0;--i) {
			//gramPredictions=currentDictionary.getPredictions(/**/, predictionsPerNgram);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
