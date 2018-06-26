package certyficate.dataContainer.datalogger;

import java.io.File;

public class Data{
	public int number = 0;
	public File file = null;
	protected boolean RH = true;
	public int num = 0;
	public String date = "";
	public String time = "";
	public String temp = "";
	public String hum = "";
	
	//ustalanie rodzaju wzorcowania t/ t/Rh
	public Data(boolean RH){
		this.RH = RH;
	}
	//ustalenie wartości nr. przyrządu na liście i plik z danymi
	public void set(int number, File file){
		this.number= number;
		this.file= file;
	}
	//podzielenie linii na dane
	public Data divide(String nextLine) {
		return new Data(RH);
	}
	// liczba linii bez danych liczbowych
	public int getN() {
		return 0;
	}
	public String toString(){
		return temp+"\t"+hum;
	}
}
