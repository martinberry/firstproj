package com.ztravel.paygate.core.dao;

import com.ibatis.sqlmap.client.SqlMapClient;

public interface Dao {
	public void setSqlMapClient(SqlMapClient sqlMapClient);

	public SqlMapClient getSqlMapClient();
}
