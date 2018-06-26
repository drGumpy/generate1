package certyficate.sheetHandlers.search;

import java.util.HashSet;

import certyficate.entitys.Certificate;
import certyficate.property.CalibrationData;

public class CodeVerification {
	static HashSet<String> VerificationCriteria;
	
	public static void setVerificationCriteria() {
		VerificationCriteria = new HashSet<String>();
		switch (CalibrationData.calibrationType) {
		case HUMINIDITY:
			VerificationCriteria.add("3");
			break;
		case INFRARED:
			VerificationCriteria.add("5");
			break;		
		default:
			VerificationCriteria.add("1");
			VerificationCriteria.add("2");
			break;
		}
		
	}
	
	public static boolean checkCalibrationCode(Certificate order) {
		String code = order.calibrationCode;
		int indexOfSeparator = code.indexOf("-");
		if(indexOfSeparator != -1)
			code = code.substring(2, indexOfSeparator);	
		return VerificationCriteria.contains(code);
	}
}
