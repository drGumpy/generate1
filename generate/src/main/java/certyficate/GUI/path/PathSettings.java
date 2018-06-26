package certyficate.GUI.path;

import java.io.File;

public class PathSettings {
	String panelName;
	String currentPath;
	
	protected File file;
	
	void updateFile(File file) {
		this.file = file;
	}	
	
	public File getFile() {
		return file;
	}
	
	public static PathSettings getSettings(PathType pathType) {
		PathSettings settings;
		switch(pathType) {
		case CERTIFICATES:
			settings = new CertificateSettings();
			break;
		case NOTES:
			settings = new NotesSettings();
			break;
		default:
			settings = new SheetSettings();
			break;	
		}
		return settings;
	}
}
