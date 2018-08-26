package hr.fer.zemris.neurofuzzy;

import java.util.Random;

public class Rule {
	
	private RuleUnit A;
	private RuleUnit B;
	private Conclusion f;
	private double output;
	
	public Rule(Random random) {
		this.A = new RuleUnit(random);
		this.B = new RuleUnit(random);
		this.f = new Conclusion(random);
		this.output = 0.;
	}

	public RuleUnit getA() {
		return A;
	}

	public RuleUnit getB() {
		return B;
	}

	public Conclusion getF() {
		return f;
	}
	
	public double getOutput() {
		return this.output;
	}
	
	public void setOutput(double output) {
		this.output = output;
	}
	
}
