package database;

import com.opencsv.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Entreprise{
	private List<Client> client;
	private List<Entrepots> entrepot;
	private List<Routes> route;
	private List<Sites> site;

	public Entreprise() {
		this.client = new ArrayList<Client>();
		this.entrepot = new ArrayList<Entrepots>();
		this.route = new ArrayList<Routes>();
		this.site = new ArrayList<Sites>();
	}

	public List<Client> getClient() {
		return client;
	}
	public List<Entrepots> getEntrepot() {
		return entrepot;
	}
	public List<Routes> getRoute() {
		return route;
	}
	public List<Sites> getSite() {
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