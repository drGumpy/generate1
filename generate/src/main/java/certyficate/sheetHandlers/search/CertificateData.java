package certyficate.sheetHandlers.search;

import java.util.ArrayList;
import java.util.HashMap;
 
import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.entitys.*;
import certyficate.property.CalibrationData;

public class CertificateData {
    final private static String ORDERS_LABEL = "Zlecenia";
	public static int calibration;
    static Sheet sheet;
    
    private static ArrayList<Certificate> orders = new ArrayList<Certificate>();

    private static HashMap<String, Client> clientsData =new HashMap<String, Client>();
    
    private static HashMap<String, Probe> probesData =new HashMap<String, Probe>();
    
    private static HashMap<String, Device> devicesData =new HashMap<String, Device>();
    
    public static void findOrdersData() {
    	CodeVerification.setVerificationCriteria();
        setSheetAndfindOrders();
        ClientsData.findClientData(clientsData);
        DeviceData.findDeviceData(devicesData);
        ProbeData.findProbeData(probesData);
        gatherData();
        CalibrationData.orders = orders;
    }
    
    //Wyszukiwanie nie wsytawionych świadectw - brak daty wzorcowania
    private static void setSheetAndfindOrders()  {
        sheet = CalibrationData.spreadSheet.getSheet(ORDERS_LABEL);
        int line = findFirstOrder();   
        while(sheet.getValueAt(5,line)!="") {
            if(sheet.getValueAt(2,line)=="")
            	checkAndAddOrderData(line);
            line++;
        }
    }

	private static int findFirstOrder() {
    	int line = 0;
    	String element;
    	do {
    		line++;
    		element = (String) sheet.getValueAt(2,line);
    	} while(!"".equals(element));
		return line;
	}
	
	private static void checkAndAddOrderData(int line) {
		Certificate order = new Certificate();
		order.calibrationCode = sheet.getValueAt(9,line).toString();
		if(CodeVerification.checkCalibrationCode(order))
			addOrder(line, order);
	}
	
	private static void addOrder(int line, Certificate order) {
		String probe = sheet.getValueAt(8,line).toString();
        order.num = sheet.getValueAt(1,line).toString();
        order.declarant.name = sheet.getValueAt(3,line).toString();
        order.user.name = sheet.getValueAt(4,line).toString();
        order.device.model = sheet.getValueAt(5,line).toString();
        order.deviceSerial = sheet.getValueAt(6,line).toString();
        order.probe.model = sheet.getValueAt(7,line).toString();
        order.calibrationCode = sheet.getValueAt(9,line).toString();
        order.calibrationDate = sheet.getValueAt(10,line).toString();
        setProbe(order, probe);
        addToContainers(order);
	}

	private static void setProbe(Certificate order, String probe) {
		String[] probeSerialArray = {""};
		order.probeSerialNumber = probe;
        if(!probe.equals(",")) {
            probe = probe.replaceAll("\\s+", "");
            probeSerialArray = probe.split(",");
        }
		order.probeSerial = probeSerialArray;
	}

	private static void addToContainers(Certificate order) {
		clientsData.put(order.declarant.name, order.declarant);
		clientsData.put(order.user.name, order.user);
		devicesData.put(order.device.model, order.device);
        probesData.put(order.probe.model, order.probe);
        orders.add(order);
	}
    
    //wprowadza uzyskane dane do klas certificate
    private static void gatherData(){
        for (int i=0; i < orders.size(); i++)
        	completeCertyficationData(i);
    }
       
    private static void completeCertyficationData(int index) {
    	completeCertyficationData(index);
    	Certificate certyficate  = orders.get(index);
        certyficate.declarant = clientsData.get(orders.get(index).declarant.name);
        certyficate.user = clientsData.get(orders.get(index).user.name);
        certyficate.device = devicesData.get(orders.get(index).device.model);
        checkProbe(certyficate, index);
        orders.set(index, certyficate);
	}

	private static void checkProbe(Certificate certyficate, int index) {
		if(probesData.containsKey(orders.get(index).probe.model))
			certyficate.probe = probesData.get(orders.get(index).probe.model);
		else
			certyficate.probe = new Probe();	
	}
}

