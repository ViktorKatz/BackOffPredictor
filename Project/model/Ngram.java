package model;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import static java.util.stream.Collectors.toList;

public class Ngram implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected final int N;
	protected final String[] words;
	protected final PriorityQueue<FollowingWord> predictions = new PriorityQueue<FollowingWord>();
	protected long numberOfPredictions=0;
	
	public int getN() {
		return N;
	}

	public String[] getWords() {
		return words;
	}
	
	public long getNumberOfPredictions() {
		return numberOfPredictions;
	}

	public Ngram(List<String> words) {
		N = words.size();
		this.words = (String[]) words.toArray();
	}

	public Ngram(int N, String[] words) {
		if (words.length != N)
			throw new InvalidParameterException("Ngram nema odgovarajuci broj reci."); 
		this.N = N;
		this.words=words;
	}
	
	public List<FollowingWord> getFollowingWords(int upto){
		List<FollowingWord> result = new ArrayList<FollowingWord>();
		
		for( int i=0; i<upto; ++i) {
			if(predictions.isEmpty())
				break;
			result.add(predictions.poll());
		}
		
		result.forEach(fw -> predictions.add(fw));
		
		return result;
	}
	
	public List<Prediction> getPredictions(int upto){
		return getFollowingWords(upto).parallelStream()
				.map(fw -> new Prediction(fw.getWord(), (double)fw.getTimesAppeared()/numberOfPredictions))
				.collect(toList());
	}

	public void addPrediction(String word) {
		Optional<FollowingWord> existingWord = predictions.parallelStream()
				.filter( fw -> fw.word.equals(word) )
				.findAny();
		
		if(existingWord.isPresent()) {
			FollowingWord fw = existingWord.get();
			fw.increaseTimesAppeared();
			if (predictions.remove(fw))
				predictions.add(fw);
		}
		else {
			predictions.add(new FollowingWord(word));
		}
		++numberOfPredictions;
	}
	
	@Override
	public String toString() {
		String result="";
		for(String word : words) {
			result+=word+" ";
		}
		return result + getFollowingWords(3);
	}
	
	public static void main(String[] args) {
		String[] dveReci = {"Aleks","nosi"};
		Ngram bi = new Ngram(2, dveReci );

		bi.addPrediction("mikrotalasnu");
		bi.addPrediction("pendrek");
		bi.addPrediction("pendrek");
		bi.addPrediction("pendrek");
		bi.addPrediction("kosmodisk");
		bi.addPrediction("kosmodisk");
		bi.addPrediction("macu");
		bi.addPrediction("macu");
		bi.addPrediction("macu");
		bi.addPrediction("macu");
		bi.addPrediction("macu");
		bi.addPrediction("macu");
		
		String[] prazan = {};
		//Ngram nula=new Ngram(0,prazan);
		
		System.out.println(bi);		
	}

}