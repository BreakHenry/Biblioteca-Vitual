package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import model.domain.areas;
import model.domain.tipos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class TiposManager {

private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public List<areas> listar(int xestado,String xtipo){
		List alu = this.jdbcTemplate.query(
				"select codtipo,nombre,estado from tipos where(nombre like ?) order by nombre",
				new Object[] { xtipo },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            tipos alu = new tipos();
			            alu.setCodtipo(rs.getInt("codtipo"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<areas> listarA(int xestado,String xtipo){
		List alu = this.jdbcTemplate.query(
				"select codtipo,nombre,estado from tipos where(nombre like ?)and(estado=1) order by nombre",
				new Object[] { xtipo },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            tipos alu = new tipos();
			            alu.setCodtipo(rs.getInt("codtipo"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<areas> listarB(int xestado,String xtipo){
		List alu = this.jdbcTemplate.query(
				"select codtipo,nombre,estado from tipos where(nombre like ?)and(estado=0) order by nombre",
				new Object[] { xtipo },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            tipos alu = new tipos();
			            alu.setCodtipo(rs.getInt("codtipo"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public int adicionarTipo(String xnombre,String xsw){
		String xsql="insert into tipos(nombre,estado,sw) values(?,1,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),Integer.parseInt(xsw) });
	}
	
	public Map<String,Object> buscarTipo(String xcodtipo){
		String xsql="select codtipo,nombre,sw FROM tipos WHERE codtipo=? ";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcodtipo)});
	}
	
	public int modificarTipo(String xnombre,String xsw,String xcodtipo){
		String xsql=" update tipos set nombre=?,sw=? where codtipo=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),Integer.parseInt(xcodtipo),Integer.parseInt(xsw) });
	}
	
	public int eliminarTipo(String xcodtipo){
		String xsql=" update tipos set estado=0 where codtipo=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodtipo)});
	}
	
	public int habilitarTipo(String xcodtipo){
		String xsql=" update tipos set estado=1 where codtipo=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodtipo)});
	}
}
