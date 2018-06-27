package certyficate.equipment;

import java.io.IOException;

import certyficate.dataContainer.DataProbe;


class TProbe extends ReferenceProbe {
    public TProbe(String path) throws IOException {
		super(path);
	}
    
	@Override
	protected void setDrifts(String[] elements) {
		driftT = getDouble(elements[1]);
	}

	@Override
	protected DataProbe findProbeData(String[] elements) {
		DataProbe data = new DataProbe();
        data.valueT = getInteger(elements[0]);
        data.correctionT = getDouble(elements[1]);
        data.uncertaintyT = getDouble(elements[2]);
        data.driftT = driftT;
		return data;
	}

	@Override
	protected void setRanges() {
		rangeSize = 2;
		ranges = new int[numberOfRanges][rangeSize];
	}

	@Override
	protected boolean equalPoint(int[] point, int index) {
		DataProbe data = standardPoints[index];
		return point[0] == data.valueT;
	}

	@Override
	protected boolean inRange(int[] point, int[] range) {
		return (point[0] >= range[0]) && (point[0] <= range[1]);
	}
	
	@Override
	protected DataProbe[] findPointsInRange(int[] range) {
		DataProbe[] pointsInRange = new DataProbe[rangeSize];
		for(int i = 0; i < 2; i++) 
			pointsInRange[i] = findInStandardPoints(
					new int[]{range[i]});
		return pointsInRange;
	}

	@Override
	protected DataProbe caluculate(DataProbe[] pointsInRange, int[] point) {
		return calculate.findPoint(pointsInRange, point);
	}
}