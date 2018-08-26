package hr.fer.zemris.neurofuzzy;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parser parser = new Parser("dataset.txt");
		ArrayList<Vector> vectors = parser.getVectors();
		
		ANFIS anfis = new ANFIS(16);
		anfis.train(vectors, 0);
		
		anfis.feedForward(0.0, 0.0);
		System.out.println(anfis.getOutput());
	}

}
