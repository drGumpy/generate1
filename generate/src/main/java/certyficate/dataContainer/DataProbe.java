package certyficate.dataContainer;

public class DataProbe {
	public boolean question = true;
	
	public int valueT;
	public int valueRh;
	
	public double correctionT;
    public double correctionRh;
    
    public double uncertaintyT;
    public double uncertaintyRh;
    
    public double driftT;
    public double driftRh;
    
	public DataProbe() {
		super();
	}
	
	public DataProbe(boolean set) {
		question = set;
	}
	
	public DataProbe(int[] point) {
		valueT = point[0];
		if(point.length == 2)
			valueRh = point[1];
	}
	
	public void setDrift(DataProbe data) {
		driftT = data.driftT;
		driftRh = data.driftRh;
	}
}