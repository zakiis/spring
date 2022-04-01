package com.zakiis.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mybatis")
public class MybatisPrintSqlProperties {

	private boolean printSql;

	public boolean isPrintSql() {
		return printSql;
	}

	public void setPrintSql(boolean printSql) {
		this.printSql = printSql;
	}
}
