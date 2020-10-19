package proyecto;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.Socket;


import java.util.Date;

public class Verificador {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException, Exception, IOException {
	
	//Variable usada para diferenciar entre la primera lectura del mensaje al ejecutarse y las futuras lecturas
	int tem = 1;
	//Variable utilizada para darle un buen formato al nombre de los archivos json
	int contador = 0;
	
	
	
	while(true){
		
		//Codigo que crea archivo txt llamado contador en el que se lleva un historial de las veces que el programa se ha
		//ejecutado para asi poderle dar un nombre correcto a cada archivo json
		BufferedWriter out = null;
		try {

		    // Lee el contenido del txt y el numero
		    BufferedReader br = new BufferedReader(new FileReader("contador.txt"));
		    String storedScore="0";
		    int storedScoreNumber = 0;
		    while ((storedScore = br.readLine()) != null) {
		        storedScoreNumber=(Integer.parseInt(storedScore==null?"0":storedScore));
		    }
		    contador = storedScoreNumber;

		    // Escribe contenido del txt y aumenta el numero
		    out = new BufferedWriter(new FileWriter("contador.txt", false));
		    out.write(String.valueOf(storedScoreNumber+1));

		} catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if (out != null) {
		            try {
		                out.close();
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
		        }
		    }
		//Fin Contador
		
		//Variable que guarda timeStamp
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
		
		
		Random r = new Random();
		
		//Instancia de sensores Temperatura, Humedad, Radiacion con valores random en sus lecturas
		SensorTemperatura temperatura = new SensorTemperatura(1, r.nextInt(30)+1);
		SensorHumedad humedad = new SensorHumedad(2, r.nextFloat() * (100));
		SensorRadiacion radiacion = new SensorRadiacion(3, r.nextFloat() * (100));
		
		//Uso de variable tem para diferencias primera lectura de las futuras
		if(tem == 1) {
			System.out.println("Realizando lecturas...\n");
		}else {
			System.out.println("Realizando nuevas lecturas...\n");
		}
		
		//Creación de arraylist de lecturas en las que se agregan los valores obtenidos por cada sensor
		ArrayList<Object> lecturas = new ArrayList<Object>();
		
		lecturas.add(temperatura);
		lecturas.add(humedad);
		lecturas.add(radiacion);
		
		//Instancia de AgenteColector
		AgenteColector agente1 = new AgenteColector(1, timeStamp, lecturas);
			

		//Creación de objetos JSON y JSONArray e insersion de datos recolectados por el agente colector
		JSONObject obj = new JSONObject();
		
	
		JSONArray list= new JSONArray();
		JSONObject list1= new JSONObject();
		list1.put("sensorID",((SensorTemperatura) agente1.getLecturas().get(0)).getSensorID());
		list1.put("lectura", ((SensorTemperatura) agente1.getLecturas().get(0)).getLectura());
		
		JSONObject list2= new JSONObject();
		list2.put("sensorID",((SensorHumedad) agente1.getLecturas().get(1)).getSensorID());
		list2.put("lectura", ((SensorHumedad) agente1.getLecturas().get(1)).getLectura());
		
		JSONObject list3= new JSONObject();
		list3.put("sensorID",((SensorRadiacion) agente1.getLecturas().get(2)).getSensorID());
		list3.put("lectura", ((SensorRadiacion) agente1.getLecturas().get(2)).getLectura());
		
		list.add(list1);
		list.add(list2);
		list.add(list3);
		
		obj.put("lecturas", list);
		obj.put("fechahoraUTC", agente1.getFechahoraUTC());
		obj.put("agenteID", agente1.getAgenteID());
		//fin de insersion de datos a objeto JSON
		
		
		//Codigo en el que se escribe en el archivo llamado reporte+contador+.json los datos almacenados en el objeto JSON
		try(FileWriter file = new FileWriter("reporte"+contador+".json"))
		{
			file.write(obj.toString());
			file.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
		//Lee la fecha y hora del archivo json generado por agente colector
		JSONParser parser = new JSONParser();

        try {
            Object obj1 = parser.parse(new FileReader("reporte"+contador+".json"));
            JSONObject jsonObject = (JSONObject) obj1;
            String fechahoraUTC =(String) jsonObject.get("fechahoraUTC");

            
       //Lee el hitorial_archivos.json y guarda la fecha del reporte actual
            Object obj2 = parser.parse(new FileReader("historial_archivos.json"));
            JSONObject json = (JSONObject) obj2;
            

       //Validación de datos a enviar al reportero, consultando en el historial si el timestamp está duplicado     
            if(json.get(fechahoraUTC) == null) {
            	
            	json.put(fechahoraUTC, fechahoraUTC);
            	
            	try(FileWriter file = new FileWriter("historial_archivos.json",false))
                {
                    file.write(json.toJSONString());
                    file.flush();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            	
            	//Conexion del verificador al reportero mediante el puerto 9999 y la ip 10.10.10.1(ip del reportero)
            	try {
	        		Socket socket = null;
	                String host = "10.10.10.1";
	
	                socket = new Socket(host, 9999);
	                
	                //Se realiza el marshalling creando un byte array en base a la informacion obtenida por el
	                //archivo json una vez realizado el mashalling se envia al reportero el byte array
	                File file = new File("reporte"+contador+".json");
	                
	                byte[] bytes = new byte[16 * 1024];
	                InputStream in = new FileInputStream(file);
	                OutputStream outt = socket.getOutputStream();
	
	                int count;
	                while ((count = in.read(bytes)) > 0) {
	                    outt.write(bytes, 0, count);
	                }
	
	                outt.close();
	                in.close();
	                socket.close();
            	 
	                //Se imprime por consola AgenteID y FechaHoraUTC una vez se enviaron los datos al reportero
	                TimeUnit.SECONDS.sleep(10);
	                System.out.println("AgenteID: " + agente1.getAgenteID());
	        		System.out.println("FechaHoraUTC: " + agente1.getFechahoraUTC());
	        		System.out.println();
	 
            	}
            	//Handeling de errores en caso de que el reportero no se esté ejecuntando y no se realice la conexión
        		catch(Exception e){
            		System.out.println("Error al conectarse con el reportero");
            		TimeUnit.SECONDS.sleep(5);
            	}
        		
            } 
            //en caso de que la fecha coincida con una fecha del historial, no se realizara la conexion al reportero y se
            //continuara realizando lecturas
            else {
            	TimeUnit.SECONDS.sleep(5);
            	System.out.println("Datos duplicados, no se conectará con el reportero");
            	
            }
            
        }
        catch(FileNotFoundException e) { e.printStackTrace();}
        catch(IOException e) { e.printStackTrace();}
        catch(ParseException e) { e.printStackTrace();}
        catch(Exception e) { e.printStackTrace();}
        		
	
	}
	
	}
	

}
