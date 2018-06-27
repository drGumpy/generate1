package certyficate.generate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.calculation.*;
import certyficate.dataContainer.*;
import certyficate.entitys.*;
import certyficate.generate.DisplayedText;


public class IRGenerate {
	private int point;
    private ArrayList<Measurements> devices;
    private File note, cal;
    private String notePath, calPath;
    private DataProbe[] dataProbe;
    
    private ArrayList<String> done = new ArrayList<String>();
    
    private String[] environment;
    

    //wygenerowanie świadectwa wzorcowania

    private void _generateCal(ArrayList<CertificateValue> data, Certificate type) throws IOException{
        final Sheet sheet = SpreadSheet.createFromFile(cal).getSheet(DisplayedText.calibraionSheet);
        int col;
        //umieszczenie daty i numeru świadectwa
        sheet.setValueAt(new Date(), 8 , 13);
        sheet.setValueAt(new Date(), 8 , 70);
        sheet.setValueAt(type.num, 22 , 13);
        sheet.setValueAt(type.num, 22 , 70);
        col=12;
        
        //dane na temat przyrządu
        String name =String.format(DisplayedText.calibrationDevice,
                type.device.type, type.device.model, type.device.producent,type.deviceSerial);
       
        name+=".";
        //dane na temat klientów i wzorcowań
        sheet.setValueAt(name, col , 16);
        sheet.setValueAt(type.declarant.name, col , 20);
        sheet.setValueAt(type.declarant, col , 21);
        sheet.setValueAt(type.user.name, col , 23);
        sheet.setValueAt(type.user, col , 24);
        sheet.setValueAt(DisplayedText.enviromentT+environment[0], col , 30);
        sheet.setValueAt(DisplayedText.enviromentRh+environment[1], col , 31);
        sheet.setValueAt(type.calibrationDate, col , 33);
        
        //wprwadzanie danych liczbowych z wzorcowania

        int line=84;

        int points=data.size();
        int lenght;
        lenght = points;
        if(lenght>3)
        	lenght=3;
        for(int i=0; i<lenght; i++){
        	sheet.setValueAt(data.get(i).probeT, 3, line);
        	sheet.setValueAt(data.get(i).deviceT, 12, line);
        	sheet.setValueAt(data.get(i).errorT, 21, line);
        	sheet.setValueAt(data.get(i).uncertaintyT, 30, line);
        	line+=2;
        }


        line=95;
        sheet.setValueAt(type.pyrometr, 4, line);
        sheet.setValueAt(String.format(DisplayedText.emissivity,type.pyrometr.emissivity) 
        		, 4, line+1);
        name = calPath+type.num+"_"+type.declarant.name + ".ods";
        sheet.getSpreadSheet().saveAs(new File(name));       
    }
    
