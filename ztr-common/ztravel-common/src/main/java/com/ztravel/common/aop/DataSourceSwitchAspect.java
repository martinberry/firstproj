/**
 *
 */
package com.ztravel.common.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.travelzen.framework.dao.rdbms.dataSource.DataSourceContextHolder;
import com.travelzen.framework.dao.rdbms.dataSource.DataSourceType;

/**
 * @author zuoning.shen
 *
 */
@Aspect
public class DataSourceSwitchAspect {

    @Before("@annotation(com.travelzen.framework.dao.annotation.ReadOnly)")
    public void setSlaveDataSource() {
            DataSourceContextHolder.clearDataSourceType();
            DataSourceContextHolder.setDataSourceType(DataSourceType.offline);
    }

    @After("@annotation(com.travelzen.framework.dao.annotation.ReadOnly)")
    public void setMasterDataSource() {
            DataSourceContextHolder.clearDataSourceType();
            DataSourceContextHolder.setDataSourceType(DataSourceType.online);
    }

}
