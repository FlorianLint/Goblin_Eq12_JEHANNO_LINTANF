package database;

import com.opencsv.bean.CsvBindByName;

public class Client {
	@CsvBindByName
	private Sites id_site;
	@CsvBindByName
	private String mail;
	@CsvBindByName
	private String nom;
	
}