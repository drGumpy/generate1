package certyficate.GUI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EnvironmentListener implements PropertyChangeListener{
	
	private EnvironmentPanel panel;
	
	private double MIN_TEMPERATURE = 17;
	private double MAX_TEMPERATURE = 27;
	private double MIN_HUMINIDITY = 20;
	private double MAX_HUMINIDITY = 80;
	
	private double minTemperature;
	private double maxTemperature;
	private double minHuminidity;
	private double maxHuminidity;
	
	EnvironmentListener(EnvironmentPanel panel) {
		this.panel = panel;
		minTemperature = panel.DEFAULT_TEMPERATURE;
		maxTemperature = panel.DEFAULT_TEMPERATURE;
		minHuminidity = panel.DEFAULT_HUMINIDITY;
		maxHuminidity = panel.DEFAULT_HUMINIDITY;
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		Object text = e.getSource();
		int index = findIndex(text);
		checkParametr(index);
	}

	private void checkParametr(int index) {
		double parametr = panel.getParametr(index);
		switch(index) {
		case 0: minTemperature = parametr;
				break;
		case 1: maxTemperature = parametr;
				break;
		case 2: minHuminidity = parametr;
				break;
		default: maxHuminidity = parametr;
		}
		if(index < 2)
			checkTemperature();
		else
			checkHuminidity();
			
	}

	private int findIndex(Object text) {
		int index = 0;
		for(int i = 0; i < panel.PARAMETS_NUMBER; i++) {
			if(panel.environment[i].equals(text))
				index = i;
		}
		return index;
	}
	
	private void checkTemperature() {
		if(minTemperature > maxTemperature) {
			maxTemperature += minTemperature;
			minTemperature = maxTemperature - minTemperature;
			maxTemperature -= minTemperature;
		}
		if(minTemperature < MIN_TEMPERATURE) {
			minTemperature = MIN_TEMPERATURE;
		}
		if(maxTemperature > MAX_TEMPERATURE) {
			maxTemperature = MAX_TEMPERATURE;
		}
		panel.environment[0].setValue(minTemperature);
		panel.environment[1].setValue(maxTemperature);
	}

	private void checkHuminidity() {
		if(minHuminidity > maxHuminidity) {
			maxHuminidity += minHuminidity;
			minHuminidity = maxHuminidity - minHuminidity;
			maxHuminidity -= minHuminidity;
		}
		if(minHuminidity < MIN_HUMINIDITY) {
			minHuminidity = MIN_HUMINIDITY;
		}
		if(maxHuminidity > MAX_HUMINIDITY) {
			maxHuminidity = MAX_HUMINIDITY;
		}
		panel.environment[2].setValue(minHuminidity);
		panel.environment[3].setValue(maxHuminidity);
	}
}