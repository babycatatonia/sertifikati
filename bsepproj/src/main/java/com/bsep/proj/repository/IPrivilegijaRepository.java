package com.bsep.proj.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bsep.proj.model.users.Privilegija;
import com.bsep.proj.model.users.Rola;

@Repository
@Transactional
public interface IPrivilegijaRepository extends JpaRepository<Privilegija, Long> {
	
	Privilegija findById (Long id);
	
	List<Privilegija> findByRole(Rola rola);
	
	Privilegija findByNaziv(String naziv);
	
}
