package hr.fer.zemris.neurofuzzy;

import java.util.Random;

public class RuleUnit {

	private double a;
	private double b;
	private double output;
	private double gradA;
	private double gradB;
	
	public RuleUnit(Random random) {
		this.a = random.nextDouble();
		this.b = random.nextDouble();
		this.output = 0.;
		this.gradA = 0.;
		this.gradB = 0.;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}
	
	public double getOutput() {
		return this.output;
	}
	
	public double getGradA() {
		return gradA;
	}

	public void setGradA(double gradA) {
		this.gradA = gradA;
	}

	public double getGradB() {
		return gradB;
	}

	public void setGradB(double gradB) {
		this.gradB = gradB;
	}

	public void calculate(double x) {
		
		//System.out.println("b=" + this.b + " * (x=" + x + " -a=" + this.a + ")");
		double e = 1. + Math.exp(this.b*(x-this.a));
		//System.out.println("e je " + e);
		this.output = 1./e;
		//System.out.println("output je " + this.output);
	}
}
