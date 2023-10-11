package com.dioblazer.fast.medical.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.repository.IUserRepository;

@Repository
public class UserRepositoryImpl implements IUserRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> findAll() {
		/*String SQL = "select * from usuario";
		return jdbcTemplate.query(SQL, BeanPropertyRowMapper.newInstance(User.class));*/
		
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("sp_fm_cons_usuario")
				.declareParameters(new SqlParameter[] {
						new SqlParameter("@s_date", Types.DATE),
						new SqlParameter("@i_operacion", Types.VARCHAR),
						new SqlParameter("@i_modo", Types.INTEGER),
						new SqlParameter("@i_login", Types.VARCHAR),
						new SqlParameter("@i_contrasena", Types.VARCHAR),
						new SqlParameter("@i_correo", Types.VARCHAR),
						new SqlParameter("@i_otp", Types.VARCHAR)
				})
				.returningResultSet("lista", new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setLogin((String) rs.getObject("login"));
						user.setName(rs.getString(2));
						return user;
					}
				});
		
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("@s_date", "04/04/2023");
		mapSqlParameterSource.addValue("@i_operacion", "S");
		mapSqlParameterSource.addValue("@i_modo", "0");
		mapSqlParameterSource.addValue("@i_login", "admin");
		mapSqlParameterSource.addValue("@i_contrasena", "");
		mapSqlParameterSource.addValue("@i_correo", "");
		mapSqlParameterSource.addValue("@i_otp", "");
		
		Map<String, Object> results = simpleJdbcCall.execute(mapSqlParameterSource);
		List<User> userlist = (List<User>) results.get("lista");
		
		return userlist;
	}

	@Override
	public int save(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
