import entreprise.*;
import java.io.*;
import java.util.Scanner;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        try {
            // Demande manuelle de la date au format AAAA-MM-JJ
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez la date du bordereau (AAAA-MM-JJ) : ");
            String date = scanner.nextLine();
            scanner.close();

            //On efface les fichiers pour empécher des bugs
            new File("Json"+ File.separator +"bordereau.json").delete();
            new File("Json"+ File.separator +"resultat.json").delete();
            new File("Json" + File.separator + "resultat-" + date + ".txt").delete();
            
            Entreprise e = new Entreprise();
            Bordereau b = new Bordereau(e, date);
            b.toJson(); // génère bordereau.json

            // Exécution du script Python, en spécifiant le fichier de sortie directement dans le bon dossier
            ProcessBuilder pb = new ProcessBuilder("python", "src"+ File.separator +"SSCFLP.py", "Json"+ File.separator +"bordereau.json", "Json"+ File.separator +"resultat.json");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Script Python exécuté avec succès !");
            } else {
                System.err.println("Le script Python a échoué !");
            }
            
            // Affiche du résultat dans la console
            Gson gson = new Gson();
            Reader resultReader = new FileReader("Json" + File.separator + "resultat.json");
            JsonResult result = gson.fromJson(resultReader, JsonResult.class);
            resultReader.close();
            System.out.println("=== Résultat de l'optimisation ===");
            System.out.println("Statut : " + result.status);
            System.out.println("Objectif : " + result.objective);
            System.out.println("Entrepôts ouverts : " + result.openfacilities);
            System.out.println("Livraisons :");
            for (int i = 0; i < result.deliveriesfor_customer.size(); i++) {
                int entrepotId = result.deliveriesfor_customer.get(i);
                System.out.println("Le client " + (i+1) + " est livré par l'entrepôt " + entrepotId);
            }
            
            // Génération du résultat dans un fichier texte
            FileWriter writer = new FileWriter("Json" + File.separator + "resultat-" + date + ".txt");
            writer.write("=== Résultat de l'optimisation ===\n");
            writer.write("Statut : " + result.status + "\n");
            writer.write("Objectif : " + result.objective + "\n");
            writer.write("Entrepôts ouverts : " + result.openfacilities + "\n");
            writer.write("Livraisons :\n");
            for (int i = 0; i < result.deliveriesfor_customer.size(); i++) {
                int entrepotId = result.deliveriesfor_customer.get(i);
                writer.write("Le client " + (i+1) + " est livré par l'entrepôt " + entrepotId + "\n");
            }
            writer.close();
            System.out.println("Résultat écrit dans Json" + File.separator + "resultat-" + date + ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}