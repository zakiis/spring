package com.zakiis.boot.interceptor;

import java.beans.Statement;
import java.util.Collection;
import java.util.Map;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zakiis.security.CipherUtil;


@Intercepts({
	@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
	@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
	@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultSetHandler.class, CacheKey.class, BoundSql.class})
	
})
public class MybatisCipherInterceptor implements Interceptor {
	
	Logger log = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		if (target instanceof ResultSetHandler) { //result decrypt process
			Object result = invocation.proceed();
			if (result == null) {
				return result;
			}
			Collection<?> collection = (Collection<?>)result;
			collection.forEach(CipherUtil::decrypt);
			return result;
		} else { // parameter encrypt process
			Object param = invocation.getArgs()[1];
			if (param == null) {
				return invocation.proceed();
			}
			if (param instanceof Collection) {
				Collection<?> collection = (Collection<?>)param;
				collection.forEach(CipherUtil::encrypt);
			} else if (param instanceof Map) { //ParamMap mostly, but Map perhaps when using PageHelper
				Map<String, ?> paramMap = (Map<String, ?>)param;
				MappedStatement statement = (MappedStatement)invocation.getArgs()[0];
				if (SqlCommandType.UPDATE.equals(statement.getSqlCommandType())) {
					if (paramMap.containsKey("et")) {
						CipherUtil.encrypt(paramMap.get("et"));
					}
				} else if (SqlCommandType.SELECT.equals(statement.getSqlCommandType())) {
					if (paramMap.containsKey("param1")) { //Entity param need put in first element
						CipherUtil.encrypt(paramMap.get("param1"));
					}
				} else {
					log.warn("Sql command type {} not processed by cipher method", statement.getSqlCommandType());
				}
			} else {
				CipherUtil.encrypt(param);
			}
			return invocation.proceed();
		}
	}

}
