package certyficate.dataContainer;

public class ChamberData {
	int valueRh;
	int valueT;
	public double t1;
	public double t2;
	public double Rh1;
	public double Rh2;
	
	public String toString(){
		return valueT+"\t"+t1+"\t"+t2+"\t"+valueRh+"\t"+Rh1+"\t"+Rh2;
	}
}
