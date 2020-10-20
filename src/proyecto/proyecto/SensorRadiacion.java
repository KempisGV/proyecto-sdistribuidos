package proyecto;

public class SensorRadiacion {

	private int sensorID;
	private float lectura;
	
	public SensorRadiacion(int sensorID, float lectura) {
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

	public float getLectura() {
		return lectura;
	}

	public void setLectura(float lectura) {
		this.lectura = lectura;
	}
	
	
	
}
