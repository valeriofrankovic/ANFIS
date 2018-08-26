package hr.fer.zemris.neurofuzzy;

import java.util.Random;

public class Conclusion {
	
	private double p;
	private double q;
	private double r;
	private double gradP;
	private double gradQ;
	private double gradR;
	private double conclusion;
	
	public Conclusion(Random random) {
		this.p = random.nextDouble();
		this.q = random.nextDouble();
		this.r = random.nextDouble();
		this.conclusion = 0.;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getQ() {
		return q;
	}

	public void setQ(double q) {
		this.q = q;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}
	
	public void conclude(double x, double y) {
		this.conclusion = this.p*x + this.q*y + this.r;
	}
	
	public double getConclusion() {
		return this.conclusion;
	}

	public double getGradP() {
		return gradP;
	}

	public void setGradP(double gradP) {
		this.gradP = gradP;
	}

	public double getGradQ() {
		return gradQ;
	}

	public void setGradQ(double gradQ) {
		this.gradQ = gradQ;
	}

	public double getGradR() {
		return gradR;
	}

	public void setGradR(double gradR) {
		this.gradR = gradR;
	}
}
