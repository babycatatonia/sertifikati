package com.bsep.proj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bsep.proj.model.Certificates;

@Repository
@Transactional
public interface CertificateRepo extends JpaRepository<Certificates, Long> {
	List<Certificates> findAll();
	Certificates findBySerial(long serial);
	
	
	@Query("update Certificates c set c.ocspValid=false where c.serial=:serial")
	@Modifying
	@Transactional
	void setOCSPflag(@Param("serial") long serial);
}
