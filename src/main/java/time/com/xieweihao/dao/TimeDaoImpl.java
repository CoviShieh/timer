package com.xieweihao.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xieweihao.entity.Time;
import com.xieweihao.jpa.BaseDaoImpl;
import com.xieweihao.jpa.Page;
import com.xieweihao.utils.StringUtils;

@Repository
public class TimeDaoImpl extends BaseDaoImpl<Time, Long>{

	public List<Map<String,Object>> searchDateByUserIdAndDatetime(Long userId,String date){
		
		List<Object> list = new ArrayList<Object>();
		StringBuilder sqlBuild = new StringBuilder("SELECT d.* FROM date d "
				+"JOIN date_x_event de ON de.date_id = d.id "
				+"JOIN event e ON e.id = de.event_id "
				+"JOIN event_x_time et ON et.event_id = e.id "
				+"JOIN time t ON t.id = et.time_id "
				+"WHERE 1=1 ");
//		if(userId == null){
//			list.add(0);
//		}else{
//			sqlBuild.append(" and d.user_id = ? ");
//			list.add(userId);
//		}
		
		if(userId != null){
			sqlBuild.append("AND (d.user_id = ?) ");
			list.add(userId);
		}
		if(StringUtils.isNotEmpty(date)){
			sqlBuild.append("AND (d.datetime = ?) ");
			list.add(date);
		}
		
		sqlBuild.append("GROUP BY d.id ORDER BY d.id DESC ");
		return this.findMapListNoPage(sqlBuild.toString(), list.toArray());
	}
	
	public List<Map<String, Object>> searchEventAndTimeByEventId(Long eventId){
		StringBuilder sql = new StringBuilder("select t.id tid,t.duration,et.id etid from time t "
				+ "JOIN event_x_time et ON et.time_id = t.id "
				+ "where 1=1 ");
		
		List<Object> list=new ArrayList<Object>();
		if(eventId != null){
			sql.append("and et.event_id = ? ");
			list.add(eventId);
		}
		sql.append(" GROUP BY t.id ");
		return this.findMapListNoPage(sql.toString(), list.toArray());
	}
	
}
