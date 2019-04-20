package com.xieweihao.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;


/**
 * 接口实现类
 * @author liuxg
 * @date 2015-04-14 
 * @param <T>
 * @param <ID>
 */
@Repository
public class BaseRepositoryPlus<T, PK extends Serializable> implements IBaseRepositoryPlus<T, PK> {

	@PersistenceContext
	public EntityManager em ;
	
	/**
	 * 获取map的集合
	 * @param sql 原生sql语句
	 * @param objects 参数集合
	 * @param start 分页start 空时不进行分页
	 * @param limit 分页limit 空时不进行分页
	 * @return map映射的list集合 ，map的key是查出来的数据库字段
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findMapList(String sql ,Integer start,Integer limit , Object...objects){
		Query query = em.createNativeQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i+1, objects[i]);
		}
		
		if (start != null && limit != null) { //
			query.setParameter(objects.length + 1, start);
			query.setParameter(objects.length + 2, limit);
		}
		
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.getResultList();
		return result;
	}

	/**
	 * 获取sql数量数量
	 * @param findAllNativeSql
	 * @param objects
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer findTotalCount(String findAllNativeSql,Object...objects){
		/*int index=findAllNativeSql.lastIndexOf("from");
		String afterFromSql=findAllNativeSql.substring(index+4);
		String countSql = "select count(*) from " +  afterFromSql ;*/
		String countSql = "select count(*) from ( " +  findAllNativeSql + " ) as tempTable" ;
		Query query = em.createNativeQuery(countSql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i+1, objects[i]);
		}
		List<Object> count = query.getResultList();
        if (count.size() == 0) {
			return 0 ;
		}else{
			return ((BigInteger)count.get(0)).intValue();
		}
	}
	
/*	@SuppressWarnings("unchecked")
	public Integer findTotalCount(String findAllNativeSql,Object...objects){
		String countSql = "select count(*) from ( " +  findAllNativeSql + " ) as tempTable" ;
		Query query = em.createNativeQuery(countSql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i+1, objects[i]);
		}
		List<Object> count = query.getResultList();
        if (count.size() == 0) {
			return 0 ;
		}else{
			return ((BigInteger)count.get(0)).intValue();
		}
	}*/

	/**
	 * 通过sql语句查询集合，不分页
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findMapListNoPage(String nativeSql,
			Object... args) {
		Query query = em.createNativeQuery(nativeSql);
		if(args.length>0&&args!=null){
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i+1, args[i]);
			}
		}
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.getResultList();
		return result;
	}

	/**
	 * 获取单实例的map
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findMapUnique(String nativeSql, Object...args) {
		Query query = em.createNativeQuery(nativeSql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i+1, args[i]);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.getResultList();
		if (result != null && result.size() > 0 ) {
			return result.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 执行sql语句
	 * @param sql
	 * @param args
	 * @return
	 */
	@Override
	public Integer executeUpdateBySql(String nativeSql, Object... args) {
		Query query = em.createNativeQuery(nativeSql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i+1, args[i]);
		}
		return  query.executeUpdate();
	}

}
