package com.xieweihao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xieweihao.entity.DateClass;
import com.xieweihao.jpa.IBaseRepository;

@Repository
public interface DateClassDao extends IBaseRepository<DateClass, Long>{

	@Query(value="select * from Date where user_id=? and datetime=? " ,nativeQuery=true)
	DateClass findByUserIdAndDatetime(Long userId,String datetime);

	@Modifying
	@Query(value="DELETE FROM date WHERE datetime=?1 AND user_id=?2 ",nativeQuery=true)
	void deleteByUserIdAndDatetime(String datetime,String userId);
}
