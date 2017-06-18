package com.bsep.proj.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bsep.proj.iservices.IPrivilegijaService;
import com.bsep.proj.model.users.Privilegija;
import com.bsep.proj.model.users.Rola;
import com.bsep.proj.repository.IPrivilegijaRepository;

@Service
@Component
public class PrivilegijaService implements IPrivilegijaService{

	@Autowired
	private IPrivilegijaRepository privilegijaService;
	
	
	@Override
	public Privilegija findOne(Long id) {
		return privilegijaService.findOne(id);
	}

	@Override
	public void save(Privilegija privilegija) {
		privilegijaService.save (privilegija);
		
	}

	@Override
	public void delete(Long id) {
		privilegijaService.delete(id);
	}

	@Override
	public List<Privilegija> getByRole(Rola role) {
		return privilegijaService.findByRole(role);
	}

	@Override
	public Privilegija getByNaziv(String value) {
		return privilegijaService.findByNaziv(value);
	}

}