    // generowanie zapiski wzorcowania
    private void _generateDoc(Measurements device, Certificate type){
        
        point = device.averageT.length;
        ArrayList<CertificateValue> cdata = new ArrayList<CertificateValue>();
        try {
            final Sheet sheet = SpreadSheet.createFromFile(note).getSheet(DisplayedText.noteSheet);
            int count=0;
            for(int i=0; i<point; i++){
                if(device.q[i] || !dataProbe[i].question)
                    continue;
                if(dataProbe[i].valueT!=type.point[0][count])
                	continue;
                CertificateValue val= new CertificateValue();
                int line = i*32+3;
                sheet.setValueAt(type.num, 3 , line);
                sheet.setValueAt(type.calibrationCode, 8 , line);
                sheet.setValueAt(type.calibrationDate, 13 , line);
                sheet.setValueAt(environment[0], 3 , line+1);
                sheet.setValueAt(environment[1], 7 , line+1);
                sheet.setValueAt(type.device.type, 3 , line+3);
                sheet.setValueAt(type.device.model, 3 , line+6);
                sheet.setValueAt(type.deviceSerial, 3 , line+9);
                sheet.setValueAt(type.device.producent, 3 , line+11);
                sheet.setValueAt(type.device.resolutionT, 3 , line+13);
                for(int j=0; j<10; j++){
                    sheet.setValueAt(type.pyrometr.reference[count],
                    		1 , line+17+j);
                    sheet.setValueAt(device.dataT[i][j], 3 , line+17+j);
                }
                
                double[] uncT= new double[7];
                uncT[0]=device.standardT[i];
                uncT[1]=Double.parseDouble(type.device.resolutionT)/Math.sqrt(3);
                uncT[2]=0;
                uncT[3]=0.1/Math.sqrt(3);
                uncT[4]=dataProbe[i].uncertaintyT/2;
                uncT[5]=dataProbe[i].driftT/Math.sqrt(3);
                uncT[6]=type.pyrometr.blackBodyError[count]/2;
 
                sheet.setValueAt(device.averageT[i], 7 , line+5);
                sheet.setValueAt(type.pyrometr.reference[count], 7 , line+7);
                sheet.setValueAt(dataProbe[i].correctionT, 7 , line+9);
                sheet.setValueAt(type.device.resolutionT, 9 , line+6);
                sheet.setValueAt(dataProbe[i].uncertaintyT, 9, line+9);
                sheet.setValueAt(dataProbe[i].driftT, 9, line+10);
                sheet.setValueAt(type.pyrometr.blackBodyError[count], 9, line+11);
                for(int j=0; j<uncT.length; j++){
                    sheet.setValueAt(uncT[j], 13, line+5+j);
                }
                double unc =DataCalculation.uncertainty(uncT);
                double round = DataCalculation.findRound(2*unc, Double.parseDouble(type.device.resolutionT));
                double pt=DataCalculation.round_d(type.pyrometr.reference[count]+
                		dataProbe[i].correctionT,round);
                double div =DataCalculation.round_d(device.averageT[i],round);
                val.probeT= DataCalculation.round(pt,round).replace(".", ",");
                val.deviceT = DataCalculation.round(div,round).replace(".", ",");
                val.errorT = DataCalculation.round(div-pt,round).replace(".", ",");
                val.uncertaintyT = DataCalculation.round(2*unc,round).replace(".", ",");
                
                sheet.setValueAt(val.probeT, 5, line+17);
                sheet.setValueAt(val.deviceT, 7, line+17);
                sheet.setValueAt(val.errorT, 9, line+17);
                sheet.setValueAt(val.uncertaintyT, 13, line+17);
                count++;
                cdata.add(val);
            }
            String name = notePath+type.num+"_"+type.device.model + ".ods";
            sheet.getSpreadSheet().saveAs(new File(name));
            _generateCal(cdata,type);
            done.add(type.num);
        }catch (IOException e){}
    }
    
    //znalezienie odpowiednich szablonów
    private void _findData(){
    	note = new File(DisplayedText.notePath[4]);
    	cal = new File(DisplayedText.certificatePath[4]);
    }
    //umieszczanie danych na temat wzorcowania
    public void putEnvironment(String[] environment){
        this.environment = environment;
    }
    
    public void putDevice(ArrayList<Measurements> devices){
        this.devices = devices;
    }
    
    public void putPaths(String notePath, String calPath){
        this.notePath = notePath;
        this.calPath = calPath;
    }
    
    public void putDataProbe(DataProbe[] dataProbe){
        this.dataProbe=dataProbe;
    }
    
    //parowanie informacji odnośnie wzorcowania
    public void run(ArrayList <Certificate> data){
        int n=data.size();
        _findData();
        for(int i=0; i<n; i++){
            if(devices.size()==0) break;
            String name = data.get(i).deviceSerial;
//          System.out.println("szukam: "+name);
            for(int j=0; j<devices.size(); j++){
//            	System.out.println(devices.get(j).name);
                if(devices.get(j).name.equals(name)){
                    _generateDoc(devices.get(j), data.get(i));
                    devices.remove(j);
                    break;
                }
            }
        }
    }  
    //lista wykonanych �wiadectw wzorcowania
    public ArrayList<String> get_done() {
        return done;
    }
}
