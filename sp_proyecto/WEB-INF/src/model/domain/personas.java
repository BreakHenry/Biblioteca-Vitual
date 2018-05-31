package model.domain;

import java.util.Date;

import utils.ClaseDESBase64;

public class personas {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int codper;
	public String nombre;
	public String ap;
	public String am;
	public String foto;
	public int estado;
	
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public int getCodper() {
		return codper;
	}
	public void setCodper(int codper) {
		this.codper = codper;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
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
	
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public String getCifrarCodper(){
		return des.encriptar(String.valueOf(this.codper));
	}
	
	public String getFotos() {
		String xfoto="";
		if(this.foto == null){
			xfoto="todos.gif";
		}else{
			xfoto=this.foto;
		}
		return xfoto;
	}
	
}
