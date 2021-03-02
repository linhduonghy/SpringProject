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

import com.iu.model.Service;

@Controller
@RequestMapping(path = "/service")
public class ServiceController {

	RestTemplate template = new RestTemplate();

	// get all
	@GetMapping("")
	public String getServices(Model model) {
		List<Service> services = Arrays
				.asList(template.getForObject("http://localhost:8080/service/all", Service[].class));
		model.addAttribute("services", services);
		return "service/service.html";
	}

	// search by name
	@PostMapping("/search")
	public String getByName(@RequestParam String name, Model model) {
		// name = "" -> get all
		if (name.isEmpty())
			return getServices(model);
		// get service by name
		List<Service> services = Arrays
				.asList(template.getForObject("http://localhost:8080/service/name/{name}", Service[].class, name));
		model.addAttribute("services", services);
		return "service/service.html";
	}

	// insert
	@GetMapping("/insert")
	public String showInsert(Model model) {
		Service service = new Service();
		model.addAttribute("service", service);
		return "/service/insert.html";
	}
	@PostMapping("/insert")
	public String insert(@ModelAttribute Service service) {
		service = template.postForObject("http://localhost:8080/service", service, Service.class);
		//System.out.println(service);
		return "redirect:/service";
	}
	
	// edit
	@GetMapping("/edit/{id}")
	public String showEdit(Model model, @PathVariable int id) {		
		Service service = template.getForObject("http://localhost:8080/service/{id}", Service.class, id);		
		model.addAttribute("service", service);
		return "/service/edit.html";
	}
	@PostMapping("/edit")
	public String edit(@ModelAttribute Service service) {
		System.out.println(service.getId());
		template.put("http://localhost:8080/service/{id}", service, service.getId());		
		return "redirect:/service";
	}
	
	// delete
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		template.delete("http://localhost:8080/service/{id}", id);		
		return "redirect:/service";
	}
	
}
