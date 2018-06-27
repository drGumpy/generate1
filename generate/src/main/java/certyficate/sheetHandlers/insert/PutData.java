package certyficate.sheetHandlers.insert;

import java.io.BufferedReader;
import java.io.File;
 
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.generate.*;
import certyficate.property.SheetData;
import certyficate.calculation.*;
import certyficate.dataContainer.*;
import certyficate.dataContainer.datalogger.*;
import certyficate.sheetHandlers.*;
import certyficate.sheetHandlers.search.*;
    
public class PutData {
        
    //parametry wzorcowania i plik docelowy
    static boolean Rh = true;                            //wzorcowanie wilgotności
    static int points = 5;                                 //liczba punktów wzorcowania
    static File file= new File(DisplayedText.sheet);
    
    //zmienne z danymi o wzorcowaniu
    static ArrayList<CalibrationPoint> loggers = new ArrayList<CalibrationPoint>();     //wzorcowanie rejejestraotory
    static ArrayList<CalibrationPoint> punkt = new ArrayList<CalibrationPoint>();     //informacje o punktach wzorcowania
        
    //sortowanie punktów na podstawie daty i godzinny
    private static void _sort(CalibrationPoint[] punkty){
        double[] arr = new double[points];
        for(int i=0; i<points;i++){
            String[] date = punkty[i].date.split("\\.");
            String[] time = punkty[i].time.split(":");
            if(date.length>=3 && time.length>=2){
                arr[i]=(Integer.parseInt(date[2])-2010)*400+Integer.parseInt(date[1])*31+Integer.parseInt(date[0]);
                arr[i]=arr[i]*1440+Integer.parseInt(time[0])*60+Integer.parseInt(time[1]);
                arr[i]+=(double)punkty[i].num/100;
            }
        }
        Arrays.sort(arr);
        for(int i=0; i<points;i++){
            int d =(int)Math.round((arr[i]*100)%100);
            punkt.add(i,punkty[d]);
        }
    }
        
    //wyszczególnianie danych z wzorca.        
    private static CalibrationPoint _divide(String line){
        StringTokenizer st = new StringTokenizer(line);
        String[] num = {st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken()};
        CalibrationPoint d =new CalibrationPoint(Rh);
        d.temp = num[3];
        d.hum = num[2];
        String[] time = num[1].split(":");
        d.time = time[0]+":"+time[1];
        return d;
    }
        
