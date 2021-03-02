package com.iu.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// lop nhan vien cong ty
public class CompanyStaff implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;	
	private String name;	
	private Date dateOfBirth;	
	private String phone;	
	// so cmnd
	private String cardNumber;	
	
	// ma cong ty
	private Company company;
	
	// thong tin ra vao cua nhan vien
	private List<CompanyStaffInOut> inOuts;	
}
