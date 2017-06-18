package com.bsep.proj.iservices;

import java.util.List;

import com.bsep.proj.model.users.Privilegija;
import com.bsep.proj.model.users.Rola;

public interface IPrivilegijaService {
	
	Privilegija findOne(Long id);

	void save(Privilegija privilegija);

	void delete(Long id);
	
	List<Privilegija> getByRole(Rola role);

	Privilegija getByNaziv(String value);
	
}