    //wprowadzanie danych z wzorca        
    private static void _putProbeData(){
            Pattern probe = Pattern.compile(SheetData.PROBE_SERIAL_NUMBER, Pattern.CASE_INSENSITIVE);
            //punkt pomiarowy
            int num=0;          
            //folder z plikami
            File path = new File(DisplayedText.probeDataPath);
            String[] list;
            //lista pilków z wynikiami wzorca + sortowanie po nazwie
            list=path.list(); //
            Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
            try{
                final Sheet sheet = SheetData.SPREAD_SHEET.
                		getSheet(SheetData.SHEET_NAME);
                for(; num<points; num++){
                    //data wzorcowania punktów
                    String[] date = punkt.get(num).date.split("\\.");
                    String findDate = date[2]+date[1]+date[0];
                    Pattern patternDate = Pattern.compile(findDate, Pattern.CASE_INSENSITIVE);
                    int fileNum=2;
                    boolean correctName=true;
                    //szukanie pliku z danymi - sprawdzanie data+ nr. sondy
                    do{
                        fileNum++;
                        if(fileNum==list.length) break;
                        Matcher matcherDate = patternDate.matcher(list[fileNum].toString());
                        Matcher matcherProbe = probe.matcher(list[fileNum].toString());
                        if(matcherDate.find() && matcherProbe.find()) correctName=false;
                    }while(correctName);
                    String line;
                    if(!correctName){
                    System.out.println("Otwieranie pliku: "+list[fileNum]);
                    BufferedReader in;
                    in = new BufferedReader(new FileReader(DisplayedText.probeDataPath+list[fileNum]));
                    //wyrzucenie pustych lini
                    for(int i=0; i<47; i++){
                       line = in.readLine();
                    }      
                    line = in.readLine();
                    while(line != null){
                        //podział danych na: godzinę, dzień, wskazania wzorca
                        CalibrationPoint d = new CalibrationPoint(Rh);
                        d = _divide(line);
                        //wprowadzanie danych z punktu pomiarowego 10 pomiarów dla punktu
                        if(d.time.equals(punkt.get(num).time)){
                            boolean two= false;
                            System.out.println("punkt pomiarowy "+(punkt.get(num).num+1)+" godzina: "+punkt.get(num).time);
                            if(num<punkt.size()-1){
                                two= punkt.get(num).time.equals(punkt.get(num+1).time);
                                }
                            int count=0;
                            do{
                                int col= SheetData.TIME_COLUMN+3*count;
                                int line1 = SheetData.START_ROW-SheetData.NUMBER_OF_PARAMETERS+
                                		SheetData.POINT_GAP*punkt.get(num).num;
                                String[] temp = d.temp.split(",");
                                String[] hum = d.hum.split(",");
                                sheet.setValueAt(temp[0], col, line1);
                                if(temp.length>1)
                                    sheet.setValueAt(temp[1], col+2, line1);
                                if(SheetData.RH){
                                    sheet.setValueAt(hum[0], col, line1+1);
                                    if(hum.length>1)
                                        sheet.setValueAt(hum[1], col+2, line1+1);
                                    }
                                if(two){
                                    line1 = SheetData.START_ROW-SheetData.NUMBER_OF_PARAMETERS+
                                    		SheetData.POINT_GAP*punkt.get(num+1).num;
                                    sheet.setValueAt(temp[0], col, line1);
                                    if(temp.length>1)
                                        sheet.setValueAt(temp[1], col+2, line1);
                                    if(SheetData.RH){
                                        sheet.setValueAt(hum[0], col, line1+1);
                                        if(hum.length>1)
                                            sheet.setValueAt(hum[1], col+2, line1+1);
                                        }
                                }
                                line=in.readLine();
                                if(line != null)
                                d= _divide(line);
 
                                count++;
                            }while(count<10);
                            if(two) {
                                num++;
                                System.out.println("punkt pomiarowy "+(punkt.get(num).num+1)+" godzina: "+punkt.get(num).time);
                            }
                            if(num+1<points){
                                if(punkt.get(num).date.equals(punkt.get(num+1).date)){
                                    num++;
                                    continue;}
                            }
                            break;
                        }
                        line = in.readLine();
                    }
                    in.close();
                    }else{
                        fileNum=2;
                        System.out.println("brak pasującego pliku z dnia: "+punkt.get(num).date);
                    }
                }
                //zapis pliku
                sheet.getSpreadSheet().saveAs(SheetData.FILE);
            }catch(IOException e){
                e.printStackTrace();
            }
        }    
 
    //szukanie dostępnych plików z wynikami z rejestratorów i dane o punktach pomiarowych        
    static private void _putLoggerData(){
        final Sheet sheet = SheetData.SPREAD_SHEET.getSheet(SheetData.SHEET_NAME);
		//ścieżka do danych z rejestratorów + ich rozszerzenie
		String[] path = {
		        "C:\\Users\\Franek\\Documents\\rejestratory\\Hobo\\",
		        "C:\\Users\\Franek\\Documents\\rejestratory\\DT-172\\",
		        "C:\\Users\\Franek\\Documents\\rejestratory\\testo\\",
		        "C:\\Users\\Franek\\Documents\\rejestratory\\RC\\",
		        "C:\\Users\\Franek\\Documents\\rejestratory\\Lascar\\",
		        "C:\\Users\\Franek\\Documents\\rejestratory\\EBI\\"
		        };
		String[] kon = {".txt",".csv",".csv",".txt",".txt",".csv"};
		//szukanie danych rejestratorów uwzględnionych w zapisce
		for(int i=0; i<SheetData.NUMBER_OF_DEVICES; i++){
		    String name = sheet.getValueAt(1,SheetData.START_ROW +
		    		SheetData.NUMBER_OF_PARAMETERS*i).toString();
		    for(int j=0; j<path.length; j++){
		        String filelocation = path[j] + name + kon[j];
		        File f = new File(filelocation);
		        if(f.exists()){
		            CalibrationPoint das;
		            switch(j){
		                case 0:{
		                    das = new Onset(Rh);
		                    break;
		                }
		                case 1:{
		                    das = new CEM(Rh);
		                    break;
		                }
		                case 2:{
		                    das = new Testo(Rh);
		                    break;
		                }
		                case 3:{
		                    das = new RC(Rh);
		                    break;
		                }
		                case 4:{
		                    das = new Lascar(Rh);
		                    break;
		                }
		                default: das = new EBI(Rh);
		            }
		            System.out.println("znaleziono plik z danymi dla: "+filelocation);
		            das.set(i,f);
		            loggers.add(das);
		        }
		    }
		}
    }
    
