package entreprise;

import com.opencsv.bean.CsvBindByName;

public class Route {
	@CsvBindByName(column = "origine")
	private Site origine;
	@CsvBindByName(column = "destination")
	private Site destination;
	private double distance;
	
	public double distance(Site site1, Site site2) {
		return Math.sqrt(Math.pow(site1.getX()-site2.getX(),2)+Math.pow(site1.getY()-site2.getY(),2));
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

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