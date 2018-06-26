package certyficate.sheetHandlers.search;

import java.util.HashMap;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.entitys.Client;
import certyficate.property.CalibrationData;

public class ClientsData {
	final private static String CLIENTS_LABEL = "Klienci";
	
	private static Sheet sheet;
	private static HashMap<String, Client> data;
	
	public static void findClientData(HashMap<String, Client> clientsData) {
		int line = 1;
		sheet = CalibrationData.spreadSheet.getSheet(CLIENTS_LABEL);
		data = clientsData;
		while(sheet.getValueAt(0,line)!=""){
			checkName(line);
			line++;
		}   
	}
	
	private static void checkName(int line) {
		String name = sheet.getValueAt(0,line).toString();
		if(data.containsKey(name))
			addClient(line);
	}

	private static void addClient(int line) {
		Client client = new Client();
		String name = sheet.getValueAt(0,line).toString();
		client.name = name;
		client.address = sheet.getValueAt(1,line).toString();
		client.postalCode = sheet.getValueAt(2,line).toString();
		client.town = sheet.getValueAt(3,line).toString();
		data.put(name, client);
	}

}
