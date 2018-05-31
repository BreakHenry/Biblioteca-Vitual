package model.domain;

import utils.ClaseDESBase64;

public class tipos {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int codtipo;
	public int getCodtipo() {
		return codtipo;
	}
	public void setCodtipo(int codtipo) {
		this.codtipo = codtipo;
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
	
	public String getCifrarCodtipo(){
		return des.encriptar(String.valueOf(this.codtipo));
	}
}
