package certyficate.calculation;

import certyficate.dataContainer.Point;

public class PointCalculation {
	final private static int MEASUREMENTS_POINTS = 10;
	
	static Point point;
	
	public static Point setFalse() {
		Point point = new Point(1);
		point.haveMeasurments = false;
		return point;
	}
	
	public static void calculate(Point currentPoint) {
		point = currentPoint;
		calculateResults();
	}

	private static void calculateResults() {
		for(int i = 0; i < point.numberOfParamters; i++) {
			point.average[i] = calculaleAverage(i);
			point.standardDeviation[i] 
					= calculateStandardDeviation(i);
		}
	}

	private static double calculaleAverage(int index) {
		double average = 0;
		for(int i = 0; i < MEASUREMENTS_POINTS; i++)
			average += point.data[index][i];
		average /= MEASUREMENTS_POINTS;
		return average;
	}
	
	private static double calculateStandardDeviation(int index) {
        double standardDeviation=0;
        for(int i = 0; i < MEASUREMENTS_POINTS; i++) {
        	double number = point.data[index][i] 
        			- point.average[index];
        	standardDeviation += Math.pow(number, 2);
        }
        standardDeviation /= MEASUREMENTS_POINTS;
        standardDeviation /= (MEASUREMENTS_POINTS - 1);
        return Math.sqrt(standardDeviation);
	}
}
