package com.xieweihao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xieweihao.entity.DateXEvent;
import com.xieweihao.jpa.IBaseRepository;

@Repository
public interface DateXEventDao extends IBaseRepository<DateXEvent, Long>{

	@Query(value="SELECT de.* FROM date_x_event de JOIN date d ON d.id = de.date_id where d.datetime=?",nativeQuery=true)
	List<DateXEvent> findByDatetime(String datetime);

	@Modifying
	@Query(value="delete from date_x_event where date_id=?2 and event_id=(select et.event_id from event_x_time et where et.id=?1)",nativeQuery=true)
	void deleteByParam( Long xid,Long dateId);

}
