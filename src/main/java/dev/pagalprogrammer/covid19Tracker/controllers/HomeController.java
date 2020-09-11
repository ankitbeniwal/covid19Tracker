package dev.pagalprogrammer.covid19Tracker.controllers;

import dev.pagalprogrammer.covid19Tracker.services.Covid19Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    Covid19Service covid19Service;

    @GetMapping("/")
    public String home(Model model){

        model.addAttribute("data",((covid19Service.getAllStats()).get("India")).getPastRecord());
        return "home";
    }
}
