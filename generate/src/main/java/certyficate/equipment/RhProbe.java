package certyficate.equipment;

import java.io.IOException;

import certyficate.dataContainer.*;

public class RhProbe extends ReferenceProbe {
    public RhProbe(String path) throws IOException{
    	super(path);
    }
  
	@Override
	protected void setDrifts(String[] elements) {
		driftT = getDouble(elements[1]);
		driftRh = getDouble(elements[2]);
	}

	@Override
	protected DataProbe findProbeData(String[] elements) {
		DataProbe data = new DataProbe();
		data.valueT = getInteger(elements[0]);
		data.valueRh = getInteger(elements[1]);
		data.correctionT = getDouble(elements[2]);
		data.correctionRh = getDouble(elements[3]);
		data.uncertaintyT = getDouble(elements[4]);
		data.uncertaintyRh = getDouble(elements[5]);
		data.driftT = driftT;
		data.driftRh = driftRh;
		return data;
	}

	@Override
	protected void setRanges() {
		rangeSize = 4;
		ranges = new int[numberOfRanges][rangeSize];	
	}

	@Override
	protected boolean equalPoint(int[] point, int index) {
		DataProbe data = standardPoints[index];
		return (point[0] == data.valueT) && (point[1] == data.valueRh);
	}

	@Override
	protected boolean inRange(int[] point, int[] range) {
		return (point[0] >= range[0]) && (point[0] <= range[1]) 
				&& (point[1] >= range[2]) && (point[1] <= range[3]);
	}

	@Override
	protected DataProbe[] findPointsInRange(int[] range) {
		DataProbe[] pointsInRange = new DataProbe[rangeSize];
		for(int i = 0; i < 2; i++) {
			pointsInRange[i] = findInStandardPoints(
					new int[]{range[i], range[i+2]});
			pointsInRange[i+2] = findInStandardPoints(
					new int[]{range[i+1], range[i+2]});
		}
		return pointsInRange;
	}

	@Override
	protected DataProbe caluculate(DataProbe[] pointsInRange, int[] point) {
		return calculate.findPoint(pointsInRange, point);
	}
}