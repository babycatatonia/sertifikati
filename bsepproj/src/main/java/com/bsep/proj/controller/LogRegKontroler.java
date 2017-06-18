package com.bsep.proj.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bsep.proj.helpers.Poruka;
import com.bsep.proj.iservices.IKlijentService;
import com.bsep.proj.model.users.Korisnik;
import com.bsep.proj.security.Password;


@Controller
@Scope("session")
@RequestMapping("/contr")
public class LogRegKontroler {

	@Autowired
	private IKlijentService korisnikService;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Poruka> login(@RequestBody Korisnik newUser, HttpSession session){	
		Korisnik kor = (Korisnik) session.getAttribute("ulogovanKorisnik");	
		
		if(kor == null){
			Korisnik korisnik = korisnikService.findByEmail(newUser.getEmail());
				
			if (Password.checkPassword(newUser.getSifra(), korisnik.getSifra())){
				session.setAttribute("ulogovanKorisnik", korisnik);
				
				return new ResponseEntity<Poruka>(new Poruka(korisnik.getIme(), null), HttpStatus.ACCEPTED);
			}
			
			return new ResponseEntity<Poruka>(new Poruka("NePostoji", null), HttpStatus.ACCEPTED);
		}else{
			return new ResponseEntity<Poruka>(new Poruka("VecUlogovan", kor), HttpStatus.ACCEPTED);
		}	
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Poruka> logout(HttpSession session){	
		
		Korisnik kor = (Korisnik) session.getAttribute("ulogovanKorisnik");
		if(kor != null){
			//korisnikService.logoutKorisnik();
			session.invalidate();
			return new ResponseEntity<Poruka>(new Poruka("Izlogovan", null), HttpStatus.ACCEPTED);
		}else{
			return new ResponseEntity<Poruka>(new Poruka("NijeIzlogovan", null), HttpStatus.ACCEPTED);			
		}
	}
}
