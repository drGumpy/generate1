package certyficate.generate;

public class DisplayedText {
	public static String account = "C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\";
	
	public static String probeDataPath = "P:\\Laboratorium\\Wyniki z wzorca\\";
	
	public static String dataPath = account+"Laboratorium\\generacja\\";
	
	public static String sheet = account+"Laboratorium.ods";
	
	public static String emissivity= "Emisyjność źródła ε=%.2f.";
	public static String distanceIR = "Odległość pirometru wzorcowanego od źródła w czasie wzorcowania wynosiła: %d mm";
	
	public static String calibraionSheet = "Świadectwo wzorcowania";
	public static String noteSheet = "Wyniki wzorcowania";
	
	public static String calibrationDevice = "%s, model: %s, producent: %s, nr seryjny: %s";
	public static String calibrationProbe1 = ", z %s, nr seryjny: %s.";
	public static String calibrationProbe2 = ", z %s model %s, producent: %s, nr seryjny: %s.";
	public static String enviromentT = "Temperatura: ";
	public static String enviromentRh = "Wilgotność: ";
	public static String[] notePath = {account+"Laboratorium\\Wyniki wzorcowań\\Zapiski\\",
			dataPath+"z_T.ods",
			dataPath+"z_T.ods",
			dataPath+"z_Rh.ods",
			dataPath+"z_T.ods"};
	public static String[] certificatePath={account+"Laboratorium\\Wyniki wzorcowań\\Świadectwa wzorcowania\\",
			dataPath+"sw_T.ods",
			dataPath+"sw_Tx2.ods",
			dataPath+"sw_Rh.ods",
			dataPath+"sw_IR.ods"};
	
	
	public static String channel = "Czujnik temperatury: %s (nazwa kanału: %s)";

}
