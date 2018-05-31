package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import model.domain.menus;
import model.domain.roles;
import model.domain.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

//@Service indica que la clase es un bean de la capa de negocio
@Service
public class MenuManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Map<String,Object>> xmenus() {
		String xsql="select codm,nombre,estado from menus" ;
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> xmenusA(String codr) {
		System.out.println("llego a la consulta "+codr);
		String xsql=" select m.codm , m.nombre"+
				" from menus m, rolme r "+
				" where m.estado=1 and m.codm=r.codm and r.codr=?"; ;
		return this.jdbcTemplate.queryForList(xsql, new Object[] { Integer.parseInt(codr) });
	}

	public List<Map<String,Object>> xopciones() {
		String xsql="select m.codm,p.nombre,p.enlace  from procesos p, mepro m where p.codp=m.codp" ;
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	//
	
	public List<Map<String,Object>> listarMenu(int xestado){		
		return this.jdbcTemplate.queryForList("select codm,nombre,estado from menus where estado=? order by nombre ", new Object[] { xestado});
	}
	
	//gestion menus
	
	public List<menus> listar(int xestado,String xmenu){
		List alu = this.jdbcTemplate.query(
				"select codm,nombre,estado from menus where(nombre like ?) order by nombre",
				new Object[] { xmenu },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            menus alu = new menus();
			            alu.setCodm(rs.getInt("codm"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<menus> listarA(int xestado,String xmenu){
		List alu = this.jdbcTemplate.query(
				"select codm,nombre,estado from menus where(nombre like ?)and (estado=1) order by nombre",
				new Object[] { xmenu },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            menus alu = new menus();
			            alu.setCodm(rs.getInt("codm"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<menus> listarB(int xestado,String xmenu){
		List alu = this.jdbcTemplate.query(
				"select codm,nombre,estado from menus where(nombre like ?)and (estado=0) order by nombre",
				new Object[] { xmenu },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            menus alu = new menus();
			            alu.setCodm(rs.getInt("codm"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public int adicionarMenu(String xnombre){
		String xsql="insert into menus(nombre,estado) values(?,1)";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase() });
	}
	
	public Map<String,Object> buscarMenu(String xcodm){
		String xsql="select codm,nombre FROM menus WHERE codm=? ";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcodm)});
	}
	
	public int modificarMenu(String xnombre,String xcodm){
		String xsql=" update menus set nombre=? where codm=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),Integer.parseInt(xcodm) });
	}
	
	public int eliminarMenu(String xcodm){
		String xsql=" update menus set estado=0 where codm=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodm)});
	}
	
	public int habilitarMenu(String xcodm){
		String xsql=" update menus set estado=1 where codm=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodm)});
	}
	
	// fin gestion menus
	
	// mepro
	
	public List<roles> listarR(int xestado,String xrol){
		List alu = this.jdbcTemplate.query(
				"select codr,nombre,estado from roles where(nombre like ?) order by nombre",
				new Object[] { xrol },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            roles alu = new roles();
			            alu.setCodr(rs.getInt("codr"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<Map<String,Object>> listarProcesos(int xestado){
		String xsql=" select codp,nombre,estado "+
					" from procesos "+
					" where estado=1";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> listarMepro(int xcodm){
		String xsql=" select p.codp "+
					" from procesos p, mepro m "+
					" where p.estado=1 and p.codp=m.codp and m.codm=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xcodm });
	}
	
	public int addMepro(int xcodp,int xcodm){
		String xsql=" insert into mepro(codp,codm)"+
					"    values(?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] { xcodp,xcodm });
	}
	
	public int delMepro(int xcodp,int xcodm){
		String xsql=" delete from  mepro "+
					"   where codp=? and codm=?";
		return this.jdbcTemplate.update(xsql, new Object[] { xcodp,xcodm });
	}
	
	// rolme
	
	public List<Map<String,Object>> listarM(int xestado){
		String xsql=" select codm,nombre,estado "+
					" from menus "+
					" where estado=1";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> listarRolme(int xcodr){
		String xsql=" select m.codm "+
					" from menus m, rolme r "+
					" where m.estado=1 and m.codm=r.codm and r.codr=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xcodr });
	}
	
	public int addRolme(int xcodm,int xcodr){
		String xsql=" insert into rolme(codm,codr)"+
					"    values(?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] { xcodm,xcodr });
	}
	
	public int delRolme(int xcodm,int xcodr){
		System.out.println("llegooo codm a consulta::"+xcodm);
    	System.out.println("llegooo codr a consulta::"+xcodr);
		String xsql=" delete from  rolme "+
					"   where codm=? and codr=?";
		return this.jdbcTemplate.update(xsql, new Object[] { xcodm,xcodr });
	}
	
	// rolusu
	
	public List<roles> listarU(int xestado,String xusuario){
		List alu = this.jdbcTemplate.query(
				"select login,estado from usuarios where(login like ?) order by login",
				new Object[] { xusuario },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            usuarios alu = new usuarios();
			            alu.setLogin(rs.getString("login"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<Map<String,Object>> listarR(int xestado){
		String xsql=" select codr,nombre,estado "+
					" from roles "+
					" where estado=1";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> listarRolusu(String xlog){
		System.out.println("llego el login:: "+xlog);
		String xsql=" select r.codr , r.nombre"+
					" from roles r, rolusu u "+
					" where r.estado=1 and r.codr=u.codr and u.login=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xlog });
	}
	
	public int addRolusu(int xcodr,String xlogin){
		System.out.println("llegaron datos de rolusu:: "+xcodr+" "+xlogin);
		String xsql=" insert into rolusu(codr,login)"+
					"    values(?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] { xcodr , xlogin});
	}
	
	public int delRolusu(int xcodr,String xlogin){
		System.out.println("llegooo codm a consulta::"+xcodr);
    	System.out.println("llegooo codr a consulta::"+xlogin);
		String xsql=" delete from  rolusu "+
					"   where codr=? and login=?";
		return this.jdbcTemplate.update(xsql, new Object[] { xcodr,xlogin });
	}
	
}