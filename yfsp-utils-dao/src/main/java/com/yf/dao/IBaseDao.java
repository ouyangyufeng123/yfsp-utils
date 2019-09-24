package com.yf.dao;

import com.yf.entity.BaseDomain;
import com.yf.entity.DomainPage;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
public interface IBaseDao {
    <T extends BaseDomain> T refresh(T var1);

    <T extends BaseDomain> BaseDomain save(T var1);

    <T extends BaseDomain> BaseDomain merge(T var1);

    <T extends BaseDomain> void saveNew(T var1);

    <T extends BaseDomain> void batchSave(List<T> var1);

    <T extends BaseDomain> void batchUpdate(List<T> var1);

    <T extends BaseDomain> void removeEntity(T var1);

    <T extends BaseDomain> BaseDomain getEntityById(Class<T> var1, Object var2);

    <T extends BaseDomain> BaseDomain getEntityByField(Class<T> var1, String var2, Object var3);

    <T extends BaseDomain> List<T> getEntitiesByField(Class<T> var1, String var2, Object var3);

    <T extends BaseDomain> List<T> getEntitiesByFieldList(Class<T> var1, Map<String, Object> var2);

    <T extends BaseDomain> List<T> getAllEntities(Class<T> var1);

    <T extends BaseDomain> DomainPage getEntitiesByParams(Class<T> var1, Map<String, Object> var2, String var3, String var4, long var5, long var7);

    <T extends BaseDomain> DomainPage getEntitiesByPage(Class<T> var1, String var2, Object var3, long var4, long var6);

    <T extends BaseDomain> DomainPage findEntitiesByPage(Class<T> var1, String var2, String var3, long var4, long var6);

    <T extends BaseDomain> DomainPage getEntitiesPagesByFieldList(Class<T> var1, Map<String, Object> var2, long var3, long var5);

    <T extends BaseDomain> DomainPage getEntitiesPagesByFieldList(Class<T> var1, Map<String, Object> var2, String var3, String var4, long var5, long var7);

    Long countByHql(String var1, Object... var2);

    List getListBySQL(String var1, Object[] var2, Object[] var3);

    List getListBySQL(String var1, Map<String, Object> var2);

    List getListBySQL(String var1, String var2, String var3, Object[] var4, Object[] var5);

    List getListBySQL(String var1, String var2, String var3, Map<String, Object> var4);

    DomainPage getDomainPageBySQL(String var1, int var2, int var3, Object[] var4, Object[] var5);

    DomainPage getDomainPageBySQL(String var1, int var2, int var3, Map<String, Object> var4);

    DomainPage getDomainPageByJoinSql(String var1, int var2, int var3, Object[] var4);

    DomainPage getDomainPageBySQL(String var1, String var2, String var3, int var4, int var5, Object[] var6, Object[] var7);

    DomainPage getDomainPageBySQL(String var1, String var2, String var3, int var4, int var5, Map<String, Object> var6);

    long getTotalCount(String var1, String var2, String var3, Object[] var4, Object[] var5);

    long getTotalCount(String var1, String var2, String var3, Map<String, Object> var4);

    long getTotalCount(Class var1, String var2, String var3, Map<String, Object> var4);
}

