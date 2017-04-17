package com.ztravel.paygate.core.dao.gen;

import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.po.gen.PaygateClientExample;
import java.sql.SQLException;
import java.util.List;

public interface PaygateClientDAO {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(PaygateClientExample example) throws SQLException;

    /**
     * 根据条件删除记录
     */
    int deleteByExample(PaygateClientExample example) throws SQLException;

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String paygateClientId) throws SQLException;

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    void insert(PaygateClient record) throws SQLException;

    /**
     * 保存属性不为空的记录
     */
    void insertSelective(PaygateClient record) throws SQLException;

    /**
     * 根据条件查询记录集
     */
    List<PaygateClient> selectByExample(PaygateClientExample example) throws SQLException;

    /**
     * 根据主键查询记录
     */
    PaygateClient selectByPrimaryKey(String paygateClientId) throws SQLException;

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(PaygateClient record, PaygateClientExample example) throws SQLException;

    /**
     * 根据条件更新记录
     */
    int updateByExample(PaygateClient record, PaygateClientExample example) throws SQLException;

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(PaygateClient record) throws SQLException;

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(PaygateClient record) throws SQLException;
}