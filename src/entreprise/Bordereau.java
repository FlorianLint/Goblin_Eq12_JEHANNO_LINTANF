package entreprise;

import java.io.*;
import java.util.*;

public class Bordereau {
	private List<String> clientMails = new ArrayList<>();
	private List<Integer> demandes = new ArrayList<>();
	private List<Integer> entrepotIds = new ArrayList<>();

	public Bordereau(Entreprise e) {
		String nomFichier = "";
		try {
			String dossier = e.getDossier();
			File f = new File("Jeux_de_donnees" + File.separator + dossier);
			System.out.println(f.listFiles().length);
			for (File ff : f.listFiles()) {
				nomFichier = ff.getName();
				if (ff.getAbsolutePath().contains("bordereau")) { // A modifier car il peut y avoir plusieurs bordereau par entreprise
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
}