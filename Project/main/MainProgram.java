package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.NgramDictionary;

public final class MainProgram {

	//Maksimalno se koriste trigrami, ne ide se do stepena 4+;
	private static final int N = 3;
	private static NgramDictionary currentDictionary = new NgramDictionary();
	private static double discounts[] = new double[N];
	
	public void setDiscount(int nGram, double discount) {
		discounts[nGram-1] = discount;
	}
	
	public double getDiscount(int nGram) {
		return discounts[nGram-1];
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
