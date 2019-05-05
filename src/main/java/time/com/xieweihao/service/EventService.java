package com.xieweihao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.xieweihao.dao.EventDao;
import com.xieweihao.entity.Event;
import com.xieweihao.utils.Result;

@Service
@Transactional
public class EventService {
	
	@Autowired EventDao eventDao;
	
	public Result getEventbyUserId(Long userId){
		
		List<Event> list = eventDao.findByUserId(userId);
		JSONObject data = new JSONObject();
		data.put("rows", list);
		data.put("total", list.size());
		data.put("pageSize", 15);
		data.put("currentPage", 1);
		return Result.ok("查询成功").put("data", data);
	}
	
	public void addEvent(String eventName,Long userId){
		Event event = new Event();
		event.setEventName(eventName);
		event.setUserId(userId);
		eventDao.saveAndFlush(event);
	}
	
	public void deleteEvent(Long eventId){
		eventDao.delete(eventId);
	}
}
