package model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import helpers.StringHelper;

public class NgramDictionary implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String savePath = "../Recnici/";

	private HashMap<String, Ngram> allNgrams = new HashMap<String, Ngram>();
	
	public NgramDictionary() {
		super();
	}
	
	public NgramDictionary(String filespath, int maxGram) {
		addFromFile(filespath, maxGram);
	}
	
	/**
	 * @param filespaths Lista putanja 
	 * @param maxGram
	 */
	public NgramDictionary(List<String> filespaths, int maxGram) {
		for (String filespath : filespaths) {
			addFromFile(filespath, maxGram);
		}
	}
	
	/**
	 * @apiNote Be careful, this adds n-grams to existing dictionary
	 */
	public void addFromFile(String filespath, int maxGram) {
		try {
			Stream<Path> walk =
					Files.walk(Paths.get(filespath))
					.filter(f->StringHelper.isTextFile(f.toString()));
			
			walk.forEach(path->{
				List<String> lines = new ArrayList<String>();
				try {
					lines = Files.readAllLines(path,StandardCharsets.UTF_8);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (String line : lines) {
					addAllNgrams(line, maxGram);
				}

			});
			
			walk.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	private void addAllNgrams(String text, int upto) {
		String noRefs = StringHelper.removeParentheses(text.trim());
		
		String bareText = StringHelper.removeUnwantedChars(noRefs);
		
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
	
	public void saveToFile(String filename) {
		try {
			File file = new File(savePath + filename);
			file.createNewFile();
			
			FileOutputStream fileStream = new FileOutputStream( savePath + filename );
			ObjectOutputStream outStream = new ObjectOutputStream(fileStream);
			
			outStream.writeObject(this);
			
			outStream.close();
			fileStream.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Can not open file to write data.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Bad path name.");
			e.printStackTrace();
		} 
	}
	
	/**
	 * 
	 * @throws IOException - Uglavnom FileNotFoundException.
	 * @throws FileNotFoundException - Uglavnom ako ne postoji takav fajl.
	 */
	public void loadFromFile(String filename) throws IOException {
		
		FileInputStream inputStream = new FileInputStream(savePath + filename); 
        ObjectInputStream in = new ObjectInputStream(inputStream); 
          
        try {
			NgramDictionary tmp = (NgramDictionary)in.readObject();
			allNgrams=tmp.allNgrams;
		}
        catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        finally {
			in.close();
			inputStream.close();
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
	
		NgramDictionary dictFromPath=new NgramDictionary();
		try {
			dictFromPath.loadFromFile("FirstTestFromWikipedia.dict");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dictFromPath.getPredictions("je", 5));
		dictFromPath.saveToFile("FirstTestFromWikipedia.dict");
	}

}
