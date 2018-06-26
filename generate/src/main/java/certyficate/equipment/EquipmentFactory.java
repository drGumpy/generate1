package certyficate.equipment;

import java.io.IOException;

import certyficate.calculation.CalculateT;
import certyficate.files.PathCreator;

public class EquipmentFactory {
	private Equipment equipment;
	
	public Equipment getEquipment(EquipmentType equipmentType)
			throws IOException {
		findAndGetEquipment (equipmentType);
		return equipment;
	}

	private void findAndGetEquipment(EquipmentType equipmentType) 
			throws IOException {
		switch(equipmentType) {
		case TEMPERATURE_REFERENCE:
			setTemperatureReference();
			break;
		case HUMIDITY_REFERENCE:
			setHumidityReference();
			break;
		case INFRARED_REFERENCE:
			setInfraredReference();
			break;
		case CHAMBER_TEMPERATURE:
			setChamberTemperature();
			break;
		case CHAMBER_HUMIDITY:
			setChamberHumidity();
			break;
		default:
			setBlackBodyGenerator();
			break;
		}
	}

	private void setTemperatureReference() throws IOException {
		String path = PathCreator.filePath("");
		equipment = new TProbe(path);
		equipment.calculate = new CalculateT();
	}
	
	private void setHumidityReference() throws IOException {
		String path = PathCreator.filePath("");
		equipment = new RhProbe(path);
	}
	
	private void setInfraredReference() throws IOException {
		String path = PathCreator.filePath("");
		equipment = new TProbe(path);
	}
	
	private void setChamberTemperature() throws IOException {
		String path = PathCreator.filePath("");
		//TODO chamber method
	}
	
	private void setChamberHumidity() throws IOException {
		String path = PathCreator.filePath("");
		//TODO chamber method
	}

	private void setBlackBodyGenerator() throws IOException {
		String path = PathCreator.filePath("");
		//TODO black body method
	}
	
}
