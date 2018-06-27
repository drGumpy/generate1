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


public class Generate {
    private int point;
    private Measurements patern;
    private ArrayList<Measurements> devices;
    private boolean Rh;
    private File note, cal;
    private String notePath, calPath;
    private ChamberData[] data;
    private DataProbe[] dataProbe;
    
    private ArrayList<String> done = new ArrayList<String>();
    
    private String[] environment;
    
    // informacja odnośnie sond i kanałów urządzeń
    private String _find(String probeSerial, String chanel) {
        String val ="";  
        if(!chanel.equals("")){
            val = String.format(DisplayedText.channel, probeSerial, chanel);
        }
        return val;
    }
    
    //wygenerowanie świadectwa wzorcowania
    private void _generateCal(ArrayList<CertificateValue> data, Certificate type) throws IOException{
        boolean sw2 = false;
        File file =cal;
        //typ świadectwa
        if(!Rh){
            if(type.calibrationCode.equals("SW2")){
                sw2=true;
                file= new File(DisplayedText.certificatePath[2]);
                }
            if(type.declarant.name.equals(Special.s) && type.device.model.equals("810-210")){ //dane wrażliwe
                sw2=true;
                file= new File(DisplayedText.certificatePath[2]);
            }
        }
        final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(DisplayedText.calibraionSheet);
        int col;
        //umieszczenie daty i numeru świadectwa
        if(Rh){
            sheet.setValueAt(new Date( ), 9 , 13);
            sheet.setValueAt(new Date( ), 9 , 70);
            sheet.setValueAt(type.num, 24 , 13);
            sheet.setValueAt(type.num, 24 , 70);
            col=13;
        }else{
            sheet.setValueAt(new Date( ), 8 , 13);
            sheet.setValueAt(new Date( ), 8 , 70);
            sheet.setValueAt(type.num, 22 , 13);
            sheet.setValueAt(type.num, 22 , 70);
            col=12;
        }
        //dane na temat przyrządu
        String name =String.format(DisplayedText.calibrationDevice,
                type.device.type, type.device.model, type.device.producent,type.deviceSerial);
        if(!type.probeSerial[0].equals("")){
            if(type.probe.type.equals(""))
                name+=String.format(DisplayedText.calibrationProbe1,
                        type.probe.model, type.probeSerialNumber);
            else
                name+=String.format(DisplayedText.calibrationProbe2,
                    type.probe.type,type.probe.model , type.probe.producent, type.probeSerialNumber);
            }
        else
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
        if(Rh){
            int line=84;
            int lenght= data.size();
            if(point>5)
                lenght=5;
            for(int i=0; i<lenght; i++){
                sheet.setValueAt(data.get(i).probeT, 3, line);
                sheet.setValueAt(data.get(i).probeRh, 8, line);
                sheet.setValueAt(data.get(i).deviceT, 13, line);
                sheet.setValueAt(data.get(i).deviceRh, 18, line);
                sheet.setValueAt(data.get(i).errorT, 23, line);
                sheet.setValueAt(data.get(i).errorRh, 28, line);
                sheet.setValueAt(data.get(i).uncertaintyT, 33, line);
                sheet.setValueAt(data.get(i).uncertaintyRh, 38, line);
                line+=2;
            }
            if(point<5){
                for(int i=0; i<5-lenght; i++){
                    sheet.setValueAt("", 3, line);
                    sheet.setValueAt("", 8, line);
                    sheet.setValueAt("", 13, line);
                    sheet.setValueAt("", 18, line);
                    sheet.setValueAt("", 23, line);
                    sheet.setValueAt("", 28, line);
                    sheet.setValueAt("", 33, line);
                    sheet.setValueAt("", 38, line);
                    line+=2;
                }
            }
        }else{
            int max =1;
            if(sw2){
                max=2;
            }
            int line=76;
            int points=data.size();
            int lenght;
            String channel;
            for(int j=0; j<max; j++){
                channel="";
                if(type.probeSerial.length>j)
                    channel = _find(type.probeSerial[j],type.device.channel[j]);
                else
                    channel = _find("",type.device.channel[j]);
                if(!channel.equals(""))
                    sheet.setValueAt(channel, 3, line);
                line+=8;
                lenght = points-3*j;
                if(lenght>3)
                    lenght=3;
                for(int i=3*j; i<3*j+lenght; i++){
                    sheet.setValueAt(data.get(i).probeT, 3, line);
                    sheet.setValueAt(data.get(i).deviceT, 12, line);
                    sheet.setValueAt(data.get(i).errorT, 21, line);
                    sheet.setValueAt(data.get(i).uncertaintyT, 30, line);
                    line+=2;
                }
                if(lenght<3){
                    for(int i=0; i<3*j-lenght; i++){
                        sheet.setValueAt("", 3, line);
                        sheet.setValueAt("", 12, line);
                        sheet.setValueAt("", 21, line);
                        sheet.setValueAt("", 30, line);
                        line+=2;
                    }
                }
                line+=1;
            }
        }
        name = calPath+type.num+"_"+type.declarant.name + ".ods";
        sheet.getSpreadSheet().saveAs(new File(name));
    }
    
