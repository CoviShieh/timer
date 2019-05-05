package com.xieweihao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xieweihao.service.EventService;
import com.xieweihao.utils.Result;

@RestController
public class EventController {
	
	@Autowired EventService eventServive;
	
	/**
	 * 返回事件list
	 * @param input
	 * @return
	 */
	@PostMapping(value = {"/getEventbyUserId","/getEventbyUserId.action"})
	public Result getEventbyUserId(Long userId){
		if(userId == null){
			return Result.error(-1, "没有收到任何数据");
		}
		try{
			return eventServive.getEventbyUserId(userId);
		}catch(Exception e){
			return Result.error(-1, "获取失败").put("data", e.getMessage());
		}
	}
	
	@PostMapping(value = {"/addEvent","/addEvent.action"})
	public Result addEvent(String eventName,Long userId){
		
		if(eventName == null){
			return Result.error(-1, "事件名不能为空");
		}
		if(userId == null){
			return Result.error(-1, "没有收到任何数据");
		}
		
		try{
			eventServive.addEvent(eventName,userId);
			return Result.ok("新增成功");
		}catch(Exception e){
			return Result.error(-1, "操作失败").put("data", e.getMessage());
		}
	}
	
	@PostMapping(value = {"/deleteEvent","/deleteEvent.action"})
	public Result deleteEvent(Long id){
		if(id == null){
			return Result.error(-1, "没有收到任何数据");
		}
		
		try{
			eventServive.deleteEvent(id);
			return Result.ok("删除成功");
		}catch(Exception e){
			return Result.error(-1, "操作失败").put("data", e.getMessage());
		}
	}
}
