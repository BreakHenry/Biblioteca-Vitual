package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import model.domain.areas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class AreasManager {

private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public List<areas> listar(int xestado,String xarea){
		List alu = this.jdbcTemplate.query(
				"select coda,nombre,estado from areas where(nombre like ?) order by nombre",
				new Object[] { xarea },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            areas alu = new areas();
			            alu.setCoda(rs.getInt("coda"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<areas> listarA(int xestado,String xarea){
		List alu = this.jdbcTemplate.query(
				"select coda,nombre,estado from areas where(nombre like ?) and (estado=1) order by nombre",
				new Object[] { xarea },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            areas alu = new areas();
			            alu.setCoda(rs.getInt("coda"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<areas> listarB(int xestado,String xarea){
		List alu = this.jdbcTemplate.query(
				"select coda,nombre,estado from areas where(nombre like ?) and (estado=0) order by nombre",
				new Object[] { xarea },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            areas alu = new areas();
			            alu.setCoda(rs.getInt("coda"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public int adicionarArea(String xnombre){
		String xsql="insert into areas(nombre,estado) values(?,1)";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase() });
	}
	
	public Map<String,Object> buscarArea(String xcoda){
		String xsql="select coda,nombre FROM areas WHERE coda=? ";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcoda)});
	}
	
	public int modificarArea(String xnombre,String xcoda){
		String xsql=" update areas set nombre=? where coda=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),Integer.parseInt(xcoda) });
	}
	
	public int eliminarArea(String xcoda){
		String xsql=" update areas set estado=0 where coda=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcoda)});
	}
	
	public int habilitarArea(String xcoda){
		String xsql=" update areas set estado=1 where coda=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcoda)});
	}
	
}
