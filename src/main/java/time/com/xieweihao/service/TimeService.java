package com.xieweihao.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xieweihao.dao.DateClassDao;
import com.xieweihao.dao.EventDao;
import com.xieweihao.dao.EventXTimeDao;
import com.xieweihao.dao.PlanDao;
import com.xieweihao.dao.TimeDao;
import com.xieweihao.dao.TimeDaoImpl;
import com.xieweihao.entity.DateClass;
import com.xieweihao.entity.Event;
import com.xieweihao.entity.EventXTime;
import com.xieweihao.entity.Plan;
import com.xieweihao.entity.Time;
import com.xieweihao.exception.BusinessException;
import com.xieweihao.utils.DateTimeUtils;
import com.xieweihao.utils.Result;

@Service
@Transactional
public class TimeService {
	@Autowired TimeDaoImpl timeDaoImpl;
	@Autowired TimeDao timeDao;
	@Autowired EventDao eventDao;
	@Autowired PlanDao planDao;
	@Autowired DateClassDao dateDao;
	@Autowired EventXTimeDao eventXTimeDao;
	
	public Result searchEventByDatetime(Long userId,String datetime){
		
		DateClass date= findByUserIdAndDatetime(userId,datetime);
		JSONObject data = new JSONObject();
		if(date != null){
			data.put("dateId", date.getId());
			data.put("userId", date.getUserId());
			data.put("datetime", DateTimeUtils.date2String(date.getDateTime()));
			//封装记录
			JSONArray eventsArray = new JSONArray();
			List<Event> listEvents = findEventsByParams(date.getId(),date.getUserId());
			if(listEvents == null){
				return Result.ok("没有记录");
			}
			for(Event e : listEvents){
				//拿到 事件时间关联表 的id 和 对应的时间
				List<Map<String,Object>> eventAndTime = searchEventAndTimeByEventId(e.getId());	//一个事件对应一个时间段
				if(eventAndTime==null || eventAndTime.size()==0){
					continue;
				}
				JSONObject eventjson = new JSONObject();
				Map<String, Object> time = eventAndTime.get(0);
				eventjson.put("id", time.get("xid"));
				eventjson.put("event", e.getEventName());
				eventjson.put("duration", time.get("duration"));
				eventsArray.add(eventjson);
			}
			data.put("events", eventsArray);
		}
		
		return Result.ok("查询成功").put("data", data);
	}
	
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
	
	public DateClass findByUserIdAndDatetime(Long userId,String datetime){
		
		return dateDao.findByUserIdAndDatetime(userId, datetime);
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
		System.out.println("id="+object.get("id"));
		if(object.getLong("id") != null){
			id = object.getLong("id");
		}
		Long userId = object.getLong("userId");
		String datetime = object.getString("datetime");
		JSONArray events = object.getJSONArray("events");
		
		//保存日期(新增或修改)
		DateClass date = saveOrUpdateDate(id,userId,datetime);	
		
		//保存全部事件
		saveOrUpdateEvents(userId,date,events);
		
		Result result = searchEventByDatetime(userId,datetime);
		result.put("msg", "设置成功");
		return result;
	}

