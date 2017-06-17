package com.bsep.proj.ostalo;

public class MessageWithObj {
	private String message;
	private boolean ok;
	private Object obj;
	
	public MessageWithObj(String message, boolean ok, Object obj){
		this.message = message;
		this.ok = ok;
		this.obj = obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
	
}
