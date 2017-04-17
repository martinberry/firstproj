package com.ztravel.test.gen.dao;

import com.ztravel.test.gen.po.OrderContactor;
import com.ztravel.test.gen.po.OrderContactorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrderContactorMapper {
    int countByExample(OrderContactorExample example);

    int deleteByExample(OrderContactorExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderContactor record);

    int insertSelective(OrderContactor record);

    List<OrderContactor> selectByExampleWithRowbounds(OrderContactorExample example, RowBounds rowBounds);

    List<OrderContactor> selectByExample(OrderContactorExample example);

    OrderContactor selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderContactor record, @Param("example") OrderContactorExample example);

    int updateByExample(@Param("record") OrderContactor record, @Param("example") OrderContactorExample example);

    int updateByPrimaryKeySelective(OrderContactor record);

    int updateByPrimaryKey(OrderContactor record);
}