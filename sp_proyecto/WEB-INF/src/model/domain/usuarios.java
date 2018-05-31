package model.domain;

import utils.ClaseDESBase64;

public class usuarios {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int estado;
	public String login;
	
}
