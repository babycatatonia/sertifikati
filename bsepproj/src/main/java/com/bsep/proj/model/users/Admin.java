package com.bsep.proj.model.users;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Admin extends Korisnik implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public Admin(){}
	
}
