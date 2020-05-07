package model;

import java.io.Serializable;

public class NgramDictionary implements Serializable {

	private static final long serialVersionUID = 1L;

	//Decomment upon use
	//private HashMap<String, Ngram> allNgrams = new HashMap<String, Ngram>();
	
	public NgramDictionary() {
		super();
	}
	
	public void addInstance(String[] prefix, String prediction) {
		//TODO Dodati Ngram u allNgrams
	}
	
	public NgramDictionary(String filepath) {
		//TODO Nauciti kako raditi sa fajlovima
	}

}
