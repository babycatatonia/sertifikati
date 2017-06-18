package com.bsep.proj.model.users;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "privilegija")
public class Privilegija {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	/*@ManyToOne
	private Rola rola;*/
	
	@ManyToMany(mappedBy = "privilegije")
    private Set<Rola> role;
	
	public Privilegija() {}

	public Privilegija(String naziv) {
		super();
		this.naziv = naziv;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	@JsonIgnore
	public Set<Rola> getRole() {
		return role;
	}

	@JsonProperty
	public void setRole(Set<Rola> role) {
		this.role = role;
	}
}
