package entreprise;

import com.opencsv.bean.CsvBindByName;

public class Route {
	@CsvBindByName(column = "origine")
	private int origine;
	@CsvBindByName(column = "destination")
	private int destination;
	
	public int getOrigine() {
		return origine;
	}
	public void setOrigine(int origine) {
		this.origine = origine;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
}