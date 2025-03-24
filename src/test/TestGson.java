package test;

import com.google.gson.Gson;

	class Person {
	    String name;
	    int age;

	    public Person(String name, int age) {
	        this.name = name;
	        this.age = age;
	    }
	}

	public class TestGson {
	    public static void main(String[] args) {
	        Gson gson = new Gson();
	        
	        // Création d'un objet Java et conversion en JSON
	        Person person = new Person("Alice", 30);
	        String json = gson.toJson(person);
	        System.out.println("Objet Java en JSON : " + json);
	        
	        // Conversion d'une chaîne JSON en objet Java
	        String jsonString = "{\"name\":\"Bob\",\"age\":25}";
	        Person person2 = gson.fromJson(jsonString, Person.class);
	        System.out.println("JSON en objet Java : " + person2.name + ", " + person2.age);
	    }
	}

