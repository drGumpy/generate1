package certyficate.calculation;

import certyficate.dataContainer.DataProbe;

public abstract class Calculate {
	double[] corection;
	DataProbe[] pointsInRange;
	int[] point;
	
	public DataProbe findPoint(DataProbe[] pointsInRange, int[] point) {
		this.point = point;
		this.pointsInRange = pointsInRange;
		return null;
	}
}
