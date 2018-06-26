package certyficate.property;

import java.io.File;
import java.io.IOException;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.dataContainer.CalibrationType;

public class SheetData {
	public static SpreadSheet SPREAD_SHEET;
	
	public static File FILE;
	
	public static String PATH;
	public static String SHEET_NAME;
	public static String PROBE_SERIAL_NUMBER;
	
	public static boolean RH;
	
	public static int POINTS;
	public static int DATE_COLUMN;
	public static int TIME_COLUMN;
	public static int POINT_GAP;
	public static int START_ROW;
	public static int NUMBER_OF_PARAMETERS = 1;
	public static int NUMBER_OF_DEVICES;

	public static void FilesSet(File file) throws IOException{
		FILE = file;
		SPREAD_SHEET = SpreadSheet.createFromFile(file);
	}

	public static void setChamberData(CalibrationType calibrationType) {
		if(calibrationType == CalibrationType.HUMINIDITY){
			RH = true;
			DATE_COLUMN = 34;
			TIME_COLUMN = 3;
			POINT_GAP = 45;
			START_ROW = 10;
			CalibrationData.numberOfParameters = 2;
			SHEET_NAME = "Zapiska Temp & RH";
			PROBE_SERIAL_NUMBER = "61602551"; // "20055774";
	        NUMBER_OF_DEVICES = 20;
		}else{
			setTemperatureSettings();
			PROBE_SERIAL_NUMBER = "20098288"; // "20068251";
		}
	}
	
	public static void setInfrared() {
		setTemperatureSettings();
		PROBE_SERIAL_NUMBER = "12030011";
	}
	
	private static void setTemperatureSettings() {
		DATE_COLUMN = 33;
		TIME_COLUMN = 2;
		POINT_GAP = 34;
		START_ROW = 9;
		SHEET_NAME = "Zapiska temp.";
		NUMBER_OF_DEVICES = 30;
	}
}
