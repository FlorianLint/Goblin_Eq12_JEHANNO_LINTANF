package database;

import java.io.FileReader;
import java.sql.*;
import java.util.List;
import com.opencsv.*;

public class Database {
	public static void main(String[] args) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:./database/clientsdb", "SA", "");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
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
                ps.setInt(3, Integer.parseInt(ligne[3])); // id_site
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            ps.close();
            conn.close();
            System.out.println("Import terminé !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
