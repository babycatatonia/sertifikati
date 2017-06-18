package com.bsep.proj.model.users;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "rola")
public class Rola implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	/*@OneToMany(mappedBy = "rola")
	private Set<Privilegija> privilegijе;*/
	
	@ManyToMany
    @JoinTable(
        name = "rola_privilegija", 
        joinColumns = @JoinColumn(
          name = "rola_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilegija_id", referencedColumnName = "id"))
    private Set<Privilegija> privilegije;  
	
	@OneToMany(mappedBy = "rola")
	private Set<Korisnik> korisnici;
	
	public Rola() {}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	@JsonIgnore
	public Set<Privilegija> getPrivilegije() {
		return privilegije;
	}

	@JsonProperty
	public void setPrivilegije(Set<Privilegija> privilegije) {
		this.privilegije = privilegije;
	}

	public Set<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(Set<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}	
}
