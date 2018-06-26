package certyficate.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import certyficate.calculation.Environment;
import certyficate.dataContainer.CalibrationType;
import certyficate.dataContainer.DataProbe;
import certyficate.equipment.Equipment;
import certyficate.equipment.EquipmentParameters;
import certyficate.equipment.EquipmentType;
import certyficate.equipment.TProbe;
import certyficate.generate.DisplayedText;
import certyficate.generate.IRGenerate;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;
import certyficate.sheetHandlers.SheetBulider;
import certyficate.sheetHandlers.insert.PutDate;
import certyficate.sheetHandlers.search.CertificateData;
import certyficate.sheetHandlers.search.MeasurementsData;

@SuppressWarnings("serial")
public class PyrometerPanel extends JPanel {
	final private Dimension SIZE = new Dimension(200, 23);
	
	final private JButton calibrationData = new JButton("wybierz zlecenia");
	final private JButton generation= new JButton("generuj świadetwa");
	
	public PyrometerPanel() {
		setButtons();
	}
	
	private void setButtons() {
		setCalibrationDataButton();
		setGenerationButton();
		addElements();
	}

	private void setCalibrationDataButton() {
		calibrationData.setMinimumSize(SIZE);
		calibrationData.addActionListener(new IRGenerateListener());
	}
	
	private class IRGenerateListener implements ActionListener {
		final private static int MAXIMUM_CALIBRATION_POINTS = 6;
		
		public void actionPerformed(ActionEvent e) {
			CalibrationData.calibrationType = CalibrationType.INFRARED;
			SheetData.setInfrared();
			getCalibrationCertyficate();
		}

		private void getCalibrationCertyficate()  {
			try {
				findCalibrationData();
				generateCalibrationCeryficate();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		private void findCalibrationData() throws IOException {
			bulidSheet();
			getCalibrationData();
			findMeasurementsData();
			findReferenceData();
		}

		private void bulidSheet() throws IOException  {
			SheetBulider.setSpreadSheet();
	
		}
		
		private void getCalibrationData() {
			CertificateData.findOrdersData();
		}
		

		private void findMeasurementsData() {
			CalibrationData.calibrationPoints 
				= MAXIMUM_CALIBRATION_POINTS;
			MeasurementsData.findMeasurementsData();
		}
		
		private void findReferenceData() throws FileNotFoundException {
			EquipmentParameters.find(EquipmentType.INFRARED_REFERENCE);
		}	

		private void generateCalibrationCeryficate() {
			// TODO Auto-generated method stub
			
		}

		public void doing() {
		/*	File file = sheetFinder.getFile();
            CertificateData.setFile(file);
            CertificateData.calibration=5;
            try {
                CertificateData.run();
                data=CertificateData.getData();
            } catch (IOException e1) {}
            new IRChoose(Console.this, true, data);
            try {
                GetData.setData(false);
                GetData.IR();
                GetData.setFile(file);
                devices = GetData.findData(6);
                point = GetData.getPoint();
            } catch (IOException e1) {}
            try {
                dataProbe = new DataProbe[point.size()];
                PaternProbe probe;
                probe= new TProbe(new File(DisplayedText.dataPath+"12030011.txt"));
                for(int i=0; i<point.size(); i++){
                    int t=Integer.parseInt(point.get(i).temp);
                    dataProbe[i]=probe.get(t, 0);
                }
            } catch (IOException e1) {}
            generation.setEnabled(true);
        }    
		}*/
	}
	}
	
	
	private void setGenerationButton() {
		generation.setMinimumSize(SIZE);
    	generation.setEnabled(false);
    	generation.addActionListener(new GenerationListener());
	}
	
	private class GenerationListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void doing() {
		/*	 IRGenerate make = new IRGenerate();
             make.putDataProbe(dataProbe);
             make.putDevice(devices);
			 make.putPaths(notesFinder.getFile().toString(),
					 certificateFinder.getFile().toString());
             Environment d= new Environment();
             make.putEnvironment(d.calculateData(
            		 environment.getEnviromentCondition()));
             make.run(data);
             try {
                 File file = sheetFinder.getFile();
                 PutDate.putFile(file);
                 PutDate.date(make.get_done());
             } catch (IOException e1) {
                 e1.printStackTrace();
             }
             System.out.println("koniec wprowadzania");
		}*/
		}
	}
			
	private void addElements() {
		add(calibrationData);
        add(generation);
	}

}
