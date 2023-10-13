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

import com.dioblazer.fast.medical.model.Client;
import com.dioblazer.fast.medical.model.ClientResponse;
import com.dioblazer.fast.medical.repository.IClientRepository;
import com.dioblazer.fast.medical.repository.param.GeneralParam;
import com.dioblazer.fast.medical.repository.param.ClientParam;
import com.dioblazer.fast.medical.utils.BusinessException;
import com.dioblazer.fast.medical.utils.Constants;

@Repository
public class ClientRepositoryImpl implements IClientRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public ClientResponse findAll() {
		ClientResponse resp = new ClientResponse();
		resp.setClient(execQueryClient("S", "0", null, null));
		if (resp.getClient() != null) {
			resp.setSuccess(true);
		} else {
			resp = new ClientResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public ClientResponse findActives() {
		ClientResponse resp = new ClientResponse();
		resp.setClient(execQueryClient("S", "1", null, null));
		if (resp.getClient() != null) {
			resp.setSuccess(true);
		} else {
			resp = new ClientResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public ClientResponse clientByLogin(String login) {
		ClientResponse resp = new ClientResponse();
		resp.setClient(execQueryClient("Q", "0", login, null));
		if (resp.getClient() != null) {
			resp.setSuccess(true);
		} else {
			resp = new ClientResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public ClientResponse clientByEmail(String email) {
		ClientResponse resp = new ClientResponse();
		resp.setClient(execQueryClient("Q", "1", null, email));
		if (resp.getClient() != null) {
			resp.setSuccess(true);
		} else {
			resp = new ClientResponse();
			resp.setSuccess(false);
			resp.setMessString(Constants.NO_REGISTERS.getName());
		}
		return resp;
	}

	@Override
	public int save(Client client) throws BusinessException {
		return execAdmClient("I", null, client, null);
	}

	@Override
	public int updateByLogin(Client client) throws BusinessException {
		return execAdmClient("U", "0", client, null);
	}

	@Override
	public int deleteByLogin(String login) throws BusinessException {
		Client client = new Client();
		client.setLogin(login);
		return execAdmClient("D", null, client, null);
	}

	@Override
	public int updateDocumentation(String loginAdm, Client client) throws BusinessException {
		return execAdmClient("U", "1", client, loginAdm);
	}

	@Override
	public int updateStatus(String loginAdm, Client client) throws BusinessException {
		return execAdmClient("U", "2", client, loginAdm);
	}

	private <T> T execQueryClient(String operation, String mode, String login, String email) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName(Constants.SP_CONS_CLIENT.getName())
				.declareParameters(new SqlParameter[] {
						new SqlParameter(GeneralParam.S_DATE.getName(), GeneralParam.S_DATE.getType()),
						new SqlParameter(GeneralParam.OPERATION.getName(), GeneralParam.OPERATION.getType()),
						new SqlParameter(GeneralParam.MODE.getName(), GeneralParam.MODE.getType()),
						new SqlParameter(GeneralParam.LOGIN.getName(), GeneralParam.LOGIN.getType()),
						new SqlParameter(GeneralParam.EMAIL.getName(), GeneralParam.EMAIL.getType()) })
				.returningResultSet("result", new RowMapper<Client>() {

					@Override
					public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
						Client client = new Client();
						client.setId(rs.getInt("ID"));
						client.setDisease(rs.getString("ENFERMEDAD"));
						client.setDocumentation(rs.getString("DOCUMENTACION"));
						client.setComentary(rs.getString("COMENTARIO"));
						client.setModificationDate(rs.getDate("FECHA_MODIFICACION"));
						client.setStatus(rs.getString("ESTADO"));
						if (login == null) {
							client.setLogin(rs.getString("LOGIN"));
						}
						return client;
					}

				});

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

		mapSqlParameterSource.addValue(GeneralParam.S_DATE.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.OPERATION.getName(), operation);
		mapSqlParameterSource.addValue(GeneralParam.MODE.getName(), mode);
		mapSqlParameterSource.addValue(GeneralParam.LOGIN.getName(), login);
		mapSqlParameterSource.addValue(GeneralParam.EMAIL.getName(), email);

		Map<String, Object> results = simpleJdbcCall.execute(mapSqlParameterSource);
		List<Client> client = (List<Client>) results.get("result");

		return client != null && !client.isEmpty() ? (T) client : null;
	}

	private int execAdmClient(String operation, String mode, Client client, String loginAdm)
			throws BusinessException {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName(Constants.SP_ADM_CLIENT.getName())
				.declareParameters(new SqlParameter[] {
						new SqlParameter(GeneralParam.S_LOGIN.getName(), GeneralParam.S_LOGIN.getType()),
						new SqlParameter(GeneralParam.S_DATE.getName(), GeneralParam.S_DATE.getType()),
						new SqlParameter(GeneralParam.OPERATION.getName(), GeneralParam.OPERATION.getType()),
						new SqlParameter(GeneralParam.MODE.getName(), GeneralParam.MODE.getType()),
						new SqlParameter(GeneralParam.LOGIN.getName(), GeneralParam.LOGIN.getType()),
						new SqlParameter(ClientParam.DISEASE.getName(), ClientParam.DISEASE.getType()),
						new SqlParameter(GeneralParam.DOCUMENTATION.getName(), GeneralParam.DOCUMENTATION.getType()),
						new SqlParameter(GeneralParam.COMENTARY.getName(), GeneralParam.COMENTARY.getType()),
						new SqlParameter(GeneralParam.STATUS.getName(), GeneralParam.STATUS.getType()),
						new SqlOutParameter(GeneralParam.ROWCOUNT.getName(), GeneralParam.ROWCOUNT.getType()),
						new SqlOutParameter(GeneralParam.ERROR_MSG.getName(), GeneralParam.ERROR_MSG.getType()) });

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(GeneralParam.S_LOGIN.getName(), loginAdm);
		mapSqlParameterSource.addValue(GeneralParam.S_DATE.getName(), "");
		mapSqlParameterSource.addValue(GeneralParam.OPERATION.getName(), operation);
		mapSqlParameterSource.addValue(GeneralParam.MODE.getName(), mode);
		mapSqlParameterSource.addValue(GeneralParam.LOGIN.getName(), client.getLogin());
		mapSqlParameterSource.addValue(ClientParam.DISEASE.getName(), client.getDisease());
		mapSqlParameterSource.addValue(GeneralParam.DOCUMENTATION.getName(), client.getDocumentation());
		mapSqlParameterSource.addValue(GeneralParam.COMENTARY.getName(), client.getComentary());
		mapSqlParameterSource.addValue(GeneralParam.STATUS.getName(), client.getStatus());

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
