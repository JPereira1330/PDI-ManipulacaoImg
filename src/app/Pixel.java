package app;

public class Pixel {

	public int x;
	public int y;
	
	public double r;
	public double g;
	public double b;
	
	public Pixel[] viz3 = new Pixel[8];
	
	/**
	 * 
	 * @param r 	red
	 * @param g 	green
	 * @param b 	blue
	 * @param x 	position x
	 * @param y 	position y
	 */
	public Pixel(double r, double g, double b, int x, int y) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.x = x;
		this.y = y;
	}
}
