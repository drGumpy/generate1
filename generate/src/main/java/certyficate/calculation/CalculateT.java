package certyficate.calculation;

import certyficate.dataContainer.DataProbe;

public class CalculateT extends Calculate {

	public DataProbe findPoint(DataProbe[] pointsInRange,
			int[] point) {
		super.findPoint(pointsInRange, point);
		getCorection();
		return getDataPoint();
	}

	private void getCorection() {
    	int minTemperature = pointsInRange[0].valueT;
    	int maxTemperature = pointsInRange[point.length].valueT;
		corection = new double[point.length];
		corection[0] = (point[0] - minTemperature)/(maxTemperature - minTemperature);
	}
	
    private DataProbe getDataPoint() {
		// TODO Auto-generated method stub
		return null;
	}
    
    

    
    //Przeszukiwanie zakresów pomiarowych
    private DataProbe find2(int[] range, int t) {
    	DataProbe d1 = null, d2=null, sol = new DataProbe();
        // wyliczenia dla punktu pomiarowego
        double correctionT= (t-range[0])/(range[1]-range[0]);
        sol.valueT=t;
        sol.correctionT = MetrologyMath.calculate(correctionT, d1.correctionT, d2.correctionT);
        sol.driftT = driftT;
        sol.uncertaintyT = Math.max(d1.uncertaintyT, d2.uncertaintyT);
        return sol;
    }

}
