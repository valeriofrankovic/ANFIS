package hr.fer.zemris.neurofuzzy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class ANFIS {
	
	private Rule[] rules;
	private double output;
	private double error;
	
	private double eta;
	
	public ANFIS(int numOfRules) {
		long milis = System.currentTimeMillis();
		Random random = new Random(milis);
		
		rules = new Rule[numOfRules];
		for (int i = 0; i < numOfRules; i++) {
			rules[i] = new Rule(random);
		}
		this.output = 0.;
		this.error = 0.;
		this.eta = 0.001;
	}
	
	public ANFIS(int numOfRules, double eta) {
		long milis = System.currentTimeMillis();
		Random random = new Random(milis);
		
		rules = new Rule[numOfRules];
		for (int i = 0; i < numOfRules; i++) {
			rules[i] = new Rule(random);
		}
		this.output = 0.;
		this.error = 0.;
		this.eta = eta;
	}

	public void feedForward(double x, double y) {
		
		// tu se izracunacaju funkcije pripadnosti muA i muB te se izracuna njihov produkt kojeg se zapise
		for (int i = 0; i < rules.length; i++) {
			RuleUnit A = rules[i].getA();
			A.calculate(x);
			RuleUnit B = rules[i].getB();
			B.calculate(y);
			rules[i].setOutput(A.getOutput() * B.getOutput());
			//System.out.println("a.getoutput " + A.getOutput());
			//System.out.println("b.getoutput " + B.getOutput());
			//System.out.println("a*b " + (A.getOutput() * B.getOutput()));
			if (Double.isNaN(A.getOutput()) || Double.isNaN(B.getOutput())) System.exit(0);
		}
		
		// tu se racuna wi * fi
		double overall = 0.;
		for (int i = 0; i < rules.length; i++) {
			rules[i].getF().conclude(x, y);
			overall = overall + rules[i].getOutput() * rules[i].getF().getConclusion();
			//System.out.println("weight " + rules[i].getOutput());
			//System.out.println("conclusion " + rules[i].getF().getConclusion());
		}
		
		// wi * fi se podijeli sa sum_i(wi)
		overall = overall / getSumOfWeights();
		
		// izlaz se spremi i ispise
		this.output = overall;
	}
	
	public void backpropagate(double x, double y, double output) {
		this.error += Math.pow(output-this.output, 2.);
		
		double sum = getSumOfWeights();
		for (int i = 0; i < rules.length; i++) {
			RuleUnit A = rules[i].getA();
			RuleUnit B = rules[i].getB();
			
			double zDiff = getSumOfZ(i);
			
			// update ruleunit A
			double a = A.getA();
			double b = A.getB();
			
			double gradA = eta * (output - this.output) * B.getOutput() * (zDiff / Math.pow(sum, 2.)) * b * A.getOutput() * (1 - A.getOutput());
			double gradB = eta * (output - this.output) * B.getOutput() * (zDiff / Math.pow(sum, 2.)) * (x - a) * A.getOutput() * (1 - A.getOutput());
			
			A.setGradA(A.getGradA() + gradA);
			A.setGradB(A.getGradB() + gradB);
			
			zDiff = getSumOfZ(i);
			
			// update ruleunit B
			a = B.getA();
			b = B.getB();
			
			gradA = eta * (output - this.output) * A.getOutput() * (zDiff / Math.pow(sum, 2.)) * b * B.getOutput() * (1 - B.getOutput());
			gradB = eta * (output - this.output) * A.getOutput() * (zDiff / Math.pow(sum, 2.)) * (y - a) * B.getOutput() * (1 - B.getOutput());
			
			B.setGradA(B.getGradA() + gradA);
			B.setGradB(B.getGradB() + gradB);
			
			Conclusion c = rules[i].getF();
			double gradP = eta * (output - this.output) * rules[i].getOutput() / getSumOfWeights() * x;
			double gradQ = eta * (output - this.output) * rules[i].getOutput() / getSumOfWeights() * y;
			double gradR = eta * (output - this.output) * rules[i].getOutput() / getSumOfWeights();
			
			c.setGradP(c.getGradP() + gradP);
			c.setGradQ(c.getGradQ() + gradQ);
			c.setGradR(c.getGradR() + gradR);
		}
	}
	
	private void update() {
		// TODO Auto-generated method stub
		for (int i = 0; i < rules.length; i++) {
			// update rule A - a
			rules[i].getA().setA(rules[i].getA().getA() + rules[i].getA().getGradA());
			rules[i].getA().setGradA(0.);
			
			// update rule A - b
			rules[i].getA().setB(rules[i].getA().getB() - rules[i].getA().getGradB());
			rules[i].getA().setGradB(0.);
			
			// update rule B - a
			rules[i].getB().setA(rules[i].getB().getA() + rules[i].getB().getGradA());
			rules[i].getB().setGradA(0.);
			
			// update rule B - b
			rules[i].getB().setB(rules[i].getB().getB() - rules[i].getB().getGradB());
			rules[i].getB().setGradB(0.);
			
			// update conclusion
			rules[i].getF().setP(rules[i].getF().getP() + rules[i].getF().getGradP());
			rules[i].getF().setGradP(0.);
			rules[i].getF().setQ(rules[i].getF().getQ() + rules[i].getF().getGradQ());
			rules[i].getF().setGradQ(0.);
			rules[i].getF().setR(rules[i].getF().getR() + rules[i].getF().getGradR());
			rules[i].getF().setGradR(0.);
		}
	}
	
	public double getOutput() {
		return this.output;
	}


	private double getSumOfZ(int i) {
		// TODO Auto-generated method stub
		double sum = 0.;
		for (int j = 0; j < rules.length; j++) {
			sum = sum + rules[j].getOutput() * (rules[i].getF().getConclusion() - rules[j].getF().getConclusion());
		}
		return sum;
	}
	
	private double getSumOfWeights() {
		double sumOfWeights = 0.;
		for (int i = 0; i < rules.length; i++) {
			sumOfWeights += rules[i].getOutput();
		}
		return sumOfWeights;
	}
	
	private double writeAndResetError(int trainingSize) {
		this.error /= trainingSize;
		System.out.println("Srednja kvadratna pogreška iznosi " + this.error);
		double err = this.error;
		this.error = 0.;
		return err;
	}
	
	public Rule[] getRules() {
		return this.rules;
	}
	
	// option = 0 --> batch; option = 1 --> stochastic;
	public void train(ArrayList<Vector> vectors, int option) {
	
		for (int i = 0; i < 100000; i++) {
			System.out.println();
			System.out.println("Epoha: " + (i+1));
			for (int j = 0; j < vectors.size(); j++) {
				Vector v = vectors.get(j);
				feedForward(v.getX(), v.getY());
				backpropagate(v.getX(), v.getY(), v.getOutput());
				if (option == 1) update();
			}
			if (option == 0) update();
			writeAndResetError(vectors.size());
		}
	}
	
	public void trainWriteToFile(ArrayList<Vector> vectors, int option, String filename) throws IOException {
		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(file);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write("# Iteration\tError");
		bw.newLine();
		
		for (int i = 0; i < 100000; i++) {
			System.out.println();
			System.out.println("Epoha: " + (i+1));
			for (int j = 0; j < vectors.size(); j++) {
				Vector v = vectors.get(j);
				feedForward(v.getX(), v.getY());
				backpropagate(v.getX(), v.getY(), v.getOutput());
				if (option == 1) update();
			}
			if (option == 0) update();
			double err = writeAndResetError(vectors.size());
			bw.write(i + " " + err);
			bw.newLine();
		}
		bw.close();
	}
	
}

	
