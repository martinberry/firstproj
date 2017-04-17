package com.ztravel.paygate.core.dao.gen;

import com.ztravel.paygate.core.po.gen.AlipayRefund;
import com.ztravel.paygate.core.po.gen.AlipayRefundExample;
import java.sql.SQLException;
import java.util.List;

public interface AlipayRefundDAO {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(AlipayRefundExample example) throws SQLException;

    /**
     * 根据条件删除记录
     */
    int deleteByExample(AlipayRefundExample example) throws SQLException;

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String alipayRefundId) throws SQLException;

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    void insert(AlipayRefund record) throws SQLException;

    /**
     * 保存属性不为空的记录
     */
    void insertSelective(AlipayRefund record) throws SQLException;

    /**
     * 根据条件查询记录集
     */
    List<AlipayRefund> selectByExample(AlipayRefundExample example) throws SQLException;

    /**
     * 根据主键查询记录
     */
    AlipayRefund selectByPrimaryKey(String alipayRefundId) throws SQLException;

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(AlipayRefund record, AlipayRefundExample example) throws SQLException;

    /**
     * 根据条件更新记录
     */
    int updateByExample(AlipayRefund record, AlipayRefundExample example) throws SQLException;

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(AlipayRefund record) throws SQLException;

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AlipayRefund record) throws SQLException;
}