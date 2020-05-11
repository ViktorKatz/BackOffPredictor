package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helpers.StringHelper;

public class NgramDictionary implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, Ngram> allNgrams = new HashMap<String, Ngram>();
	
	public NgramDictionary() {
		super();
	}
	
	public void addInstance(String prefix, String prediction) {
		addInstance( StringHelper.divideToUnigrams(prefix.trim()), prediction);
	}
	
	public void addInstance(String[] prefix, String prediction) {
		String key = StringHelper.mergeStrings(prefix).toLowerCase();
		
		if(!allNgrams.containsKey(key)) {
			allNgrams.put(key, new Ngram( prefix.length , prefix));
		}
		
		allNgrams.get(key).addPrediction(prediction);
	}
	
	public List<Prediction> getPredictions(String[] prefix, int count){
		String key = StringHelper.mergeStrings(prefix).toLowerCase();
		
		if(!allNgrams.containsKey(key)) {
			return new ArrayList<Prediction>();
		}
		else {
			return allNgrams.get(key).getPredictions(count);
		}
		
	}
	
	public List<Prediction> getPredictions(String prefix, int count){
		return getPredictions(StringHelper.divideToUnigrams(prefix.trim()), count);
	}
	
	public NgramDictionary(String filespath, int maxGram) {
		
	}
	
	private void addAllNgrams(String text, int upto) {
		String bareText = StringHelper.removeUnwantedChars(text.trim());
		
		String adjustedText = StringHelper.replaceNUMChars(StringHelper.replaceEOSChars(bareText));
		
		String[] words = StringHelper.divideToUnigrams(adjustedText);
		
		for(int N = 0; N<upto;++N) {
			String[] prefix = new String[N];
			for(int i = N; i<words.length;++i) {
				for(int k=0; k<N;++k) {
					prefix[k] = words[i-N+k];
				}
				addInstance(prefix, words[i]);
			}
		}
	}
	
	public static void main(String[] args) {
		NgramDictionary dict = new NgramDictionary();

		dict.addInstance("Alex nosi", "kosmodisk");
		dict.addInstance("Alex nosi", "kosmodisk");
		dict.addInstance("Alex nosi", "cevap");
		dict.addInstance("Alex nosi", "cevap");
		dict.addInstance("Alex nosi", "cevap");
		dict.addInstance("Alex nosi", "cevap");
		dict.addInstance("Alex nosi", "pendrek");
		dict.addInstance("Alex nosi", "pendrek");
		dict.addInstance("Alex nosi", "pendrek");
		dict.addInstance("Alex nosi", "kosmodisk");
		dict.addInstance("Alex nosi", "kosmodisk");
		dict.addInstance("Alex nosi", "kosmodisk");
		
		dict.addInstance("Viktor je", "lep");
		dict.addInstance("Viktor je", "lep");
		dict.addInstance("Viktor je", "zgodan");
		dict.addInstance("Viktor je", "lep");
		dict.addInstance("Viktor je", "pametan");
		dict.addInstance("Viktor je", "pametan");
		
		dict.addAllNgrams("Alex ima kapu, a Viktor ima naocare. A da li Alex ima Viktora? Mozda Alex nema kosmodisk, ali ima kapu da.", 3);
		
		System.out.println(dict.getPredictions("Alex nosi", 5));
		System.out.println(dict.getPredictions("Viktor je", 5));
		dict.addInstance("Alex", "ima");
		dict.addInstance("Alex", "ima");
		System.out.println(dict.getPredictions("Alex", 5));
	}

}
