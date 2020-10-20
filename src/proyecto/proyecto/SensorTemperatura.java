package proyecto;

public class SensorTemperatura {
	
	private int sensorID;
	private int lectura;
	
	public SensorTemperatura(int sensorID, int lectura) {
		super();
		this.sensorID = sensorID;
		this.lectura = lectura;
	}

	public int getSensorID() {
		return sensorID;
	}

	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}

	public int getLectura() {
		return lectura;
	}

	public void setLectura(int lectura) {
		this.lectura = lectura;
	}
	
	
	
}
