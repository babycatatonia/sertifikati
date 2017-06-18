package com.bsep.proj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bsep.proj.model.users.Korisnik;

@Repository
@Transactional
public interface IKlijentRepository extends JpaRepository<Korisnik, Long>{
	Korisnik findById(Long id);
	
	Korisnik findByEmail(String email);


}
