package com.xieweihao.jpa;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 自定义接口,不使用jpa接口实现方式 
 * @author liuxg 2015-04-08
 * @param <T>
 * @param <ID>
 */
public interface IBaseRepositoryPlus<T, PK extends Serializable>  {

	/**
	 * 获取map的集合
	 * @param sql 原生sql语句
	 * @param objects 参数集合
	 * @param start 分页start 空时不进行分页
	 * @param limit 分页limit 空时不进行分页
	 * @return map映射的list集合 ，map的key是查出来的数据库字段
	 */
	public List<Map<String, Object>> findMapList(String nativeSql , Integer start,Integer limit ,Object...args) ;
	
	
	/**
	 * 获取总条目总数
	 * @param findAllNativeSql 原生查找所有条目sql
	 * @param objects 参数集合
	 * @return
	 */
	public Integer findTotalCount(String findAllNativeSql,Object...args);
	
	/**
	 * 获取不分页的map集合
	 * @param nativeSql
	 * @param args
	 * @return
	 */
	public List<Map<String, Object>> findMapListNoPage(String nativeSql ,Object...args) ;
  

	/**
	 * 获取唯一的map
	 * @param sql
	 * @param args
	 * @return
	 */
	public Map<String, Object> findMapUnique(String sql, Object...args);


	/**
	 * 执行sql语句
	 * @param sql
	 * @param args
	 * @return
	 */
	public Integer executeUpdateBySql(String sql,Object...args);
	
	

}

