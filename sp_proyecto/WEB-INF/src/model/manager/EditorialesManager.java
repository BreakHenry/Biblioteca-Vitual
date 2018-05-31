package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import model.domain.areas;
import model.domain.editoriales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class EditorialesManager {

private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public List<areas> listar(int xestado,String xeditorial){
		List alu = this.jdbcTemplate.query(
				"select code,nombre,estado from editoriales where(nombre like ?) order by nombre",
				new Object[] { xeditorial },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            editoriales alu = new editoriales();
			            alu.setCode(rs.getInt("code"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<areas> listarA(int xestado,String xeditorial){
		List alu = this.jdbcTemplate.query(
				"select code,nombre,estado from editoriales where(nombre like ?) and (estado=1) order by nombre",
				new Object[] { xeditorial },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            editoriales alu = new editoriales();
			            alu.setCode(rs.getInt("code"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public List<areas> listarB(int xestado,String xeditorial){
		List alu = this.jdbcTemplate.query(
				"select code,nombre,estado from editoriales where(nombre like ?) and (estado=0) order by nombre",
				new Object[] { xeditorial },
				new RowMapper() {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            editoriales alu = new editoriales();
			            alu.setCode(rs.getInt("code"));
			            alu.setNombre(rs.getString("nombre"));
			            alu.setEstado(rs.getInt("estado"));
			            return alu;
			        }
			    });
		return alu;
	}
	
	public int adicionarEditorial(String xnombre){
		String xsql="insert into editoriales(nombre,estado) values(?,1)";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase() });
	}
	
	public Map<String,Object> buscarEditorial(String xcode){
		String xsql="select code,nombre FROM editoriales WHERE code=? ";
		return this.jdbcTemplate.queryForMap(xsql, new Object[] {Integer.parseInt(xcode)});
	}
	
	public int modificarEditorial(String xnombre,String xcode){
		String xsql=" update editoriales set nombre=? where code=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),Integer.parseInt(xcode) });
	}
	
	public int eliminarEditorial(String xcode){
		String xsql=" update editoriales set estado=0 where code=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcode)});
	}
	
	public int habilitarEditorial(String xcode){
		String xsql=" update editoriales set estado=1 where code=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcode)});
	}
}
