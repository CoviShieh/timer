package com.xieweihao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xieweihao.dao.EventDao;
import com.xieweihao.entity.Event;
import com.xieweihao.utils.Result;

@Service
@Transactional
public class EventService {
	
	@Autowired EventDao eventDao;
	
	public Result getEventbyUserId(Long userId){
		
		List<Event> list = eventDao.findByUserId(userId);
		return Result.ok().put("events", list);
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
