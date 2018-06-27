package certyficate.calculation;

import certyficate.dataContainer.DataProbe;

public class CalculateT {
	double[] factor;
	DataProbe[] pointsInRange;
	int[] point;
	
	public DataProbe findPoint(DataProbe[] pointsInRange, int[] point) {
		this.point = point;
		this.pointsInRange = pointsInRange;
		return getDataPoint();
	}
	
    private DataProbe getDataPoint() {
    	DataProbe data  = new DataProbe(point);
    	setData(data);
		return data;
	}
    
    private void setData(DataProbe data) {
    	data.setDrift(pointsInRange[0]);
    	calculatePatametrs(data);
	}

	private void calculatePatametrs(DataProbe data) {
		estimateCorection(data);
    	findUncertainty(data);		
	}

	private void estimateCorection(DataProbe data) {
		double[] correction = findCorection();
		data.correctionT = correction[0];
		data.correctionRh = correction[1];
	}

	protected double[] findCorection() {
		double[] correction = new double[2];
		getFactors();
		correction[0] = calculateTemperatureCorretion(0);
		return correction;
	}
	
	protected void getFactors() {
    	int minTemperature = pointsInRange[0].valueT;
    	int maxTemperature = pointsInRange[1].valueT;
		factor = new double[point.length];
		factor[0] = getFactor((double)point[0], minTemperature, maxTemperature);
	}
	
	protected double getFactor(double point, int min, int max) {
		return (point - min) / (max - min);
	}
	
	private double calculateTemperatureCorretion(int index) {
		double low = pointsInRange[0 + index].uncertaintyT;
		double hight = pointsInRange[1 + index].uncertaintyT;
		double correction = low + (hight - low) * factor[0];
		return correction;
	}

	private void findUncertainty(DataProbe data) {
		double[] uncertainty = DataCalculation.maxUncertainty(pointsInRange);
		data.uncertaintyT = uncertainty[0];
	}
}
