package model.domain;

import utils.ClaseDESBase64;

public class ejmplares {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int codinv;
	public int estado;
	public int disponible;
	public int getCodinv() {
		return codinv;
	}
	public void setCodinv(int codinv) {
		this.codinv = codinv;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getDisponible() {
		return disponible;
	}
	public void setDisponible(int disponible) {
		this.disponible = disponible;
	}
	
	public String getCifrarCodinv(){
		return des.encriptar(String.valueOf(this.codinv));
	}
}
