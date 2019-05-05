package com.xieweihao.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xieweihao.exception.BusinessException;
import com.xieweihao.service.TimeService;
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
		
		if(userId==null || datetime==null || datetime==""){
			return Result.error(-1, "没有收到任何数据");
		}
		
		try{
			return timeService.searchEventByDatetime(userId,datetime);
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
	
	/*
	 * 获取折线图的周数据
	 */
	@PostMapping(value={"getWeekRecord","getWeekRecord.action"})
	public Result getWeekRecord(Long userId,String datetime,String eventname){
		if(StringUtils.isBlank(eventname) || StringUtils.isBlank(datetime) || userId ==null){
			return Result.error(-1, "没有收到任何数据");
		}
		try{
			return timeService.getWeekRecord(userId,datetime,eventname);
		}catch(Exception e){
			return Result.error(-1, "获取失败").put("data", e.getMessage());
		}
	}
	
	@PostMapping(value={"getMonthRecord","getMonthRecord.action"})
	public Result getMonthRecord(Long userId,String datetime,String eventname){
		if(StringUtils.isBlank(eventname) || StringUtils.isBlank(datetime) || userId ==null){
			return Result.error(-1, "没有收到任何数据");
		}
		try{
			return timeService.getMonthRecord(userId,datetime,eventname);
		}catch(Exception e){
			return Result.error(-1, "获取失败").put("data", e.getMessage());
		}
	}
	
	@PostMapping(value={"getYearRecord","getYearRecord.action"})
	public Result getYearRecord(Long userId,String datetime,String eventname){
		if(StringUtils.isBlank(eventname) || StringUtils.isBlank(datetime) || userId ==null){
			return Result.error(-1, "没有收到任何数据");
		}
		try{
			return timeService.getYearRecord(userId,datetime,eventname);
		}catch(Exception e){
			return Result.error(-1, "获取失败").put("data", e.getMessage());
		}
	}
	
	/*
	 * {
			"id": 1,
			"userId": 1,
			"datetime": "2018-10-30",
			"plans": [{
				
				"event": "读书",
				"startTime": "xx",
				"endTime": "xx"
			},
			{
				
				"event": "睡觉",
				"startTime": "xx",
				"endTime": "xx"
			}]
		}
	 */
	@GetMapping(value={"getPlanByDate","getPlanByDate.action"})
	public Result getPlanByDate(Long userId,String datetime){
		
		if(userId==null || datetime==null || datetime==""){
			return Result.error(-1, "没有收到任何数据");
		}
		
		try{
			return timeService.getPlanByDate(userId,datetime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@PostMapping(value = {"/saveOrUpdatePlan","/saveOrUpdatePlan.action"})
	@ResponseBody
	public Result saveOrUpdatePlan(@RequestBody String sInput) throws BusinessException{
		if(StringUtils.isBlank(sInput)){
			return Result.error(-1, "没有收到任何数据");
		}
		try{
			return timeService.saveOrUpdatePlan(sInput);
		}catch(Exception e){
			return Result.error(-1, "设置失败").put("data", e.getMessage());
		}
	}
}
