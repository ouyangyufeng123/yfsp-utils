package com.yf.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.yf.entity.BaseDomain;
import com.yf.entity.DomainPage;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
@Transactional
public class JpaBaseDao implements IBaseDao {
    @PersistenceContext
    protected EntityManager em;
    private static final int BATCH_NUM = 100;

    public JpaBaseDao() {
    }

    @Override
    public <T extends BaseDomain> T refresh(T domain) {
        this.em.refresh(domain);
        return domain;
    }

    @Override
    public <T extends BaseDomain> BaseDomain save(T t) {
        if(t.getCreatedTime() == null) {
            t.setCreatedTime(new Date());
        }

        t.setUpdatedTime(new Date());
        return (BaseDomain)this.em.merge(t);
    }

    @Override
    public <T extends BaseDomain> BaseDomain merge(T t) {
        return (BaseDomain)this.em.merge(t);
    }

    @Override
    public <T extends BaseDomain> void saveNew(T t) {
        t.setCreatedTime(new Date());
        t.setUpdatedTime(new Date());
        this.em.persist(t);
    }

    @Override
    public <T extends BaseDomain> void batchSave(List<T> list) {
        int i = 1;

        for(Iterator var3 = list.iterator(); var3.hasNext(); ++i) {
            BaseDomain t = (BaseDomain)var3.next();
            if(t.getCreatedTime() == null) {
                t.setCreatedTime(new Date());
            }

            t.setUpdatedTime(new Date());
            this.em.persist(t);
            if(i == list.size()) {
                this.em.flush();
                this.em.clear();
                break;
            }

            if(i % 100 == 0) {
                this.em.flush();
                this.em.clear();
            }
        }

    }

    @Override
    public <T extends BaseDomain> void batchUpdate(List<T> list) {
        int i = 1;

        for(Iterator var3 = list.iterator(); var3.hasNext(); ++i) {
            BaseDomain t = (BaseDomain)var3.next();
            t.setUpdatedTime(new Date());
            this.em.merge(t);
            if(i == list.size()) {
                this.em.flush();
                this.em.clear();
                break;
            }

            if(i % 100 == 0) {
                this.em.flush();
                this.em.clear();
            }
        }

    }

    @Override
    public <T extends BaseDomain> void removeEntity(T t) {
        this.em.remove(this.em.merge(t));
    }

    @Override
    public <T extends BaseDomain> BaseDomain getEntityById(Class<T> clazz, Object id) {
        return (BaseDomain)this.em.find(clazz, id);
    }

    @Override
    public <T extends BaseDomain> BaseDomain getEntityByField(Class<T> clazz, String fieldName, Object fieldValue) {
        HashMap fieldMap = new HashMap();
        fieldMap.put(fieldName, fieldValue);
        List ret = this.getEntitiesByFieldList(clazz, fieldMap);
        return ret != null && ret.size() >= 1?(BaseDomain)ret.get(0):null;
    }

