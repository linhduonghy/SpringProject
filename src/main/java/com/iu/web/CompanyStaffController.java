package com.iu.web;

import java.util.Arrays;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.iu.model.Company;
import com.iu.model.CompanyStaff;

@Controller
@RequestMapping("/company_staff")
public class CompanyStaffController {

	RestTemplate rest = new RestTemplate();

	@GetMapping
	public String getAll(Model model) {
		List<CompanyStaff> c_staffs = Arrays
				.asList(rest.getForObject("http://localhost:8080/company_staff/all", CompanyStaff[].class));
		System.out.println(c_staffs);
		model.addAttribute("c_staffs", c_staffs);
		return "company_staff/company_staff.html";
	}

	@PostMapping("/search")
	public String searchCustomerByName(@RequestParam String name, Model model) {
		if (name.isEmpty()) {
			return "redirect:/company_staff";
		}
		List<CompanyStaff> c_satffs = Arrays.asList(
				rest.getForObject("http://localhost:8080/company_staff/name/{name}", CompanyStaff[].class, name));
		model.addAttribute("c_staffs", c_satffs);
		return "company_staff/company_staff.html";
	}

	// get
	@GetMapping("/insert")
	public String showInsertStaff(Model model) {
		List<Company> companies = Arrays
				.asList(rest.getForObject("http://localhost:8080/company/all", Company[].class));
		model.addAttribute("companies", companies);

		CompanyStaff staff = new CompanyStaff();
		model.addAttribute("staff", staff);
		return "company_staff/insert.html";
	}

	@PostMapping("/insert")
	public String insertStaff(@ModelAttribute("staff") CompanyStaff staff, Model model, @RequestParam int company_id) {
		Company company = rest.getForObject("http://localhost:8080/company/{id}", Company.class, company_id);
		staff.setCompany(company);
		staff = rest.postForObject("http://localhost:8080/company_staff", staff, CompanyStaff.class);
		return "redirect:/company_staff";
	}

	// edit
	@GetMapping("/edit/{id}")
	public String showEditStaff(@PathVariable int id, Model model) {
		List<Company> companies = Arrays
				.asList(rest.getForObject("http://localhost:8080/company/all", Company[].class));
		model.addAttribute("companies", companies);
		
		CompanyStaff staff = rest.getForObject("http://localhost:8080/company_staff/{id}", CompanyStaff.class, id);
		model.addAttribute("staff", staff);
		return "company_staff/edit.html";
	}

	@PostMapping("/edit")
	public String editStaff(@ModelAttribute("staff") CompanyStaff staff, Model model, @RequestParam int company_id) {
		Company company = rest.getForObject("http://localhost:8080/company/{id}", Company.class, company_id);
		staff.setCompany(company);
		
		rest.put("http://localhost:8080/company_staff/{id}", staff, staff.getId());
		return "redirect:/company_staff";
	}

	// delete
	@GetMapping("/delete/{id}")
	public String deleteStaff(@PathVariable int id) {
		rest.delete("http://localhost:8080/company_staff/{id}", id);
		return "redirect:/company_staff";
	}
}
