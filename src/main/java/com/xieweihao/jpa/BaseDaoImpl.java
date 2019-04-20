package com.xieweihao.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * 基础业务层实现类--baseRepositoryPlus这个是个接口，调用方法是怎么实现的？
 * @author hch
 *
 * @param <T>
 * @param <PK>
 */
public abstract class BaseDaoImpl<T, PK extends Serializable> {

	@Autowired
	private IBaseRepositoryPlus<T, PK> baseRepositoryPlus;
	
	/**
	 * 获取分页的map集合
	 * @param page page实体类，需要
	 * @param findAllNativeSql 查找所有数据的原生sql语句，
	 * @param objects sql的查询参数
	 * @return
	 */
	public Page<Map<String, Object>> findPageMapList(Page<Map<String, Object>> page,String findAllNativeSql , Object...objects){
		
		try {
			int start = (page.getCurrent_page() - 1) * page.getPage_size() ;
			int limit = page.getPage_size() ;
			String sql =  findAllNativeSql + " limit ? , ? " ;
			List<Map<String, Object>> maps = baseRepositoryPlus.findMapList(sql, start ,limit ,objects);
			Integer count = baseRepositoryPlus.findTotalCount(findAllNativeSql, objects);
			page.setTotal_count(count);
			page.setList(maps);
			page.setPage_count(count%limit==0?count/limit:count/limit+1);
			return page ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public Page<Map<String, Object>> findPageMapList(Page<Map<String, Object>> page,String findAllNativeSql , String sqlCount,Object...objects){
		
		try {
			int start = (page.getCurrent_page() - 1) * page.getPage_size() ;
			int limit = page.getPage_size() ;
			String sql =  findAllNativeSql + " limit ? , ? " ;
			List<Map<String, Object>> maps = baseRepositoryPlus.findMapList(sql, start ,limit ,objects);
			Integer count = baseRepositoryPlus.findTotalCount(sqlCount, objects);
			page.setTotal_count(count);
			page.setList(maps);
			page.setPage_count(count%limit==0?count/limit:count/limit+1);
			return page ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null ;
	}

	
	
	/**
	 * 获取分页的map集合的list
	 * @param page    页码
	 * @param pageSize  每页显示多少条
	 * @param findAllNativeSql    sql语句
	 * @param objects     参数
	 * @return   List<Map<String, Object>>
	 */
	public List<Map<String, Object>> findMapListWithPage(Integer page,Integer pageSize,String findAllNativeSql , Object...objects){
		
		try {
			int start = (page - 1) * pageSize ;
			int limit = pageSize ;
			String sql =  findAllNativeSql + " limit ? , ? " ;
			List<Map<String, Object>> maps = baseRepositoryPlus.findMapList(sql, start ,limit ,objects);
			return maps ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	
	/**
	 * 获取map的集合
	 * @param sql 原生sql语句
	 * @param objects 参数集合
	 * @return map映射的list集合 ，map的key是查出来的数据库字段
	 */
	public List<Map<String, Object>> findMapListNoPage(String sql, Object... args) {
		return baseRepositoryPlus.findMapListNoPage(sql,args);
	}
	
	/**
	 * 获取单实例的map
	 * @param sql
	 * @param args
	 * @return
	 */
	public Map<String, Object> findMapUnique(String sql, Object... args){
		return baseRepositoryPlus.findMapUnique(sql,args);
	}
	
	/**
	 * 执行sql语句
	 * @param sql
	 * @param args
	 * @return
	 */
	public Integer executeUpdateBySql(String sql,Object... args){
		return baseRepositoryPlus.executeUpdateBySql(sql,args);
	}

}
