package model.domain;

import utils.ClaseDESBase64;

public class editoriales {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int code;
	public int estado;
	public String nombre;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public String getCifrarCode(){
		return des.encriptar(String.valueOf(this.code));
	}

}
