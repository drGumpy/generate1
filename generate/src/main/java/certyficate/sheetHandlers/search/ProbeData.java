package certyficate.sheetHandlers.search;

import java.util.HashMap;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.entitys.Probe;
import certyficate.property.CalibrationData;

public class ProbeData {
	final private static String PROBES_LABEL = "Sondy";
	
	private static Sheet sheet;
	private static HashMap<String, Probe> data;
	
	public static void findProbeData(HashMap<String, Probe> probesData) {
		int line = 1;
		sheet = CalibrationData.spreadSheet.getSheet(PROBES_LABEL);
		data = probesData;
		while(sheet.getValueAt(0,line) != "") {
			checkModel(line);		
			line++;
		}
	}

	private static void checkModel(int line) {
		String  model = sheet.getValueAt(0,line).toString();
		if(data.containsKey(model))
			addProbe(line);
		line++;
	}

	private static void addProbe(int line) {
		Probe nunczaku = new Probe();
		String  model = sheet.getValueAt(0,line).toString();
		nunczaku.model = model;
		nunczaku.type = sheet.getValueAt(1,line).toString();
		nunczaku.producent = sheet.getValueAt(2,line).toString();
		data.put(model, nunczaku);		
	}
}
