package com.bsep.proj.model.users;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "korisnik")
@Component
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tip_korisnika", discriminatorType=DiscriminatorType.STRING)
public class Korisnik {
	
	@Id
	@GeneratedValue
	private Long id;

	
	@Column(name = "ime", nullable = false)
	private String ime;
	
	@Column(name = "prezime", nullable = false)
	private String prezime;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "sifra", length = 60, nullable = false)
	private String sifra;
	
	@ManyToOne
	private Rola rola;
		
	public Korisnik() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	@JsonIgnore
	public Rola getRola() {
		return rola;
	}

	@JsonProperty
	public void setRola(Rola rola) {
		this.rola = rola;
	}
}
