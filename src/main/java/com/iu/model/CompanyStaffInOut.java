package com.iu.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyStaffInOut implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	private CompanyStaff companyStaff;
	
	private String inOut;

	private String addressInOut;

	private Timestamp time;
	
}