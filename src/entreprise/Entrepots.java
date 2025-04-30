package entreprise;

public class Entrepots {
	private Sites id_entrepot;
	private Sites id_site;
	private int stock;
	private int cout_fixe;
	
	public Entrepots(Sites id_entrepot, Sites id_site, int stock, int cout_fixe) {
		this.id_entrepot = id_entrepot;
		this.id_site = id_site;
		this.stock = stock;
		this.cout_fixe = cout_fixe;
	}

	public Sites getId_entrepot() {
		return id_entrepot;
	}

	public void setId_entrepot(Sites id_entrepot) {
		this.id_entrepot = id_entrepot;
	}

	public Sites getId_site() {
		return id_site;
	}

	public void setId_site(Sites id_site) {
		this.id_site = id_site;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getCout_fixe() {
		return cout_fixe;
	}

	public void setCout_fixe(int cout_fixe) {
		this.cout_fixe = cout_fixe;
	}
}