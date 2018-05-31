package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.management.Query;
import javax.sql.DataSource;

import model.domain.personas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

//@Service indica que la clase es un bean de la capa de negocio
@Service
public class PersonasManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public List<Map<String,Object>> listar(int xestado, String xper, String xpersonal, String xpersonal2){
		//String xsql="select ru,nombre,ap,am,estado from alumnos where (nombre||ap||am LIKE ?)";
		
		String xsql=
				"select codper,nombre,ap,am,estado,tipoper,sw,foto "+
						"from( "+
						"select a.codper,a.nombre,a.ap,a.am,a.estado,foto,tipoper, 1 as sw "+
						"from personas a,usuarios u "+
						"where(a.codper=u.codper) and (a.nombre||a.ap||a.am LIKE ?)  "+
						"union all "+
						"select a.codper,a.nombre,a.ap,a.am,a.estado,foto,tipoper, 0 as sw "+
						"from personas a "+
						"where(a.codper not in (select codper from usuarios)) "+
						" ) x "+ 
						"where(x.nombre||x.ap||x.am like ?) AND ((x.tipoper=?) OR (x.tipoper=?))" +
						"order by ap ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xper , xper , xpersonal, xpersonal2});
	}

	/*public List<personas> listar(int xestado){
		List alu = this.jdbcTemplate.query(
			    "select codper,nombre,ap,am,estado,foto from personas",
			    new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            personas alu = new personas();
			            alu.setCodper(rs.getInt("codper"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setAp(rs.getString("ap"));
			            alu.setAm(rs.getString("am"));
			            alu.setFoto(rs.getString("foto"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;		
	}*/
	
	public List<Map<String,Object>> listarA(int xestado, String xper, String xpersonal, String xpersonal2){
		//String xsql="select ru,nombre,ap,am,estado from alumnos where (nombre||ap||am LIKE ?)";
		
		String xsql=
				"select codper,nombre,ap,am,estado,tipoper,sw,foto "+
						"from( "+
						"select a.codper,a.nombre,a.ap,a.am,a.estado,foto,tipoper, 1 as sw "+
						"from personas a,usuarios u "+
						"where(a.codper=u.codper) and (a.nombre||a.ap||a.am LIKE ?) and (a.estado=1)  "+
						"union all "+
						"select a.codper,a.nombre,a.ap,a.am,a.estado,foto,tipoper, 0 as sw "+
						"from personas a "+
						"where(a.codper not in (select codper from usuarios)) "+
						" ) x "+ 
						"where(x.nombre||x.ap||x.am like ?)and (x.estado=1) AND ((x.tipoper=?) OR (x.tipoper=?))" +
						"order by ap ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xper , xper , xpersonal, xpersonal2});
	}
	
	public List<Map<String,Object>> listarB(int xestado, String xper, String xpersonal, String xpersonal2){
		//String xsql="select ru,nombre,ap,am,estado from alumnos where (nombre||ap||am LIKE ?)";
		
		String xsql=
				"select codper,nombre,ap,am,estado,tipoper,sw,foto "+
						"from( "+
						"select a.codper,a.nombre,a.ap,a.am,a.estado,foto,tipoper, 1 as sw "+
						"from personas a,usuarios u "+
						"where(a.codper=u.codper) and (a.nombre||a.ap||a.am LIKE ?) and (a.estado=0)  "+
						"union all "+
						"select a.codper,a.nombre,a.ap,a.am,a.estado,foto,tipoper, 0 as sw "+
						"from personas a "+
						"where(a.codper not in (select codper from usuarios)) "+
						" ) x "+ 
						"where(x.nombre||x.ap||x.am like ?)and (x.estado=0) AND ((x.tipoper=?) OR (x.tipoper=?))" +
						"order by ap ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { xper , xper ,xpersonal,xpersonal2});
	}
	
	public Map<String,Object> buscarUsuario(String xlogin, String xclave){
		String xsql="	select u.login,u.passwd,p.codper,p.nombre,p.ap,p.am,p.foto,p.tipoper	"+
					"	FROM usuarios u, personas p	"+
					"	WHERE u.LOGIN=? AND u.PASSWD=? AND u.CODPER=p.CODPER	";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {xlogin,xclave});
	}
	
	public Map<String,Object>  adicionarPer(String xfilename,String xnombre,String xap,String xam,String xgenero,String xtipoper){
		String xsql="insert into personas(nombre,ap,am,genero,tipoper,foto,estado) values(?,?,?,?,?,?,1) returning codper";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {xnombre.toUpperCase(),xap.toUpperCase(),xam.toUpperCase(),xgenero.toUpperCase(),xtipoper.toUpperCase(),xfilename });
	}
	
	public Map<String,Object> adicionarPer2(String xnombre,String xap,String xam,String xgenero,String xtipoper){
		String xsql="insert into personas(nombre,ap,am,genero,tipoper,foto,estado) values(?,?,?,?,?,'todos.gif',1) returning codper";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {xnombre.toUpperCase(),xap.toUpperCase(),xam.toUpperCase(),xgenero.toUpperCase(),xtipoper.toUpperCase() });
	}
	
	public int adicionarCi(String xci,int codper){
		System.out.println(xci+" "+codper);
		String xsql="insert into datos(ci,codper) values(?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xci),codper});
	}
	
	public int adicionarTelef(int xcodper,String xnumero){
		System.out.println("llego numero y codper:: "+xnumero+" "+xcodper);
		String xsql="insert into telefonos(codper,numero) values(?,?) ";
		return this.jdbcTemplate.update(xsql, new Object[] {xcodper,Integer.parseInt(xnumero)});
	}
	
	public int eliminarPer(String xcodper){
		String xsql=" update personas set estado=0 where codper=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodper)});
	}
	
	public int habilitarPer(String xcodper){
		String xsql=" update personas set estado=1 where codper=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodper)});
	}
	
	public int modificarPer(String xcodper,String xnombre,String xap,String xam,String xgenero,String xtipoper){
		String xsql=" update personas set nombre=?, ap=?, am=?, genero=?, tipoper=?  where codper=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),xap.toUpperCase(),xam.toUpperCase(),xgenero.toUpperCase(),xtipoper.toUpperCase(),Integer.parseInt(xcodper) });
	}
	
	public int modificarCi(String xci,int xcodper){
		String xsql=" update datos set ci=? where codper=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xci), xcodper });
	}
	
	public Map<String,Object> buscarPer(String xcodper){
		String xsql="select codper,nombre,ap,am,genero,tipoper,foto,estado FROM personas WHERE CODPER=? ";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcodper)});
	}
	
	public Map<String,Object> buscarModPer(String xcodper){
		System.out.println(xcodper);
		String xsql="	select d.ci,p.codper,p.nombre,p.ap,p.am,p.genero,p.tipoper,p.foto	"+ 
					"	FROM datos d, personas p	"+
					"	WHERE p.codper=? and d.codper=p.codper";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcodper)});
	}
	
	public Map<String,Object> buscarModtelf(String xcodper){
		System.out.println(xcodper);
		String xsql="	select p.codper, t.numero	"+ 
					"	FROM personas p, telefonos t 	"+
					"	WHERE p.codper=? and p.codper=t.codper";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcodper)});
	}
	
	public int crearDatosAcceso(String xcodper, String xlogin,String xpasswd){
		String xsql="insert into usuarios(codper,login,passwd,estado) values(?,?,?,1)";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodper),xlogin.toUpperCase(),xpasswd.toUpperCase() });
	}
	
	public int modDatosAcceso(String xcodper,String xpasswd){
		String xsql=" update usuarios set passwd=? where codper=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xpasswd.toUpperCase(),Integer.parseInt(xcodper) });
	}
	
}