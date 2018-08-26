package hr.fer.zemris.neurofuzzy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MembershipsDrawer {
	
	public static void main(String[] args) throws IOException {
		Parser parser = new Parser("dataset.txt");
		ArrayList<Vector> vectors = parser.getVectors();
		
		ANFIS anfis = new ANFIS(16);
		anfis.train(vectors, 1);
		
		
		File file = new File("memberships");
		FileOutputStream fos = new FileOutputStream(file);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write("### Start multiplot(4x8 layout)");
		bw.newLine();
		bw.write("set macros");
		bw.newLine();
		
		bw.write("NOXTICS = \"set xtics ('' -4,'' -2,'' 0,'' 2,'' 4); \\ unset xlabel\"");
		bw.newLine();
		
		bw.write("XTICS = \"set xtics ('-4' -4,'-2' -2,'0' 0,'2' 2,'4' 4);\\ set xlabel 'x'\"");
		bw.newLine();
		
		bw.write("NOYTICS = \"set format y ''; unset ylabel\"");
		bw.newLine();
		
		bw.write("YTICS = \"set format y '%.1f'; set ytic(0.0, 0.5, 1.0); set ylabel 'y'\"");
		bw.newLine();
		
		bw.write("set multiplot layout 4,8 rowsfirst");
		bw.newLine();
		int i = 0;
		for (Rule r : anfis.getRules()) {
			bw.write("# --- GRAPH rule" + i);
			bw.newLine();
			if (i>=12 && i%4==0) {
				bw.write("@XTICS; @YTICS");
				bw.newLine();
			}
			else if (i>=12 && i%4 != 0) {
				bw.write("@XTICS; @NOYTICS");
				bw.newLine();
			}
			else if (i<12 && i%4==0) {
				bw.write("@NOXTICS; @YTICS");
				bw.newLine();
			}
			RuleUnit a = r.getA();
			bw.write("set label 1 'rule" + (2*i+1) + "' at graph 0.92,0.9 font ',8'");
			bw.newLine();
			bw.write("set yrange [0:1]");
			bw.newLine();
			bw.write("set xrange [-4:4]");
			bw.newLine();
			bw.write("f(x) = abs(x)<4 ? 1/(1+exp((" + a.getB() + ")*(x - (" + a.getA() + ")))) : 1/0");
			bw.newLine();
			bw.write("plot f(x) with lines ls 1");
			bw.newLine();
			RuleUnit b = r.getB();
			if (i>=12) {
				bw.write("@XTICS; @NOYTICS");
				bw.newLine();
			}
			else {
				bw.write("@NOXTICS; @NOYTICS");
				bw.newLine();
			}
			bw.write("set label 1 'rule" + (2*i+2) + "' at graph 0.92,0.9 font ',8'");
			bw.newLine();
			bw.write("set yrange [0:1]");
			bw.newLine();
			bw.write("set xrange [-4:4]");
			bw.newLine();
			bw.write("f(x) = abs(x)<4 ? 1/(1+exp((" + b.getB() + ")*(x - (" + b.getA() + ")))) : 1/0");
			bw.newLine();
			bw.write("plot f(x) with lines ls 1");
			bw.newLine();
			i++;
		}
		bw.write("unset multiplot");
		bw.newLine();
		bw.close();
	}
}
