package certyficate.sheetHandlers;

import java.io.File;
import java.io.IOException;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.property.CalibrationData;
import certyficate.property.SheetData;

public class SheetBulider {
	public static void setSpreadSheet() throws IOException {
		File file = CalibrationData.sheet;
		try {
			setSheet(file);
		} catch (IOException e) {
			System.out.println("Brak dostępu do pliku :" + file.toString() + "/n");
			throw e;
		}
	}

	private static void setSheet(File file) throws IOException {
		SpreadSheet sheet = SpreadSheet.createFromFile(file);
		SheetData.SPREAD_SHEET = sheet;
	}
}
