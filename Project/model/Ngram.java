package model;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class Ngram implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected final int N;
	protected final String[] words;
	protected final PriorityQueue<FollowingWord> predictions = new PriorityQueue<FollowingWord>();
	
	public int getN() {
		return N;
	}

	public String[] getWords() {
		return words;
	}
	
	public Ngram(List<String> words) {
		N = words.size();
		this.words = (String[]) words.toArray();
	}

	public Ngram(int N, String[] words) {
		if (words.length != N)
			throw new InvalidParameterException("Ngram nema odgovarajuci broj reci."); 
		this.N=N;
		this.words=words;
	}
	
	public List<FollowingWord> getPredictions(int upto){
		List<FollowingWord> result = new ArrayList<FollowingWord>();
		
		for( int i=0; i<upto; ++i) {
			if(predictions.isEmpty())
				break;
			result.add(predictions.poll());
		}
		
		result.forEach(fw -> predictions.add(fw));
		
		return result;
	}

	public void addFollowingWord(String word) {
		Optional<FollowingWord> existingWord = predictions.parallelStream()
				.filter( fw -> fw.word==word )
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
	}
	
	@Override
	public String toString() {
		String result="";
		for(String word : words) {
			result+=word+" ";
		}
		return result + getPredictions(3);
	}
	
	public static void main(String[] args) {
		String[] dveReci = {"Aleks","nosi"};
		Ngram bi = new Ngram(2, dveReci );

		bi.addFollowingWord("mikrotalasnu");
		bi.addFollowingWord("pendrek");
		bi.addFollowingWord("pendrek");
		bi.addFollowingWord("pendrek");
		bi.addFollowingWord("kosmodisk");
		bi.addFollowingWord("kosmodisk");
		bi.addFollowingWord("macu");
		bi.addFollowingWord("macu");
		bi.addFollowingWord("macu");
		bi.addFollowingWord("macu");
		bi.addFollowingWord("macu");
		bi.addFollowingWord("macu");
		
		System.out.println(bi);
	}

}