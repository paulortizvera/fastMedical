package com.dioblazer.fast.medical.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.dioblazer.fast.medical.model.User;
import com.dioblazer.fast.medical.model.UserResponse;
import com.dioblazer.fast.medical.repository.IUserRepository;
import com.dioblazer.fast.medical.repository.param.GeneralParam;
import com.dioblazer.fast.medical.repository.param.UserParam;
import com.dioblazer.fast.medical.utils.BusinessException;
import com.dioblazer.fast.medical.utils.Constants;

@Repository
public class UserRepositoryImpl implements IUserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<User> findOneByLogin(String login) {
		return execLogin(login);
	}

	@Override
	public UserResponse findAll() {
		UserResponse resp = new UserResponse();
		resp.setUser(execQueryUser("S", "0", null, null));
		if (resp.getUser() != null) {
			resp.setSuccess(true);
		} else {
			resp = new UserResponse();
			resp.setSuccess(false);
			resp.setMessString("No se encontraron registros");
		}
		return resp;
	}

	@Override
	public UserResponse findActives() {
		UserResponse resp = new UserResponse();
		resp.setUser(execQueryUser("S", "1", null, null));
		if (resp.getUser() != null) {
			resp.setSuccess(true);
		} else {
			resp = new UserResponse();
			resp.setSuccess(false);
			resp.setMessString("No se encontraron registros");
		}
		return resp;
	}

	@Override
	public UserResponse userByLogin(String login) {
		UserResponse resp = new UserResponse();
		resp.setUser(execQueryUser("Q", "0", login, null));
		if (resp.getUser() != null) {
			resp.setSuccess(true);
		} else {
			resp = new UserResponse();
			resp.setSuccess(false);
			resp.setMessString("No se encontraron registros");
		}
		return resp;
	}

	@Override
	public UserResponse userByEmail(String email) {
		UserResponse resp = new UserResponse();
		resp.setUser(execQueryUser("Q", "1", null, email));
		if (resp.getUser() != null) {
			resp.setSuccess(true);
		} else {
			resp = new UserResponse();
			resp.setSuccess(false);
			resp.setMessString("No se encontraron registros");
		}
		return resp;
	}

	@Override
	public int save(User user) throws BusinessException {
		return execAdmUser("I", null, user, null);
	}

	@Override
	public int updateByLogin(User user) throws BusinessException {
		return execAdmUser("U", "0", user, null);
	}

	@Override
	public int deleteByLogin(String login) throws BusinessException {
		User user = new User();
		user.setLogin(login);
		return execAdmUser("D", null, user, null);
	}

	@Override
	public int updateRol(String loginAdm, User user) throws BusinessException {
		return execAdmUser("U", "1", user, loginAdm);
	}

	@Override
	public int updateStatus(String loginAdm, User user) throws BusinessException {
		return execAdmUser("U", "2", user, loginAdm);
	}

	private Optional<User> execLogin(String login) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.SP_LOGIN.getName())
				.declareParameters(new SqlParameter[] {
						new SqlParameter(GeneralParam.S_DATE.getName(), GeneralParam.S_DATE.getType()),
						new SqlParameter(GeneralParam.OPERATION.getName(), GeneralParam.OPERATION.getType()),
						new SqlParameter(GeneralParam.MODE.getName(), GeneralParam.MODE.getType()),
						new SqlParameter(GeneralParam.LOGIN.getName(), GeneralParam.LOGIN.getType()),
						new SqlParameter(UserParam.PASSWORD.getName(), UserParam.PASSWORD.getType()),
						new SqlParameter(GeneralParam.EMAIL.getName(), GeneralParam.EMAIL.getType()),
						new SqlParameter(UserParam.OTP.getName(), UserParam.OTP.getType()),
						new SqlParameter(UserParam.OTP_TYPE.getName(), UserParam.OTP_TYPE.getType()) })
				.returningResultSet("result", new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setLogin(rs.getString("LOGIN"));
						user.setPassword(rs.getString("CONTRASENA"));
						user.setName(rs.getString("NOMBRE"));
						return user;
					}

				});

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(GeneralParam.S_DATE.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.OPERATION.getName(), "S");
		mapSqlParameterSource.addValue(GeneralParam.MODE.getName(), "0");
		mapSqlParameterSource.addValue(GeneralParam.LOGIN.getName(), login);
		mapSqlParameterSource.addValue(UserParam.PASSWORD.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.EMAIL.getName(), "");
		mapSqlParameterSource.addValue(UserParam.OTP.getName(), "");
		mapSqlParameterSource.addValue(UserParam.OTP_TYPE.getName(), "");

		Map<String, Object> results = simpleJdbcCall.execute(mapSqlParameterSource);

		List<User> userList = (List<User>) results.get("result");
		return Optional.of(userList.get(0));
	}

	private <T> T execQueryUser(String operation, String mode, String login, String email) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName(Constants.SP_CONS_USER.getName())
				.declareParameters(new SqlParameter[] {
						new SqlParameter(GeneralParam.S_DATE.getName(), GeneralParam.S_DATE.getType()),
						new SqlParameter(GeneralParam.OPERATION.getName(), GeneralParam.OPERATION.getType()),
						new SqlParameter(GeneralParam.MODE.getName(), GeneralParam.MODE.getType()),
						new SqlParameter(GeneralParam.LOGIN.getName(), GeneralParam.LOGIN.getType()),
						new SqlParameter(GeneralParam.EMAIL.getName(), GeneralParam.EMAIL.getType()),
						new SqlParameter(UserParam.PASSWORD.getName(), UserParam.PASSWORD.getType()),
						new SqlParameter(UserParam.OTP.getName(), UserParam.OTP.getType()) })
				.returningResultSet("result", new RowMapper<User>() {

					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setLogin(rs.getString("LOGIN"));
						user.setName(rs.getString("NOMBRE"));
						user.setGender(rs.getString("SEXO"));
						user.setCountry(rs.getString("PAIS"));
						user.setCity(rs.getString("CIUDAD"));
						user.setAddress(rs.getString("DIRECCION"));
						user.setPhone(rs.getString("TELEFONO"));
						user.setEmail(rs.getString("CORREO"));
						user.setBirthDate(rs.getDate("FECHA_NACIMIENTO"));
						if (login == null && email == null) {
							user.setRegistrationDate(rs.getDate("FECHA_REGISTRO"));
							user.setModificiationDate(rs.getDate("FECHA_MODIFICACION"));
							user.setRol(rs.getString("ROL"));
							user.setStatus(rs.getString("ESTADO"));
						}
						return user;
					}

				});

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		mapSqlParameterSource.addValue(GeneralParam.S_DATE.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.OPERATION.getName(), operation);
		mapSqlParameterSource.addValue(GeneralParam.MODE.getName(), mode);
		mapSqlParameterSource.addValue(GeneralParam.LOGIN.getName(), login);
		mapSqlParameterSource.addValue(UserParam.PASSWORD.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.EMAIL.getName(), email);
		mapSqlParameterSource.addValue(UserParam.OTP.getName(), "");

		Map<String, Object> results = simpleJdbcCall.execute(mapSqlParameterSource);
		List<User> usersList = (List<User>) results.get("result");

		return usersList != null && !usersList.isEmpty() ? (T) usersList : null;
	}

	private int execAdmUser(String operation, String mode, User user, String loginAdm) throws BusinessException {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName(Constants.SP_ADM_USER.getName())
				.declareParameters(new SqlParameter[] {
						new SqlParameter(GeneralParam.S_LOGIN.getName(), GeneralParam.S_LOGIN.getType()),
						new SqlParameter(GeneralParam.S_DATE.getName(), GeneralParam.S_DATE.getType()),
						new SqlParameter(GeneralParam.OPERATION.getName(), GeneralParam.OPERATION.getType()),
						new SqlParameter(GeneralParam.MODE.getName(), GeneralParam.MODE.getType()),
						new SqlParameter(GeneralParam.LOGIN.getName(), GeneralParam.LOGIN.getType()),
						new SqlParameter(UserParam.PASSWORD.getName(), UserParam.PASSWORD.getType()),
						new SqlParameter(GeneralParam.EMAIL.getName(), GeneralParam.EMAIL.getType()),
						new SqlParameter(UserParam.NAME.getName(), UserParam.NAME.getType()),
						new SqlParameter(UserParam.GENDER.getName(), UserParam.GENDER.getType()),
						new SqlParameter(UserParam.COUNTRY.getName(), UserParam.COUNTRY.getType()),
						new SqlParameter(UserParam.CITY.getName(), UserParam.CITY.getType()),
						new SqlParameter(UserParam.ADDRESS.getName(), UserParam.ADDRESS.getType()),
						new SqlParameter(UserParam.PHONE.getName(), UserParam.PHONE.getType()),
						new SqlParameter(UserParam.BIRTHDATE.getName(), UserParam.BIRTHDATE.getType()),
						new SqlParameter(GeneralParam.ROL.getName(), GeneralParam.ROL.getType()),
						new SqlParameter(GeneralParam.STATUS.getName(), GeneralParam.STATUS.getType()),
						new SqlOutParameter(GeneralParam.ROWCOUNT.getName(), GeneralParam.ROWCOUNT.getType()),
						new SqlOutParameter(GeneralParam.ERROR_MSG.getName(), GeneralParam.ERROR_MSG.getType()) });

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(GeneralParam.S_LOGIN.getName(), loginAdm);
		mapSqlParameterSource.addValue(GeneralParam.S_DATE.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.OPERATION.getName(), operation);
		mapSqlParameterSource.addValue(GeneralParam.MODE.getName(), mode);
		mapSqlParameterSource.addValue(GeneralParam.LOGIN.getName(), user.getLogin());
		mapSqlParameterSource.addValue(UserParam.PASSWORD.getName(), user.getPassword());
		mapSqlParameterSource.addValue(GeneralParam.EMAIL.getName(), user.getEmail());
		mapSqlParameterSource.addValue(UserParam.NAME.getName(), user.getName());
		mapSqlParameterSource.addValue(UserParam.GENDER.getName(), user.getGender());
		mapSqlParameterSource.addValue(UserParam.COUNTRY.getName(), user.getCountry());
		mapSqlParameterSource.addValue(UserParam.CITY.getName(), user.getCity());
		mapSqlParameterSource.addValue(UserParam.ADDRESS.getName(), user.getAddress());
		mapSqlParameterSource.addValue(UserParam.PHONE.getName(), user.getPhone());
		mapSqlParameterSource.addValue(UserParam.BIRTHDATE.getName(), user.getBirthDate());
		mapSqlParameterSource.addValue(GeneralParam.ROL.getName(), user.getRol());
		mapSqlParameterSource.addValue(GeneralParam.STATUS.getName(), user.getStatus());

		Map<String, Object> results = simpleJdbcCall.execute(mapSqlParameterSource);
		int result = 0;
		if (results.get(GeneralParam.ERROR_MSG.getName()) != null) {
			throw new BusinessException((String) results.get(GeneralParam.ERROR_MSG.getName()));
		}
		if (results.get(GeneralParam.ROWCOUNT.getName()) != null) {
			result = (int) results.get(GeneralParam.ROWCOUNT.getName());
		}
		return result;
	}
}
