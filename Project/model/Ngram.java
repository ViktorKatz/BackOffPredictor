package model;

import java.io.Serializable;

public class Ngram implements Serializable {

	protected class FollowingWord implements Comparable<FollowingWord>{
		String word;
		int timesAppeared = 1;
		
		@Override
		public int compareTo(FollowingWord arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	/**
	 * Mora imati verziju za serijalizaciju da java ne bi lupila svoju verziju.
	 * Ako su razlicite verzije, nekad baca gresku.
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		System.out.print("Testing testing 123");

	}

}