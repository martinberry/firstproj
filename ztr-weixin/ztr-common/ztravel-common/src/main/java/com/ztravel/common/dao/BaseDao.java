/**
 *
 */
package com.ztravel.common.dao;

import java.util.List;
import java.util.Map;

import com.travelzen.framework.core.wrapper.Pagination;

/**
 * @author zuoning.shen
 *
 */
public interface BaseDao<T> {

    void insert(T obj);

    void update(T obj);

    void deleteById(String id);

    T selectById(String id);

    T selectOne(Map params);

    List<T> select(Map params);

    Pagination<T> select(Map params, Pagination<T> pagination);

    Integer count(Map params);
}
