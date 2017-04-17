package com.ztravel.test.gen.dao;

import com.ztravel.test.gen.po.OrderPassenger;
import com.ztravel.test.gen.po.OrderPassengerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrderPassengerMapper {
    int countByExample(OrderPassengerExample example);

    int deleteByExample(OrderPassengerExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderPassenger record);

    int insertSelective(OrderPassenger record);

    List<OrderPassenger> selectByExampleWithRowbounds(OrderPassengerExample example, RowBounds rowBounds);

    List<OrderPassenger> selectByExample(OrderPassengerExample example);

    OrderPassenger selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderPassenger record, @Param("example") OrderPassengerExample example);

    int updateByExample(@Param("record") OrderPassenger record, @Param("example") OrderPassengerExample example);

    int updateByPrimaryKeySelective(OrderPassenger record);

    int updateByPrimaryKey(OrderPassenger record);
}