package com.bsep.proj.model;

public class CsrRequest {
	
	
	
	private String signer;
	private String csr;
	private String days;
	
	public CsrRequest(){}


	public void setSigner(String signer) {
		this.signer = signer;
	}

	public String getCsr() {
		return csr;
	}

	public void setCsr(String csr) {
		this.csr = csr;
	}

	public String getSigner() {
		// TODO Auto-generated method stub
		return signer;
	}


	public String getDays() {
		return days;
	}


	public void setDays(String days) {
		this.days = days;
	}
	
	

}
