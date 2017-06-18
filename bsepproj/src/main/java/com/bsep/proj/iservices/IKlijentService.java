package com.bsep.proj.iservices;

import java.util.List;

import com.bsep.proj.model.users.Korisnik;

public interface IKlijentService{
	
	List<Korisnik> findAll();

	
	Korisnik findOne(Long id);

	void save(Korisnik klijent);

	void delete(Long id);

	Korisnik findByEmail(String email);
	
}
