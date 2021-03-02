package com.iu.model;

import java.io.Serializable;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingStaff implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;	
	private String name;
	private Date dateOfBirth;
	private String address;
	private String phone;
	private int ranks;
	private String position;	
	
	// dich vu nhan vien thuc hien
	private Service service;
}
