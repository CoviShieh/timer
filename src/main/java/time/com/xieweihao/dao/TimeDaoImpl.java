package com.xieweihao.dao;

import java.util.ArrayList;
import java.util.Date;
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
		StringBuilder sqlBuild = new StringBuilder("SELECT d.* FROM date d WHERE 1=1 ");
//				+"JOIN event e ON e.date_id = d.id "
//				+"JOIN event_x_time et ON et.event_id = e.id "
//				+"JOIN time t ON t.id = et.time_id "
//				+"WHERE 1=1 ");
		
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
		StringBuilder sql = new StringBuilder("select t.id tid,t.duration,et.id xid from time t "
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

	public List<Map<String, Object>> getWeekRecord(Long userId,
			String eventName, String mondayDate) {
		
		StringBuilder sql = new 
				StringBuilder("SELECT left_table.date,IFNULL(right_table.event_name,'"+eventName+"') as eventname,IFNULL(right_table.duration,'0') as duration "
				+"FROM (select DATE_SUB('"+mondayDate+"', INTERVAL -(n.index2) DAY) as date from num n) as left_table "
				+"LEFT JOIN (SELECT d.datetime,e.event_name,t.duration FROM date d "
				+"LEFT JOIN event e ON e.date_id = d.id "
				+"LEFT JOIN event_x_time et ON et.event_id = e.id  "
				+"LEFT JOIN time t ON t.id = et.time_id  "
				+"WHERE e.event_name='"+eventName+"' and e.user_id="+userId+") as right_table "
				+"on DATE_FORMAT(left_table.date,'%Y-%c-%d')=right_table.datetime "
				+"ORDER BY left_table.date" );
		
		return this.findMapListNoPage(sql.toString(), new Object[]{});
	}

	public List<Map<String, Object>> getMonthRecord(Long userId,
			String eventName, String monthStr) {
		
		StringBuilder sql = new 
				StringBuilder("SELECT left_table.date,IFNULL(right_table.event_name,'"+eventName+"') as eventname,IFNULL(right_table.duration,'0') as duration FROM "
				+"(SELECT ADDDATE(y.first, x.d - 1) as date FROM "
				+"(SELECT 1 AS d UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL "
				+"SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL SELECT 20 UNION ALL "
				+"SELECT 21 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL SELECT 29 UNION ALL SELECT 30 UNION ALL SELECT 31) x,"
				+"(SELECT CONCAT('"+monthStr+"','-01') as FIRST, DAY(LAST_DAY(str_to_date('"+monthStr+"','%Y-%m'))) AS last) y WHERE x.d <= y.last) as left_table "
				+"LEFT JOIN (SELECT d.datetime,e.event_name,t.duration FROM date d "
				+"LEFT JOIN event e ON e.date_id = d.id "
				+"LEFT JOIN event_x_time et ON et.event_id = e.id "
				+"LEFT JOIN time t ON t.id = et.time_id "
				+"WHERE e.event_name='"+eventName+"' and e.user_id="+userId+") as right_table "
				+"on DATE_FORMAT(left_table.date,'%Y-%c-%d')=right_table.datetime ORDER BY left_table.date ");
		return this.findMapListNoPage(sql.toString(), new Object[]{});
	}

	public List<Map<String, Object>> getYearRecord(Long userId,
			String eventName, String yearStr) {
		
		StringBuilder sql = new StringBuilder("SELECT left_table.month_num as month, IFNULL(sum,0) as duration "
				+ "from (SELECT m.month_num from month_table m ) as left_table left join "
				+ "(select month(d.datetime) as month,sum(t.duration) as sum FROM date d "
				+ "LEFT JOIN event e ON e.date_id = d.id "
				+ "LEFT JOIN event_x_time et ON et.event_id = e.id "
				+ "LEFT JOIN time t ON t.id = et.time_id "
				+ "where year(d.datetime)="+yearStr+" and e.event_name='"+eventName+"' and e.user_id="+userId+" ) as right_table  "
				+ "on left_table.month_num = right_table.month ORDER BY left_table.month_num ");
		return this.findMapListNoPage(sql.toString(), new Object[]{});
	}
	
}
