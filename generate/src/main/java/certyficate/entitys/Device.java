package certyficate.entitys;

public class Device{
	public String model;
	public String type;
	public String producent;
	public String resolutionT;
	public String resolutionRh;
	public String[] channel;
	
	public String toString(){
		return type+"\t"+model+"\t"+producent;
	}
}