    public static ArrayList<CalibrationPoint> getPoints() throws IOException{
    //	SheetData.setChamberData(Rh);
    	SheetData.FilesSet(file);
        final Sheet sheet = SheetData.SPREAD_SHEET.getSheet(SheetData.SHEET_NAME);
        CalibrationPoint[] punkty = new CalibrationPoint[points];
        for (int i=0; i< points; i++){
            CalibrationPoint pr = new CalibrationPoint(Rh);
            pr.time = DataCalculation.parseTime(sheet.getValueAt(SheetData.TIME_COLUMN,6+SheetData.POINT_GAP*i).toString());
            pr.date = DataCalculation.parseDate(sheet.getValueAt(SheetData.DATE_COLUMN,6+SheetData.POINT_GAP*i).toString());
            pr.num=i;
            if(Rh){
                pr.temp=sheet.getValueAt(0,6+SheetData.POINT_GAP*i).toString();
                pr.hum=sheet.getValueAt(1,6+SheetData.POINT_GAP*i).toString();
            }else{
                pr.temp=sheet.getValueAt(1,6+SheetData.POINT_GAP*i).toString();
            }
            punkty[i]= pr;
        }
        _sort(punkty);
        return punkt;
    }
   
    public static void clean(){
        loggers.removeAll(loggers);
    }
   
    public static void set(boolean Rh_, File file_, int points_){
        Rh=Rh_;
        file=file_;
        points=points_;
    }
    
    public static void run(){
        try{
            //zebranie danych z arkusza
            _putLoggerData();
            _putProbeData();
            final Sheet sheet = SheetData.SPREAD_SHEET.getSheet(SheetData.SHEET_NAME);
            //dane z rejestratorów
            if(loggers.size()==0) System.out.println("brak danych z rejestratorów do wprowadzenia");
            else for(int i=0; i<loggers.size();i++){
                int g = loggers.get(i).number;
                System.out.println("wprowadzanie danych z pliku: "+ loggers.get(i).file);
                Scanner sc = new Scanner(loggers.get(i).file);
                //odrzucenie lini pliku bez danych liczbowych
                for(int j=0; j<loggers.get(i).getN(); j++){
                    sc.nextLine();
                }
                int j =0;
                
                while(sc.hasNextLine()){
                    //rozdział danych z lini
                    String dd = sc.nextLine();
                    if(dd.equals(""));
                        CalibrationPoint d= loggers.get(i).divide(dd);
                    //wprowadzanie danych dla punktu
                    if(d.time.equals(punkt.get(j).time) && d.date.equals(punkt.get(j).date)){
                        System.out.println("pobieranie danych dla punktu: "+ (punkt.get(j).num+1));
                        int count=0;
                        do{
                            int col= SheetData.TIME_COLUMN+3*count;
                            int line = SheetData.START_ROW+SheetData.NUMBER_OF_PARAMETERS*g+SheetData.POINT_GAP*punkt.get(j).num;
                            String[] temp = d.temp.split(",");
                            sheet.setValueAt(temp[0], col, line);
                            if(temp.length>1)
                                sheet.setValueAt(temp[1], col+2, line);
                            else
                                sheet.setValueAt("0", col+2, line);
                            if(SheetData.RH){
                                String[] hum = d.hum.split(",");
                                sheet.setValueAt(hum[0], col, line+1);
                                if(hum.length>1)
                                    sheet.setValueAt(hum[1], col+2, line+1);
                                else
                                    sheet.setValueAt("0", col+2, line+1);
                            }
                            //czy koniec pliku
                            if(sc.hasNextLine())
                            d= loggers.get(i).divide(sc.nextLine());
                            else{
                                System.out.println("koniec pliku dla: "+ d.time+" "+d.date);
                                break;
                            }
                            count++;
                        }while(count<10);
                        j++;
                        if(j==points) break;
                    }
                    if(!sc.hasNextLine()){
                        System.out.println("brak danych dla punktu: "+(punkt.get(j).num+1));
                        j++;
                        if(j==points) break;
                        sc= new Scanner(loggers.get(i).file);
                        for(int k=0; k<loggers.get(i).getN(); k++){
                            sc.nextLine();
                        }
                    }
                }
                sc.close();
                sheet.getSpreadSheet().saveAs(SheetData.FILE);
            }
        }catch(IOException e){
               e.printStackTrace();
        }
        System.out.print("koniec wprowadzania danych");
    }
    
    public static void main(String[] args){
        try {
            punkt= getPoints();
        }catch (IOException e) {
            e.printStackTrace();
        }
        run();
    }
    
}