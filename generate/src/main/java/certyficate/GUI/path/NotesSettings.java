package certyficate.GUI.path;

import java.io.File;

import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class NotesSettings extends PathSettings {

	NotesSettings() {
		currentPath = PathCreator.notePath();
		panelName = "Folder zapisu zapisek z wzorcowania";
		file = new File(currentPath);
		updateFile(file);
	}
	@Override
	void updateFile(File file) {
		CalibrationData.notes = file;
	}

}
