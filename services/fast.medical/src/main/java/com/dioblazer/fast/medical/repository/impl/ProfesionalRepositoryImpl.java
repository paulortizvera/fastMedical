package com.dioblazer.fast.medical.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.dioblazer.fast.medical.model.Profesional;
import com.dioblazer.fast.medical.model.ProfesionalResponse;
import com.dioblazer.fast.medical.repository.IProfesionalRepository;
import com.dioblazer.fast.medical.repository.param.GeneralParam;
import com.dioblazer.fast.medical.repository.param.ProfesionalParam;
import com.dioblazer.fast.medical.utils.BusinessException;
import com.dioblazer.fast.medical.utils.Constants;

@Repository
public class ProfesionalRepositoryImpl implements IProfesionalRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public ProfesionalResponse findAll() {
		ProfesionalResponse resp = new ProfesionalResponse();
		resp.setProfesional(execQueryProfesional("S", "0", null, null));
		if (resp.getProfesional() != null) {
			resp.setSuccess(true);
		} else {
			resp = new ProfesionalResponse();
			resp.setSuccess(false);
			resp.setMessString("No se encontraron registros");
		}
		return resp;
	}

	@Override
	public ProfesionalResponse findActives() {
		ProfesionalResponse resp = new ProfesionalResponse();
		resp.setProfesional(execQueryProfesional("S", "1", null, null));
		if (resp.getProfesional() != null) {
			resp.setSuccess(true);
		} else {
			resp = new ProfesionalResponse();
			resp.setSuccess(false);
			resp.setMessString("No se encontraron registros");
		}
		return resp;
	}

	@Override
	public ProfesionalResponse ProfesionalByLogin(String login) {
		ProfesionalResponse resp = new ProfesionalResponse();
		resp.setProfesional(execQueryProfesional("Q", "0", login, null));
		if (resp.getProfesional() != null) {
			resp.setSuccess(true);
		} else {
			resp = new ProfesionalResponse();
			resp.setSuccess(false);
			resp.setMessString("No se encontraron registros");
		}
		return resp;
	}

	@Override
	public ProfesionalResponse ProfesionalByEmail(String email) {
		ProfesionalResponse resp = new ProfesionalResponse();
		resp.setProfesional(execQueryProfesional("Q", "1", null, email));
		if (resp.getProfesional() != null) {
			resp.setSuccess(true);
		} else {
			resp = new ProfesionalResponse();
			resp.setSuccess(false);
			resp.setMessString("No se encontraron registros");
		}
		return resp;
	}

	@Override
	public int save(Profesional profesional) throws BusinessException {
		return execAdmProfesional("I", null, profesional, null);
	}

	@Override
	public int updateByLogin(Profesional profesional) throws BusinessException {
		return execAdmProfesional("U", "0", profesional, null);
	}

	@Override
	public int deleteByLogin(String login) throws BusinessException {
		Profesional profesional = new Profesional();
		profesional.setLogin(login);
		return execAdmProfesional("D", null, profesional, null);
	}

	@Override
	public int updateDocumentation(String loginAdm, Profesional profesional) throws BusinessException {
		return execAdmProfesional("U", "1", profesional, loginAdm);
	}

	@Override
	public int updateStatus(String loginAdm, Profesional profesional) throws BusinessException {
		return execAdmProfesional("U", "2", profesional, loginAdm);
	}

	private <T> T execQueryProfesional(String operation, String mode, String login, String email) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName(Constants.SP_CONS_PROFESIONAL.getName())
				.declareParameters(new SqlParameter[] {
						new SqlParameter(GeneralParam.S_DATE.getName(), GeneralParam.S_DATE.getType()),
						new SqlParameter(GeneralParam.OPERATION.getName(), GeneralParam.OPERATION.getType()),
						new SqlParameter(GeneralParam.MODE.getName(), GeneralParam.MODE.getType()),
						new SqlParameter(GeneralParam.LOGIN.getName(), GeneralParam.LOGIN.getType()),
						new SqlParameter(GeneralParam.EMAIL.getName(), GeneralParam.EMAIL.getType()) })
				.returningResultSet("result", new RowMapper<Profesional>() {

					@Override
					public Profesional mapRow(ResultSet rs, int rowNum) throws SQLException {
						Profesional profesional = new Profesional();
						profesional.setCode(rs.getInt("CODIGO"));
						profesional.setType(rs.getString("TIPO"));
						profesional.setSpecialty(rs.getString("ESPECIALIDAD"));
						profesional.setModificationDate(rs.getDate("FECHA_MODIFICACION"));
						profesional.setDocumentation(rs.getString("DOCUMENTACION"));
						profesional.setComentary(rs.getString("COMENTARIO"));
						profesional.setStatus(rs.getString("ESTADO"));
						if (login == null) {
							profesional.setLogin(rs.getString("LOGIN"));
						}
						return profesional;
					}

				});

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		mapSqlParameterSource.addValue(GeneralParam.S_DATE.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.OPERATION.getName(), operation);
		mapSqlParameterSource.addValue(GeneralParam.MODE.getName(), mode);
		mapSqlParameterSource.addValue(GeneralParam.LOGIN.getName(), login);
		mapSqlParameterSource.addValue(GeneralParam.EMAIL.getName(), email);

		Map<String, Object> results = simpleJdbcCall.execute(mapSqlParameterSource);
		List<Profesional> profesional = (List<Profesional>) results.get("result");

		return profesional != null && !profesional.isEmpty() ? (T) profesional : null;
	}

	private int execAdmProfesional(String operation, String mode, Profesional profesional, String loginAdm)
			throws BusinessException {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName(Constants.SP_ADM_PROFESIONAL.getName())
				.declareParameters(new SqlParameter[] {
						new SqlParameter(GeneralParam.S_LOGIN.getName(), GeneralParam.S_LOGIN.getType()),
						new SqlParameter(GeneralParam.S_DATE.getName(), GeneralParam.S_DATE.getType()),
						new SqlParameter(GeneralParam.OPERATION.getName(), GeneralParam.OPERATION.getType()),
						new SqlParameter(GeneralParam.MODE.getName(), GeneralParam.MODE.getType()),
						new SqlParameter(GeneralParam.LOGIN.getName(), GeneralParam.LOGIN.getType()),
						new SqlParameter(GeneralParam.TYPE.getName(), GeneralParam.TYPE.getType()),
						new SqlParameter(ProfesionalParam.SPECIALITY.getName(), ProfesionalParam.SPECIALITY.getType()),
						new SqlParameter(GeneralParam.DOCUMENTATION.getName(), GeneralParam.DOCUMENTATION.getType()),
						new SqlParameter(GeneralParam.COMENTARY.getName(), GeneralParam.COMENTARY.getType()),
						new SqlParameter(GeneralParam.ROL.getName(), GeneralParam.ROL.getType()),
						new SqlParameter(GeneralParam.STATUS.getName(), GeneralParam.STATUS.getType()),
						new SqlOutParameter(GeneralParam.ROWCOUNT.getName(), GeneralParam.ROWCOUNT.getType()),
						new SqlOutParameter(GeneralParam.ERROR_MSG.getName(), GeneralParam.ERROR_MSG.getType()) });

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(GeneralParam.S_LOGIN.getName(), loginAdm);
		mapSqlParameterSource.addValue(GeneralParam.S_DATE.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.OPERATION.getName(), operation);
		mapSqlParameterSource.addValue(GeneralParam.MODE.getName(), mode);
		mapSqlParameterSource.addValue(GeneralParam.LOGIN.getName(), profesional.getLogin());
		mapSqlParameterSource.addValue(GeneralParam.TYPE.getName(), profesional.getType());
		mapSqlParameterSource.addValue(ProfesionalParam.SPECIALITY.getName(), profesional.getSpecialty());
		mapSqlParameterSource.addValue(GeneralParam.DOCUMENTATION.getName(), profesional.getDocumentation());
		mapSqlParameterSource.addValue(GeneralParam.COMENTARY.getName(), profesional.getComentary());
		mapSqlParameterSource.addValue(GeneralParam.STATUS.getName(), profesional.getStatus());

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
