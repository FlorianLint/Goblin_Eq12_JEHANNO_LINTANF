package entreprise;

import java.io.File;
import java.sql.*;
import java.util.List;

public class Entreprise {
	private List<Client> clients;
	private List<Entrepot> entrepots;
	private List<Route> routes;
	private List<Site> sites;
	private String dossier;
	private int[][] distancesMin;

	public Entreprise() {//lecture de la BD pour initialiser les variables d'instances
		
	}
	public Entreprise(String dossier) {
		this.dossier = dossier;
		String nomFichier = "";
		try {
			File f = new File("Jeux_de_donnees"+File.separator+dossier);
			System.out.println(f.listFiles().length);
			for (File ff :f.listFiles()) {
				nomFichier = ff.getName();					
				if (ff.getAbsolutePath().contains("client")) {
					//	System.out.println(ff.getAbsolutePath()); // Optionel pour voir le chemin du fichier
					this.clients = CsvUtils.readCsv(ff.getAbsolutePath(), Client.class);
					System.out.println("client fini"); // optionnel
				}else if(ff.getAbsolutePath().contains("entrepot")) {
					this.entrepots = CsvUtils.readCsv(ff.getAbsolutePath(), Entrepot.class);
					System.out.println("entrepot fini");// optionnel
				}else if(ff.getAbsolutePath().contains("route")) {
					this.routes = CsvUtils.readCsv(ff.getAbsolutePath(), Route.class);
					System.out.println("route fini");// optionnel
				}else if(ff.getAbsolutePath().contains("site")) {
					this.sites = CsvUtils.readCsv(ff.getAbsolutePath(), Site.class);
					System.out.println("site fini");// optionnel
				}
			}
		} catch (Exception e) {
			System.out.println("nom fichier"+ nomFichier);
			System.err.println(e);
		}
		//test affichage list
		for (Client c : clients) {
			System.out.println(c.getId_site());
		}
		this.distancesMin = Floyd();
	}
	public String getDossier() {
		return dossier;
	}

//	public static void main(String[] args) throws Exception {
//		Entreprise e = new Entreprise("petit");
//		System.out.println("taille e :" +e.sites.size());
//		e.BaseDonnees();
//	}

	public Site findSiteFromId(int id_site, List<Site> sites) {
		for (int i = 0; i < sites.size(); i++) {
			if (sites.get(i).getId_site() == id_site) {
				return sites.get(i);
			}
		}
		return null;
	}

	public int distance(Site site1, Site site2) {
		double dist = Math.sqrt(Math.pow(site1.getX() - site2.getX(), 2) + Math.pow(site1.getY() - site2.getY(), 2));
		return (int) Math.ceil(dist);
	}

	public void BaseDonnees() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");

		String url = "jdbc:hsqldb:file:database"+File.separator+"basic;shutdown=true";
		String login = "sa";
		String password = "";
		try (Connection connection=DriverManager.getConnection(url, login, password )){

			String requeteSite = "DROP TABLE site IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteSite );
			}
			requeteSite = "CREATE TABLE site ("
					+"id_site int,"
					+"x int,"
					+"y int,"
					+"PRIMARY KEY(id_site))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteSite );
			}
			StringBuffer sSite = new StringBuffer ("INSERT INTO site (id_site, x, y) VALUES");
			for (int i = 0; i<this.sites.size(); i++) {
				Site site = this.sites.get(i);
				sSite.append("("+ site.getId_site() +",");
				sSite.append(site.getX() +",");
				sSite.append(site.getY() +")");
			}

			String requeteClient = "DROP TABLE client IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteClient );
			}
			requeteClient = "CREATE TABLE client ("
					+"id_site int,"
					+"mail VARCHAR(100),"
					+"nom VARCHAR(100),"
					+"PRIMARY KEY(mail))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteClient );
			}
			StringBuffer sClient = new StringBuffer ("INSERT INTO client (id_site, mail, nom) VALUES");
			for (int i = 0; i<this.clients.size(); i++) {
				Client client = this.clients.get(i);
				sClient.append("("+ client.getId_site() +",");
				sClient.append(client.getMail() +",");
				sClient.append(client.getNom() +")");
			}

			String requeteEntrepot = "DROP TABLE entrepot IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteEntrepot );
			}
			requeteClient = "CREATE TABLE entrepot ("
					+"id_entrepot int,"
					+"id_site int,"
					+"cout_fixe int,"
					+"stock int,"
					+"PRIMARY KEY(id_entrepot))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteEntrepot );
			}
			StringBuffer sEntrepot = new StringBuffer ("INSERT INTO entrepot (id_entrepot, id_site, cout_fixe, stock) VALUES");
			for (int i = 0; i<this.entrepots.size(); i++) {
				Entrepot entrepot = this.entrepots.get(i);
				sEntrepot.append("("+ entrepot.getId_entrepot() +",");
				sEntrepot.append(entrepot.getId_site() +",");
				sEntrepot.append(entrepot.getCout_fixe() +",");
				sEntrepot.append(entrepot.getStock() +")");
			}

			String requeteRoute = "DROP TABLE route IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteRoute );
			}
			requeteRoute = "CREATE TABLE route ("
					+"origine int,"
					+"destination int,"
					+"PRIMARY KEY(origine, destination))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteRoute );
			}
			StringBuffer sRoute = new StringBuffer ("INSERT INTO route (origine, destination) VALUES");
			for (int i = 0; i<this.routes.size(); i++) {
				Route route = this.routes.get(i);
				sRoute.append("("+ route.getOrigine() +",");
				sRoute.append(route.getDestination() +")");
			}

			try ( Statement statement = connection.createStatement() ) {
				try ( ResultSet resultSet = statement.executeQuery( requeteRoute ) ) {
					while( resultSet.next() ) {
						int origine = resultSet.getInt("origine");
						int destination = resultSet.getInt("destination");

						System.out.println(String.format("Origine: %-5d | Destination: %-5d ", 
								origine, destination));
					}
				}
			}
			String requeteFloydW = "DROP TABLE FloydW IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteFloydW );
			}
			requeteFloydW = "CREATE TABLE FloydW ("
					+"origine int,"
					+"destination int,"
					+"distance int,"
					+"PRIMARY KEY(rigine, destination))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteFloydW );
				
			StringBuffer sFloydW = new StringBuffer ("INSERT INTO FloydW (origine, destination, distance) VALUES");
			for (int i = 0; i<this.distancesMin.length; i++) {
				for (int j = 0; j<this.distancesMin.length; j++) {
				sFloydW.append("("+ i + "," + j + ","  + distancesMin[i][j] +")");
			}
			}
			}
		}
	}

	public int[][] Floyd() {
		final int INF = Integer.MAX_VALUE / 2;
		int n = sites.size();
		int[][] M = new int[n][n];
		//initialisation
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					M[i][j] = 0;
				} else {
					M[i][j] = INF;
				}
			}
		}
		//Remplir la matrice
		for (Route routes : routes) {
			int origine = routes.getOrigine();
			int destination = routes.getDestination();

			int distance = distance(findSiteFromId(origine, sites), findSiteFromId(destination, sites));
			M[origine][destination] = distance;
			M[destination][origine] = distance;
		}
		//Déroulement de l'algorithme
		int k = 0;
		for (k=0; k<n; k++); {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (M[i][k] + M[k][j] < M[i][j]) {
						M[i][j] = M[i][k] + M[k][j];
					}
				}
			}
		}
		return M; 
	}
}