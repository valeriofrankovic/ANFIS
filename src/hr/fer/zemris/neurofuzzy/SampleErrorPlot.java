package hr.fer.zemris.neurofuzzy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SampleErrorPlot {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Parser parser = new Parser("dataset.txt");
		ArrayList<Vector> vectors = parser.getVectors();
		
		ANFIS anfis = new ANFIS(16);
		anfis.train(vectors, 1);
		
		
		File file = new File("sample_error.dat");
		FileOutputStream fos = new FileOutputStream(file);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write("### X\tY\tZ");
		bw.newLine();
		
		for (Vector v : vectors) {
			double x = v.getX();
			double y = v.getY();
			anfis.feedForward(x, y);
			double delta = anfis.getOutput() - v.getOutput();
			bw.write(x + " " + y + " " + delta);
			bw.newLine();
		}
		bw.close();
		
		file = new File("sample_error");
		fos = new FileOutputStream(file);
		
		bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write("set xrange [-4:4]");
		bw.newLine();
		bw.write("set yrange [-4:4]");
		bw.newLine();
		bw.write("set ticslevel 0");
		bw.newLine();
		bw.write("set dgrid3d 30,30");
		bw.newLine();
		bw.write("set hidden3d");
		bw.newLine();
		bw.write("splot \"sample_error.dat\" u 1:2:3 with lines title 'Delta(x,y)'");
		bw.newLine();
		bw.close();
	}

}
