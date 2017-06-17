package com.bsep.proj.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsep.proj.model.Certificates;
@Service
public class CertServiceImpl implements CertService {

	@Autowired
	CertificateRepo repo;
	
	
	@Override
	public Certificates addCertificate(Certificates c) {
		
		return repo.save(c);
	}


	@Override
	public List<Certificates> vratiSveSertifikate() {
		
		return repo.findAll();
	}


	@Override
	public Certificates vratiPrekoSerijskog(long serial) {
	
		return repo.findBySerial(serial);
	}


	@Override
	public void povuciSertifikat(long serial) {
		repo.setOCSPflag(serial);
		
	}



}
