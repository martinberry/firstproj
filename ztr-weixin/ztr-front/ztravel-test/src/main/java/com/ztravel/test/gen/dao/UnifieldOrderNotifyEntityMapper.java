package com.ztravel.test.gen.dao;

import com.ztravel.test.gen.po.UnifieldOrderNotifyEntity;
import com.ztravel.test.gen.po.UnifieldOrderNotifyEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnifieldOrderNotifyEntityMapper {
    int countByExample(UnifieldOrderNotifyEntityExample example);

    int deleteByExample(UnifieldOrderNotifyEntityExample example);

    int deleteByPrimaryKey(String transactionId);

    int insert(UnifieldOrderNotifyEntity record);

    int insertSelective(UnifieldOrderNotifyEntity record);

    List<UnifieldOrderNotifyEntity> selectByExampleWithRowbounds(UnifieldOrderNotifyEntityExample example, RowBounds rowBounds);

    List<UnifieldOrderNotifyEntity> selectByExample(UnifieldOrderNotifyEntityExample example);

    UnifieldOrderNotifyEntity selectByPrimaryKey(String transactionId);

    int updateByExampleSelective(@Param("record") UnifieldOrderNotifyEntity record, @Param("example") UnifieldOrderNotifyEntityExample example);

    int updateByExample(@Param("record") UnifieldOrderNotifyEntity record, @Param("example") UnifieldOrderNotifyEntityExample example);

    int updateByPrimaryKeySelective(UnifieldOrderNotifyEntity record);

    int updateByPrimaryKey(UnifieldOrderNotifyEntity record);
}