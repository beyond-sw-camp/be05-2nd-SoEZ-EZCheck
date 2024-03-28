package com.soez.ezcheck.checkout.domain;

import java.sql.Date;
import java.sql.Time;

import com.soez.ezcheck.entity.CheckOutStatusEnum;

import lombok.Data;

@Data
public class CheckOutDTO {

	private Integer coutId;
	private CheckOutStatusEnum checkOutStatusEnum;
	private Date coutDate;
	private Time coutTime;
	private String userId;
	private Integer cinId;

	public CheckOutDTO() {
	}

	public CheckOutDTO(Integer coutId, Integer cinId){
		this.coutId=coutId;
		this.cinId=cinId;
	}

	public CheckOutDTO(Integer coutId, CheckOutStatusEnum checkOutStatusEnum, Date coutDate, Time coutTime, String uId, Integer cinId) {
		this.coutId = coutId;
		this.checkOutStatusEnum = checkOutStatusEnum;
		this.coutDate = coutDate;
		this.coutTime = coutTime;
		this.userId = uId;
		this.cinId = cinId;
	}
}
