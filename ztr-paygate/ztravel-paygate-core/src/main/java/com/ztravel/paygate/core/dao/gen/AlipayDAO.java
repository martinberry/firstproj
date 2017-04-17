package com.ztravel.paygate.core.dao.gen;

import com.ztravel.paygate.core.po.gen.Alipay;
import com.ztravel.paygate.core.po.gen.AlipayExample;
import java.sql.SQLException;
import java.util.List;

public interface AlipayDAO {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(AlipayExample example) throws SQLException;

    /**
     * 根据条件删除记录
     */
    int deleteByExample(AlipayExample example) throws SQLException;

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String alipayId) throws SQLException;

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    void insert(Alipay record) throws SQLException;

    /**
     * 保存属性不为空的记录
     */
    void insertSelective(Alipay record) throws SQLException;

    /**
     * 根据条件查询记录集
     */
    List<Alipay> selectByExample(AlipayExample example) throws SQLException;

    /**
     * 根据主键查询记录
     */
    Alipay selectByPrimaryKey(String alipayId) throws SQLException;

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(Alipay record, AlipayExample example) throws SQLException;

    /**
     * 根据条件更新记录
     */
    int updateByExample(Alipay record, AlipayExample example) throws SQLException;

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Alipay record) throws SQLException;

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Alipay record) throws SQLException;
}