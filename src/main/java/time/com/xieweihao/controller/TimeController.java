package com.xieweihao.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xieweihao.entity.Event;
import com.xieweihao.entity.Time;
import com.xieweihao.exception.BusinessException;
import com.xieweihao.jpa.Page;
import com.xieweihao.service.TimeService;
import com.xieweihao.utils.JsonUtil;
import com.xieweihao.utils.Result;
import com.xieweihao.utils.StringUtils;

@RestController
public class TimeController {
	@Autowired TimeService timeService;
	
	/**
	 * 根据userid和datetime查询事件-时间
	 * @param userId
	 * @param datetime
	 * @return
	 */
	@GetMapping(value={"searchEventByDatetime","searchEventByDatetime.action"})
	public Result searchEventByDatetime(Long userId,String datetime){
		
		try{
			List<Map<String,Object>> list= timeService.searchDateByUserIdAndDatetime(userId,datetime);
			JSONArray jarray = new JSONArray();
			if(list != null){
				for(Map<String,Object> map :list){
					JSONObject user = new JSONObject();
					user.put("dateId", map.get("id"));
					user.put("userId", map.get("user_id"));
					user.put("datetime", map.get("datetime"));
					
					JSONArray eventsArray = new JSONArray();
					
					List<Event> listEvents = timeService.findEventsByParams(map.get("id"),map.get("user_id"));
					if(listEvents == null){
						break;
					}
					for(Event e : listEvents){
						//拿到 事件时间关联表 的id 和 对应的时间
						List<Map<String,Object>> eventAndTime = timeService.searchEventAndTimeByEventId(e.getId());	//一个事件对应一个时间段
						if(eventAndTime==null || eventAndTime.size()==0){
							continue;
						}
						JSONObject eventjson = new JSONObject();
						Map<String, Object> time = eventAndTime.get(0);
						eventjson.put("id", time.get("etid"));
						eventjson.put("event", e.getEventName());
						eventjson.put("duration", time.get("duration"));
						eventsArray.add(eventjson);
					}
					user.put("events", eventsArray);
					jarray.add(user);
				}
			}
			return Result.ok("查询成功").put("data", jarray);
			 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * sInput {
	 * 					"id": 1, //日期
						"userId": 1,
						"datetime": "2018-10-30",
						"events": [{
						    "id": 1, // 事件时间关联表的id，是修改的根据，新增可以不传id
							"event": "读书",
							"duration": "3"
						}, {
							"id": 2,
							"event": "睡觉",
							"duration": "6"
						}]
					}
	 * @return
	 * @throws BusinessException 
	 */
	@PostMapping(value = {"/saveOrUpdateData","/saveOrUpdateData.action"})
	@ResponseBody
	public Result saveOrUpdateData(@RequestBody String sInput) throws BusinessException{
		if(StringUtils.isBlank(sInput)){
			return Result.error(-1, "没有收到任何数据");
		}
		try{
			return timeService.saveOrUpdateData(sInput);
		}catch(Exception e){
			return Result.error(-1, "设置失败").put("data", e.getMessage());
		}
	}
	
//	@PostMapping(value = {"/deleteDatetime","/deleteDatetime.action"})
//	@ResponseBody
//	public Result deleteDatetime(String datetime,String userId){
//		if(StringUtils.isBlank(datetime)){
//			return Result.error(-1, "datetime为空");
//		}
//		if(StringUtils.isBlank(datetime)){
//			return Result.error(-1, "userId为空");
//		}
//		try{
//			timeService.deleteDatetime(datetime,userId);
//			return Result.ok("删除成功");
//		}catch(Exception e){
//			return Result.error(-1, "删除失败").put("data", e.getMessage());
//		}
//		
//	}
//	
//	/**
//	 * 
//	 * input{
//	 * 	"id":1,
//	 * 	"event":"阅读"， 日期事件关联没有去掉
//	 * 	"time":1
//	 * }
//	 * @param input
//	 * @return
//	 */
//	@PostMapping(value = {"/deleteEventAndTime","/deleteEventAndTime.action"})
//	@ResponseBody
//	public Result deleteEventAndTime(Long xid,Long dateId){
//		if(xid == null){
//			return Result.error(-1, "没有收到任何数据");
//		}
//		try{
//			timeService.deleteEventAndTime(xid,dateId);
//			return Result.ok("删除成功");
//		}catch(Exception e){
//			return Result.error(-1, "删除失败").put("data", e.getMessage());
//		}
//	}
}
