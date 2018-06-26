package certyficate.dataContainer;

import certyficate.property.CalibrationData;

//przechowywanie danych o wzorcowanych puntkach
public class CalibrationPoint{
	public int number;
	public String date = "";
	public String time = "";
	public int[] calibrationPoint;
	
	public CalibrationPoint() {
		calibrationPoint = new int[CalibrationData.numberOfParameters];
	}
	
	//ustalenie wartości nr. przyrządu na liście i plik z danymi
	public void set(int number){
		this.number= number;
	}
}