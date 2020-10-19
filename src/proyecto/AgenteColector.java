package proyecto;
import java.util.ArrayList;

public class AgenteColector {
	
	private int AgenteID;
	private String fechahoraUTC;
	private ArrayList<Object> lecturas = new ArrayList<Object>();
	
	public AgenteColector(int agenteID, String fechahoraUTC, ArrayList<Object> lecturas) {
		super();
		AgenteID = agenteID;
		this.fechahoraUTC = fechahoraUTC;
		this.lecturas = lecturas;
	}
	public int getAgenteID() {
		return AgenteID;
	}
	public void setAgenteID(int agenteID) {
		AgenteID = agenteID;
	}
	public String getFechahoraUTC() {
		return fechahoraUTC;
	}
	public void setFechahoraUTC(String fechahoraUTC) {
		this.fechahoraUTC = fechahoraUTC;
	}
	public ArrayList<Object> getLecturas() {
		return lecturas;
	}
	public void setLecturas(ArrayList<Object> lecturas) {
		this.lecturas = lecturas;
	}
	
	
}
