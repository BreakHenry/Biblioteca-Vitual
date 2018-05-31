package model.manager;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.management.Query;
import javax.sql.DataSource;

import model.domain.areas;
import model.domain.autores;
import model.domain.ejmplares;
import model.domain.prestamos;
import model.domain.textos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class TextosManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public List<Map<String,Object>> xautores(){
		String xsql="	select coda,nombre,ap,am	"+
					"	from autores where (estado = 1) and (coda <> 0) "+
					"	order by nombre	";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> xeditoriales(){
		String xsql="	select code,nombre	"+
					"	from editoriales	"+
					"	order by nombre	";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> xareas(){
		String xsql="	select coda,nombre	"+
					"	from areas	"+
					"	order by nombre	";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> xtipos(){
		String xsql="	select nombre	"+
					"	from tipos	"+
					"	order by nombre	";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	
	/*public List<textos> listarTextos(int xestado ,String xtexto ,String xeditorial, String xarea , String xtipo){
		List alu = this.jdbcTemplate.query(
				"select t.codt, t.titulo , a.nombre as anombre , e.nombre as enombre , ti.nombre as tinombre , t.estado as testado "+
						"from textos t , areas a , editoriales e , tipos ti , tipotex y "+
						"where (t.coda=a.coda)and(t.code=e.code) and ((t.codt=y.codt) and (ti.codtipo=y.codtipo)) and (t.titulo LIKE ?) and (e.nombre LIKE ?) AND (a.nombre LIKE ?) AND (ti.nombre LIKE ?) order by titulo ",
				new Object[] {  xtexto , xeditorial, xarea , xtipo },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            textos alu = new textos();
			            alu.setCodt(rs.getInt("codt"));
			            alu.setTitulo(rs.getString("titulo"));
			            alu.setEstado(rs.getInt("testado"));
			            alu.setCoda(rs.getString("anombre"));
			            //alu.setCode(rs.getInt("code"));
			            return alu;
			        }
			    });
		return alu;
	}*/
	
	public List<Map<String,Object>> listarTextos(int xestado ,String xtexto ,String xeditorial, String xarea , String xtipo){
		String xsql="select t.codt, t.titulo , a.nombre as anombre , e.nombre as enombre , ti.nombre as tinombre , t.estado as testado "+
		"from textos t , areas a , editoriales e , tipos ti , tipotex y "+
		"where (t.coda=a.coda)and(t.code=e.code) and ((t.codt=y.codt) and (ti.codtipo=y.codtipo)) and (t.titulo LIKE ?) and (e.nombre LIKE ?) AND (a.nombre LIKE ?) AND (ti.nombre LIKE ?) order by titulo ";
					
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xtexto , xeditorial, xarea , xtipo});
	}
	
	public List<Map<String,Object>> listarTextosA(int xestado ,String xtexto ,String xeditorial, String xarea , String xtipo){
		String xsql="select t.codt, t.titulo , a.nombre as anombre , e.nombre as enombre , ti.nombre as tinombre , t.estado as testado "+
		"from textos t , areas a , editoriales e , tipos ti , tipotex y "+
		"where (t.coda=a.coda)and(t.code=e.code) and ((t.codt=y.codt) and (ti.codtipo=y.codtipo)) and (t.titulo LIKE ?) and (e.nombre LIKE ?) AND (a.nombre LIKE ?) AND (ti.nombre LIKE ?) and (t.estado=1) order by titulo ";
					
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xtexto , xeditorial, xarea , xtipo});
	}
	
	public List<Map<String,Object>> listarTextosB(int xestado ,String xtexto ,String xeditorial, String xarea , String xtipo){
		String xsql="select t.codt, t.titulo , a.nombre as anombre , e.nombre as enombre , ti.nombre as tinombre , t.estado as testado "+
		"from textos t , areas a , editoriales e , tipos ti , tipotex y "+
		"where (t.coda=a.coda)and(t.code=e.code) and ((t.codt=y.codt) and (ti.codtipo=y.codtipo)) and (t.titulo LIKE ?) and (e.nombre LIKE ?) AND (a.nombre LIKE ?) AND (ti.nombre LIKE ?) and (t.estado=0) order by titulo ";
					
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xtexto , xeditorial, xarea , xtipo});
	}
	public Map<String,Object>adicionarTexto(String xtitulo,String xresumen,String xisbn,int xedicion,Date xfecha,String xcoda,String xcode){
		System.out.println("hola "+xtitulo+" "+xresumen+" "+xisbn+" "+xedicion+" "+xfecha+" "+xcoda+" "+xcode);		
		String xsql="insert into textos(titulo,resumen,isbn,edicion,fechapub,coda,code,estado) values(?,?,?,?,?,?,?,1) returning codt";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {xtitulo.toUpperCase(),xresumen.toUpperCase(),xisbn.toUpperCase(),xedicion,xfecha,Integer.parseInt(xcoda),Integer.parseInt(xcode) });
	}
	
	public int adicionarTipo(int codt,int xcodtipo,String xfilename){
		System.out.println(xcodtipo+" tipito "+codt);
		String xsql="insert into tipotex(codt,codtipo,docum) values(?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {codt ,xcodtipo,xfilename});
	}
	
	public int adicionarTipo2(int codt,int xcodtipo){
		System.out.println(xcodtipo+" tipito "+codt);
		String xsql="insert into tipotex(codt,codtipo,docum) values(?,?,' ')";
		return this.jdbcTemplate.update(xsql, new Object[] {codt ,xcodtipo});
	}
	
	public int adicionarAutor(int coda,int xcodt){
		//System.out.println(xcodtipo+" tipito "+codt);
		String xsql="insert into escriben(coda,codt) values(?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {coda ,xcodt});
	}
	
	public int eliminarTexto(String xcodt){
		String xsql=" update textos set estado=0 where codt=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodt)});
	}
	
	public int habilitarTexto(String xcodt){
		String xsql=" update textos set estado=1 where codt=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodt)});
	}
	
	public Map<String,Object> buscarTexto(String xcodt){
		String xsql=" select d.codt ,d.codtipo, t.codt , t.titulo,t.resumen,t.isbn,t.edicion,t.fechapub,t.coda,t.code,t.estado "+
					"FROM tipotex d, textos t "+  
					"WHERE d.codt=? and d.codt=t.codt  ";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcodt)});
	}
	
	public int modificarTexto(String xtitulo,String xresumen,String xisbn,int xedicion,int xcoda,int xcode,int xcodt){
		//int codt=Integer.parseInt(xcodt);
		//System.out.println("CONSULTA MODTEXTO:: "+xcodt+" "+xtitulo+" "+xresumen+" "+xisbn+" "+xedicion+" "+xcoda+" "+xcode);		
		String xsql=" update textos set titulo=?,resumen=?,isbn=?,edicion=?,coda=?,code=? where codt=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xtitulo.toUpperCase(),xresumen.toUpperCase(),xisbn.toUpperCase(),xedicion,xcoda,xcode,xcodt});
	}
	
	public int modificarTipo(int xcodt,int xcodtipo){
		//System.out.println(xcodt+" "+xcodtipo);
		String xsql=" update tipotex set codtipo=? where codt=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xcodtipo,xcodt });
	}
	
	// ejemplares
	
	public List<ejmplares> listarEjemplares( int xcodt){
		List alu = this.jdbcTemplate.query(
				"select codinv,disponible,estado from ejemplares where codt=? order by codinv",
				new Object[] { xcodt },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            ejmplares alu = new ejmplares();
			            alu.setCodinv(rs.getInt("codinv"));
			            alu.setDisponible(rs.getInt("disponible"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public int adicionarEjemplar(int xcodinv,int xcodt,String xperR,String xperA){
		System.out.println(xcodinv+" "+xcodt+"  "+xperR+" "+xperA);
		String xsql="insert into ejemplares(codinv,codt,per_resp,per_anula,disponible,estado) values(?,?,?,?,10,1);";
		return this.jdbcTemplate.update(xsql, new Object[] {xcodinv,xcodt,xperR,xperA});
	}
	
	public int eliminarEjemplar(String xcodinv){
		String xsql=" update ejemplares set estado=0 where codinv=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodinv)});
	}
	
	public int habilitarEjemplar(String xcodinv){
		String xsql=" update ejemplares set estado=1 where codinv=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodinv)});
	}
	
	public Map<String,Object> buscarEjemplar(String xcodinv){
		String xsql=" select codinv "+
					"FROM ejemplares "+
					"where(codinv=?) ";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcodinv)});
	}
	
	public int modificarEjemplar(int xnvo,int xcodinv){
		String xsql=" update ejemplares set codinv=? where codinv=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xnvo,xcodinv});
	}
	
	// PRESTAMOS
	
	public List<Map<String,Object>> listarPrestamos(String xnombre){

		String xsql="select p.codp , p.fecha , p.fini , p.ffin , p.tipopres , p.estado , d.codper , x.nombre , x.ap , x.am "+
					"from mprestamo p , datos d , personas x "+
					"where (d.ci=p.ci) and (d.codper=x.codper) and (x.nombre like ?)  order by codp ";
					
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xnombre.toUpperCase()});
	}
	
	public List<Map<String,Object>> listarPrestamos2(String xnombre, Date fechaIni2, Date fechaFin2){

		String xsql="select p.codp , p.fecha , p.fini , p.ffin , p.tipopres , p.estado , d.codper , x.nombre , x.ap , x.am "+
					"from mprestamo p , datos d , personas x "+
					"where (d.ci=p.ci) and (d.codper=x.codper) and (x.nombre like ?) and (p.estado=1) and ((fini >= ?)and(ffin <= ?)) ";
					
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xnombre.toUpperCase(),fechaIni2,fechaFin2});
	}
	
	public List<Map<String,Object>> listarDprestamos(int xcodp){
		String xsql=" select d.codinv , t.titulo , d.estado , d.codp "+
					"from mprestamo p , dprestamo d ,ejemplares e , textos t "+
					"where (p.codp=d.codp) and (e.codinv=d.codinv) and(e.codt=t.codt) and(p.codp=?)  ";
								
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xcodp});
	}
	
	public int eliminarPrestamo(String xcodp){
		String xsql=" update mprestamo set estado=0 where codp=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodp)});
	}
	
	public List<Map<String,Object>> xpersona(){
		String xsql=" select d.ci,p.nombre "+
					" from personas p , datos d "+
					" where (p.codper=d.codper)	";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> mpresta(){
		String xsql=" select codp "+
					" from mprestamo ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> xejemplares(){
		String xsql=" select e.codinv,t.titulo "+
					" from ejemplares e , textos t "+
					" where (e.codt=t.codt)and(e.estado=1)	";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public Map<String,Object> adicionarPrestamo(Date xfecha,Date xfini,Date xffin,int tipopres,String xci,String xlogin){
		System.out.println(xfecha+" "+xfini+"  "+xffin+" "+tipopres+" "+xci+" "+xlogin);
		String xsql="insert into mprestamo(fecha,fini,ffin,tipopres,estado,ci,login) values(?,?,?,?,1,?,?) returning codp;";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {xfecha,xfini,xffin,tipopres,xci,xlogin});
	}
	
	public int adicionarDPrestamo(int codp,int codinv){
		System.out.println(codp+"::"+codinv);
		String xsql="insert into dprestamo(codp,codinv,estado) values(?,?,1);";
		return this.jdbcTemplate.update(xsql, new Object[] {codp,codinv});
	}
	
	// DEVOLUCIONES
	
	public int addDevol(int xcodp){
		String xsql=" update dprestamo set estado=0 where codp=?  ";		
		return this.jdbcTemplate.update(xsql, new Object[] { xcodp });
	}
	
	public List<Map<String,Object>> listarPrestamosDevol(String xnombre){

		String xsql="select p.codp , p.fecha , p.fini , p.ffin , p.tipopres , p.estado , d.codper , x.nombre , x.ap , x.am , y.codinv "+
					"from mprestamo p , datos d , personas x , dprestamo y "+
					"where (d.ci=p.ci) and (d.codper=x.codper) and (p.codp=y.codp) and (x.nombre like ?) order by codp ";
					
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xnombre.toUpperCase()});
	}
	
	public List<Map<String,Object>> listarPrestamos2Devol(String xnombre , Date fechaIni2, Date fechaFin2){

		String xsql="select p.codp , p.fecha , p.fini , p.ffin , p.tipopres , p.estado , d.codper , x.nombre , x.ap , x.am , y.codinv "+
					"from mprestamo p , datos d , personas x , dprestamo y "+
					"where (d.ci=p.ci) and (d.codper=x.codper) and (p.codp=y.codp) and (x.nombre like ?) and (p.estado=1) and ((fini >= ?)and(ffin <= ?)) ";
					
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xnombre.toUpperCase(),fechaIni2,fechaFin2});
	}
	
	// ANULACIONES
	
	public List<Map<String,Object>> listarAnulacionDevol(String xnombre){

		String xsql=" select p.codp ,m.codd, m.fecha , p.fini , p.ffin , p.tipopres , p.estado , d.codper , x.nombre , x.ap , x.am , y.codinv  , v.codinv  "+
					"from mprestamo p , datos d , personas x , dprestamo y , mdevol m , ddevol v "+
					"where (d.ci=p.ci) and (d.codper=x.codper) and (p.codp=y.codp) and (m.codp=p.codp) and (v.codd=m.codd) and (x.nombre like ?) order by codp  ";
					
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xnombre.toUpperCase()});
	}
	
	public List<Map<String,Object>> listarAnulacionDevol2(String xnombre, Date fechaIni2, Date fechaFin2){

		String xsql=" select p.codp , m.codd , m.fecha , p.fini , p.ffin , p.tipopres , p.estado , d.codper , x.nombre , x.ap , x.am , y.codinv  , v.codinv  "+
					"from mprestamo p , datos d , personas x , dprestamo y , mdevol m , ddevol v "+
					"where (d.ci=p.ci) and (d.codper=x.codper) and (p.codp=y.codp) and (m.codp=p.codp) and (v.codd=m.codd) (x.nombre like ?) and ((fini >= ?)and(ffin <= ?))  ";
					
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xnombre.toUpperCase(),fechaIni2,fechaFin2});
	}
	
	public List<Map<String,Object>> listarDdevol(int xcodd){
		String xsql=" select d.codinv , t.titulo "+
					"from mprestamo p , mdevol m,ddevol d ,ejemplares e , textos t "+
					"where (p.codp=m.codp) and (e.codinv=d.codinv) and(e.codt=t.codt) and (m.codd=?) ";
								
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xcodd});
	}
}
