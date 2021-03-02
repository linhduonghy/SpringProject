package com.iu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// lop Dich Vu toa nha
public class Service implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String name;
	// loai dich vu
	private String type;
	// don gia
	private long unitPrice;
	
	// danh sach cong ty su dung dich vu
	@JsonIgnore
	private List<CompanyService> companies = new ArrayList<>();
	
	// danh sach nhan vien toan nha thuc hien dich vu nay
	private List<BuildingStaff> buildingStaffs;
	
	public int hashCode() {
		return Objects.hashCode(id);
	}
	
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Service)) return false;
		
		Service that = (Service) obj;
		
		return id == that.id;
	}
}
