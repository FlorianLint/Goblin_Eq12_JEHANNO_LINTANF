package entreprise;

public class Routes {
	private Sites origine;
	private Sites destination;
	
	public Routes(Sites origine, Sites destination) {
		this.origine = origine;
		this.destination = destination;
	}

	public Sites getOrigine() {
		return origine;
	}

	public void setOrigine(Sites origine) {
		this.origine = origine;
	}

	public Sites getDestination() {
		return destination;
	}

	public void setDestination(Sites destination) {
		this.destination = destination;
	}
}