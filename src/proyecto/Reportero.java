package proyecto;

//Librerias utilizadas
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Reportero {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException{

		//Se utiliza un while para asegurar que se ejecute el programa permanentemente
		while(true) {

		//Se crea un socket de clase ServerSocket
		ServerSocket serverSocket = null;

		//Se utiliza un try, catch para evitar que se detenga la ejecucion de todo el programa en caso de tener un problema
		//Se le asigna el puerto 9999 al serverSocket previamente declarado
        try {
            serverSocket = new ServerSocket(9999);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
        }

        Socket socket = null;
        InputStream in = null;
        OutputStream outt = null;

        try {
            socket = serverSocket.accept();
        } catch (IOException ex) {
            System.out.println("Can't accept client connection. ");
        }
        //la funcion getInputStream te permite obtener la informacion del archivo recibido
        try {
            in = socket.getInputStream();
        } catch (IOException ex) {
            System.out.println("Can't get socket input stream. ");
        }
        //FileOutputStream prepara variable outt en la que se realizara el unmarshalling del byte array enviado por el verificador
        try {
            outt = new FileOutputStream("reporteSensor.json");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. ");
        }

        byte[] bytes = new byte[16*1024];

        int count;
        //la condicion while ejecuta el unmarshalling y permite que mientras exista informacion en el archivo recibido este se va agregando en el reporteSensor.json
        while ((count = in.read(bytes)) > 0) {
            outt.write(bytes, 0, count);
        }

        outt.close();
        in.close();
        socket.close();
        serverSocket.close();
        
        
      
        //Definicion de parser para conversion de informacion leida a objeto JSON
        JSONParser parser = new JSONParser();

        try {
        	//Instancia de JSONArray en los que se guardara la informacion existente de cada reporte 
    		JSONArray lecturaHistorialTemp= new JSONArray();
    		JSONArray lecturaHistorialHum= new JSONArray();
    		JSONArray lecturaHistorialRad= new JSONArray();
    		
    		//Lectura de reporteSensor.json y obtención de lecturas de cada sensor
        	Object reporteFile = parser.parse(new FileReader("reporteSensor.json"));
            JSONObject reporteJson = (JSONObject) reporteFile;
            JSONArray lecturas=  (JSONArray) reporteJson.get("lecturas");
            JSONObject temperatura = (JSONObject) lecturas.get(0);
            JSONObject humedad = (JSONObject) lecturas.get(1);
            JSONObject radiacion = (JSONObject) lecturas.get(2);

            //Obtencion de agenteID y fechahoraUTC de reporteJson
            Long agenteID =(Long) reporteJson.get("agenteID");
            String fechahoraUTC =(String) reporteJson.get("fechahoraUTC");

            //Obtencion de lecturas de sensor temperatura
            Object reporteTempFile = parser.parse(new FileReader("reporttemp.json"));
            JSONObject reporteTempJson = (JSONObject) reporteTempFile;
            JSONObject infoTemp= new JSONObject();

            infoTemp.put("lectura",  temperatura.get("lectura"));
            infoTemp.put("fechahoraUTC", fechahoraUTC); 

            //el arreglo tempAnteriores almacena lecturas anteriores
            JSONArray tempAnteriores = (JSONArray) reporteTempJson.get("lecturas");
            
            //mientras existan lecturas anteriores en el arreglo se concatenaran estas con las lecturas nuevas obtenidas
            if(tempAnteriores != null) {
            	for(int i=0;i<tempAnteriores.size();i++){
                    lecturaHistorialTemp.add(tempAnteriores.get(i));
                 }
            }
            
            
            lecturaHistorialTemp.add(infoTemp);
            
            //Declaracion  de variables de calculo de promedio, max, min
            long maxTemp = 0;
            long minTemp = 101;
            long avgTemp;
            long sumTemp = 0;
            long contadorTemp =0;
            
            //for para obtener lecturas maximas, minimas y promedio
            for(int i = 0; i<lecturaHistorialTemp.size();i++) {
            	contadorTemp++;
            JSONObject temp = (JSONObject) lecturaHistorialTemp.get(i);            
            sumTemp = sumTemp + (Long) temp.get("lectura");
            if(((Long) temp.get("lectura")) > maxTemp) {
                maxTemp = (Long) temp.get("lectura");
            	}
            if(((Long) temp.get("lectura")) < minTemp) {
                minTemp = (Long) temp.get("lectura");
            }
           }
            avgTemp = sumTemp/contadorTemp;
            
            //insercion de datos de objetos y array en reporte json de sensor temperatura
            reporteTempJson.put("sensorID", temperatura.get("sensorID"));
            reporteTempJson.put("lecturas", lecturaHistorialTemp);
            reporteTempJson.put("agenteID", agenteID );
            reporteTempJson.put("lectura_max", maxTemp);
            reporteTempJson.put("lectura_min", minTemp);
            reporteTempJson.put("lectura_med", avgTemp);
            
            
            
            
            //Obtencion de lecturas de sensor humedad
            Object reporteHumFile = parser.parse(new FileReader("reporthum.json"));
            JSONObject reporteHumJson = (JSONObject) reporteHumFile;
            JSONObject infoHum= new JSONObject();

            infoHum.put("lectura",  humedad.get("lectura"));
            infoHum.put("fechahoraUTC", fechahoraUTC); 

            //el arreglo humAnteriores almacena lecturas anteriores
            JSONArray humAnteriores = (JSONArray) reporteHumJson.get("lecturas");
            
            //mientras existan lecturas anteriores en el arreglo se concatenaran estas con las lecturas nuevas obtenidas
            if(humAnteriores != null) {
            	for(int i=0;i<humAnteriores.size();i++){
            		lecturaHistorialHum.add(humAnteriores.get(i));
                 }
            }
            
            lecturaHistorialHum.add(infoHum);
            
            //Declaracion  de variables de calculo de promedio, max, min
            double maxHum = 0;
            double minHum = 101;
            double avgHum;
            double sumHum = 0;
            double contadorHum =0;
            
          //for utilizado para obtener lecturas maximas, minimas y promedio
            for(int i = 0; i<lecturaHistorialHum.size();i++) {
            	contadorHum++;
            JSONObject hum = (JSONObject) lecturaHistorialHum.get(i);            
            sumHum = sumHum + (Double) hum.get("lectura");
            if(((Double) hum.get("lectura")) > maxHum) {
            	maxHum = (Double) hum.get("lectura");
            	}
            if(((Double) hum.get("lectura")) < minHum) {
                minHum = (Double) hum.get("lectura");
            }
           }
            avgHum = sumHum/contadorHum;
            
           // insercion de datos de objetos y array en reporte json de sensor humedad
            reporteHumJson.put("sensorID", humedad.get("sensorID"));
            reporteHumJson.put("lecturas", lecturaHistorialHum);
            reporteHumJson.put("agenteID",agenteID );
            reporteHumJson.put("lectura_max", maxHum);
            reporteHumJson.put("lectura_min", minHum);
            reporteHumJson.put("lectura_med", avgHum);
            
            //Obtencion de lecturas de sensor radiacion
            Object reporteRadFile = parser.parse(new FileReader("reportrad.json"));
            JSONObject reporteRadJson = (JSONObject) reporteRadFile;
            JSONObject infoRad= new JSONObject();

            
            infoRad.put("lectura",  radiacion.get("lectura"));
            infoRad.put("fechahoraUTC", fechahoraUTC); 

          //el arreglo radAnteriores almacena lecturas anteriores
            JSONArray radAnteriores = (JSONArray) reporteRadJson.get("lecturas");
            
            //mientras existan lecturas anteriores en el arreglo se concatenaran estas con las lecturas nuevas obtenidas
            if(radAnteriores != null) {
            	for(int i=0;i<radAnteriores.size();i++){
            		lecturaHistorialRad.add(radAnteriores.get(i));
                 }
            }
            
            lecturaHistorialRad.add(infoRad);

            //Declaracion  de variables de calculo de promedio, max, min
            double maxRad = 0;
            double minRad = 101;
            double avgRad;
            double sumRad = 0;
            double contadorRad =0;
            
          //for utilizado para obtener lecturas maximas, minimas y promedio
            for(int i = 0; i<lecturaHistorialRad.size();i++) {
            	contadorRad++;
            JSONObject rad = (JSONObject) lecturaHistorialRad.get(i);            
            sumRad = sumRad + (Double) rad.get("lectura");
            if(((Double) rad.get("lectura")) > maxRad) {
            	maxRad = (Double) rad.get("lectura");
            	}
            if(((Double) rad.get("lectura")) < minRad) {
                minRad = (Double) rad.get("lectura");
            }
           }
            avgRad = sumRad/contadorRad;
            
            //insercion de datos de objetos y array en reporte json  radiacion solar
            reporteRadJson.put("sensorID", radiacion.get("sensorID"));
            reporteRadJson.put("lecturas", lecturaHistorialRad);
            reporteRadJson.put("agenteID",agenteID );
            reporteRadJson.put("lectura_max", maxRad);
            reporteRadJson.put("lectura_min", minRad);
            reporteRadJson.put("lectura_med", avgRad);
            
            
            
       //Se anade toda la informacion ingresada en reporteTempJson (el reporte de sensor temperatura)
       try(FileWriter file = new FileWriter("reporttemp.json",false))
       {
           file.write(reporteTempJson.toJSONString());
           file.flush();
            }
       catch(IOException e) {
           e.printStackTrace();
            }

    
        //Se anade toda la informacion ingresada en reporteHumJson (el reporte de sensor humedad )
        try(FileWriter file = new FileWriter("reporthum.json",false))
        {
            file.write(reporteHumJson.toJSONString());
            file.flush();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
          //Se anade toda la informacion ingresada en reporteRadJson (el reporte de sensor radiacion solar)
        try(FileWriter file = new FileWriter("reportrad.json",false))
        {
            file.write(reporteRadJson.toJSONString());
            file.flush();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    catch(FileNotFoundException e) { e.printStackTrace();}
    catch(IOException e) { e.printStackTrace();}
    catch(ParseException e) { e.printStackTrace();}
    catch(Exception e) { e.printStackTrace();}
		}
	}
}
	

