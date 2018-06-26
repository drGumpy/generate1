package certyficate.GUI.path;

import java.io.File;

import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class CertificateSettings extends PathSettings {

	CertificateSettings() {
		currentPath = PathCreator.certificatesPath();
		panelName = "Folder zapisu świadectw wzorcowania";
		file = new File(currentPath);
		updateFile(file);
	}
	
	@Override
	void updateFile(File file) {
		CalibrationData.certificate = file;
	}

}
