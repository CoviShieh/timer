package com.xieweihao.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xieweihao.dao.DateDao;
import com.xieweihao.dao.DateXEventDao;
import com.xieweihao.dao.EventDao;
import com.xieweihao.dao.EventXTimeDao;
import com.xieweihao.dao.TimeDao;
import com.xieweihao.dao.TimeDaoImpl;
import com.xieweihao.entity.Date;
import com.xieweihao.entity.DateXEvent;
import com.xieweihao.entity.Event;
import com.xieweihao.entity.EventXTime;
import com.xieweihao.entity.Time;
import com.xieweihao.exception.BusinessException;
import com.xieweihao.jpa.Page;
import com.xieweihao.utils.Result;

@Service
@Transactional
public class TimeService {
	@Autowired TimeDaoImpl timeDaoImpl;
	@Autowired TimeDao timeDao;
	@Autowired EventDao eventDao;
	@Autowired DateDao dateDao;
	@Autowired EventXTimeDao eventXTimeDao;
	@Autowired DateXEventDao dateXEventDao;
	
	public List<Map<String,Object>> searchDateByUserIdAndDatetime(Long userId,String datetime){
		
		return timeDaoImpl.searchDateByUserIdAndDatetime(userId,datetime);
	}
	

	public List<Event> findEventsByParams(Object dateId,Object userId){
		return eventDao.findEventsByParams(dateId,userId);
	}
	
	public List<Time> findTimesByDatetime(String datetime){
		return timeDao.findByDatetime(datetime);
	}
	
	public List<Map<String,Object>> searchEventAndTimeByEventId(Long eventId){
		return timeDaoImpl.searchEventAndTimeByEventId(eventId);
	}
	
	/**
	 * sInput {
						"id": 1,  //date的id
						"userId": 1,
						"datetime": "2018-10-30",
						"events": [{
						    "id": 1, // 事件时间关联表的id
							"event": "读书",
							"duration": "3"
						}, {
							"id": 2,
							"event": "睡觉",
							"duration": "6"
						}]
					}
	 * @param sInput
	 * @return
	 * @throws BusinessException 
	 */
	public Result saveOrUpdateData(String sInput) throws BusinessException{
		
		JSONObject object = JSONObject.parseObject(sInput);
		Long id = 0l;	//date的id
		if(object.get("id") != null){
			id = object.getLong("id");
		}
		Long userId = object.getLong("userId");
		String datetime = object.getString("datetime");
		JSONArray events = object.getJSONArray("events");
		
		//保存日期
		Date date = saveOrUpdateDate(id,userId,datetime);	
		
		//保存全部事件
		saveOrUpdateEvents(userId,date,events);
		
		return Result.ok("设置成功");
	}

	private void saveOrUpdateEvents(Long userId,Date date,JSONArray events) throws BusinessException {
		// TODO Auto-generated method stub
		for(int i=0;i<events.size();i++){
			Long xid = 0L;	//事件时间关联的id
			if(events.getJSONObject(i).get("id")!=null){
				xid = events.getJSONObject(i).getLong("id");
			}
			String eventName = events.getJSONObject(i).getString("event");
			int duration = events.getJSONObject(i).getInteger("duration");
			
			//保存事件(根据date_id取到具体天做的event)
			Event event = saveOrUpdateEvent(userId,date.getId(),eventName);
			
			//保存时间
			Time time = saveOrUpdateTime(event.getId(),xid,duration);
		}
		
	}

	/**
	 * 设置时间值
	 * @param xid
	 * @param duration
	 * @return
	 * @throws BusinessException 
	 */
	private Time saveOrUpdateTime(Long eventId,Long xid, int duration) throws BusinessException {
		Time time = timeDao.findByDuration(duration);
		if(time==null){
			time = saveTime(duration);
		}
		//保存事件时间关联
		if(xid.equals(0L)){
			saveEventXTime(eventId,time.getId());
		}else{
			updateEventXTime(xid,eventId,time.getId());
		}
		return time;
	}

	/**
	 * 更新事件时间关联
	 * @param xid
	 * @param eventId
	 * @param timeId
	 */
	private void updateEventXTime(Long xid, Long eventId, Long timeId) {
		EventXTime eventXTime = eventXTimeDao.findOne(xid);
		eventXTime.setEventId(eventId);
		eventXTime.setTimeId(timeId);
		eventXTimeDao.saveAndFlush(eventXTime);
	}

	/**
	 * 保存事件时间关联
	 * @param eventId
	 * @param timeId
	 */
	private void saveEventXTime(Long eventId, Long timeId) {
		// TODO Auto-generated method stub
		EventXTime eventXTime = new EventXTime();
		eventXTime.setEventId(eventId);
		eventXTime.setTimeId(timeId);
		eventXTimeDao.saveAndFlush(eventXTime);
	}


