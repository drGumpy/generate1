package certyficate.sheetHandlers.search;

import java.util.ArrayList;
 
import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.dataContainer.*;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;

public class MeasurementsData {
	private static ArrayList<CalibrationPoint> points 
		= new ArrayList<CalibrationPoint>();
    
	static Sheet sheet;
    
	private static int calibrationPoints 
		= CalibrationData.calibrationPoints;

    public static void findProbeData() {
    	int line = SheetData.START_ROW - SheetData.NUMBER_OF_PARAMETERS;
    	Measurements reference = MeasurementResults.findDeviceResults(line);
    	CalibrationData.patern = reference;
	}
	
	public static void findMeasurementsData() {
		setSheet();
		getCalibtationPoints();
		MeasurementResults.findMeasurmentData();
	}
	
	private static void setSheet() {
		sheet = SheetData.SPREAD_SHEET.getSheet(SheetData.SHEET_NAME);
	}
	
	private static void getCalibtationPoints() {
		int line = 6;
		for(int i = 0; 
				i < calibrationPoints; i++){
			addCalibrationPoint(line);
			line += SheetData.POINT_GAP;
		}
	}

	private static void addCalibrationPoint(int line) {
		CalibrationPoint point = new CalibrationPoint();
		point.time = sheet.getValueAt(SheetData.TIME_COLUMN, line)
				.toString();
		point.date = sheet.getValueAt(SheetData.DATE_COLUMN, line)
				.toString();
		point.number = points.size();
		point.calibrationPoint = getPoint(line);
		points.add(point);
	}

	private static int[] getPoint(int line) {
		int[] point = new int[CalibrationData.numberOfParameters];
		for(int i = 0; i < CalibrationData.numberOfParameters; i++) 
			point[i] = Integer.parseInt(sheet.getValueAt(1 - i, line).toString());
		return point;
	}
}
 

