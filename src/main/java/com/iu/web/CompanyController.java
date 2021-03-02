package com.iu.web;

import java.sql.Date;
import java.util.ArrayList;
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
import com.iu.model.CompanyService;
import com.iu.model.Service;

@Controller
@RequestMapping("company")
public class CompanyController {

	RestTemplate template = new RestTemplate();

	// get all
	@GetMapping
	public String getCompany(Model model) {
		List<Company> companies = Arrays
				.asList(template.getForObject("http://localhost:8080/company/all", Company[].class));
		model.addAttribute("companies", companies);

		return "company/company.html";
	}

	// search 
	@PostMapping("/search")
	public String searchCompany(@RequestParam("name") String name, Model model) {
		if (name.isEmpty()) 
			return "redirect:/company";
		// get companies by name
		List<Company> companies = Arrays.asList(template.getForObject("http://localhost:8080/company/name/{name}",
				Company[].class, name));
		model.addAttribute("companies", companies);
		return "company/company.html";
	}
	
	// insert
	@GetMapping("/insert")
	public String showInsert(Model model) {
		model.addAttribute("company", new Company());
		return "company/insert.html";
	}

	@PostMapping("/insert")
	public String insert(@ModelAttribute("company") Company company) {

		company = template.postForObject("http://localhost:8080/company", company, Company.class);

		// lay 2 dich vu mac dinh la Bao Ve, Ve sinh
		List<Service> defaultServices = Arrays.asList(
				template.getForObject("http://localhost:8080/service/default_company_service", Service[].class));

		List<CompanyService> css = new ArrayList<>();
		// company.setId();
		for (int i = 0; i < defaultServices.size(); ++i) {
			CompanyService cs = new CompanyService(company, defaultServices.get(i),
					new Date(System.currentTimeMillis()));
			css.add(cs);
		}

		// them 2 dich vu mac dinh
		for (int i = 0; i < defaultServices.size(); ++i) {
			// defaultServices.get(i).setCompanies(css);
			template.postForObject("http://localhost:8080/company_service/", css.get(i), CompanyService.class);
		}

		return "redirect:/company";
	}

	
	// edit 
	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable("id") int id, Model model) {
		Company company = template.getForObject("http://localhost:8080/company/{id}", Company.class, id);
		model.addAttribute("company", company);
		return "company/edit.html";
	}
	@PostMapping("/edit") 
	public String handleEdit(@ModelAttribute("company") Company company) {
		template.put("http://localhost:8080/company/{id}", company, company.getId());
		return "redirect:/company";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id) {
		template.delete("http://localhost:8080/company/{id}", id);
		return "redirect:/company";
	}
	
	// add service
	@GetMapping("/{company_id}")
	public String detailCompany(@PathVariable int company_id, Model model) {
		Company company = template.getForObject("http://localhost:8080/company/{id}", Company.class, company_id);
		model.addAttribute("company", company);

		List<Service> services = Arrays
				.asList(template.getForObject("http://localhost:8080/service/all", Service[].class));

		List<Service> dif = new ArrayList<Service>();
		for (Service s : services) {
			boolean f = company.getServices().stream().map(cs -> cs.getService()).anyMatch(x -> x.getId() == s.getId());
			if (!f) {
				dif.add(s);
			}
		}
		model.addAttribute("services", dif);
		return "company/detail.html";
	}

	@PostMapping("/insert_service")
	public String handleInsertService(@RequestParam("company_id") int company_id,
			@RequestParam("services") String[] services, Model model) {

		Company company = template.getForObject("http://localhost:8080/company/{id}", Company.class, company_id);
		List<CompanyService> css = new ArrayList<CompanyService>();

		for (String s : services) {
			Service service = template.getForObject("http://localhost:8080/service/{id}", Service.class,
					Integer.parseInt(s));
			css.add(new CompanyService(company, service, new Date(System.currentTimeMillis())));
		}

		for (CompanyService cs : css) {
			template.postForObject("http://localhost:8080/company_service/", cs, CompanyService.class);
		}
		return "redirect:/company/" + company_id;
	}

	// thong ke thong tin cong ty cung voi tong tien moi thang
	@GetMapping("/report")
	public String companyReport(Model model) {
		List<Company> companies = Arrays
				.asList(template.getForObject("http://localhost:8080/company/all", Company[].class));
		
		companies.sort((c1, c2) -> {
			if (c2.getTotalCost() < c1.getTotalCost()) 
				return -1;			
			return 1;
		});
		model.addAttribute("companies", companies);
		return "company/company_report.html";
	}
}
