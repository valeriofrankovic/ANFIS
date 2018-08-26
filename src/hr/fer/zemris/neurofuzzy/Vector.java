package hr.fer.zemris.neurofuzzy;

public class Vector {

	private double x;
	private double y;
	private double output;
	
	public Vector(double x, double y, double output) {
		this.x = x;
		this.y = y;
		this.output = output;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getOutput() {
		return output;
	}
}
