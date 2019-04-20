package com.xieweihao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xieweihao.jpa.IdEntity;

@Entity
@Table(name = "event_x_time")
public class EventXTime extends IdEntity implements java.io.Serializable{

	private long eventId;
	private long timeId;
	
	@Column(name="event_id")
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	@Column(name="time_id")
	public long getTimeId() {
		return timeId;
	}
	public void setTimeId(long timeId) {
		this.timeId = timeId;
	}
	
	
}
