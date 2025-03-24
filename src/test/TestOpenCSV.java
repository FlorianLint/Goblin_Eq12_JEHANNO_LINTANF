package test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;

public class TestOpenCSV {
    public static void main(String[] args) {
        String filePath = "jeux_de_donnees/data.csv"; // Chemin relatif à la racine du projet

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println("Nom: " + nextLine[0] + ", Age: " + nextLine[1] + ", Ville: " + nextLine[2]);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}




