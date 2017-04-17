package com.ztravel.test.gen.dao;

import com.ztravel.test.gen.po.Operator;
import com.ztravel.test.gen.po.OperatorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OperatorMapper {
    int countByExample(OperatorExample example);

    int deleteByExample(OperatorExample example);

    int deleteByPrimaryKey(String userId);

    int insert(Operator record);

    int insertSelective(Operator record);

    List<Operator> selectByExampleWithRowbounds(OperatorExample example, RowBounds rowBounds);

    List<Operator> selectByExample(OperatorExample example);

    Operator selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") Operator record, @Param("example") OperatorExample example);

    int updateByExample(@Param("record") Operator record, @Param("example") OperatorExample example);

    int updateByPrimaryKeySelective(Operator record);

    int updateByPrimaryKey(Operator record);
}