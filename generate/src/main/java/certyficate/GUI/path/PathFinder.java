package certyficate.GUI.path;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PathFinder  extends JPanel {
	private final Dimension SIZE = new Dimension(650, 50);
	private final String BUTTON_TEXT = "Zmień";
	
	private File file;
	private PathType pathType;
	private JTextField PathTextField = new JTextField(48);
	
	private PathSettings setting = new PathSettings();
	
	public File getFile() {
		return file;
	}
	
	public PathFinder(PathType pathType) {
		this.pathType = pathType;
		setSettingsAndFile(pathType);
		setPanelSettings();
		setPanelElements();
	}

	private void setSettingsAndFile(PathType pathType) {
		setting = PathSettings.getSettings(pathType);
		file = setting.getFile();
	}

	private void setPanelSettings() {
		String panelName = setting.panelName;
		this.setPreferredSize(SIZE);
		this.setLayout(new FlowLayout());
		this.setBorder(new TitledBorder(panelName));
	}

	private void setPanelElements() {
		addButton();
		addTextField();
	}
	
	private void addButton() {
		JButton button = createButton();
		this.add(button);
	}
	
	private void addTextField() {
		setTextField();
		this.add(PathTextField);
	}

	private JButton createButton() {
		JButton button = new JButton(BUTTON_TEXT);
		button.addActionListener(new ButtonListener());
		return button;
	}

	private void setTextField() {
		String currentPath = file.toString();
		PathTextField.setText(currentPath);
		PathTextField.setEditable(false);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = getChooser();
            chooser.showOpenDialog(chooser);
            setPath(chooser);   
        }

		private JFileChooser getChooser() {
			JFileChooser chooser = new JFileChooser(file);
			if(pathType != PathType.SHEET)
				chooser.setFileSelectionMode(
						JFileChooser.DIRECTORIES_ONLY);
			return chooser;
		}

		private void setPath(JFileChooser chooser) {
			file = chooser.getSelectedFile();
            PathTextField.setText(file.toString());
            setting.updateFile(file);
		}
	}
}
