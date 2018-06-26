package certyficate.dataContainer;

public class DataProbe {
	public boolean question = true;
	
	public int valueT;
	public int valueRh = 0;
	
	public double correctionT;
    public double correctionRh = 0;
    
    public double uncertaintyT;
    public double uncertaintyRh = 0;
    
    public double driftT;
    public double driftRh = 0;
    
	public DataProbe() {
		super();
	}
	
	public DataProbe(boolean set) {
		question = set;
	}
}