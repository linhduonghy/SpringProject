package com.iu.model;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	// ten cong ty
	private String name;
	// ma so thue
	private String taxId;
	// von dieu le
	private Long charterCapital;
	// linh vuc hoat dong
	private String operationField;
	// so nhan vien
	private int numberOfStaff;
	// dia chi trong toa nha
	private String addressInBuilding;
	// so dien thoai
	private String phone;
	// dien tich mat bang
	private int spaceArea;

	// danh sach nhan vien trong cong ty

	private List<CompanyStaff> companyStaffs;

	// dich vu su dung
	private List<CompanyService> services;
	
	public void addService(Service s) {
		CompanyService cs = new CompanyService(this, s, new Date(System.currentTimeMillis()));
		services.add(cs);
		s.getCompanies().add(cs);
	}
	private long totalCost;
	
}
