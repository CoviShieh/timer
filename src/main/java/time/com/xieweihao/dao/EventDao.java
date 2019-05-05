package com.xieweihao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xieweihao.entity.DateClass;
import com.xieweihao.entity.Event;
import com.xieweihao.jpa.IBaseRepository;

public interface EventDao extends IBaseRepository<Event, Long>{

	@Query(value="SELECT e.* FROM event e WHERE e.date_id = ? and e.user_id=? GROUP BY e.id ",nativeQuery=true)
	List<Event> findEventsByParams(Object dateId,Object userId);

	@Query(value="SELECT e.* FROM event e JOIN event_x_time et ON et.event_id = e.id WHERE et.id=?1 and e.event_name=?2 ",nativeQuery=true)
	Event findByXidAndEventName(Long xid,String eventName);

	@Query(value="SELECT e.* FROM event e WHERE e.eventname=? and e.user_id=? and e.date_id=? ",nativeQuery=true)
	Event findByParams(String eventName,Long userId,Long dateId);
	
	@Query(value="SELECT e.* FROM event e WHERE e.user_id = ? and date_id is null ",nativeQuery=true)
	List<Event> findByUserId(Long userId);
	
	
	@Modifying
	@Query(value="update event set event_name=? where id=? ",nativeQuery=true)
	void modifyEventName(String eventName,Long id);

}
