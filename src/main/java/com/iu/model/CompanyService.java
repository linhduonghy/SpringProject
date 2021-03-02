package com.iu.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CompanyService implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonIgnoreProperties("services")
	private Company company;	
	
	private Service service;

	private Date createdOn;
	
//	@Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof CompanyService)) return false;
//        CompanyService that = (CompanyService) o;
//        return Objects.equals(company.getId(), that.company.getId()) &&
//                Objects.equals(service.getId(), that.getService().getId()) &&
//                Objects.equals(createdOn, that.createdOn);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(company.getId(), service.getId(), createdOn);
//    }
}
