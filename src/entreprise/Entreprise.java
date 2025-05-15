package entreprise;

import java.io.File;
import java.sql.*;
import java.util.List;

public class Entreprise {
	List<Client> clients;
	List<Entrepot> entrepots;
	List<Route> routes;
	List<Site> sites ;
	public Entreprise(String dossier) {
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
			System.out.println("non fichier"+ nomFichier);
			System.err.println(e);
		}
		//test affichage list
						for (Client c : clients) {
							System.out.println(c.getId_site());
						}
	}
	public static void main(String[] args) throws Exception {
		Entreprise e = new Entreprise("petit");
		System.out.println("taille e :" +e.sites.size());
		e.BaseDonnees();
	}

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

			String requeteRoute = "DROP TABLE route IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteRoute );
			}
			requeteRoute = "CREATE TABLE route ("
					+"origine int,"
					+"destination int,"
					+"distance int,"
					+"PRIMARY KEY(origine))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate( requeteRoute );
			}
			StringBuffer sRoute = new StringBuffer ("INSERT INTO route (origine, destination, distance) VALUES");
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
						double distance = resultSet.getDouble("distance");

						System.out.println(String.format("Origine: %-5d | Destination: %-5d | Distance: %.2f", 
								origine, destination, distance));
					}
				}
			}
		}
	}


	//	public static int[][] Floyd(int n, int[][] cout, Set<Route> r) {
	//		final int INF = Integer.MAX_VALUE / 2;
	//		int[][] M = new int[n][n];
	//
	//		//initialisation
	//		for (int i = 0; i < n; i++) {
	//			for (int j = 0; j < n; j++) {
	//				if (i == j) {
	//					M[i][j] = 0;
	//				} else {
	//					M[i][j] = INF;
	//				}
	//			}
	//		}
	//		//Remplir la matrice
	//
	//	}
}