package entreprise;

import com.opencsv.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Entreprise{
	private List<Client> client;
	private List<Entrepot> entrepot;
	private List<Route> route;
	private List<Site> site;

	public Entreprise() {
		this.client = new ArrayList<Client>();
		this.entrepot = new ArrayList<Entrepot>();
		this.route = new ArrayList<Route>();
		this.site = new ArrayList<Site>();
	}

	public List<Client> getClient() {
		return client;
	}
	public List<Entrepot> getEntrepot() {
		return entrepot;
	}
	public List<Route> getRoute() {
		return route;
	}
	public List<Site> getSite() {
		return site;
	}

	public void chargerClients(){
		try {
			CSVReader reader = new CSVReaderBuilder(new FileReader("Jeux_de_donnees/petit/init-clients-30-10-Carre.csv")) // Lire le CSV
					.withCSVParser(new CSVParserBuilder().withSeparator(';').build()) // Prendre ";" comme séparateur dans le CSV
					.build();
			String[] line; // Variable pour stocker chaque ligne lue du CSV
			reader.readNext(); // Pour ne par lire la première ligne
			while ((line = reader.readNext()) != null) {


			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}