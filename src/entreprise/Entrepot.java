package entreprise;

import com.opencsv.bean.CsvBindByName;

public class Entrepot {
	@CsvBindByName(column = "id entrepot")
	private int id_entrepot;
	@CsvBindByName(column = "id site")
	private int id_site;
	@CsvBindByName(column = "cout fixe")
	private int cout_fixe;
	@CsvBindByName(column = "stock")
	private int stock;

	public Entrepot() {} //Pour que OpenCSV fonctionne
	
	public Entrepot(int id_entrepot, int id_site, int cout_fixe, int stock) {
		this.id_entrepot = id_entrepot;
		this.id_site = id_site;
		this.cout_fixe = cout_fixe;
		this.stock = stock;
	}
	
	public int getId_entrepot() {
		return id_entrepot;
	}
	public void setId_entrepot(int id_entrepot) {
		this.id_entrepot = id_entrepot;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getCout_fixe() {
		return cout_fixe;
	}
	public void setCout_fixe(int cout_fixe) {
		this.cout_fixe = cout_fixe;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}	
}