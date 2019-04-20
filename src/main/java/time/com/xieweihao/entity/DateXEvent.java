package com.xieweihao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xieweihao.jpa.IdEntity;

@Entity
@Table(name = "date_x_event")
public class DateXEvent extends IdEntity implements java.io.Serializable{

	private long dateId;
	private long eventId;
	
	@Column(name="date_id")
	public long getDateId() {
		return dateId;
	}
	public void setDateId(long dateId) {
		this.dateId = dateId;
	}
	@Column(name="event_id")
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
}
