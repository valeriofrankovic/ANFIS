package hr.fer.zemris.neurofuzzy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class OptionErrorPlot {

	public static void main(String[] args) throws IOException {
		Parser parser = new Parser("dataset.txt");
		ArrayList<Vector> vectors = parser.getVectors();
		
		ANFIS anfis = new ANFIS(16);
		anfis.trainWriteToFile(vectors, 0, "batch.dat");
		
		anfis = new ANFIS(16);
		anfis.trainWriteToFile(vectors, 1, "stochastic.dat");
		
		File file = new File("error_plot");
		FileOutputStream fos = new FileOutputStream(file);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write("set multiplot");
		bw.newLine();
		bw.write("set size 1, 0.5");
		bw.newLine();
		bw.write("set autoscale yfix");
		bw.newLine();
		
		bw.write("set origin 0.0, 0.5");
		bw.newLine();
		bw.write("set yrange [*:0.1]");
		bw.newLine();
		bw.write("plot \"batch.dat\" u 1:2 with lines title 'batch error'");
		bw.newLine();
		
		bw.write("set origin 0.0, 0.0");
		bw.newLine();
		bw.write("set yrange [*:0.1]");
		bw.newLine();
		bw.write("plot \"stochastic.dat\" u 1:2 with lines title 'stochastic error'");
		bw.newLine();
		
		bw.write("unset multiplot");
		bw.newLine();
		
		bw.close();
	}

}
