package entreprise;

import com.opencsv.bean.CsvBindByName;

public class Client {
	@CsvBindByName(column = "id site")
	private int id_site;
	@CsvBindByName(column = "mail")
	private String mail;
	@CsvBindByName(column = "nom")
	private String nom;
	
	public Client() {} //Pour que OpenCSV fonctionne
	
	public Client(int id_site, String mail, String nom) {
		this.id_site = id_site;
		this.mail = mail;
		this.nom = nom;
	}
	
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
}