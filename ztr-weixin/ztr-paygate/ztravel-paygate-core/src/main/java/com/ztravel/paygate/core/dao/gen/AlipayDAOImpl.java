package com.ztravel.paygate.core.dao.gen;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ztravel.paygate.core.po.gen.Alipay;
import com.ztravel.paygate.core.po.gen.AlipayExample;
import java.sql.SQLException;
import java.util.List;

public class AlipayDAOImpl implements AlipayDAO {
    private SqlMapClient sqlMapClient;

    public AlipayDAOImpl() {
        super();
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public int countByExample(AlipayExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("paygate_alipay.countByExample", example);
        return count;
    }

    public int deleteByExample(AlipayExample example) throws SQLException {
        int rows = sqlMapClient.delete("paygate_alipay.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String alipayId) throws SQLException {
        Alipay key = new Alipay();
        key.setAlipayId(alipayId);
        int rows = sqlMapClient.delete("paygate_alipay.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(Alipay record) throws SQLException {
        sqlMapClient.insert("paygate_alipay.insert", record);
    }

    public void insertSelective(Alipay record) throws SQLException {
        sqlMapClient.insert("paygate_alipay.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<Alipay> selectByExample(AlipayExample example) throws SQLException {
        List<Alipay> list = sqlMapClient.queryForList("paygate_alipay.selectByExample", example);
        return list;
    }

    public Alipay selectByPrimaryKey(String alipayId) throws SQLException {
        Alipay key = new Alipay();
        key.setAlipayId(alipayId);
        Alipay record = (Alipay) sqlMapClient.queryForObject("paygate_alipay.selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(Alipay record, AlipayExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("paygate_alipay.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(Alipay record, AlipayExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("paygate_alipay.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(Alipay record) throws SQLException {
        int rows = sqlMapClient.update("paygate_alipay.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(Alipay record) throws SQLException {
        int rows = sqlMapClient.update("paygate_alipay.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends AlipayExample {
        private Object record;

        public UpdateByExampleParms(Object record, AlipayExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}