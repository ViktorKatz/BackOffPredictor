package model;

class Prediction {
	public String word;
	public double probability;
	
	public Prediction(String word, double probability) {
		this.word = word;
		this.probability = probability;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return word+"("+probability+")";
	}
}
