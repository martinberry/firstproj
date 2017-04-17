package com.ztravel.paygate.core.dao.gen;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.po.gen.PaygateClientExample;
import java.sql.SQLException;
import java.util.List;

public class PaygateClientDAOImpl implements PaygateClientDAO {
    private SqlMapClient sqlMapClient;

    public PaygateClientDAOImpl() {
        super();
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public int countByExample(PaygateClientExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("paygate_paygate_client.countByExample", example);
        return count;
    }

    public int deleteByExample(PaygateClientExample example) throws SQLException {
        int rows = sqlMapClient.delete("paygate_paygate_client.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String paygateClientId) throws SQLException {
        PaygateClient key = new PaygateClient();
        key.setPaygateClientId(paygateClientId);
        int rows = sqlMapClient.delete("paygate_paygate_client.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(PaygateClient record) throws SQLException {
        sqlMapClient.insert("paygate_paygate_client.insert", record);
    }

    public void insertSelective(PaygateClient record) throws SQLException {
        sqlMapClient.insert("paygate_paygate_client.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<PaygateClient> selectByExample(PaygateClientExample example) throws SQLException {
        List<PaygateClient> list = sqlMapClient.queryForList("paygate_paygate_client.selectByExample", example);
        return list;
    }

    public PaygateClient selectByPrimaryKey(String paygateClientId) throws SQLException {
        PaygateClient key = new PaygateClient();
        key.setPaygateClientId(paygateClientId);
        PaygateClient record = (PaygateClient) sqlMapClient.queryForObject("paygate_paygate_client.selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(PaygateClient record, PaygateClientExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("paygate_paygate_client.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(PaygateClient record, PaygateClientExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("paygate_paygate_client.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(PaygateClient record) throws SQLException {
        int rows = sqlMapClient.update("paygate_paygate_client.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(PaygateClient record) throws SQLException {
        int rows = sqlMapClient.update("paygate_paygate_client.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends PaygateClientExample {
        private Object record;

        public UpdateByExampleParms(Object record, PaygateClientExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}