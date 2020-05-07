package model;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.PriorityQueue;

public class Ngram implements Serializable {

	protected class FollowingWord implements Comparable<FollowingWord>{
		
		protected final String word;
		protected int timesAppeared = 1;

		public FollowingWord(String word) {
			this.word=word;
		}
		
		/**
		 * @comment Mislim da nam nece trebati.
		 * @param timesAppeared Ako se rec vec pojavljivala vise puta. 
		 */
		public FollowingWord(String word, int timesAppeared) {
			this(word);
			this.timesAppeared=timesAppeared;
		}
		
		public String getWord() {
			return word;
		}
		
		public int getTimesAppeared() {
			return timesAppeared;
		}
		
		public void increaseTimesAppeared() {
			timesAppeared++;
		}
		
		@Override
		public int compareTo(FollowingWord arg0) {
			return this.timesAppeared-arg0.timesAppeared;
		}
		
	}
	
	/**
	 * Mora imati verziju za serijalizaciju da java ne bi lupila svoju verziju.
	 * Ako su razlicite verzije, nekad baca gresku.
	 */
	private static final long serialVersionUID = 1L;
	
	protected final int N;
	protected final String[] words;
	protected final PriorityQueue<FollowingWord> predictions;
	
	
	public int getN() {
		return N;
	}

	public String[] getWords() {
		return words;
	}

	public Ngram(int N, String[] words) {
		if (words.length != N)
			throw new InvalidParameterException("Ngram nema odgovarajuci broj reci."); 
		
		this.N=N;
		this.words=words;
		this.predictions=new PriorityQueue<Ngram.FollowingWord>();
	}

	public static void main(String[] args) {
		System.out.print("Testing testing 123");

	}

}