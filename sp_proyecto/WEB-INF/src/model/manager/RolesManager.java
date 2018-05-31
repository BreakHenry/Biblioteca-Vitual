package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import model.domain.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class RolesManager {

private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public List<roles> listar(int xestado,String xrol){
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
	
	public List<roles> listarA(int xestado,String xrol){
		List alu = this.jdbcTemplate.query(
				"select codr,nombre,estado from roles where(nombre like ?)and(estado=1) order by nombre",
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
	
	public List<roles> listarB(int xestado,String xrol){
		List alu = this.jdbcTemplate.query(
				"select codr,nombre,estado from roles where(nombre like ?)and(estado=0) order by nombre",
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
	
	public int adicionarRol(String xnombre){
		String xsql="insert into roles(nombre,estado) values(?,1)";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase() });
	}
	
	public Map<String,Object> buscarRol(String xcodr){
		String xsql="select codr,nombre FROM roles WHERE codr=? ";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcodr)});
	}
	
	public int modificarRol(String xnombre,String xcodr){
		String xsql=" update roles set nombre=? where codr=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),Integer.parseInt(xcodr) });
	}
	
	public int eliminarRol(String xcodr){
		String xsql=" update roles set estado=0 where codr=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodr)});
	}
	
	public int habilitarRol(String xcodr){
		String xsql=" update roles set estado=1 where codr=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodr)});
	}
}