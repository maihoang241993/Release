package cpm.web.dao.bussiness;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;

public interface ExecuteQueryDao {
    <T> T executeQueryFunction(String sql, Map parram, T clazz);

    <T> T executeQuery(String viewSql,Map parram, T clazz);

    <T> T executeQueryAll(String viewSql,Map parram, T clazz);

    boolean executeUpdate(String viewSql, SqlParameterSource parram);

//    void insertData(Class<?> data);
//
//    void updateData(Class<?> data);
//
//    void bulkUpdateData(Class<?> data);
//
//    void delete(Class<?> data);
//
//    void bulkDelete(Class<?> data);
}
