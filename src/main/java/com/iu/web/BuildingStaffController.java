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

import com.iu.model.BuildingStaff;

@Controller
@RequestMapping("/building_staff")
public class BuildingStaffController {

	RestTemplate template = new RestTemplate();

	@GetMapping
	public String getBuildingStaff(Model model) {
		List<BuildingStaff> b_staffs = Arrays
				.asList(template.getForObject("http://localhost:8080/building_staff/all", BuildingStaff[].class));
		model.addAttribute("b_staffs", b_staffs);
		return "building_staff/building_staff.html";
	}

	@PostMapping("/search")
	public String getByName(Model model, @RequestParam String name) {		
		if (name.isEmpty()) {
			return "redirect:/building_staff";			
		}
		List<BuildingStaff> b_staffs = Arrays.asList(
				template.getForObject("http://localhost:8080/building_staff/name/{name}", BuildingStaff[].class, name));		
		model.addAttribute("b_staffs", b_staffs);
		return "building_staff/building_staff.html";
	}
	
	// insert
	@GetMapping("/insert")
	public String showInsert(Model model) {
		model.addAttribute("b_staff", new BuildingStaff());
		return "building_staff/insert.html";
	}
	@PostMapping("/insert")
	public String insert(@ModelAttribute BuildingStaff buildingStaff) {		
		buildingStaff = template.postForObject("http://localhost:8080/building_staff", buildingStaff, BuildingStaff.class);		
		return "redirect:/building_staff";
	}	
	
	// edit
	@GetMapping("/edit/{id}")
	public String showEdit(Model model, @PathVariable int id) {
		BuildingStaff b_staff = template.getForObject("http://localhost:8080/building_staff/{id}", BuildingStaff.class, id);
		model.addAttribute("b_staff", b_staff);
		return "building_staff/edit.html";
	}
	@PostMapping("/edit") 
	public String edit(@ModelAttribute BuildingStaff b_staff) {			
		template.put("http://localhost:8080/building_staff/{id}", b_staff, b_staff.getId());
		return "redirect:/building_staff";
	}
	
	// delete
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {		
		template.delete("http://localhost:8080/building_staff/{id}", id);
		return "redirect:/building_staff";
	}
}
