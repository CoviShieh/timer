package com.xieweihao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xieweihao.jpa.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "time")
public class Time extends IdEntity implements java.io.Serializable{

	private int duration; //时长

	@Column(name = "duration")
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
