package certyficate.files;

import java.io.File;

public class PathCreator {
	private static StringBuilder PATH_TO_DESCOP = pathToDescop();
	private static String SHEETNAME = "Laboratorium.ods";
	
	private static StringBuilder pathToDescop() {
		StringBuilder path = new StringBuilder(System.getProperty("user.home"));
		path.append(File.separator);
		path.append(withSeparator("Descop"));
		return path;
	}
	
	private static StringBuilder withSeparator(String expression) {
		StringBuilder path = new StringBuilder(expression);
		path.append(File.separator);
		return path;
	}
	
	public static String certificatesPath() {
		StringBuilder path = new StringBuilder();
		path.append(archiwumPath());
		path.append(withSeparator("Świadectwa wzorcowania"));
		return path.toString();
	}

	public static String notePath() {
		StringBuilder path = new StringBuilder();
		path.append(archiwumPath());
		path.append(withSeparator("Zapiski"));
		return path.toString();
	}
	
	private static StringBuilder archiwumPath(){
		StringBuilder path = new StringBuilder(folderPath());
		path.append(withSeparator("Wyniki wzorcowań"));
		return path;
	}
	
	private static StringBuilder folderPath(){
		StringBuilder path = new StringBuilder(PATH_TO_DESCOP);
		path.append(withSeparator("Laboratorium"));
		return path;
	}
	
	public static String filePath(String fileName) {
		StringBuilder path = new StringBuilder(filePath());
		path.append(withSeparator(fileName));
		return path.toString();
	}
	
	public static StringBuilder filePath() {
		StringBuilder path = new StringBuilder(folderPath());
		path.append(withSeparator("generacja"));
		return path;
	}
		
	public static String sheetPath() {
		StringBuilder path = new StringBuilder(PATH_TO_DESCOP);
		path.append(SHEETNAME);
		return path.toString();
	}
	
}
