package com.iu.web;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.iu.model.Company;
import com.iu.model.CompanyStaff;
import com.iu.model.CompanyStaffInOut;

@Controller
@RequestMapping("company_staff_in_out")
public class CompanyStaffInOutController {

	RestTemplate template = new RestTemplate();
	
	
	@GetMapping
	public String showStaffInOut(Model model) {
		
		List<CompanyStaff> staffs = Arrays.asList(template.getForObject("http://localhost:8080/company_staff/all", 
				CompanyStaff[].class));
		model.addAttribute("staffs", staffs);
		List<CompanyStaffInOut> inouts = Arrays.asList(template.getForObject("http://localhost:8080/staff_inout/all", 
				CompanyStaffInOut[].class));
		Collections.reverse(inouts);
		model.addAttribute("inouts", inouts);
		
		return "inout/company_staff_in_out.html";
	}
	
	// insert inout
	@PostMapping("/insert")
	public String insertInOut(@RequestParam("staff") String staff, @RequestParam("inOut") String inOut, 
			@RequestParam("addressInOut") String addressInOut) {
		CompanyStaffInOut cs = new CompanyStaffInOut();
		cs.setCompanyStaff(
				template.getForObject("http://localhost:8080/company_staff/{id}",
						CompanyStaff.class, Integer.parseInt(staff)));
		cs.setInOut(inOut);
		cs.setAddressInOut(addressInOut);
		cs.setTime(new Timestamp(System.currentTimeMillis()));
		
		template.postForObject("http://localhost:8080/staff_inout", cs, Company.class);
		return "redirect:/company_staff_in_out";
	}
	
	// report 
	@GetMapping("/report")
	public String showReportInOut(Model model) {
		List<CompanyStaff> c_staffs = null;
		c_staffs = Arrays
					.asList(template.getForObject("http://localhost:8080/company_staff/all", CompanyStaff[].class));
		
		model.addAttribute("c_staffs", c_staffs);
		return "inout/inout_report.html";
	}
	@PostMapping("/search")
	public String searchCustomerByName(@RequestParam String name, Model model) {
		List<CompanyStaff> c_staffs = null;
		if (name.isEmpty()) {
			c_staffs = Arrays
					.asList(template.getForObject("http://localhost:8080/company_staff/all", CompanyStaff[].class));
		} else {
			c_staffs = Arrays.asList(
					template.getForObject("http://localhost:8080/company_staff/name/{name}", CompanyStaff[].class, name));
		}
		model.addAttribute("c_staffs", c_staffs);
		return "inout/inout_report.html";
	}
	@PostMapping("/report")
	public String report(@RequestParam("dateReport") Date d, @RequestParam("staffId") int staffId, Model model) {	
		
		List<CompanyStaffInOut> inouts = Arrays.asList(template.getForObject("http://localhost:8080/staff_inout/report/{staffId}/{date}", 
				CompanyStaffInOut[].class, staffId, d));
		model.addAttribute("inouts", inouts);
		
		CompanyStaff staff = template.getForObject("http://localhost:8080/company_staff/{id}", 
				CompanyStaff.class, staffId);
		model.addAttribute("staff", staff);
		return "inout/report_detail.html";
	}
	
}
