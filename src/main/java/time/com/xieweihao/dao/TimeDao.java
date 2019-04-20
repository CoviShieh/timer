package com.xieweihao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xieweihao.entity.Time;
import com.xieweihao.jpa.IBaseRepository;

@Repository
public interface TimeDao extends IBaseRepository<Time, Long>{

	@Query(value="SELECT t.* FROM time t JOIN event_x_time et ON et.time_id = t.id JOIN date_x_event de ON de.event_id = et.event_id JOIN date d ON d.id=de.date_id WHERE d.datetime= ? GROUP BY t.id",nativeQuery=true)
	List<Time> findByDatetime(String datetime);

	@Query(value="SELECT t.* FROM time t JOIN event_x_time et ON et.time_id = t.id WHERE et.id=?1 and t.duration=?2 ",nativeQuery=true)
	Time findByXidAndDuration(Long xid, int duration);

	Time findByDuration(int duration);

}
