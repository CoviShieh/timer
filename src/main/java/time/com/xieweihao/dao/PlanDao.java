package com.xieweihao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xieweihao.entity.Plan;
import com.xieweihao.jpa.IBaseRepository;

public interface PlanDao extends IBaseRepository<Plan, Long>{
	
	@Query(value="SELECT p.* FROM plan p WHERE p.date_id = ? and p.user_id=? GROUP BY p.id ",nativeQuery=true)
	List<Plan> findPlansByParams(Long dateId,Long userId);
	
	@Modifying
	@Query(value="update event set event_name=? ,start_time=? , end_time=? where id=? ",nativeQuery=true)
	void modifyPlan(String eventName,String startTime,String endTime,Long id);

}
