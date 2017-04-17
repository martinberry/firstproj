package postgres;

import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class TestPG {
	public static void main(String[] arg) {

		com.ibatis.sqlmap.client.SqlMapClient sqlMap = null;
		try {
			java.io.Reader reader = com.ibatis.common.resources.Resources
					.getResourceAsReader("ibatortest/flat/SqlMapConfig.xml");
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
//			
//			Pv pv =new Pv();
//			pv.setId("123");
//			sqlMap.insert("mkt_pv.insert",pv);
			
			int i=0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
