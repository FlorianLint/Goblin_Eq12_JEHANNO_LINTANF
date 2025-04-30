package entreprise;

public class Sites {
	private int id_site;
	private int x;
	private int y;
	
	public Sites(int id_site, int x, int y) {
		this.id_site = id_site;
		this.x = x;
		this.y = y;
	}

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