    @Override
    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String fieldName, Object fieldValue) {
        HashMap fieldMap = new HashMap();
        fieldMap.put(fieldName, fieldValue);
        return this.getEntitiesByFieldList(clazz, fieldMap);
    }

    @Override
    public <T extends BaseDomain> List<T> getEntitiesByFieldList(Class<T> clazz, Map<String, Object> fieldMap) {
        String sql = "select c from " + clazz.getName() + " c ";
        Set fieldNames = fieldMap.keySet();
        Iterator iterator = fieldNames.iterator();

        for(int query = 1; query <= fieldNames.size(); ++query) {
            String var12 = (String)iterator.next();
            if(query == 1) {
                sql = sql + " where c." + var12 + " = ?" + query;
            } else {
                sql = sql + " and c." + var12 + " = ?" + query;
            }
        }

        sql = sql + " order by c.updatedTime desc";
        Query var9 = this.em.createQuery(sql);
        iterator = fieldNames.iterator();

        for(int var10 = 1; var10 <= fieldNames.size(); ++var10) {
            String fieldName = (String)iterator.next();
            var9.setParameter(var10, fieldMap.get(fieldName));
        }

        return var9.getResultList();
    }

    @Override
    public <T extends BaseDomain> List<T> getAllEntities(Class<T> clazz) {
        return this.getEntitiesByFieldList(clazz, new HashMap());
    }

    @Override
    public <T extends BaseDomain> DomainPage getEntitiesByParams(Class<T> clazz, Map<String, Object> fieldMap, String beginDate, String endDate, long pageIndex, long pageSize) {
        pageIndex = pageIndex < 1L?1L:pageIndex;
        pageSize = pageSize < 1L?1L:pageSize;
        Iterator iterator = null;
        Set fieldNames = null;
        String sql = "select c from " + clazz.getName() + " c where 1=1 ";
        if(fieldMap != null) {
            fieldNames = fieldMap.keySet();
            iterator = fieldNames.iterator();

            for(int query = 1; query <= fieldNames.size(); ++query) {
                String resultList = (String)iterator.next();
                sql = sql + " and c." + resultList + " = ?" + query;
            }
        }

        if(!StringUtils.isBlank(beginDate) && !StringUtils.isBlank(endDate)) {
            sql = sql + " and (DATE_FORMAT(c.createdTime,\'%Y-%m-%d\') between DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\') and DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\'))";
        } else if(!StringUtils.isBlank(beginDate)) {
            sql = sql + " and DATE_FORMAT(c.createdTime,\'%Y-%m-%d\')>=DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\')";
        } else if(!StringUtils.isBlank(endDate)) {
            sql = sql + " and DATE_FORMAT(c.createdTime,\'%Y-%m-%d\')<=DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\')";
        }

        sql = sql + " order by c.updatedTime desc";
        Query var17 = this.em.createQuery(sql);
        if(fieldMap != null) {
            iterator = fieldNames.iterator();

            for(int var18 = 1; var18 <= fieldNames.size(); ++var18) {
                String totalCount = (String)iterator.next();
                var17.setParameter(var18, fieldMap.get(totalCount));
            }
        }

        var17.setFirstResult((int)((pageIndex - 1L) * pageSize));
        var17.setMaxResults((int)pageSize);
        List var19 = var17.getResultList();
        long var20 = this.getEntityTotalCount(clazz.getName(), fieldMap, beginDate, endDate);
        DomainPage domainPage = new DomainPage(pageSize, pageIndex, var20);
        domainPage.getDomains().addAll(var19);
        return domainPage;
    }

    @Override
    public <T extends BaseDomain> DomainPage getEntitiesByPage(Class<T> clazz, String fieldName, Object fieldValue, long pageIndex, long pageSize) {
        HashMap params = new HashMap();
        if(!StringUtils.isBlank(fieldName)) {
            params.put(fieldName, fieldValue);
        }

        return this.getEntitiesPagesByFieldList(clazz, params, pageIndex, pageSize);
    }

    @Override
    public <T extends BaseDomain> DomainPage findEntitiesByPage(Class<T> clazz, String fieldName, String keyword, long pageIndex, long pageSize) {
        pageIndex = pageIndex < 1L?1L:pageIndex;
        pageSize = pageSize < 1L?1L:pageSize;
        String sql = "select c from " + clazz.getName() + " c where c." + fieldName + " like ?1 ";
        sql = sql + " order by c.updatedTime desc";
        Query query = this.em.createQuery(sql);
        query.setParameter(1, "%" + keyword + "%");
        query.setFirstResult((int)((pageIndex - 1L) * pageSize));
        query.setMaxResults((int)pageSize);
        List resultList = query.getResultList();
        String sqlCount = "select count(c) from " + clazz.getName() + " c where c." + fieldName + " like ?1";
        query = this.em.createQuery(sqlCount);
        query.setParameter(1, "%" + keyword + "%");
        Long totalCount = (Long)query.getResultList().get(0);
        DomainPage domainPage = new DomainPage(pageSize, pageIndex, totalCount.longValue());
        domainPage.getDomains().addAll(resultList);
        return domainPage;
    }

    @Override
    public <T extends BaseDomain> DomainPage getEntitiesPagesByFieldList(Class<T> clazz, Map<String, Object> fieldMap, long pageIndex, long pageSize) {
        return this.getEntitiesPagesByFieldList(clazz, fieldMap, "", "", pageIndex, pageSize);
    }

    @Override
    public <T extends BaseDomain> DomainPage getEntitiesPagesByFieldList(Class<T> clazz, Map<String, Object> fieldMap, String beginDate, String endDate, long pageIndex, long pageSize) {
        pageIndex = pageIndex < 1L?1L:pageIndex;
        pageSize = pageSize < 1L?1L:pageSize;
        String sql = "select c from " + clazz.getName() + " c where 1=1 ";
        Set fieldNames = null;
        Iterator iterator = null;
        if(fieldMap != null) {
            fieldNames = fieldMap.keySet();
            iterator = fieldNames.iterator();

            for(int query = 1; query <= fieldNames.size(); ++query) {
                String list = (String)iterator.next();
                if(query == 1) {
                    sql = sql + " and c." + list + " = ?" + query;
                } else {
                    sql = sql + " and c." + list + " = ?" + query;
                }
            }
        }

        if(!StringUtils.isBlank(beginDate) && !StringUtils.isBlank(endDate)) {
            sql = sql + " and (DATE_FORMAT(c.createdTime,\'%Y-%m-%d\') between DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\') and DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\'))";
        } else if(!StringUtils.isBlank(beginDate)) {
            sql = sql + " and DATE_FORMAT(c.createdTime,\'%Y-%m-%d\')>=DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\')";
        } else if(!StringUtils.isBlank(endDate)) {
            sql = sql + " and DATE_FORMAT(c.createdTime,\'%Y-%m-%d\')<=DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\')";
        }

        sql = sql + " order by c.updatedTime desc";
        Query var16 = this.em.createQuery(sql);
        if(fieldMap != null) {
            iterator = fieldNames.iterator();

            for(int var17 = 1; var17 <= fieldNames.size(); ++var17) {
                String totalCount = (String)iterator.next();
                var16.setParameter(var17, fieldMap.get(totalCount));
            }
        }

        var16.setFirstResult((int)((pageIndex - 1L) * pageSize));
        var16.setMaxResults((int)pageSize);
        List var18 = var16.getResultList();
        Long var19 = Long.valueOf(this.getEntityTotalCount(clazz.getName(), fieldMap, beginDate, endDate));
        DomainPage domainPage = new DomainPage(pageSize, pageIndex, var19.longValue());
        domainPage.getDomains().addAll(var18);
        return domainPage;
    }

    public <T extends BaseDomain> long getEntityTotalCount(Class<T> clazz) {
        return this.getEntityTotalCount(clazz, new HashMap());
    }

    public <T extends BaseDomain> long getEntityTotalCount(Class<T> clazz, Map<String, Object> fieldMap) {
        return this.getEntityTotalCount(clazz.getName(), fieldMap, (String)null, (String)null);
    }

    public <T extends BaseDomain> long getEntityTotalCount(String className, Map<String, Object> fieldMap, String beginDate, String endDate) {
        String sql = "select count(c.objId) from " + className + " c where 1=1 ";
        Iterator iterator = null;
        Set fieldNames = null;
        if(fieldMap != null) {
            fieldNames = fieldMap.keySet();
            iterator = fieldNames.iterator();

            for(int query = 1; query <= fieldNames.size(); ++query) {
                String i = (String)iterator.next();
                sql = sql + " and c." + i + " = ?" + query;
            }
        }

        if(!StringUtils.isBlank(beginDate) && !StringUtils.isBlank(endDate)) {
            sql = sql + " and (DATE_FORMAT(c.createdTime,\'%Y-%m-%d\') between DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\') and DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\'))";
        } else if(!StringUtils.isBlank(beginDate)) {
            sql = sql + " and DATE_FORMAT(c.createdTime,\'%Y-%m-%d\')>=DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\')";
        } else if(!StringUtils.isBlank(endDate)) {
            sql = sql + " and DATE_FORMAT(c.createdTime,\'%Y-%m-%d\')<=DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\')";
        }

        Query var11 = this.em.createQuery(sql);
        if(fieldMap != null) {
            iterator = fieldNames.iterator();

            for(int var12 = 1; var12 <= fieldNames.size(); ++var12) {
                String fieldName = (String)iterator.next();
                var11.setParameter(var12, fieldMap.get(fieldName));
            }
        }

        return ((Long)var11.getResultList().get(0)).longValue();
    }

    public <T extends BaseDomain> long getEntityTotalCount(Class<T> clazz, String fieldName, Object fieldValue) {
        HashMap fieldMap = new HashMap();
        fieldMap.put(fieldName, fieldValue);
        return this.getEntityTotalCount(clazz, fieldMap);
    }

    @Override
    public Long countByHql(String hql, Object... values) {
        Query query = this.em.createQuery(hql);
        if(values != null) {
            for(int i = 0; i < values.length; ++i) {
                query.setParameter(i, values[i]);
            }
        }

        return (Long)query.getSingleResult();
    }

    @Override
    public List getListBySQL(String sqlString, Object[] params, Object[] values) {
        return this.getListBySQL(sqlString, (String)null, (String)null, params, values);
    }

    @Override
    public List getListBySQL(String sqlString, Map<String, Object> fieldMap) {
        return this.getListBySQL(sqlString, (String)null, (String)null, fieldMap);
    }

    @Override
    public List getListBySQL(String sqlString, String beginDate, String endDate, Object[] params, Object[] values) {
        if(params != null && params.length > 0) {
            for(int query = 0; query < params.length; ++query) {
                sqlString = sqlString + " and " + params[query] + "= ?" + (query + 1);
            }
        }

        if(!StringUtils.isBlank(beginDate) && !StringUtils.isBlank(endDate)) {
            sqlString = sqlString + " and (DATE_FORMAT(createdTime,\'%Y-%m-%d\') between DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\') and DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\'))";
        } else if(!StringUtils.isBlank(beginDate)) {
            sqlString = sqlString + " and DATE_FORMAT(createdTime,\'%Y-%m-%d\')>=DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\')";
        } else if(!StringUtils.isBlank(endDate)) {
            sqlString = sqlString + " and DATE_FORMAT(createdTime,\'%Y-%m-%d\')<=DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\')";
        }

        sqlString = sqlString + " order by updatedTime desc";
        Query var8 = this.em.createNativeQuery(sqlString);
        if(params != null && params.length > 0) {
            for(int i = 0; i < params.length; ++i) {
                var8.setParameter(i + 1, values[i]);
            }
        }

        ((SQLQuery)var8.unwrap(SQLQuery.class)).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return var8.getResultList();
    }

    @Override
    public List getListBySQL(String sqlString, String beginDate, String endDate, Map<String, Object> fieldMap) {
        Set fieldNames = fieldMap.keySet();
        Iterator iterator = fieldNames.iterator();
        if(fieldMap != null) {
            for(int query = 1; query <= fieldNames.size(); ++query) {
                String i = (String)iterator.next();
                sqlString = sqlString + " and " + i + "= ?" + (query + 1);
            }
        }

        if(!StringUtils.isBlank(beginDate) && !StringUtils.isBlank(endDate)) {
            sqlString = sqlString + " and (DATE_FORMAT(createdTime,\'%Y-%m-%d\') between DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\') and DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\'))";
        } else if(!StringUtils.isBlank(beginDate)) {
            sqlString = sqlString + " and DATE_FORMAT(createdTime,\'%Y-%m-%d\')>=DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\')";
        } else if(!StringUtils.isBlank(endDate)) {
            sqlString = sqlString + " and DATE_FORMAT(createdTime,\'%Y-%m-%d\')<=DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\')";
        }

        sqlString = sqlString + " order by updatedTime desc";
        Query var10 = this.em.createNativeQuery(sqlString);

        for(int var11 = 1; var11 <= fieldNames.size(); ++var11) {
            String totalCount = (String)iterator.next();
            var10.setParameter(var11, fieldMap.get(totalCount));
        }

        ((SQLQuery)var10.unwrap(SQLQuery.class)).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return var10.getResultList();
    }

    @Override
    public DomainPage getDomainPageBySQL(String sqlString, int pageIndex, int pageSize, Object[] params, Object[] values) {
        return this.getDomainPageBySQL(sqlString, "", "", pageIndex, pageSize, params, values);
    }

    @Override
    public DomainPage getDomainPageBySQL(String sqlString, int pageIndex, int pageSize, Map<String, Object> fieldMap) {
        return this.getDomainPageBySQL(sqlString, "", "", pageIndex, pageSize, fieldMap);
    }

    @Override
    public DomainPage getDomainPageByJoinSql(String sql, int pageIndex, int pageSize, Object[] values) {
        pageIndex = pageIndex < 1?1:pageIndex;
        pageSize = pageSize < 1?1:pageSize;
        Query query = this.em.createNativeQuery(sql);
        if(values.length > 0) {
            for(int list = 0; list < values.length; ++list) {
                query.setParameter(list + 1, values[list]);
            }
        }

        query.setFirstResult((int)(((long)pageIndex - 1L) * (long)pageSize));
        query.setMaxResults(pageSize);
        ((SQLQuery)query.unwrap(SQLQuery.class)).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List var10 = query.getResultList();
        String sqlString = "select count(a.objId) from (" + sql + ") a";
        query = this.em.createNativeQuery(sqlString);
        if(values.length > 0) {
            for(int totalCount = 0; totalCount < values.length; ++totalCount) {
                query.setParameter(totalCount + 1, values[totalCount]);
            }
        }

        BigInteger var11 = (BigInteger)query.getResultList().get(0);
        DomainPage domainPage = new DomainPage((long)pageSize, (long)pageIndex, var11.longValue());
        domainPage.setDomains(var10);
        return domainPage;
    }

    @Override
    public DomainPage getDomainPageBySQL(String sqlString, String beginDate, String endDate, int pageIndex, int pageSize, Object[] params, Object[] values) {
        pageIndex = pageIndex < 1?1:pageIndex;
        pageSize = pageSize < 1?1:pageSize;
        if(params != null && params.length > 0) {
            for(int query = 0; query < params.length; ++query) {
                sqlString = sqlString + " and " + params[query] + "= ?" + (query + 1);
            }
        }

        if(!StringUtils.isBlank(beginDate) && !StringUtils.isBlank(endDate)) {
            sqlString = sqlString + " and (DATE_FORMAT(createdTime,\'%Y-%m-%d\') between DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\') and DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\'))";
        } else if(!StringUtils.isBlank(beginDate)) {
            sqlString = sqlString + " and DATE_FORMAT(createdTime,\'%Y-%m-%d\')>=DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\')";
        } else if(!StringUtils.isBlank(endDate)) {
            sqlString = sqlString + " and DATE_FORMAT(createdTime,\'%Y-%m-%d\')<=DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\')";
        }

        sqlString = sqlString + " order by updatedTime desc";
        Query var13 = this.em.createNativeQuery(sqlString);
        if(params != null && params.length > 0) {
            for(int list = 0; list < params.length; ++list) {
                var13.setParameter(list + 1, values[list]);
            }
        }

        var13.setFirstResult((int)(((long)pageIndex - 1L) * (long)pageSize));
        var13.setMaxResults(pageSize);
        ((SQLQuery)var13.unwrap(SQLQuery.class)).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List var14 = var13.getResultList();
        long totalCount = this.getTotalCount(sqlString, beginDate, endDate, params, values);
        DomainPage domainPage = new DomainPage((long)pageSize, (long)pageIndex, totalCount);
        domainPage.setDomains(var14);
        return domainPage;
    }

    @Override
    public DomainPage getDomainPageBySQL(String sqlString, String beginDate, String endDate, int pageIndex, int pageSize, Map<String, Object> fieldMap) {
        pageIndex = pageIndex < 1?1:pageIndex;
        pageSize = pageSize < 1?1:pageSize;
        Set fieldNames = null;
        Iterator iterator = null;
        if(fieldMap != null) {
            fieldNames = fieldMap.keySet();
            iterator = fieldNames.iterator();

            for(int query = 1; query <= fieldNames.size(); ++query) {
                String list = (String)iterator.next();
                sqlString = sqlString + " and " + list + "= ?" + query;
            }
        }

        if(!StringUtils.isBlank(beginDate) && !StringUtils.isBlank(endDate)) {
            sqlString = sqlString + " and (DATE_FORMAT(createdTime,\'%Y-%m-%d\') between DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\') and DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\'))";
        } else if(!StringUtils.isBlank(beginDate)) {
            sqlString = sqlString + " and DATE_FORMAT(createdTime,\'%Y-%m-%d\')>=DATE_FORMAT(\'" + beginDate + "\',\'%Y-%m-%d\')";
        } else if(!StringUtils.isBlank(endDate)) {
            sqlString = sqlString + " and DATE_FORMAT(createdTime,\'%Y-%m-%d\')<=DATE_FORMAT(\'" + endDate + "\',\'%Y-%m-%d\')";
        }

        sqlString = sqlString + " order by updatedTime desc";
        Query var14 = this.em.createNativeQuery(sqlString);
        if(fieldMap != null) {
            iterator = fieldNames.iterator();

            for(int var15 = 1; var15 <= fieldNames.size(); ++var15) {
                String totalCount = (String)iterator.next();
                var14.setParameter(var15, fieldMap.get(totalCount));
            }
        }

        var14.setFirstResult((int)(((long)pageIndex - 1L) * (long)pageSize));
        var14.setMaxResults(pageSize);
        ((SQLQuery)var14.unwrap(SQLQuery.class)).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List var16 = var14.getResultList();
        long var17 = this.getTotalCount(sqlString, beginDate, endDate, fieldMap);
        DomainPage domainPage = new DomainPage((long)pageSize, (long)pageIndex, var17);
        domainPage.setDomains(var16);
        return domainPage;
    }

    @Override
    public long getTotalCount(String sql, String beginDate, String endDate, Object[] params, Object[] values) {
        String sqlString = "select count(a.objId) from (" + sql + ") a";
        Query query = this.em.createNativeQuery(sqlString);
        if(params != null) {
            for(int totalCount = 0; totalCount < params.length; ++totalCount) {
                query.setParameter(totalCount + 1, values[totalCount]);
            }
        }

        BigInteger var9 = (BigInteger)query.getResultList().get(0);
        return var9.longValue();
    }

    @Override
    public long getTotalCount(String sql, String beginDate, String endDate, Map<String, Object> fieldMap) {
        String sqlString = "select count(*) from (" + sql + ") a";
        Query query = this.em.createNativeQuery(sqlString);
        if(fieldMap != null) {
            Set totalCount = fieldMap.keySet();
            Iterator iterator = totalCount.iterator();

            for(int i = 1; i <= totalCount.size(); ++i) {
                String fieldValue = (String)iterator.next();
                query.setParameter(i, fieldMap.get(fieldValue));
            }
        }

        BigInteger var11 = (BigInteger)query.getResultList().get(0);
        return var11.longValue();
    }

    @Override
    public long getTotalCount(Class clazz, String beginDate, String endDate, Map<String, Object> fieldMap) {
        String sqlString = "select count(*) from " + clazz.getName() + " ";
        Query query = this.em.createNativeQuery(sqlString);
        if(fieldMap != null) {
            Set totalCount = fieldMap.keySet();
            Iterator iterator = totalCount.iterator();

            for(int i = 1; i <= totalCount.size(); ++i) {
                String fieldValue = (String)iterator.next();
                query.setParameter(i, fieldMap.get(fieldValue));
            }
        }

        BigInteger var11 = (BigInteger)query.getResultList().get(0);
        return var11.longValue();
    }
}
