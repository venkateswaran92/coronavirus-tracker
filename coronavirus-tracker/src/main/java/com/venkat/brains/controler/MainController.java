package com.venkat.brains.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.venkat.brains.models.LocationStats;
import com.venkat.brains.service.CoronaVirusDataService;

@Controller
public class MainController {

	@Autowired
	private CoronaVirusDataService coronaVirusDataService;

	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> GobalList = coronaVirusDataService.getGobal(); 
		int totalReportedCases = GobalList.stream().mapToInt(data -> data.getLatesTotalCases()).sum();
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("locationStates", coronaVirusDataService.getGobal());
		return "home";
	}
}
