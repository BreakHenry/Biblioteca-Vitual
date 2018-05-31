package model.domain;

import utils.ClaseDESBase64;

public class autores {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int coda;
	public int estado;
	public String nombre,ap,am;
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}
	public String getAm() {
		return am;
	}
	public void setAm(String am) {
		this.am = am;
	}
	public int getCoda() {
		return coda;
	}
	public void setCoda(int coda) {
		this.coda = coda;
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
	
	public String getCifrarCoda(){
		return des.encriptar(String.valueOf(this.coda));
	}
}
