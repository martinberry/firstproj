/**
 *
 */
package com.ztravel.common.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.travelzen.framework.core.wrapper.Pagination;

/**
 * @author zuoning.shen
 * @param <T>
 *
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

    public static final String INSERT = ".insert";
    public static final String UPDATE = ".update";
    public static final String DELETE_BY_ID = ".deleteById";
    public static final String SELECT_BY_ID = ".selectById";
    public static final String SELECT_ONE = ".selectOne";
    public static final String SELECT = ".select";
    public static final String COUNT = ".count";

    /**
     * nameSpace规则：clazz.getName()
     */
    protected String nameSpace;

    @Resource(name="sqlSession")
    protected SqlSession session;

    public BaseDaoImpl(){
        Class clazz = (Class)((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        //Exmaple: com.ztravel.rbac.po.UserMapper
        this.nameSpace = clazz.getName() + "Mapper";
    }
    @Override
    public void insert(T obj) {
        session.insert(nameSpace + INSERT, obj);
    }

    @Override
    public void update(T obj) {
        session.update(nameSpace + UPDATE, obj);
    }

    @Override
    public void deleteById(String id) {
        session.delete(nameSpace + DELETE_BY_ID, id);
    }

    @Override
    public T selectById(String id) {
        return session.selectOne(nameSpace + SELECT_BY_ID, id);
    }

    @Override
    public T selectOne(Map params) {
        return session.selectOne(nameSpace + SELECT_ONE, params);
    }

    @Override
    public List<T> select(Map params) {
        return session.selectList(nameSpace + SELECT, params);
    }

    @Override
    public Pagination<T> select(Map params, Pagination<T> pagination) {
        params.put("offset", pagination.getStart());
        params.put("limit", pagination.getPageSize());
        pagination.setTotalItemCount(count(params));
        Collection<T> resultList = session.selectList(nameSpace + SELECT, params);
        pagination.setData(resultList);
        return pagination;
    }

    public Integer count(Map params) {
        return session.selectOne(nameSpace + COUNT, params);
    }

}
