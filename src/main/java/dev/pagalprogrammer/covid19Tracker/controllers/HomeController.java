package dev.pagalprogrammer.covid19Tracker.controllers;

import dev.pagalprogrammer.covid19Tracker.models.LocationStats;
import dev.pagalprogrammer.covid19Tracker.services.Covid19Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    Covid19Service covid19Service;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("countryList", covid19Service.getAllCountries());
        return "index";
    }

    @GetMapping(value = "/result")
    public String result(@RequestParam(value = "country") String country, Model model){
        Set<String> allCountries = covid19Service.getAllCountries();
        if(country.isEmpty()){
            model.addAttribute("error", "Please Enter a country name to search.");
            model.addAttribute("countryList", covid19Service.getAllCountries());
            return "index";
        }
        else if(allCountries.contains(country.toLowerCase())){
            LocationStats location = (covid19Service.getAllStats()).get(country.toLowerCase());
            model.addAttribute("pastRecord",location.getPastRecord());
            model.addAttribute("country",country);
            model.addAttribute("confirmedCases",location.getConfirmedCases());
            model.addAttribute("recoveredCases",location.getRecoveredCases());
            model.addAttribute("deaths",location.getDeaths());
            return "result";
        }
        else{
            model.addAttribute("error", "Country not found in our database!");
            model.addAttribute("countryList", covid19Service.getAllCountries());
            return "index";
        }
    }
}
