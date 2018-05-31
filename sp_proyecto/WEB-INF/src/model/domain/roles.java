package model.domain;

import utils.ClaseDESBase64;

public class roles {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int codr;
	public int estado;
	public String nombre;
	public int getCodr() {
		return codr;
	}
	public void setCodr(int codr) {
		this.codr = codr;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCifrarCodr(){
		return des.encriptar(String.valueOf(this.codr));
	}
}
