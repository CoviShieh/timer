package com.xieweihao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xieweihao.entity.EventXTime;
import com.xieweihao.jpa.IBaseRepository;

@Repository
public interface EventXTimeDao extends IBaseRepository<EventXTime, Long>{

	@Query(value="SELECT et.* FROM event_x_time et JOIN date_x_event de ON de.event_id=et.event_id JOIN date d ON d.id=de.date_id where d.datetime=?",nativeQuery=true)
	List<EventXTime> findByDatetime(String datetime);
	
	@Modifying
	@Query(value="update event_x_time set time_id=? where id=? ",nativeQuery=true)
	void modifyTime(Long timeId,Long id);

}
