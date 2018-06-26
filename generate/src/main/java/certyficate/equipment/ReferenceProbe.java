package certyficate.equipment;

import java.io.IOException;

import certyficate.dataContainer.DataProbe;

public abstract class ReferenceProbe extends Equipment {
	protected DataProbe[] standardPoints;
	
	protected double driftT;
	protected double driftRh;
	
	protected int rangeSize;
	
	public ReferenceProbe(String path) throws IOException {
		super(path);
	}

	@Override
	protected void getConstantData(String line) {
		String[] elements = line.split(SEPARATOR);
		numberOfStandardPoint = getInteger(elements[0]);
		standardPoints = new DataProbe[numberOfStandardPoint];
		setDrifts(elements);
	}

	protected abstract void setDrifts(String[] elements);

	@Override
	protected void getCalibrationPoint(String line, int index) {
		String[] elements = line.split(SEPARATOR);
		standardPoints[index] = findProbeData(elements);
	}

	protected abstract DataProbe findProbeData(String[] elements);

	@Override
	protected void getRange(String line, int index) {
		setRanges();
		String[] elements = line.split(SEPARATOR);
		ranges[index] = findRange(elements);
	}

	protected abstract void setRanges();
	
	private int[] findRange(String[] elements) {
		int[] range = new int[rangeSize];
		for(int i = 0; i < rangeSize; i++){
			range[i] = getInteger(elements[0]);
		}
		return range;
	} 
	
	@SuppressWarnings("unchecked")
	public DataProbe getPointData(int[] point) {
		DataProbe pointData = findInStandardPoints(point);
		if(pointData == null)
			pointData = checkInRanges(point);
		return pointData;
	}

	protected DataProbe findInStandardPoints(int[] point) {
		DataProbe pointData = null;
		for(int i = 0; i < numberOfStandardPoint; i++)
			if(equalPoint(point, i)) {
				pointData = standardPoints[i];
				break;
			}
		return pointData;
	}

	protected abstract boolean equalPoint(int[] point, int index);

	protected DataProbe checkInRanges(int[] point) {
		DataProbe pointData = new DataProbe(false);
		for(int i = 0; i < numberOfRanges; i++)
			if(inRange(point, ranges[i])) {
				pointData = findInRange(point, ranges[i]);
				break;
			}
		return pointData;
	}
	
	protected abstract boolean inRange(int[] point, int[] range);
	
	protected DataProbe findInRange(int[] point, int[] range) {
		DataProbe[] pointsInRange = findPointsInRange(range);
		DataProbe data = caluculate(pointsInRange, point);
		return data;
	}

	protected abstract DataProbe[] findPointsInRange(int[] range);

	protected abstract DataProbe caluculate(DataProbe[] pointsInRange,
			int[] point);
}
