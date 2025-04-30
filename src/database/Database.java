package database;

import java.io.FileReader;
import java.sql.*;
import java.util.List;
import com.opencsv.*;

public class Database {
	public static void main(String[] args) {

		////////////////////////////Clients//////////////////////////////

		try {
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:./database/clientsdb", "SA", "");
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS Clients");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Clients (" +
					"Nom VARCHAR(100), " +
					"Mail VARCHAR(100), " +
					"id_site INT)");

			CSVReader reader = new CSVReaderBuilder(new FileReader("Jeux_de_donnees/petit/init-clients-30-10-Carre.csv"))
					.withCSVParser(new CSVParserBuilder().withSeparator(';').build())
					.build();
			List<String[]> lignes = reader.readAll();
			reader.close();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Clients (Nom, Mail, id_site) VALUES (?, ?, ?)");

			for (int i = 1; i < lignes.size(); i++) {
				String[] ligne = lignes.get(i);
				ps.setString(1, ligne[0]); // Nom
				ps.setString(2, ligne[1]); // Mail
				ps.setInt(3, Integer.parseInt(ligne[2])); // id_site
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			ps.close();
			conn.close();
			System.out.println("Import Clients terminé !");
		} catch (Exception e) {
			e.printStackTrace();
		}

		////////////////////////////Entrepôts//////////////////////////////

		try {
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:./database/clientsdb", "SA", "");
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS Entrepots");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Entrepots (" +
					"id_entrepot INT, " +
					"id_site INT, " +
					"cout_fixe INT, " +
					"stock INT)");

			CSVReader reader = new CSVReaderBuilder(new FileReader("Jeux_de_donnees/petit/init-entrepots-30-5-Carre.csv"))
					.withCSVParser(new CSVParserBuilder().withSeparator(';').build())
					.build();
			List<String[]> lignes = reader.readAll();
			reader.close();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Entrepots (id_entrepot, id_site, cout_fixe, stock) VALUES (?, ?, ?, ?)");

			for (int i = 1; i < lignes.size(); i++) {
				String[] ligne = lignes.get(i);
				ps.setInt(1, Integer.parseInt(ligne[0])); // id_entrepot
				ps.setInt(2, Integer.parseInt(ligne[1])); // id_site
				ps.setInt(3, Integer.parseInt(ligne[2])); // cout_fixe
				ps.setInt(4, Integer.parseInt(ligne[3])); // stock
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			ps.close();
			conn.close();
			System.out.println("Import Entrepôts terminé !");
		} catch (Exception e) {
			e.printStackTrace();
		}

		////////////////////////////Routes//////////////////////////////

		try {
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:./database/clientsdb", "SA", "");
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS Routes");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Routes (" +
					"origine INT, " +
					"destination INT)");

			CSVReader reader = new CSVReaderBuilder(new FileReader("Jeux_de_donnees/petit/init-routes-30-45-Carre.csv"))
					.withCSVParser(new CSVParserBuilder().withSeparator(';').build())
					.build();
			List<String[]> lignes = reader.readAll();
			reader.close();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Routes (origine, destination) VALUES (?, ?)");

			for (int i = 1; i < lignes.size(); i++) {
				String[] ligne = lignes.get(i);
				ps.setInt(1, Integer.parseInt(ligne[0])); // origine
				ps.setInt(2, Integer.parseInt(ligne[1])); // destination
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			ps.close();
			conn.close();
			System.out.println("Import Routes terminé !");
		} catch (Exception e) {
			e.printStackTrace();
		}
		////////////////////////////Sites//////////////////////////////

		try {
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:./database/clientsdb", "SA", "");
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS Sites");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Sites (" +
					"id_site INT, " +
					"x INT, " +
					"y INT)");

			CSVReader reader = new CSVReaderBuilder(new FileReader("Jeux_de_donnees/petit/init-sites-30-Carre.csv"))
					.withCSVParser(new CSVParserBuilder().withSeparator(';').build())
					.build();
			List<String[]> lignes = reader.readAll();
			reader.close();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Sites (id_site, x, y) VALUES (?, ?, ?)");

			for (int i = 1; i < lignes.size(); i++) {
				String[] ligne = lignes.get(i);
				ps.setInt(1, Integer.parseInt(ligne[0])); // id_site
				ps.setInt(2, Integer.parseInt(ligne[1])); // x
				ps.setInt(3, Integer.parseInt(ligne[2])); // y
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			ps.close();
			conn.close();
			System.out.println("Import Sites terminé !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}