package database;

public class Clients {
	private Sites id_site;
	private String mail;
	private String nom;

	public Clients(Sites id_site, String mail, String nom) {
		this.id_site = id_site;
		this.mail = mail;
		this.nom = nom;
	}

	public Sites getId_site() {
		return id_site;
	}

	public void setId_site(Sites id_site) {
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