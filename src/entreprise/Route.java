package entreprise;

import com.opencsv.bean.CsvBindByName;

public class Route {
	@CsvBindByName(column = "origine")
	private int origine;
	@CsvBindByName(column = "destination")
	private int destination;
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