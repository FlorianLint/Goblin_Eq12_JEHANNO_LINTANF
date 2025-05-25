package entreprise;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Entreprise {
	private List<Client> clients;
	private List<Entrepot> entrepots;
	private List<Route> routes;
	private List<Site> sites;
	private String dossier;
	private int[][] distancesMin;

	public Entreprise() {
		this.clients = new ArrayList<>();
		this.entrepots = new ArrayList<>();
		this.routes = new ArrayList<>();
		this.sites = new ArrayList<>();

		try {
			Class.forName("org.hsqldb.jdbcDriver");
			String url = "jdbc:hsqldb:file:database" + File.separator + "basic;shutdown=true";
			String login = "sa";
			String password = "";
			try (Connection conn = DriverManager.getConnection(url, login, password)) {

				// Lire le jeux de données
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM jeuxDeDonnees");
				if(rs.next()) {
					dossier = rs.getString("taille");
				}
				rs.close();

				// Lire les sites
				rs = st.executeQuery("SELECT * FROM site");
				while (rs.next()) {
					Site s = new Site(rs.getInt("id_site"), rs.getInt("x"), rs.getInt("y"));
					this.sites.add(s);
				}
				rs.close();

				// Lire les clients
				rs = st.executeQuery("SELECT * FROM client");
				while (rs.next()) {
					Client c = new Client(rs.getInt("id_site"), rs.getString("mail"), rs.getString("nom"));
					this.clients.add(c);
				}
				rs.close();

				// Lire les entrepôts
				rs = st.executeQuery("SELECT * FROM entrepot");
				while (rs.next()) {
					Entrepot e = new Entrepot(
							rs.getInt("id_entrepot"),
							rs.getInt("id_site"),
							rs.getInt("cout_fixe"),
							rs.getInt("stock")
							);
					this.entrepots.add(e);
				}
				rs.close();

				// Lire les routes
				rs = st.executeQuery("SELECT * FROM route");
				while (rs.next()) {
					Route r = new Route(rs.getInt("origine"), rs.getInt("destination"));
					this.routes.add(r);
				}
				rs.close();
				
				// lire les distancesMin
				int n = sites.size();
				this.distancesMin = new int[n][n];
				final int INF = Integer.MAX_VALUE / 2;
				for (int i = 0; i < n; i++) {
				    Arrays.fill(this.distancesMin[i], INF);
				    this.distancesMin[i][i] = 0;
				}
				rs = st.executeQuery("SELECT * FROM FloydW");
				while (rs.next()) {
				    int origine = rs.getInt("origine");
				    int destination = rs.getInt("destination");
				    int distance = rs.getInt("distance");
				    int i = indexFromId(origine);
				    int j = indexFromId(destination);
				    this.distancesMin[i][j] = distance;
				}
				rs.close();
				st.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Entreprise(String dossier) {
		this.dossier = dossier;
		String nomFichier = "";
		try {
			File f = new File("Jeux_de_donnees"+File.separator+dossier);
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
		//		for (Route r : routes) {
		//			System.out.println(r.getDestination());
		//		}
		this.distancesMin = Floyd();
	}

	public void BaseDonnees() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");

		String url = "jdbc:hsqldb:file:database"+File.separator+"basic;shutdown=true";
		String login = "sa";
		String password = "";
		try (Connection connection=DriverManager.getConnection(url, login, password )){

			//Création et remplissage table site
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
				if (i != this.sites.size()-1) { 
					sSite.append(",");
				}
			}
			String requeteSiteInsert = "";
			requeteSiteInsert = sSite.toString();
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteSiteInsert);
			}

			//Création et remplissage table client
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
				sClient.append("'"+ client.getMail() +"',");
				sClient.append("'"+ client.getNom() +"')");
				if (i != this.clients.size()-1) { 
					sClient.append(",");
				}
			}
			String requeteClientInsert = "";
			requeteClientInsert = sClient.toString();
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteClientInsert);
			}

			//Création et remplissage table entrepot
			String requeteEntrepot = "DROP TABLE entrepot IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteEntrepot);
			}
			requeteEntrepot = "CREATE TABLE entrepot ("
					+"id_entrepot int,"
					+"id_site int,"
					+"cout_fixe int,"
					+"stock int,"
					+"PRIMARY KEY(id_entrepot))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteEntrepot);
			}
			StringBuffer sEntrepot = new StringBuffer ("INSERT INTO entrepot (id_entrepot, id_site, cout_fixe, stock) VALUES");
			for (int i = 0; i<this.entrepots.size(); i++) {
				Entrepot entrepot = this.entrepots.get(i);
				sEntrepot.append("("+ entrepot.getId_entrepot() +",");
				sEntrepot.append(entrepot.getId_site() +",");
				sEntrepot.append(entrepot.getCout_fixe() +",");
				sEntrepot.append(entrepot.getStock() +")");
				if (i != this.entrepots.size()-1) { 
					sEntrepot.append(",");
				}
			}
			String requeteEntrepotInsert = "";
			requeteEntrepotInsert = sEntrepot.toString();
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteEntrepotInsert);
			}

			//Création et remplissage table route
			String requeteRoute = "DROP TABLE route IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteRoute);
			}
			requeteRoute = "CREATE TABLE route ("
					+"origine int,"
					+"destination int,"
					+"PRIMARY KEY(origine, destination))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteRoute);
			}
			StringBuffer sRoute = new StringBuffer ("INSERT INTO route (origine, destination) VALUES");
			for (int i = 0; i<this.routes.size(); i++) {
				Route route = this.routes.get(i);
				sRoute.append("("+ route.getOrigine() +",");
				sRoute.append(route.getDestination() +")");
				if (i != this.routes.size()-1) { 
					sRoute.append(",");
				}
			}
			String requeteRouteInsert = "";
			requeteRouteInsert = sRoute.toString();
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteRouteInsert);
			}

			//Creation et remplissage de la table FloydW
			String requeteFloydW = "DROP TABLE FloydW IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteFloydW);
			}
			requeteFloydW = "CREATE TABLE FloydW ("
					+"origine int,"
					+"destination int,"
					+"distance int,"
					+"PRIMARY KEY(origine, destination))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteFloydW);
			}
				StringBuffer sFloydW = new StringBuffer ("INSERT INTO FloydW (origine, destination, distance) VALUES");
				for (int i = 0; i<this.distancesMin.length; i++) {
					for (int j = 0; j<this.distancesMin.length; j++) {
						sFloydW.append("("+ i + "," + j + ","  + distancesMin[i][j] +")");
						if (!(i == this.distancesMin.length-1 && j == this.distancesMin.length-1)) { 
							sFloydW.append(",");
						}
					}
				}
			String requeteFloydWInsert = sFloydW.toString();
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(requeteFloydWInsert);
			}

			//Creation et remplissage de la table jeuxDeDonnees
			String jeuxDeDonnees = "DROP TABLE jeuxDeDonnees IF EXISTS;";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(jeuxDeDonnees);
			}

			jeuxDeDonnees = "CREATE TABLE jeuxDeDonnees ("
					+"taille VARCHAR(100),"
					+"PRIMARY KEY(taille))";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(jeuxDeDonnees);
			}

			String insertJeuxDeDonnees = "INSERT INTO jeuxDeDonnees (taille) VALUES"
					+"('"+ dossier +"')";
			try ( Statement statement = connection.createStatement() ) {
				statement.executeUpdate(insertJeuxDeDonnees);
			}

			//Pour afficher le contenu de la table route
			try ( Statement statement = connection.createStatement() ) {
				try ( ResultSet resultSet = statement.executeQuery( "SELECT * FROM route" ) ) {
					System.out.println("route : ");
					while( resultSet.next() ) {
						System.out.println();
						int origine = resultSet.getInt("origine");
						int destination = resultSet.getInt("destination");

						System.out.println(String.format("Origine: %-5d | Destination: %-5d ", 
								origine, destination));
					}
				}
			}
		}
	}

	public int[][] Floyd() {
		final int INF = Integer.MAX_VALUE / 2;
		int n = sites.size();
		int[][] M = new int[n+1][n+1];
		//initialisation
		for (int i = 0; i <=n; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == j) {
					M[i][j] = 0;
				} else {
					M[i][j] = 10000;
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
		for (k=1; k<=n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (i==j)continue;
					int a = M[i][k];
					int b = M[k][j];
					int d = M[i][j];
					if (M[i][k] + M[k][j] < M[i][j]) {
						M[i][j] = M[i][k] + M[k][j];
					}
				}
			}
		}
		return M; 
	}

	public String getDossier() {
		return dossier;
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
	
	private int indexFromId(int id_site) {
	    for (int i = 1; i < sites.size(); i++) {
	        if (sites.get(i).getId_site() == id_site) {
	            return i;
	        }
	    }
	    throw new IllegalArgumentException("Site non trouvé : " + id_site);
	}
}