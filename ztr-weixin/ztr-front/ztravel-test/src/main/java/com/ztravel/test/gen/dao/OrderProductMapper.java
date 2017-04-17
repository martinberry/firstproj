package com.ztravel.test.gen.dao;

import com.ztravel.test.gen.po.OrderProduct;
import com.ztravel.test.gen.po.OrderProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrderProductMapper {
    int countByExample(OrderProductExample example);

    int deleteByExample(OrderProductExample example);

    int deleteByPrimaryKey(String snapshotId);

    int insert(OrderProduct record);

    int insertSelective(OrderProduct record);

    List<OrderProduct> selectByExampleWithRowbounds(OrderProductExample example, RowBounds rowBounds);

    List<OrderProduct> selectByExample(OrderProductExample example);

    OrderProduct selectByPrimaryKey(String snapshotId);

    int updateByExampleSelective(@Param("record") OrderProduct record, @Param("example") OrderProductExample example);

    int updateByExample(@Param("record") OrderProduct record, @Param("example") OrderProductExample example);

    int updateByPrimaryKeySelective(OrderProduct record);

    int updateByPrimaryKey(OrderProduct record);
}