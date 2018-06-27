package certyficate.equipment;

import java.io.BufferedReader;
import java.io.IOException;

import certyficate.calculation.CalculateT;
import certyficate.files.ReaderCreator;

public abstract class Equipment {
	final protected String SEPARATOR = "/t";
	
	public CalculateT calculate;
	
    protected int numberOfStandardPoint;
    protected int numberOfRanges;
    
    protected int[][] ranges;
    
    public Equipment(String file) throws IOException {
    	BufferedReader reader = ReaderCreator.getReader(file);
    	getConstantData(reader.readLine());
    	getCalibrationData(reader);
    	getNuberOfRanges(reader.readLine());
    	getRangesData(reader);
    	reader.close();
    }

	protected abstract void getConstantData(String line);
	
	private void getCalibrationData(BufferedReader reader) throws IOException {
		for(int i = 0; i < numberOfStandardPoint; i++) {
			getCalibrationPoint(reader.readLine(), i);
		}
	}

	protected abstract void getCalibrationPoint(String line, int index);
	
	private void getNuberOfRanges(String line) {
		numberOfRanges = getInteger(line);	
	}
	
	protected int getInteger(String element) {
		int data = Integer.parseInt(element);
		return data;
	}
	
	protected double getDouble(String element) {
		double data = Double.parseDouble(element);
		return data;
	}
	
	private void getRangesData(BufferedReader reader) throws IOException {
		for(int i = 0; i < numberOfRanges; i++) {
			getRange(reader.readLine(), i);
		}
	}

	protected abstract void getRange(String readLine, int index);
	
	protected abstract <E> E getPointData(int[] point);
}
