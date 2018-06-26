package certyficate.dataContainer;

public class Point {
	final private int MEASUREMENTS_POINTS = 10;
	public int numberOfParamters;
	
	public double[][] data;
	
	public boolean haveMeasurments;

	public double[] standardDeviation;
	public double[] average;
	
	public Point(int numberOfParamters) {
		this.numberOfParamters = numberOfParamters;
		data = new double[numberOfParamters][MEASUREMENTS_POINTS];
		standardDeviation = new double[numberOfParamters];
		average = new double[numberOfParamters];
	}

	public static Point setFalse() {
		Point point = new Point(1);
		point.haveMeasurments = false;
		return point;
	}
}
