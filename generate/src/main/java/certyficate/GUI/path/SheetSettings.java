package certyficate.GUI.path;

import java.io.File;

import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class SheetSettings extends PathSettings {

	SheetSettings(){
		currentPath = PathCreator.sheetPath();
		panelName = "Arkusz z danymi";
		file = new File(currentPath);
		updateFile(file);
	}
	@Override
	void updateFile(File file) {
		this.file = file;
		CalibrationData.sheet = file;
	}

}
