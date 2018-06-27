package certyficate.calculation;

import certyficate.dataContainer.DataProbe;

public class CalculateRh extends CalculateT {
	protected double[] findCorection() {
		double[] correction = super.findCorection();
		correction[0] = calculateHuminidityCorretion(0);
		return correction;
	}	
	
	protected void getFactors() {
		super.getFactors();
		int minHuminidity = pointsInRange[0].valueRh;
    	int maxHuminidity = pointsInRange[point.length].valueRh;
		factor[1]= getFactor((double)point[1], minHuminidity, maxHuminidity);;
	}
    
    //Przeszukiwanie zakresów pomiarowych
    private DataProbe find2(int[] range, int t, int rh) {
    	DataProbe sol = new DataProbe();
        // wyliczenia dla punktu pomiarowego
        double correctionT= (t-range[0])/(range[1]-range[0]);
        double correctionRh = (rh-range[2])/(range[3]-range[2]);
        sol.valueRh=rh;
        sol.valueT=t;
        sol.correctionRh = DataCalculation.calculate(correctionT, correctionRh, d1.correctionRh, d2.correctionRh,
                d3.correctionRh, d4.correctionRh);
        sol.correctionT = DataCalculation.calculate(correctionT, correctionRh, d1.correctionT, d2.correctionT,
                d3.correctionT, d4.correctionT);
        sol.driftRh = driftRh;
        sol.driftT = driftT;
        sol.uncertaintyRh= Math.max(Math.max(d1.uncertaintyRh, d2.uncertaintyRh),
                Math.max(d3.uncertaintyRh, d3.uncertaintyRh));
        sol.uncertaintyT = Math.max(Math.max(d1.uncertaintyT, d2.uncertaintyT),
                Math.max(d3.uncertaintyT, d3.uncertaintyT));
        return sol;
    }
    
    //wyznaczanie wartości poprawek
    private DataProbe easyCalculateRh(int t, int rh, int t1, int t2){
    	DataProbe d1 = null, d2=null;
        int b=0;
        for(int i=0; i<numberOfStandardPoint; i++){
            if(standardPoints[i].valueT ==t1 && standardPoints[i].valueRh==rh){
                d1=standardPoints[i];
                b++;
                continue;
            }
            if(standardPoints[i].valueT ==t2 && standardPoints[i].valueRh==rh){
                d2=standardPoints[i];
                b++;
                continue;
            }            
            if(b==2) break;
        }
        double cor= (t-t1)/(t2-t1);
        return DataCalculation.easyCalculate(cor, d1, d2);
    }
    
  //wyznaczanie wartości poprawek
    private DataProbe easyCalculateT(int t, int rh, int rh1, int rh2) {
    	DataProbe d1 = null, d2=null;
        int b=0;
        for(int i=0; i<numberOfStandardPoint; i++){
            if(standardPoints[i].valueT ==t && standardPoints[i].valueRh==rh1){
                d1=standardPoints[i];
                b++;
                continue;
            }
            if(standardPoints[i].valueT ==t && standardPoints[i].valueRh==rh2){
                d2=standardPoints[i];
                b++;
                continue;
            }            
            if(b==2) break;
        }
        double cor= (rh-rh1)/(rh2-rh1);
        return DataCalculation.easyCalculate(cor, d1, d2);
    }

	
}
