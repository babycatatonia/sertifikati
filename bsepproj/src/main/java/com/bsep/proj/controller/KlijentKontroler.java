package com.bsep.proj.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bsep.proj.iservices.IKlijentService;
import com.bsep.proj.iservices.IPrivilegijaService;
import com.bsep.proj.model.users.Korisnik;
import com.bsep.proj.model.users.Privilegija;



@Controller
@RequestMapping("/klijentKontroler")
public class KlijentKontroler {

	@Autowired
	public IKlijentService klijentService;

	@Autowired
	private IPrivilegijaService prvilegijaService;

	
	@RequestMapping(value = "/ucitajPrivilegije", method = RequestMethod.GET)
	public ResponseEntity<List<String>> ucitajPrivilegije(HttpSession session) {
		Korisnik kor = (Korisnik) session.getAttribute("ulogovanKorisnik");
		if(kor != null){
			List<String> privilegije = new ArrayList<>();
			if(kor.getRola() == null)
				return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.BAD_REQUEST);
			
			List<Privilegija> collection = new ArrayList<>();	
			//collection.addAll(kor.getRola().getPrivilegije());
			collection.addAll(prvilegijaService.getByRole(kor.getRola()));
			
			for (Privilegija priv : collection) {
				privilegije.add(priv.getNaziv());
			}
			return new ResponseEntity<List<String>>(privilegije, HttpStatus.OK);					
		}
		return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.BAD_REQUEST);
	}
}
