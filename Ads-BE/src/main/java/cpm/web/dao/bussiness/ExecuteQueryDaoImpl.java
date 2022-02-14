package cpm.web.dao.bussiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExecuteQueryDaoImpl implements ExecuteQueryDao {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static NamedParameterJdbcTemplate JdbcTemplateQuery;

    @Autowired
    private JdbcTemplate jdbcTemplateExecute;

    private SimpleJdbcCall jdbcCallStore;

    public ExecuteQueryDaoImpl(NamedParameterJdbcTemplate template) {
        this.JdbcTemplateQuery = template;
    }

    @Override
    public <T> T executeQueryFunction(String sql, Map requestParram, T clazz) {
        return (T) this.queryFunctionData(sql, requestParram, clazz);
    }

    @Override
    public <T> T executeQuery(String sql, Map parram, T clazz) {
        return (T) this.queryData(sql, parram, clazz);
    }

    @Override
    public <T> T executeQueryAll(String sql, Map parram, T clazz) {
        return (T) this.queryAllData(sql, parram, clazz);
    }

    @Override
    @Transactional
    public boolean executeUpdate(String sql, SqlParameterSource parram) {
        return this.updateData(sql, parram);
    }

//    @Override
//    public void insertData(Class<?> data) {
//
//    }
//
//    @Override
//    public void updateData(Class<?> data) {
//
//    }
//
//    @Override
//    public void bulkUpdateData(Class<?> data) {
//
//    }
//
//    @Override
//    public void delete(Class<?> data) {
//
//    }
//
//    @Override
//    public void bulkDelete(Class<?> data) {
//
//    }

    private <T> T queryFunctionData(String sql, Map<String, String> requestData, T clazz) {

        StringBuffer strParramFun = new StringBuffer();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        int index = 0;
        for (HashMap.Entry<String, String> entry : requestData.entrySet()) {
            strParramFun = index > 0 ? strParramFun.append(",") : strParramFun;
            strParramFun.append(String.format(":%s", entry.getKey()));
            sqlParameterSource.addValue(entry.getKey(), entry.getValue());
            index++;
        }
        final String sqlLast = String.format("SELECT * FROM %s(%s)", sql, strParramFun.toString());
        try {
            LOGGER.info("Start queryFunctionData: " + sqlLast);
            SqlParameterSource parameters = sqlParameterSource;

            List<?> result = JdbcTemplateQuery.query(sqlLast, parameters, new BeanPropertyRowMapper<>(clazz.getClass()));
            LOGGER.info("End queryFunctionData: " + sql);
            return (T) result;
        } catch (DataAccessException e) {
            LOGGER.error("Error : " + e.toString());
            throw new RuntimeException(e);
        }
    }

    private <T> T queryAllData(String viewSql, Map<String, String> requestData, T clazz) {
        StringBuffer strParramFun = new StringBuffer();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        int index = 0;
        for (HashMap.Entry<String, String> entry : requestData.entrySet()) {
            strParramFun = index > 0 ? strParramFun.append(",") : strParramFun;
            strParramFun.append(String.format(":%s", entry.getKey()));
            sqlParameterSource.addValue(entry.getKey(), entry.getValue());
            index++;
        }
        final String sqlLast = String.format("SELECT * FROM %s ", viewSql, strParramFun.toString());
        try {
            LOGGER.info("Start queryFunctionData: " + sqlLast);
            SqlParameterSource parameters = sqlParameterSource;

            List<?> result = JdbcTemplateQuery.query(sqlLast, parameters, new BeanPropertyRowMapper<>(clazz.getClass()));
            LOGGER.info("End queryFunctionData: " + sqlLast);
            return (T) result;
        } catch (DataAccessException e) {
            LOGGER.error("Error : " + e.toString());
            throw new RuntimeException(e);
        }
    }

    private <T> T queryData(String viewSql, Map<String, String> requestData, T clazz) {
        StringBuffer strParramFun = new StringBuffer();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        int index = 0;
        for (HashMap.Entry<String, String> entry : requestData.entrySet()) {
            strParramFun = index > 0 ? strParramFun.append(",") : strParramFun;
            strParramFun.append(String.format(":%s", entry.getKey()));
            sqlParameterSource.addValue(entry.getKey(), entry.getValue());
            index++;
        }
        final String sqlLast = String.format("SELECT * FROM %s Where user_id = %s", viewSql, strParramFun.toString());
        try {
            LOGGER.info("Start queryFunctionData: " + sqlLast);
            SqlParameterSource parameters = sqlParameterSource;

            List<?> result = JdbcTemplateQuery.query(sqlLast, parameters, new BeanPropertyRowMapper<>(clazz.getClass()));
            LOGGER.info("End queryFunctionData: " + sqlLast);
            return (T) result;
        } catch (DataAccessException e) {
            LOGGER.error("Error : " + e.toString());
            throw new RuntimeException(e);
        }
    }

    private boolean updateData(String sqlStore, SqlParameterSource requestParam) {

        LOGGER.info("Start queryFunctionData: " + sqlStore);
        try {
            jdbcTemplateExecute.setResultsMapCaseInsensitive(true);

            jdbcCallStore = new SimpleJdbcCall(jdbcTemplateExecute)
                    .withFunctionName(sqlStore);

            Integer result = jdbcCallStore.executeFunction(Integer.class, requestParam);
            LOGGER.info("End queryFunctionData: " + sqlStore);

            return (result == 1 ? true : false);
        } catch (DataAccessException e) {
            LOGGER.error("Error : " + e.toString());
            throw new RuntimeException(e);
        }
    }
}
