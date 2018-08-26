package hr.fer.zemris.neurofuzzy;

import java.io.IOException;
import java.io.PrintWriter;

public class DatasetMaker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			PrintWriter writer = new PrintWriter("dataset.txt", "UTF-8");
			for (int x = -4; x <= 4; x++) {
				for (int y = -4; y <= 4; y++) {
					writer.println(x + ", " + y + ", " + func(x,y));
				}
			}
			writer.close();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

	public static double func(double x, double y) {
		return (Math.pow(x-1, 2) + Math.pow(y+2, 2) - 5*x*y + 3) * Math.pow(Math.cos(x/5), 2);
	}
	
}