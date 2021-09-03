package cpm.web.service.execute;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;

public interface ExecuteQueryService {
    <T> T executeQueryFunction(String sql, Map hmParram, T clazz);

    <T> T executeQuery(String sql,Map hmParram, T clazz);

    <T> T executeQueryAll(String sql,Map hmParram, T clazz);

    boolean executeUpdate(String sql, SqlParameterSource hmParram);

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
