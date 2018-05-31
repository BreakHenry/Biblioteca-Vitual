package model.domain;

import utils.ClaseDESBase64;

public class textos {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int codt;
	public int estado;
	public String coda;
	public int code;
	public String titulo;
	public int getCodt() {
		return codt;
	}
	public void setCodt(int codt) {
		this.codt = codt;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
	
	public String getCoda() {
		return coda;
	}
	public void setCoda(String coda) {
		this.coda = coda;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getCifrarCodt(){
		return des.encriptar(String.valueOf(this.codt));
	}
}
