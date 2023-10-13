package com.group13.tmae.controller.athletes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/athletes")
public class AthletesListController {

    @GetMapping("athletes_list")
    public String showAthletesList(){
        System.out.println("here");
        return "athletes/athletes_list";
    }

}
