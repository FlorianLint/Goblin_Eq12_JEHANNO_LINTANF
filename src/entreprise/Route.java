package entreprise;

import com.opencsv.bean.CsvBindByName;

public class Route {
	@CsvBindByName(column = "origine")
	private Site origine;
	@CsvBindByName(column = "destination")
	private Site destination;
	
	public Site getOrigine() {
		return origine;
	}
	public void setOrigine(Site origine) {
		this.origine = origine;
	}
	public Site getDestination() {
		return destination;
	}
	public void setDestination(Site destination) {
		this.destination = destination;
	}
}