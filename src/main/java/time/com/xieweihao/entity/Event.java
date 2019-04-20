package com.xieweihao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xieweihao.jpa.IdEntity;
/**
 * @author xieweihao
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "event")
public class Event extends IdEntity implements java.io.Serializable{

	private String eventName;
	private Long userId;
	private Long dateId;

	@Column(name = "event_name")
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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
