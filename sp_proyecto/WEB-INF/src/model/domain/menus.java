package model.domain;

import utils.ClaseDESBase64;

public class menus {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int codm;
	public int getCodm() {
		return codm;
	}
	public void setCodm(int codm) {
		this.codm = codm;
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
	public int estado;
	public String nombre;
	
	public String getCifrarCodm(){
		return des.encriptar(String.valueOf(this.codm));
	}
}
