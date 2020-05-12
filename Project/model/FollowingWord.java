package model;

import java.io.Serializable;

public class FollowingWord implements Comparable<FollowingWord>, Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
		return arg0.timesAppeared - this.timesAppeared;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return word+"("+timesAppeared+")";
	}
	
}