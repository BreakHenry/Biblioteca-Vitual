package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.management.Query;
import javax.sql.DataSource;

import model.domain.areas;
import model.domain.autores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class AutoresManager {
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public List<areas> listar(int xestado,String xautor){
		List alu = this.jdbcTemplate.query(
				"select coda,nombre,ap,am,estado from autores where(nombre||ap||am like ?) order by nombre",
				new Object[] { xautor },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            autores alu = new autores();
			            alu.setCoda(rs.getInt("coda"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setAp(rs.getString("ap"));
			            alu.setAm(rs.getString("am"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<areas> listarA(int xestado,String xautor){
		List alu = this.jdbcTemplate.query(
				"select coda,nombre,ap,am,estado from autores where(nombre||ap||am like ?)and(estado=1) order by nombre",
				new Object[] { xautor },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            autores alu = new autores();
			            alu.setCoda(rs.getInt("coda"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setAp(rs.getString("ap"));
			            alu.setAm(rs.getString("am"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<areas> listarB(int xestado,String xautor){
		List alu = this.jdbcTemplate.query(
				"select coda,nombre,ap,am,estado from autores where(nombre||ap||am like ?)and(estado=0) order by nombre",
				new Object[] { xautor },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            autores alu = new autores();
			            alu.setCoda(rs.getInt("coda"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setAp(rs.getString("ap"));
			            alu.setAm(rs.getString("am"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public int adicionarAutor(String xnombre,String xap,String xam,String xgenero){
		String xsql="insert into autores(nombre,ap,am,genero,estado) values(?,?,?,?,1)";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),xap.toUpperCase(),xam.toUpperCase(),xgenero.toUpperCase() });
	}
	
	public Map<String,Object> buscarAutor(String xcoda){
		String xsql="select coda,nombre,ap,am FROM autores WHERE coda=? ";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcoda)});
	}
	
	public int modificarAutor(String xcoda,String xnombre,String xap,String xam,String xgenero){
		String xsql=" update autores set nombre=?,ap=?,am=?,genero=? where coda=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),xap.toUpperCase(),xam.toUpperCase(),xgenero.toUpperCase(),Integer.parseInt(xcoda) });
	}
	
	public int eliminarAutor(String xcoda){
		String xsql=" update autores set estado=0 where coda=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcoda)});
	}
	
	public int habilitarAutor(String xcoda){
		String xsql=" update autores set estado=1 where coda=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcoda)});
	}
}
