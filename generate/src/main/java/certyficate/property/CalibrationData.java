package certyficate.property;

import java.io.File;
import java.util.ArrayList;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.dataContainer.CalibrationPoint;
import certyficate.dataContainer.CalibrationType;
import certyficate.dataContainer.Measurements;
import certyficate.entitys.Certificate;

public class CalibrationData {
	final public static int MEASUREMENTS_POINTS = 10;
	public static int calibrationPoints;
	public static int numberOfParameters = 1;
	
	public static String referenceSerial;
	
	public static ArrayList<Certificate> orders = new ArrayList<Certificate>();
	public static ArrayList<Measurements> devices = new ArrayList<Measurements>();
	public static ArrayList<CalibrationPoint> point = new ArrayList<CalibrationPoint>();
	
	public static Measurements patern = new Measurements(1);
	
	public static CalibrationType calibrationType;
	
	public static SpreadSheet spreadSheet;
	
	public static File sheet;
	public static File certificate;
	public static File notes;
}
