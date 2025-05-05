package entreprise;

import com.opencsv.bean.CsvBindByName;

public class Site {
	@CsvBindByName(column = "id site")
	private int id_site;
	@CsvBindByName(column = "x")
	private int x;
	@CsvBindByName(column = "y")
	private int y;
	
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}