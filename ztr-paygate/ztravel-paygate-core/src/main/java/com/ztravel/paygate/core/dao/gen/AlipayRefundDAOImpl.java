package com.ztravel.paygate.core.dao.gen;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ztravel.paygate.core.po.gen.AlipayRefund;
import com.ztravel.paygate.core.po.gen.AlipayRefundExample;
import java.sql.SQLException;
import java.util.List;

public class AlipayRefundDAOImpl implements AlipayRefundDAO {
    private SqlMapClient sqlMapClient;

    public AlipayRefundDAOImpl() {
        super();
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public int countByExample(AlipayRefundExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("paygate_alipay_refund.countByExample", example);
        return count;
    }

    public int deleteByExample(AlipayRefundExample example) throws SQLException {
        int rows = sqlMapClient.delete("paygate_alipay_refund.deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String alipayRefundId) throws SQLException {
        AlipayRefund key = new AlipayRefund();
        key.setAlipayRefundId(alipayRefundId);
        int rows = sqlMapClient.delete("paygate_alipay_refund.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(AlipayRefund record) throws SQLException {
        sqlMapClient.insert("paygate_alipay_refund.insert", record);
    }

    public void insertSelective(AlipayRefund record) throws SQLException {
        sqlMapClient.insert("paygate_alipay_refund.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<AlipayRefund> selectByExample(AlipayRefundExample example) throws SQLException {
        List<AlipayRefund> list = sqlMapClient.queryForList("paygate_alipay_refund.selectByExample", example);
        return list;
    }

    public AlipayRefund selectByPrimaryKey(String alipayRefundId) throws SQLException {
        AlipayRefund key = new AlipayRefund();
        key.setAlipayRefundId(alipayRefundId);
        AlipayRefund record = (AlipayRefund) sqlMapClient.queryForObject("paygate_alipay_refund.selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(AlipayRefund record, AlipayRefundExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("paygate_alipay_refund.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(AlipayRefund record, AlipayRefundExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("paygate_alipay_refund.updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(AlipayRefund record) throws SQLException {
        int rows = sqlMapClient.update("paygate_alipay_refund.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AlipayRefund record) throws SQLException {
        int rows = sqlMapClient.update("paygate_alipay_refund.updateByPrimaryKey", record);
        return rows;
    }

    protected static class UpdateByExampleParms extends AlipayRefundExample {
        private Object record;

        public UpdateByExampleParms(Object record, AlipayRefundExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}