	private Time saveTime(int duration) {
		// TODO Auto-generated method stub
		Time time = new Time();
		time.setDuration(duration);
		time = timeDao.saveAndFlush(time);
		return time;
	}


	/**
	 * 设置事件(没有新增事件，只有新增/修改事件与日期的关系)
	 * @param dateId
	 * @param xid
	 * @param eventName
	 * @return
	 * @throws BusinessException
	 */
	private Event saveOrUpdateEvent(Long userId,Long dateId, String eventName) throws BusinessException {
		Event event = eventDao.findByEventNameAndUserId(eventName,userId);
		
		//保存日期事件关联
//		if(xid.equals(0L)){
//			saveDateXEvent(dateId,event.getId());	//新增关联关系
//		}else{
//			updateDateXEvent(xid,dateId,event.getId());
//		}
		
		
		return event;
	}


	private void updateDateXEvent(Long xid, Long dateId, Long eventId) {
		DateXEvent dateXEvent = dateXEventDao.findOne(xid);		//修改关联关系
		if(dateId!=dateXEvent.getDateId() || eventId!=dateXEvent.getEventId()){
			dateXEvent.setDateId(dateId);
			dateXEvent.setEventId(eventId);
			dateXEventDao.saveAndFlush(dateXEvent);
		}
	}


	/**
	 * 保存日期事件关联表
	 * @param dateId
	 * @param eventId
	 */
	private void saveDateXEvent(Long dateId, Long eventId) {
		// TODO Auto-generated method stub
		DateXEvent dateXEvent = new DateXEvent();
		dateXEvent.setDateId(dateId);
		dateXEvent.setEventId(eventId);
		dateXEventDao.saveAndFlush(dateXEvent);
	}

	/**
	 * 保存事件
	 * @param eventName
	 * @return
	 */
	private Event saveEvent(String eventName) {
		// TODO Auto-generated method stub
		Event event = new Event();
		event.setEventName(eventName);
		event = eventDao.save(event);
		return event;
	}

	/**
	 * 设置日期
	 * @param id
	 * @param userId
	 * @param datetime
	 * @return
	 * @throws BusinessException
	 */
	private Date saveOrUpdateDate(Long id, Long userId, String datetime) throws BusinessException {

		Date date = null ;
		if(id.equals(0L)){//日期id为空
			List<Date> list = dateDao.findByUserIdAndDatetime(userId, datetime);
			if(list != null && list.size()>0){
				throw new BusinessException("已存在相同的日期");
			}else{
				date = saveDate(userId,datetime); //新增
			}
		}else{
			date = dateDao.findOne(id);
		}
		
		return date;
	}


	/**
	 * 更新日期
	 * @param id
	 * @param userId
	 * @param datetime
	 * @return
	 */
//	private Date updateDate(Long id, Long userId, String datetime) {
//		Date date = dateDao.findOne(id);
//		date.setDateTime(datetime);
//		date.setUserId(userId);
//		date = dateDao.save(date);
//		return date;
//	}


	/**
	 * 保存日期
	 * @param userId
	 * @param datetime
	 * @return
	 */
	private Date saveDate(Long userId, String datetime) {

		Date date = new Date();
		date.setDateTime(datetime);
		date.setUserId(userId);
		date = dateDao.save(date);
		return date;
	}


	/**
	 * 删除一个日期数据，包括其下的事件和时间
	 * @param datetime
	 */
	public void deleteDatetime(String datetime,String userId) {
		
		//找出datetime下的事件、时间、日期事件关联、事件时间关联。 注意事件和时间是通用的，我是不希望过多操作事件 时间表的
		
		List<DateXEvent> dateXEvents = dateXEventDao.findByDatetime(datetime);
		List<EventXTime> eventXTimes = eventXTimeDao.findByDatetime(datetime);
		if(dateXEvents!=null &&dateXEvents.size()>0)
			dateXEventDao.deleteInBatch(dateXEvents);
		
		if(eventXTimes!=null &&eventXTimes.size()>0)
			eventXTimeDao.deleteInBatch(eventXTimes);
		
		dateDao.deleteByUserIdAndDatetime(datetime,userId);
	}

	/**
	 * input{
	 * 		"id":1,
	 * 		"event":"阅读"，
	 * 		"time":1
	 * }
	 * @param input
	 * @return
	 */
	public void deleteEventAndTime(Long xid,Long dateId) {
		dateXEventDao.deleteByParam(xid,dateId);
		eventXTimeDao.delete(xid);
		
	}


}
