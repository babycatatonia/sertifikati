package com.bsep.proj.repository;

import java.util.List;

import com.bsep.proj.model.Certificates;

public interface CertService {

	
	Certificates addCertificate(Certificates c);
	
	List<Certificates> vratiSveSertifikate();
	
	Certificates vratiPrekoSerijskog(long serial);
	
	void povuciSertifikat(long serial);
}
