package certyficate.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class EnvironmentPanel extends JPanel {
	
	protected final double DEFAULT_TEMPERATURE = 22.000;
	protected final double DEFAULT_HUMINIDITY = 45.000;
	private final Dimension SIZE = new Dimension(400, 80);
	protected int PARAMETS_NUMBER = 4;
	protected String[] LABEL_TEXT  = {"t min","t max","Rh min","Rh max"}; 
	private String PANEL_TITLE = "Warunki środowiskowe";
	
	protected JFormattedTextField[] environment = new JFormattedTextField[PARAMETS_NUMBER];	
	private NumberFormat NumbersFormat;
	private GridBagConstraints constrain = new GridBagConstraints();
	private EnvironmentListener listener = new EnvironmentListener(this);

	EnvironmentPanel(){
		setPanelSettings();
		setEviromentsTextField();
		addFields();
		addListener();
	}

	private void setPanelSettings() {
		this.setPreferredSize(SIZE);
		this.setBorder(new TitledBorder(PANEL_TITLE));
		this.setLayout(new GridBagLayout());
		setNumbersFormat();
		setLayoutSettings();
	}

	private void setLayoutSettings() {
		this.setLayout(new GridBagLayout());
		constrain.fill = GridBagConstraints.HORIZONTAL;
		constrain.ipadx=10;
	}

	private void setNumbersFormat() {
		NumbersFormat = new DecimalFormat("#0.000");;
		NumbersFormat.setMinimumFractionDigits(3);
	}
	
	private void setEviromentsTextField() {
		 for(int i = 0; i < PARAMETS_NUMBER ; i++) {
		    	environment[i] = new JFormattedTextField(NumbersFormat);
		    	environment[i].setName(LABEL_TEXT[i]);
		    	if(i<2)
		    		environment[i].setValue(new Double(DEFAULT_TEMPERATURE));
		    	else
		    		environment[i].setValue(new Double(DEFAULT_HUMINIDITY));
		    	environment[i].setColumns(5);
		    	environment[i].addPropertyChangeListener(listener);
		 }
	}
	
	private void addFields() {
	    for(int i = 0; i < PARAMETS_NUMBER ; i++) {
	    	constrain.gridx = (i * 2) % PARAMETS_NUMBER;
	    	constrain.gridy = i / 2;
	    	this.add(environment[i], constrain);
	    	JLabel label= new JLabel();
	    	label.setText(LABEL_TEXT[i]);
	    	constrain.gridx++;
	    	this.add(label, constrain);
	    }	
	}
	
	private void addListener() {
		for(int i = 0; i < PARAMETS_NUMBER ; i++) {
	    	environment[i].addPropertyChangeListener(listener);
		}	
	}
	
	public double[] getEnviromentCondition() {
		double[] enviromentCondition = new double[PARAMETS_NUMBER];
		for(int i = 0; i < PARAMETS_NUMBER; i++)
			enviromentCondition[i] = getParametr(i);
		return enviromentCondition;
	}
	
	protected double getParametr(int index) {
		double parametr = Double.parseDouble(environment[index]
				.getValue().toString());
		return parametr;
	}
}
