package entreprise;

import com.opencsv.bean.CsvBindByName;

public class Entrepot {
	@CsvBindByName(column = "id entrepot")
	private Site id_entrepot;
	@CsvBindByName(column = "id site")
	private Site id_site;
	@CsvBindByName(column = "cout fixe")
	private int cout_fixe;
	@CsvBindByName(column = "stock")
	private int stock;
	
	public Site getId_entrepot() {
		return id_entrepot;
	}
	public void setId_entrepot(Site id_entrepot) {
		this.id_entrepot = id_entrepot;
	}
	public Site getId_site() {
		return id_site;
	}
	public void setId_site(Site id_site) {
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