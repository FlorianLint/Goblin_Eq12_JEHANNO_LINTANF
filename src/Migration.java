import entreprise.Entreprise;
import java.util.Scanner;

public class Migration {
	public static void main(String[] arg) {
		 Scanner scanner = new Scanner(System.in);
	        System.out.println("Quel jeu de données voulez-vous utiliser ? (petit, moyen, grand) :");
	        String dossier = scanner.nextLine().trim().toLowerCase();

	        while (!dossier.equals("petit") && !dossier.equals("moyen") && !dossier.equals("grand")) {
	            System.out.println("Entrée invalide. Veuillez taper : petit, moyen ou grand.");
	            dossier = scanner.nextLine().trim().toLowerCase();
	        }
	Entreprise e = new Entreprise(dossier);
     try {
         e.BaseDonnees();
     } catch (Exception ex) {
         ex.printStackTrace();
     }
	scanner.close(); 
	}
}