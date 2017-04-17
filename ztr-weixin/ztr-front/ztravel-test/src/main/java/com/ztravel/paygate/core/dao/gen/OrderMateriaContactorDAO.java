package com.ztravel.paygate.core.dao.gen;

import com.ztravel.paygate.core.po.gen.OrderMateriaContactor;
import com.ztravel.paygate.core.po.gen.OrderMateriaContactorExample;
import java.sql.SQLException;
import java.util.List;

public interface OrderMateriaContactorDAO {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(OrderMateriaContactorExample example) throws SQLException;

    /**
     * 根据条件删除记录
     */
    int deleteByExample(OrderMateriaContactorExample example) throws SQLException;

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    void insert(OrderMateriaContactor record) throws SQLException;

    /**
     * 保存属性不为空的记录
     */
    void insertSelective(OrderMateriaContactor record) throws SQLException;

    /**
     * 根据条件查询记录集
     */
    List<OrderMateriaContactor> selectByExample(OrderMateriaContactorExample example) throws SQLException;

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(OrderMateriaContactor record, OrderMateriaContactorExample example) throws SQLException;

    /**
     * 根据条件更新记录
     */
    int updateByExample(OrderMateriaContactor record, OrderMateriaContactorExample example) throws SQLException;
}