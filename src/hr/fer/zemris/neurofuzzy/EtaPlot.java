package hr.fer.zemris.neurofuzzy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class EtaPlot {

	public static void main(String[] args) throws IOException {
		
		Parser parser = new Parser("dataset.txt");
		ArrayList<Vector> vectors = parser.getVectors();
		
		ANFIS anfis = new ANFIS(16, 0.000001);
		anfis.trainWriteToFile(vectors, 0, "etasmall.dat");
		
		anfis = new ANFIS(16, 0.0005);
		anfis.trainWriteToFile(vectors, 0, "etataman.dat");
		
		anfis = new ANFIS(16, 0.03);
		anfis.trainWriteToFile(vectors, 0, "etabig.dat");
		
		File file = new File("eta_plot");
		FileOutputStream fos = new FileOutputStream(file);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write("set multiplot");
		bw.newLine();
		bw.write("set size 1, 0.33");
		bw.newLine();
		bw.write("set autoscale yfix");
		bw.newLine();
		
		bw.write("set origin 0.0, 0.66");
		bw.newLine();
		bw.write("set yrange [*:400]");
		bw.newLine();
		bw.write("plot \"etasmall.dat\" u 1:2 with lines title 'eta small'");
		bw.newLine();
		
		bw.write("set origin 0.0, 0.33");
		bw.newLine();
		bw.write("set yrange [*:0.1]");
		bw.newLine();
		bw.write("plot \"etataman.dat\" u 1:2 with lines title 'eta taman'");
		bw.newLine();
		
		bw.write("set origin 0.0, 0.0");
		bw.newLine();
		bw.write("set yrange [*:10000]");
		bw.newLine();
		bw.write("plot \"etabig.dat\" u 1:2 with lines title 'eta big'");
		bw.newLine();
		
		bw.write("unset multiplot");
		bw.newLine();
		
		bw.close();
		
	}
	
}
