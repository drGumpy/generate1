package certyficate.files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ReaderCreator {
	final private static String CODING = "UTF-8";
	
	public static BufferedReader getReader(String filePath) throws FileNotFoundException {
		BufferedReader reader = newReader(filePath);
		return reader;
	}
	
	private static BufferedReader newReader(String filePath) throws FileNotFoundException {
		InputStreamReader streamReader = getStreamReader(filePath);
		return new BufferedReader(streamReader);
	}
	
	private static InputStreamReader getStreamReader(String fileName) throws FileNotFoundException {
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(getStream(fileName), CODING);
		} catch (UnsupportedEncodingException e) {
			throw new FileNotFoundException("File coding error /n");
		}
		return reader;
	}

	private static InputStream getStream(String fileName) throws FileNotFoundException {
		InputStream stream = new FileInputStream(fileName);
		return stream;
	}
}
