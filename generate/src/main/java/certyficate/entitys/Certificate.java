package certyficate.entitys;

import certyficate.dataContainer.IRData;

//dane do świadectwa
public class Certificate{
	public String num;
	public Client user= new Client();
	public Client declarant= new Client();
	public Device device = new Device();
	public String deviceSerial;
	public Probe probe= new Probe();
	public String[] probeSerial =null;
	public String probeSerialNumber;
	public String calibrationDate;
	public String calibrationCode;
	public int[][] point;
	public int channelNumber;
	public IRData pyrometr;

	/* TODO remove after tests
	public String toString(){
		String s= "numer świadectwa: "+num+"\n";
		s+="Zgłaszający:\n";
		s+=declarant+"\n";
		s+="Użytkownik:\n";
		s+=user+"\n";
    	s+="Urządzenie:\n";
    	s+=device+"\t"+deviceSerial+"\n";
    	if(!probeSerial.equals("")){
    		s+="Sonda:\n";
    		s+=probe+"\t"+probeSerial+"\n";
    	}
    	s+="kod wzorcowania:\t"+calibrationCode+"\n";
    	s+="data wzorcowania:\t"+calibrationDate;
    	return s;
	}*/
}

