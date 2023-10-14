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

import com.dioblazer.fast.medical.model.Solicitude;
import com.dioblazer.fast.medical.model.SolicitudeResponse;
import com.dioblazer.fast.medical.repository.ISolicitudeRepository;
import com.dioblazer.fast.medical.repository.param.GeneralParam;
import com.dioblazer.fast.medical.repository.param.SolicitudeParam;
import com.dioblazer.fast.medical.utils.BusinessException;
import com.dioblazer.fast.medical.utils.Constants;

@Repository
public class SolicitudeRepositoryImpl implements ISolicitudeRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public SolicitudeResponse findAll() {
		SolicitudeResponse resp = new SolicitudeResponse();
		resp.setSolicitude(execQuerySolicitude(Constants.SEARCH.getName(), "0", null, null, null, null, null));
		if (resp.getSolicitude() != null) {
			resp.setSuccess(true);
		} else {
			resp = new SolicitudeResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public SolicitudeResponse findComplete() {
		SolicitudeResponse resp = new SolicitudeResponse();
		resp.setSolicitude(execQuerySolicitude(Constants.SEARCH.getName(), "1", null, null, null, null, null));
		if (resp.getSolicitude() != null) {
			resp.setSuccess(true);
		} else {
			resp = new SolicitudeResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public SolicitudeResponse solicitudeByNumber(Integer number) {
		SolicitudeResponse resp = new SolicitudeResponse();
		resp.setSolicitude(execQuerySolicitude(Constants.QUERY.getName(), "0", number, null, null, null, null));
		if (resp.getSolicitude() != null) {
			resp.setSuccess(true);
		} else {
			resp = new SolicitudeResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public SolicitudeResponse solicitudeByClient(Integer client) {
		SolicitudeResponse resp = new SolicitudeResponse();
		resp.setSolicitude(execQuerySolicitude(Constants.QUERY.getName(), "1", null, client, null, null, null));
		if (resp.getSolicitude() != null) {
			resp.setSuccess(true);
		} else {
			resp = new SolicitudeResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public SolicitudeResponse solicitudeByClientLogin(String loginCli) {
		SolicitudeResponse resp = new SolicitudeResponse();
		resp.setSolicitude(execQuerySolicitude(Constants.QUERY.getName(), "2", null, null, null, loginCli, null));
		if (resp.getSolicitude() != null) {
			resp.setSuccess(true);
		} else {
			resp = new SolicitudeResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public SolicitudeResponse solicitudeByProfesional(Integer profesional) {
		SolicitudeResponse resp = new SolicitudeResponse();
		resp.setSolicitude(execQuerySolicitude(Constants.QUERY.getName(), "3", null, null, profesional, null, null));
		if (resp.getSolicitude() != null) {
			resp.setSuccess(true);
		} else {
			resp = new SolicitudeResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public SolicitudeResponse solicitudeByProfesionalLogin(String loginCli) {
		SolicitudeResponse resp = new SolicitudeResponse();
		resp.setSolicitude(execQuerySolicitude(Constants.QUERY.getName(), "4", null, null, null, null, loginCli));
		if (resp.getSolicitude() != null) {
			resp.setSuccess(true);
		} else {
			resp = new SolicitudeResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public int save(Solicitude solicitude) throws BusinessException {
		return execAdmSolicitude(Constants.INSERT.getName(), null, solicitude);
	}

	@Override
	public int updateByNumber(Solicitude solicitude) throws BusinessException {
		return execAdmSolicitude(Constants.UPDATE.getName(), "0", solicitude);
	}

	@Override
	public int deleteByNumber(Integer number) throws BusinessException {
		Solicitude solicitude = new Solicitude();
		solicitude.setNumber(number);
		return execAdmSolicitude(Constants.DELETE.getName(), null, solicitude);
	}

	@Override
	public int aceptSolicitude(Solicitude solicitude) throws BusinessException {
		return execAdmSolicitude(Constants.UPDATE.getName(), "1", solicitude);
	}

	@Override
	public int updateStatus(Solicitude solicitude) throws BusinessException {
		return execAdmSolicitude(Constants.UPDATE.getName(), "2", solicitude);
	}

	private <T> T execQuerySolicitude(String operation, String mode, Integer number, Integer client,
			Integer profesional, String login_cli, String login_pro) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName(Constants.SP_CONS_SOLICITUDE.getName())
				.declareParameters(new SqlParameter[] {
						new SqlParameter(GeneralParam.S_DATE.getName(), GeneralParam.S_DATE.getType()),
						new SqlParameter(GeneralParam.OPERATION.getName(), GeneralParam.OPERATION.getType()),
						new SqlParameter(GeneralParam.MODE.getName(), GeneralParam.MODE.getType()),
						new SqlParameter(SolicitudeParam.NUMBER.getName(), SolicitudeParam.NUMBER.getType()),
						new SqlParameter(SolicitudeParam.CLIENT.getName(), SolicitudeParam.CLIENT.getType()),
						new SqlParameter(SolicitudeParam.PROFESIONAL.getName(), SolicitudeParam.PROFESIONAL.getType()),
						new SqlParameter(SolicitudeParam.LOGIN_CLI.getName(), SolicitudeParam.LOGIN_CLI.getType()),
						new SqlParameter(SolicitudeParam.LOGIN_PRO.getName(), SolicitudeParam.LOGIN_PRO.getType()) })
				.returningResultSet(Constants.RESULT.getName(), new RowMapper<Solicitude>() {

					@Override
					public Solicitude mapRow(ResultSet rs, int rowNum) throws SQLException {
						Solicitude solicitude = new Solicitude();
						solicitude.setNumber(rs.getInt("NUMERO"));
						solicitude.setAddress(rs.getString("DIRECCION"));
						solicitude.setLatitude(rs.getString("LATITUDE"));
						solicitude.setLongitude(rs.getString("LONGITUDE"));
						solicitude.setType(rs.getString("TIPO"));
						solicitude.setComentary(rs.getString("COMENTARIO"));
						solicitude.setProposedValue(rs.getDouble("VALOR_PROPUESTO"));
						solicitude.setAceptedValue(rs.getDouble("VALOR_ACEPTADO"));
						solicitude.setRegistrationDate(rs.getDate("FECHA_REGISTRO"));
						solicitude.setModificationDate(rs.getDate("FECHA_MODIFICACION"));
						solicitude.setStatus(rs.getString("ESTADO"));
						solicitude.setClient(rs.getInt("CLIENTE"));
						solicitude.setProfesional(rs.getInt("PROFESIONAL"));
						return solicitude;
					}

				});

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(GeneralParam.S_DATE.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.OPERATION.getName(), operation);
		mapSqlParameterSource.addValue(GeneralParam.MODE.getName(), mode);
		mapSqlParameterSource.addValue(SolicitudeParam.NUMBER.getName(), number);
		mapSqlParameterSource.addValue(SolicitudeParam.CLIENT.getName(), client);
		mapSqlParameterSource.addValue(SolicitudeParam.PROFESIONAL.getName(), profesional);
		mapSqlParameterSource.addValue(SolicitudeParam.LOGIN_CLI.getName(), login_cli);
		mapSqlParameterSource.addValue(SolicitudeParam.LOGIN_PRO.getName(), login_pro);

		Map<String, Object> results = simpleJdbcCall.execute(mapSqlParameterSource);
		List<Solicitude> solicitude = (List<Solicitude>) results.get(Constants.RESULT.getName());

		return solicitude != null && !solicitude.isEmpty() ? (T) solicitude : null;
	}

	private int execAdmSolicitude(String operation, String mode, Solicitude solicitude) throws BusinessException {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName(Constants.SP_ADM_SOLICITUDE.getName())
				.declareParameters(new SqlParameter[] {
						new SqlParameter(GeneralParam.S_DATE.getName(), GeneralParam.S_DATE.getType()),
						new SqlParameter(GeneralParam.OPERATION.getName(), GeneralParam.OPERATION.getType()),
						new SqlParameter(GeneralParam.MODE.getName(), GeneralParam.MODE.getType()),
						new SqlParameter(SolicitudeParam.NUMBER.getName(), SolicitudeParam.NUMBER.getType()),
						new SqlParameter(SolicitudeParam.CLIENT.getName(), SolicitudeParam.CLIENT.getType()),
						new SqlParameter(SolicitudeParam.PROFESIONAL.getName(), SolicitudeParam.PROFESIONAL.getType()),
						new SqlParameter(GeneralParam.TYPE.getName(), GeneralParam.TYPE.getType()),
						new SqlParameter(SolicitudeParam.LATITUDE.getName(), SolicitudeParam.LATITUDE.getType()),
						new SqlParameter(SolicitudeParam.LONGITUDE.getName(), SolicitudeParam.LONGITUDE.getType()),
						new SqlParameter(SolicitudeParam.PROPUSED_VALUE.getName(),
								SolicitudeParam.PROPUSED_VALUE.getType()),
						new SqlParameter(SolicitudeParam.ACEPTED_VALUE.getName(),
								SolicitudeParam.ACEPTED_VALUE.getType()),
						new SqlParameter(SolicitudeParam.ADDRESS.getName(), SolicitudeParam.ADDRESS.getType()),
						new SqlParameter(SolicitudeParam.DESCRIPTION.getName(), SolicitudeParam.DESCRIPTION.getType()),
						new SqlParameter(GeneralParam.COMENTARY.getName(), GeneralParam.COMENTARY.getType()),
						new SqlParameter(GeneralParam.STATUS.getName(), GeneralParam.STATUS.getType()),
						new SqlOutParameter(GeneralParam.ROWCOUNT.getName(), GeneralParam.ROWCOUNT.getType()),
						new SqlOutParameter(GeneralParam.ERROR_MSG.getName(), GeneralParam.ERROR_MSG.getType()) });

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(GeneralParam.S_DATE.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.OPERATION.getName(), operation);
		mapSqlParameterSource.addValue(GeneralParam.MODE.getName(), mode);
		mapSqlParameterSource.addValue(SolicitudeParam.NUMBER.getName(), solicitude.getNumber());
		mapSqlParameterSource.addValue(SolicitudeParam.CLIENT.getName(), solicitude.getClient());
		mapSqlParameterSource.addValue(SolicitudeParam.PROFESIONAL.getName(), solicitude.getProfesional());
		mapSqlParameterSource.addValue(GeneralParam.TYPE.getName(), solicitude.getType());
		mapSqlParameterSource.addValue(SolicitudeParam.LATITUDE.getName(), solicitude.getLatitude());
		mapSqlParameterSource.addValue(SolicitudeParam.LONGITUDE.getName(), solicitude.getLongitude());
		mapSqlParameterSource.addValue(SolicitudeParam.PROPUSED_VALUE.getName(), solicitude.getProposedValue());
		mapSqlParameterSource.addValue(SolicitudeParam.ACEPTED_VALUE.getName(), solicitude.getAceptedValue());
		mapSqlParameterSource.addValue(SolicitudeParam.ADDRESS.getName(), solicitude.getAddress());
		mapSqlParameterSource.addValue(SolicitudeParam.DESCRIPTION.getName(), solicitude.getDescription());
		mapSqlParameterSource.addValue(GeneralParam.COMENTARY.getName(), solicitude.getComentary());
		mapSqlParameterSource.addValue(GeneralParam.STATUS.getName(), solicitude.getStatus());

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
