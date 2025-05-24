import entreprise.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Demande manuelle de la date au format AAAA-MM-JJ
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez la date du bordereau (AAAA-MM-JJ) : ");
            String date = scanner.nextLine();
            scanner.close();

            Entreprise e = new Entreprise();
            Bordereau b = new Bordereau(e, date);
            b.toJson(); // génère bordereau.json

            // Exécution du script Python, en spécifiant le fichier de sortie directement dans le bon dossier
            ProcessBuilder pb = new ProcessBuilder("python", "src"+ File.separator +"SSCFLP.py", "Json"+ File.separator +"bordereau.json", "Json"+ File.separator +"resultat.json");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Lecture de la sortie console Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[PYTHON] " + line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Script Python exécuté avec succès !");
            } else {
                System.err.println("Le script Python a échoué !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}