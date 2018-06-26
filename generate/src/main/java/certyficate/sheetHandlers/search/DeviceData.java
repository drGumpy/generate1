package certyficate.sheetHandlers.search;

import java.util.HashMap;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.entitys.Device;
import certyficate.property.CalibrationData;

public class DeviceData {
	final private static String DEVICES_LABEL = "Urządzenia";
	
	private static Sheet sheet;
	private static HashMap<String, Device> data;
	
	public static void findDeviceData(HashMap<String, Device> devicesData) {
		int line = 1;
		sheet = CalibrationData.spreadSheet.getSheet(DEVICES_LABEL);
		data = devicesData;
        while(sheet.getValueAt(0,line) != ""){
        	checkModel(line);
            line++;
        }	
	}
	
	private static void checkModel(int line) {
		String model = sheet.getValueAt(0,line).toString();
        if(data.containsKey(model))
        	addDevice(line);
	}

	private static void addDevice(int line){
		Device device = new Device();
		String model = sheet.getValueAt(0,line).toString();
		String channel = sheet.getValueAt(11,line).toString();
		addChannels(device, channel);
        device.model = model;
        device.type = sheet.getValueAt(1,line).toString();
        device.producent = sheet.getValueAt(2,line).toString();
        device.resolutionT = sheet.getValueAt(4,line).toString();
        device.resolutionRh = sheet.getValueAt(7,line).toString();;
        data.put(model, device);
	}

	private static void addChannels(Device device, String channel) {
		String[] arr = {""};
		if(!channel.equals(","))
            arr = channel.split(",");
		device.channel = arr;
	}
}
