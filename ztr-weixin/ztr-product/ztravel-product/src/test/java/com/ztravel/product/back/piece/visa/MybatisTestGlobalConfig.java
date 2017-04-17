package com.ztravel.product.back.piece.visa;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * mybatis 全局配置，用于加载应用下所有模块的mapper 和xml
 * @author liuzhuo
 *
 */
@Configuration
public class MybatisTestGlobalConfig {

	@Resource(name="ztravel-dataSource")
	DataSource ztravelDataSource;


	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(ztravelDataSource);

		PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		org.springframework.core.io.Resource[] mapperLocations = resourceResolver.getResources("classpath*:mybatis/sqlmap/**/*.xml");
		org.springframework.core.io.Resource configLocation = resourceResolver.getResource("classpath:mybatis/mybatis-configuration.xml") ;
		sessionFactory.setMapperLocations(mapperLocations);
		sessionFactory.setConfigLocation(configLocation);
		return sessionFactory.getObject();
	}

	@Bean(name="sqlSession")
    //@Scope("prototype")
    public SqlSessionTemplate sqlSessionTemplateBean() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean()) ;
        return sqlSessionTemplate;
    }

}
