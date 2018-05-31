package model.domain;

import utils.ClaseDESBase64;

public class areas {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int coda;
	public int estado;
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String nombre;
	public int getCoda() {
		return coda;
	}
	public void setCoda(int coda) {
		this.coda = coda;
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
