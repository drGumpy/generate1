package certyficate.dataContainer;

public class Measurements{
	public int num;
	public Point[] measurmets;
	public String name;
	
	public Measurements(int numberOfPoint){
		measurmets = new Point[numberOfPoint];
	}
}