package com.bsep.proj.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsep.proj.iservices.IKlijentService;
import com.bsep.proj.model.users.Korisnik;
import com.bsep.proj.repository.IKlijentRepository;


@Service
public class KlijentService implements IKlijentService {

	@Autowired
	private IKlijentRepository klijentRepository;


	@Override
	public List<Korisnik> findAll() {
		// TODO Auto-generated method stub
		return klijentRepository.findAll();
	}

	@Override
	public Korisnik findOne(Long id) {
		// TODO Auto-generated method stub
		return klijentRepository.findById(id);
	}

	@Override
	public void save(Korisnik klijent) {
		klijentRepository.save(klijent);
	}

	@Override
	public void delete(Long id) {
		klijentRepository.delete(id);
	}

	@Override
	public Korisnik findByEmail(String email) {
		return klijentRepository.findByEmail(email);
	}

}
