package com.xieweihao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xieweihao.entity.Date;
import com.xieweihao.jpa.IBaseRepository;

@Repository
public interface DateDao extends IBaseRepository<Date, Long>{

	@Query(value="from Date where userId=? and datetime=? ")
	List<Date> findByUserIdAndDatetime(Long userId,String datetime);

	@Modifying
	@Query(value="DELETE FROM date WHERE datetime=?1 AND user_id=?2 ",nativeQuery=true)
	void deleteByUserIdAndDatetime(String datetime,String userId);
}