    // generowanie zapiski wzorcowania
    private void _generateDoc(Measurements device, Certificate type){
        
        point = device.averageT.length;
        ArrayList<CertificateValue> cdata = new ArrayList<CertificateValue>();
        try {
            final Sheet sheet = SpreadSheet.createFromFile(note).getSheet(DisplayedText.noteSheet);
            int line =3;
            for(int i=0; i<point; i++){
                if(device.q[i] || !dataProbe[i].question)
                    continue;
                CertificateValue val= new CertificateValue();
                sheet.setValueAt(type.num, 3 , line);
                sheet.setValueAt(type.calibrationCode, 8 , line);
                sheet.setValueAt(type.calibrationDate, 13 , line);
                sheet.setValueAt(environment[0], 3 , line+1);
                sheet.setValueAt(environment[1], 7 , line+1);
                sheet.setValueAt(type.device.type, 3 , line+3);
                sheet.setValueAt(type.device.model, 3 , line+6);
                sheet.setValueAt(type.probe.model, 4 , line+6);
                sheet.setValueAt(type.device.channel[0], 3 , line+8);
                sheet.setValueAt(type.deviceSerial, 3 , line+9);
                if(i<3)
                    sheet.setValueAt(type.probeSerial[0], 4 , line+9);
                else if(type.probeSerial.length>1)
                    sheet.setValueAt(type.probeSerial[1], 4 , line+9);
                sheet.setValueAt(type.device.producent, 3 , line+11);
                sheet.setValueAt(type.probe.producent, 4 , line+11);
                sheet.setValueAt(type.device.resolutionT, 3 , line+13);
                if(Rh)
                    sheet.setValueAt(type.device.resolutionRh, 4 , line+13);
                for(int j=0; j<10; j++){
                //    System.out.println(patern.time[i]);
                    sheet.setValueAt(DataCalculation.time(patern.time[i], j), 0 , line+17+j);
                    sheet.setValueAt(patern.dataT[i][j], 1 , line+17+j);
                    sheet.setValueAt(device.dataT[i][j], 3 , line+17+j);
                    if(Rh){
                        sheet.setValueAt(patern.dataRh[i][j], 2 , line+17+j);
                        if(device.averageRh[i]==-1)
                            sheet.setValueAt("-", 4 , line+17+j);
                        else
                            sheet.setValueAt(device.dataRh[i][j], 4 , line+17+j);
                    }
                }
                
                double[] uncT= new double[8];
                uncT[0]=device.standardT[i];
                uncT[1]=Double.parseDouble(type.device.resolutionT)/Math.sqrt(3);
                uncT[2]=patern.standardT[i];
                uncT[3]=0.01/Math.sqrt(3);
                uncT[4]=dataProbe[i].uncertaintyT/2;
                uncT[5]=dataProbe[i].driftT/Math.sqrt(3);
                uncT[6]=data[i].t1/Math.sqrt(3);
                uncT[7]=data[i].t2/2;
 
                sheet.setValueAt(device.averageT[i], 7 , line+5);
                sheet.setValueAt(patern.averageT[i], 7 , line+7);
                sheet.setValueAt(dataProbe[i].correctionT, 7 , line+9);
                sheet.setValueAt(type.device.resolutionT, 9 , line+6);
                sheet.setValueAt(dataProbe[i].uncertaintyT, 9, line+9);
                sheet.setValueAt(dataProbe[i].driftT, 9, line+10);
                sheet.setValueAt(data[i].t1, 9, line+11);
                sheet.setValueAt(data[i].t2, 9, line+12);
                for(int j=0; j<uncT.length; j++){
                    sheet.setValueAt(uncT[j], 13, line+5+j);
                }
                double unc =DataCalculation.uncertainty(uncT);
                double round = DataCalculation.findRound(2*unc, Double.parseDouble(type.device.resolutionT));
                if(Rh){
                    if(round<0.1)
                        round=0.1;
                }
                double pt=DataCalculation.round_d(patern.averageT[i]+dataProbe[i].correctionT,round);
                double div =DataCalculation.round_d(device.averageT[i],round);
                val.probeT= DataCalculation.round(pt,round).replace(".", ",");
                val.deviceT = DataCalculation.round(div,round).replace(".", ",");
                val.errorT = DataCalculation.round(div-pt,round).replace(".", ",");
                val.uncertaintyT = DataCalculation.round(2*unc,round).replace(".", ",");
                
                if(Rh){
                    sheet.setValueAt(val.probeT, 5, line+14);
                    sheet.setValueAt(val.deviceT, 7, line+14);
                    sheet.setValueAt(val.errorT, 9, line+14);
                    sheet.setValueAt(val.uncertaintyT, 13, line+14);
                    double[] uncRh= new double[8];
                    uncRh[0]=device.standardRh[i];
                    uncRh[1]=Double.parseDouble(type.device.resolutionRh)/Math.sqrt(3);
                    uncRh[2]=patern.standardRh[i];
                    uncRh[3]=0.01/Math.sqrt(3);
                    uncRh[4]=dataProbe[i].uncertaintyRh/2;
                    uncRh[5]=dataProbe[i].driftRh/Math.sqrt(3);
                    uncRh[6]=data[i].Rh1/Math.sqrt(3);
                    uncRh[7]=data[i].Rh2/2;
                    
                    line+=13;
                    sheet.setValueAt(device.averageRh[i], 7 , line+5);
                    sheet.setValueAt(patern.averageRh[i], 7 , line+7);
                    sheet.setValueAt(dataProbe[i].correctionRh, 7 , line+9);
                    sheet.setValueAt(type.device.resolutionRh, 9 , line+6);
                    sheet.setValueAt(dataProbe[i].uncertaintyRh, 9, line+9);
                    sheet.setValueAt(dataProbe[i].driftRh, 9, line+10);
                    sheet.setValueAt(data[i].Rh1, 9, line+11);
                    sheet.setValueAt(data[i].Rh2, 9, line+12);
                    for(int j=0; j<uncRh.length; j++){
                        sheet.setValueAt(uncRh[j], 13, line+5+j);
                    }
                    if(device.averageRh[i]==-1){
                        val.probeRh= "-";
                        val.deviceRh = "-";
                        val.errorRh = "-";
                        val.uncertaintyRh = "-";
                    }else{
                    unc =DataCalculation.uncertainty(uncRh);
                    round = DataCalculation.findRound(2*unc, Double.parseDouble(type.device.resolutionRh));
                    pt=DataCalculation.round_d(patern.averageRh[i]+dataProbe[i].correctionRh,round);
                    div =DataCalculation.round_d(device.averageRh[i],round);
                        val.probeRh= DataCalculation.round(pt,round).replace(".", ",");
                        val.deviceRh = DataCalculation.round(div,round).replace(".", ",");
                        val.errorRh = DataCalculation.round(div-pt,round).replace(".", ",");
                        val.uncertaintyRh = DataCalculation.round(2*unc,round).replace(".", ",");
                    }
                    sheet.setValueAt(val.probeRh, 5, line+14);
                    sheet.setValueAt(val.deviceRh, 7, line+14);
                    sheet.setValueAt(val.errorRh, 9, line+14);
                    sheet.setValueAt(val.uncertaintyRh, 13, line+14);
                    line+=19;
                }else{
                    sheet.setValueAt(val.probeT, 5, line+17);
                    sheet.setValueAt(val.deviceT, 7, line+17);
                    sheet.setValueAt(val.errorT, 9, line+17);
                    sheet.setValueAt(val.uncertaintyT, 13, line+17);
                    line+=32;
                }
                cdata.add(val);
            }
            if(cdata.isEmpty()) return;
            String name = notePath+type.num+"_"+type.device.model + ".ods";
            sheet.getSpreadSheet().saveAs(new File(name));
            _generateCal(cdata,type);
            done.add(type.num);
        } catch (IOException e) {
        	System.out.println("błąd przy generowaniu");
        	e.printStackTrace();
    	}
    }
    
    //znalezienie odpowiednich szablonĂłw
    private void _findData(){
        if(patern.averageRh!=null){
            Rh = true;
            note = new File(DisplayedText.notePath[3]);
        	cal = new File(DisplayedText.certificatePath[3]);
        }else{
            Rh = false;
            note = new File(DisplayedText.notePath[1]);
        	cal = new File(DisplayedText.certificatePath[1]);
        }
    }
    //umieszczanie danych o wzorcowaniu
    public void putPatern(Measurements patern){
        this.patern= patern;
    }
   
    public void putEnvironment(String[] environment){
        this.environment = environment;
    }
    
    public void putDevice(ArrayList<Measurements> devices){
        this.devices = devices;
    }
    
    public void putChamber(ChamberData[] data){
        this.data = data;
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
            for(int j=0; j<devices.size(); j++){
                if(devices.get(j).name.equals(name)){
                    _generateDoc(devices.get(j), data.get(i));
                    devices.remove(j);
                    break;
                }
            }
        }
    }  
    //lista wykonanych świadectw wzorcowania
    public ArrayList<String> getDone() {
        return done;
    }
}