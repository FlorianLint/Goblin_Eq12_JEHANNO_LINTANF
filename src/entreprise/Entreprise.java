package entreprise;

import java.io.File;
import java.util.List;

public class Entreprise {
    public static void main(String[] args) throws Exception {
        List<Client> clients = CsvUtils.readCsv("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-clients-30-10-Carre.csv", Client.class);
        List<Entrepot> entrepots = CsvUtils.readCsv("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-entrepots-30-5-Carre.csv", Entrepot.class);
        List<Route> routes = CsvUtils.readCsv("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-routes-30-45-Carre.csv", Route.class);
        List<Site> sites = CsvUtils.readCsv("Jeux_de_donnees"+File.separator+"petit"+File.separator+"init-sites-30-Carre.csv", Site.class);

        // Exemple d'affichage
        for (Client c : clients) {
            System.out.println(c.getId_site());
        }
    }
}