	private void saveOrUpdateEvents(Long userId,DateClass date,JSONArray events) throws BusinessException {
		for(int i=0;i<events.size();i++){
			Long xid = 0L;	//事件时间关联的id
			if(events.getJSONObject(i).getLong("id") != null ){
				xid = events.getJSONObject(i).getLong("id");
			}
			String eventName = events.getJSONObject(i).getString("event");
			int duration = events.getJSONObject(i).getInteger("duration");
			
			//保存事件(根据date_id取到具体天做的event)
			saveOrUpdateEvent(xid,userId,date.getId(),eventName,duration);
		}
		
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


//	private Time saveTime(int duration) {
//		// TODO Auto-generated method stub
//		Time time = new Time();
//		time.setDuration(duration);
//		time = timeDao.saveAndFlush(time);
//		return time;
//	}


	/**
	 * 设置事件(没有新增事件，只有新增/修改事件与日期的关系)
	 * @param dateId
	 * @param xid
	 * @param eventName
	 * @return
	 * @throws BusinessException
	 */
	private void saveOrUpdateEvent(Long xid ,Long userId,Long dateId, String eventName,int duration) throws BusinessException {
		
		Time time = timeDao.findByDuration(duration);
		
		if(xid.equals(0L)){
			Event event = saveEvent(userId,dateId,eventName);
			saveEventXTime(event.getId(),time.getId());
		}else{
			//事件操作
			EventXTime eventXTime = eventXTimeDao.findOne(xid);
			Long eventId = eventXTime.getEventId();
			Event dbEvent = eventDao.findOne(eventId);
			if(dbEvent.getEventName() != eventName){
				eventDao.modifyEventName(eventName,eventId);
			}
			
			//时间操作
			Long timeId = eventXTime.getTimeId();
			if(time.getId() != timeId){ //修改EventXTime 的time_id
				eventXTimeDao.modifyTime(time.getId(),xid);
			}
			
		}
		
	}
	
	private Event saveEvent(Long userId,Long dateId, String eventName){
		Event event = new Event();
		event.setUserId(userId);
		event.setDateId(dateId);
		event.setEventName(eventName);
		
		return eventDao.saveAndFlush(event);
		
	}


	/**
	 * 设置日期
	 * @param id
	 * @param userId
	 * @param datetime
	 * @return
	 * @throws BusinessException
	 */
	private DateClass saveOrUpdateDate(Long id, Long userId, String datetime) throws BusinessException {

		DateClass date = null ;
		if(id.equals(0L)){//日期id为空
			date = dateDao.findByUserIdAndDatetime(userId, datetime);
			if(date == null){
				date = saveDate(userId,datetime); //新增
			}else{
				return date;
			}
		}else{
			date = dateDao.findOne(id);
		}
		
		return date;
	}


	/**
	 * 保存日期
	 * @param userId
	 * @param datetime
	 * @return
	 */
	private DateClass saveDate(Long userId, String datetime) {

		DateClass date = new DateClass();
		date.setDateTime(DateTimeUtils.string2Date(datetime));
		date.setUserId(userId);
		date = dateDao.save(date);
		return date;
	}

	/*
	  	获取当前一周的时间范围（要知道今天是星期几，再拿到周一到周日的时间的值）
	  	获取当前事件对象
	  	顺序去拿每一天事件的时长值放在一个list里面返回
	*/
	public Result getWeekRecord(Long userId,String datetime,String eventName){
		
		String mondayDate =DateTimeUtils.getWeekOfMon(datetime);
		
		List<Map<String,Object>> data = timeDaoImpl.getWeekRecord(userId,eventName,mondayDate);
		
		return Result.ok("查询成功").put("data", data);
	}

	public Result getMonthRecord(Long userId,String datetime,String eventName){
		//获取"2019-04"字符串
		String monthStr = datetime.substring(0, 7);
		List<Map<String,Object>> data = timeDaoImpl.getMonthRecord(userId,eventName,monthStr);
		return Result.ok("查询成功").put("data", data);
	}
	
	public Result getYearRecord(Long userId,String datetime,String eventName){
		
		String yearStr = datetime.substring(0, 4);
		List<Map<String,Object>> data = timeDaoImpl.getYearRecord(userId,eventName,yearStr);
		return Result.ok("查询成功").put("data", data);
	}

	public Result getPlanByDate(Long userId, String datetime) {
		// TODO Auto-generated method stub
		DateClass date= findByUserIdAndDatetime(userId,datetime);
		JSONObject data = new JSONObject();
		if(date != null){
			data.put("dateId", date.getId());
			data.put("userId", date.getUserId());
			data.put("datetime", DateTimeUtils.date2String(date.getDateTime()));
			
			List<Plan> listPlans = findPlansByParams(date.getId(),date.getUserId());
			if(listPlans == null || listPlans.size()==0){
				return Result.ok("没有记录");
			}
			data.put("plans", listPlans);
		}
		
		return Result.ok("查询成功").put("data", data);
	}

	private List<Plan> findPlansByParams(Long dateId, Long userId) {
		
		return planDao.findPlansByParams(dateId,userId);
	}

	/*
	 	{
			"id": 1,
			"userId": 1,
			"datetime": "2018-10-30",
			"plans": [{
				"id":xx, //plan的id
				"event": "读书",
				"startTime": "xx"
				"endTime": "xx"
			},
			{
				"id":xx,
				"event": "睡觉",
				"startTime": "xx"
				"endTime": "xx"
			}]
		}
	 */
	public Result saveOrUpdatePlan(String sInput) throws BusinessException {
		// TODO Auto-generated method stub
		JSONObject object = JSONObject.parseObject(sInput);
		Long id = 0l;	//date的id
		System.out.println("id="+object.get("id"));
		if(object.getLong("id") != null){
			id = object.getLong("id");
		}
		Long userId = object.getLong("userId");
		String datetime = object.getString("datetime");
		JSONArray plans = object.getJSONArray("plans");
		
		//保存日期(新增或修改)
		DateClass date = saveOrUpdateDate(id,userId,datetime);	
		
		//保存全部计划
		saveOrUpdatePlans(userId,date,plans);
		
		Result result = getPlanByDate(userId,datetime);
		result.put("msg", "设置成功");
		return result;
	}
	
	private void saveOrUpdatePlans(Long userId,DateClass date,JSONArray plans) throws BusinessException {
		for(int i=0;i<plans.size();i++){
			Long planId = 0L;	
			if(plans.getJSONObject(i).getLong("id") != null ){
				planId = plans.getJSONObject(i).getLong("id");
			}
			String eventName = plans.getJSONObject(i).getString("event");
			String startTime = plans.getJSONObject(i).getString("startTime");
			String endTime = plans.getJSONObject(i).getString("endTime");
			
			//有id就是修改，没有id就是新增
			saveOrUpdateEvent(planId,eventName,startTime,endTime,userId,date.getId());
		}
		
	}

	private void saveOrUpdateEvent(Long planId, String eventName,
			String startTime, String endTime, Long userId, Long dateId) {
		
		Plan plan = null;
		if(planId.equals(0L)){
			plan = savePlan(eventName,startTime,endTime,userId,dateId);
		}else{
			
			plan = planDao.findOne(planId);
			if(plan.getEventName()!= eventName || plan.getStartTime()!=startTime || plan.getEndTime() != endTime){
				planDao.modifyPlan(eventName, startTime, endTime, planId);
			}else{
				return;
			}
		}
	}

	private Plan savePlan(String eventName, String startTime, String endTime,
			Long userId, Long dateId) {
		// TODO Auto-generated method stub
		Plan plan = new Plan();
		plan.setEventName(eventName);
		plan.setStartTime(startTime);
		plan.setEndTime(endTime);
		plan.setDateId(dateId);
		plan.setUserId(userId);
		
		return planDao.saveAndFlush(plan);
	}
}
