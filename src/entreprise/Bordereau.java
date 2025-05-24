package entreprise;

import java.io.*;
import java.sql.*;
import java.util.*;
import com.google.gson.Gson;

public class Bordereau {
	private List<String> clientMails = new ArrayList<>();
	private List<Integer> demandes = new ArrayList<>();
	private List<Integer> entrepotIds = new ArrayList<>();

	public Bordereau(Entreprise e, String FchTxt) {
		String nomFichier = "";
		try {
			String dossier = e.getDossier();
			System.out.println("dossier : "+ dossier); // voir dossier
			File f = new File("Jeux_de_donnees" + File.separator + dossier);
			System.out.println(f.listFiles().length);
			for (File ff : f.listFiles()) {
				
				System.out.println("Fichier trouvé : " + ff.getAbsolutePath());
				System.out.println("Je cherche : " + FchTxt);

				nomFichier = ff.getName();
				if (ff.getAbsolutePath().contains(FchTxt)) {
					BufferedReader reader = new BufferedReader(new FileReader(ff));
					//Pour sauter les deux premières lignes
					reader.readLine();
					reader.readLine();
					//Pour lire les clients
					int n = Integer.parseInt(reader.readLine().trim());
					for (int i = 0; i < n; i++) {
						String line = reader.readLine();
						String[] parts = line.split(":");
						String email = parts[0].trim();
						int demande = Integer.parseInt(parts[1].trim());
						clientMails.add(email);
						demandes.add(demande);
					}
					//Pour lire les entrepots
					String[] entrepotParts = reader.readLine().split(",");
					for (String code : entrepotParts) {
						int idEntrepot = Integer.parseInt(code.trim());
						entrepotIds.add(idEntrepot);
					}
					reader.close();
				}
			}
		} catch (Exception ex) {
			System.out.println("nom fichier : " + nomFichier);
			ex.printStackTrace();
		}
	}

	public void toJson() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:hsqldb:file:database"+File.separator+"basic", "sa", "");

			JsonExport json = new JsonExport();
			json.capacity_facility = new ArrayList<>();
			json.fixed_cost_facility = new ArrayList<>();
			json.demand_customer = new ArrayList<>();
			json.cost_matrix = new ArrayList<>();

			// Remplir les demandes
			for (int demande : demandes) {
				json.demand_customer.add((double) demande);
			}

			// Remplir les capacités et coûts fixes des entrepôts
			for (int idEntrepot : entrepotIds) {
				PreparedStatement ps = conn.prepareStatement("SELECT stock, cout_fixe FROM entrepot WHERE id_entrepot = ?");
				ps.setInt(1, idEntrepot);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					json.capacity_facility.add(rs.getDouble("stock"));
					json.fixed_cost_facility.add(rs.getDouble("cout_fixe"));
				} else {
					json.capacity_facility.add(0.0);
					json.fixed_cost_facility.add(0.0);
					System.err.println("Entrepôt introuvable : " + idEntrepot);
				}
				rs.close();
				ps.close();
			}

			// Générer la matrice de coûts à partir de la table FloydW
			for (int idEntrepot : entrepotIds) {
				List<Double> ligne = new ArrayList<>();
				for (String email : clientMails) {
					// Récupérer l'id_site du client
					PreparedStatement psClient = conn.prepareStatement("SELECT id_site FROM client WHERE mail = ?");
					psClient.setString(1, email);
					ResultSet rsClient = psClient.executeQuery();
					if (rsClient.next()) {
						int idSiteClient = rsClient.getInt("id_site");

						// Récupérer id_site de l'entrepôt
						PreparedStatement psEntrepot = conn.prepareStatement("SELECT id_site FROM entrepot WHERE id_entrepot = ?");
						psEntrepot.setInt(1, idEntrepot);
						ResultSet rsEntrepot = psEntrepot.executeQuery();
						if (rsEntrepot.next()) {
							int idSiteEntrepot = rsEntrepot.getInt("id_site");

							// Récupérer la distance depuis FloydW
							PreparedStatement psDist = conn.prepareStatement("SELECT distance FROM FloydW WHERE origine = ? AND destination = ?");
							psDist.setInt(1, idSiteEntrepot);
							psDist.setInt(2, idSiteClient);
							ResultSet rsDist = psDist.executeQuery();
							if (rsDist.next()) {
								int distance = rsDist.getInt("distance");
								ligne.add((double) distance); // coût = distance (int to double)
							} else {
								ligne.add(Double.MAX_VALUE); // distance inconnue
							}
							rsDist.close();
							psDist.close();
						}
						rsEntrepot.close();
						psEntrepot.close();
					}
					rsClient.close();
					psClient.close();
				}
				json.cost_matrix.add(ligne);
			}
			json.num_facility_locations = entrepotIds.size();
			json.num_customers = clientMails.size();

			// Écriture JSON dans un fichier
			Gson gson = new Gson();
			FileWriter writer = new FileWriter("Json"+ File.separator +"bordereau.json");
			
//			//pour voir le contenue des listes
//			System.out.println("Données JSON à écrire :");
//			System.out.println("Capacités : " + json.capacity_facility);
//			System.out.println("Coûts fixes : " + json.fixed_cost_facility);
//			System.out.println("Demandes clients : " + json.demand_customer);
//			System.out.println("Matrice des coûts : " + json.cost_matrix);
//			System.out.println("Nombre d'entrepôts : " + json.num_facility_locations);
//			System.out.println("Nombre de clients : " + json.num_customers);
			
			gson.toJson(json, writer);
			writer.close();

			System.out.println("Fichier JSON écrit avec succès !");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}