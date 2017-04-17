package com.ztravel.paygate.core.dao.gen;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ztravel.paygate.core.po.gen.OrderMateriaContactor;
import com.ztravel.paygate.core.po.gen.OrderMateriaContactorExample;
import java.sql.SQLException;
import java.util.List;

public class OrderMateriaContactorDAOImpl implements OrderMateriaContactorDAO {
    private SqlMapClient sqlMapClient;

    public OrderMateriaContactorDAOImpl() {
        super();
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public int countByExample(OrderMateriaContactorExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("ztorder_t_order_material_contactor.countByExample", example);
        return count;
    }

    public int deleteByExample(OrderMateriaContactorExample example) throws SQLException {
        int rows = sqlMapClient.delete("ztorder_t_order_material_contactor.deleteByExample", example);
        return rows;
    }

    public void insert(OrderMateriaContactor record) throws SQLException {
        sqlMapClient.insert("ztorder_t_order_material_contactor.insert", record);
    }

    public void insertSelective(OrderMateriaContactor record) throws SQLException {
        sqlMapClient.insert("ztorder_t_order_material_contactor.insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<OrderMateriaContactor> selectByExample(OrderMateriaContactorExample example) throws SQLException {
        List<OrderMateriaContactor> list = sqlMapClient.queryForList("ztorder_t_order_material_contactor.selectByExample", example);
        return list;
    }

    public int updateByExampleSelective(OrderMateriaContactor record, OrderMateriaContactorExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("ztorder_t_order_material_contactor.updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(OrderMateriaContactor record, OrderMateriaContactorExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("ztorder_t_order_material_contactor.updateByExample", parms);
        return rows;
    }

    protected static class UpdateByExampleParms extends OrderMateriaContactorExample {
        private Object record;

        public UpdateByExampleParms(Object record, OrderMateriaContactorExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}