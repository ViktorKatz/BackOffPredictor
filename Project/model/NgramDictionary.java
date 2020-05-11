package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helpers.StringHelper;
import model.FollowingWord;

public class NgramDictionary implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, Ngram> allNgrams = new HashMap<String, Ngram>();
	
	public NgramDictionary() {
		super();
	}
	
	public void addInstance(String[] prefix, String prediction) {
		String key = StringHelper.mergeStrings(prefix);
		
		if(!allNgrams.containsKey(key)) {
			allNgrams.put(key, new Ngram( prefix.length , prefix));
		}
		
		allNgrams.get(key).addPrediction(prediction);
	}
	
	public List<Prediction> getPredictions(String[] prefix, int count){
		String key = StringHelper.mergeStrings(prefix);
		
		if(!allNgrams.containsKey(key)) {
			return new ArrayList<Prediction>();
		}
		else {
			return allNgrams.get(key).getPredictions(count);
		}
		
	}
	
	public NgramDictionary(String filespath) {
		//TODO Nauciti kako raditi sa fajlovima
	}

}
