package com.xieweihao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xieweihao.jpa.IdEntity;

@Entity
@Table(name = "plan")
public class Plan extends IdEntity implements java.io.Serializable{
	
	private String eventName;
	private String startTime;
	private String endTime;
	private Long userId;
	private Long dateId;
	
	@Column(name = "event_name")
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	@Column(name = "start_time")
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Column(name = "end_time")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name = "date_id")
	public Long getDateId() {
		return dateId;
	}
	public void setDateId(Long dateId) {
		this.dateId = dateId;
	}

	
}
