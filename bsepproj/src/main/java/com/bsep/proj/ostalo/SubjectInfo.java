package com.bsep.proj.ostalo;

import java.util.Date;

public class SubjectInfo {
	
	private String ime;
	private String prezime;
	private String email;
	private String kompanija;
	private String orgUnit;
	private Date startDate;
	private Date endDate;
	private String keystorepass;
	private String alias;
	private String signer;
	private String ca;
	private String certpass;
	
	public SubjectInfo(){}

	public SubjectInfo(String ime, String prezime, String email, String kompanija,String orgUnit, String idKorisnika, Date startDate,
			Date endDate, String keystorepass, String alias, String signer, String ca, String certpass) {
		super();
		this.signer=signer;
		this.ime = ime;
		this.certpass =certpass;
		this.setKeystorepass(keystorepass);
		this.prezime = prezime;
		this.email = email;
		this.alias = alias;
		this.ca=ca;
		this.kompanija = kompanija;
		this.setOrgUnit(orgUnit);

		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKompanija() {
		return kompanija;
	}

	public void setKompanija(String kompanija) {
		this.kompanija = kompanija;
	}



	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}

	public String getKeystorepass() {
		return keystorepass;
	}

	public void setKeystorepass(String keystorepass) {
		this.keystorepass = keystorepass;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

	public String getCertpass() {
		return certpass;
	}

	public void setCertpass(String certpass) {
		this.certpass = certpass;
	}
}
