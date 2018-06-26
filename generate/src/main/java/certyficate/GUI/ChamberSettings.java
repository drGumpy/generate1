package certyficate.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import certyficate.dataContainer.CalibrationType;
import certyficate.property.CalibrationData;

@SuppressWarnings("serial")
public class ChamberSettings extends JPanel {
	private final String PANEL_TITLE = "ilość punktów pomiarowych i rodzaj wzorcowania";
	private final AbstractButton temperature = new JRadioButton("temperatura"); 
	private final AbstractButton huminidity = new JRadioButton("temperatura i wilgotność");
	private JComboBox<Integer> pointsBox = new JComboBox<Integer>();   
	static int points = 3;
	static CalibrationType calibrationType = CalibrationType.TEMPERATURE;
	
	public ChamberSettings() {
		setButtonGroup();
		setPointsBox();
		setPanel();
		updateCalibrationData();
	}

	private void setButtonGroup() {
		setButtons();
		ButtonGroup buttons = new ButtonGroup();
		buttons.add(huminidity);
		buttons.add(temperature);
	}

	private void setButtons() {
		setHuminidityButton();
		setTemperaturButton();
	}

	private void setHuminidityButton() {
		huminidity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setNewParametrs(true, 5);
			}
		});
	}
	
	private void setTemperaturButton() {
		temperature.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setNewParametrs(false, 3);
			}
		});
		temperature.setSelected(true);
	}
	
	private void setNewParametrs(boolean Rh, int pointsNumber) {
		calibrationType = CalibrationType.TEMPERATURE;
		points = pointsNumber;
		pointsBox.setSelectedIndex(pointsNumber - 1);
	}
	
	private void setPointsBox() {
		for(int i = 1; i < 7; i++)
			pointsBox.addItem(i);
		pointsBox.setSelectedIndex(2);		
	}
	
	private void setPanel() {
		setBorder(new TitledBorder(PANEL_TITLE));
		addElements();
	}
	
	private void addElements() {
		add(pointsBox);	
		add(temperature);
		add(huminidity);
	}
	
	void updateCalibrationData() {
		CalibrationData.calibrationType = CalibrationType.HUMINIDITY;
		CalibrationData.calibrationPoints = points;
	}
}
