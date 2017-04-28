package net.codejava.hibernate;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PERSON_PHOTO")
public class Person {
	private int id;
	private String name;
	private Blob photo;
	
	public Person() {
	}
	
	public Person(String name) {
		this.name = name;
	}

	@Id
	@Column(name = "PERSON_ID")
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Blob getPhoto() {
		return photo;
	}

	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
}