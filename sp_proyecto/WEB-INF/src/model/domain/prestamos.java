package model.domain;

import utils.ClaseDESBase64;

public class prestamos {
	ClaseDESBase64 des=new ClaseDESBase64();
	public int codp;
	public String fecha;
	public String fini;
	public String ffin;
	public int tipopres;
	public int estado;
	public String ci;
	public String login;
	public int getCodp() {
		return codp;
	}
	public void setCodp(int codp) {
		this.codp = codp;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFini() {
		return fini;
	}
	public void setFini(String fini) {
		this.fini = fini;
	}
	public String getFfin() {
		return ffin;
	}
	public void setFfin(String ffin) {
		this.ffin = ffin;
	}
	public int getTipopres() {
		return tipopres;
	}
	public void setTipopres(int tipopres) {
		this.tipopres = tipopres;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getCifrarCodp(){
		return des.encriptar(String.valueOf(this.codp));
	}
}
