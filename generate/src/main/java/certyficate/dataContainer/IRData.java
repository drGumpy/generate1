package certyficate.dataContainer;

import certyficate.generate.*;

public class IRData {
	public double[] blackBodyError;
	public double emissivity;
	public int distance;
	public double[] reference;
	
	public String toString(){
		return String.format(DisplayedText.distanceIR, distance);
	}
}
