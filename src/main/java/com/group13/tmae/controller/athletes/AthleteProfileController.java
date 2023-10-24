package com.group13.tmae.controller.athletes;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.repository.AthleteRepository;
import com.group13.tmae.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/athlete_profile")
public class AthleteProfileController {
    @Autowired
    private AthleteService athleteService;
    @Autowired
    private AthleteRepository athleteRepository;

    /**
     * Handles the submission of a form to create or update an athlete's information, including uploading a photo.
     * @param athlete The athlete object populated with the data from the form.
     * @return A string indicating the view to redirect to after processing the form submission.
     */
    @PostMapping("/saveAthlete")
    public String saveAthlete(@ModelAttribute("athlete") Athlete athlete) {
        try {
            MultipartFile photoFile = athlete.getPhotoFile();
            if (photoFile != null && !photoFile.isEmpty()) {
                athlete.setPhotoData(photoFile.getBytes());
                athlete.setPhotoContentType(photoFile.getContentType());
            }
            athleteService.createAthlete(athlete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/landing_page";
    }

    /**
     * Handles a request to view an athlete's profile.
     * @param id    The ID of the athlete to be retrieved.
     * @param model The model to which the athlete object is to be added.
     * @return      The name of the view to be rendered.
     */
    @GetMapping("/athlete/{id}")
    public String showAthleteProfile(@PathVariable(value = "id") Long id, Model model){
        Athlete athlete = athleteService.getAthleteById(id);
        model.addAttribute("athlete", athlete);
        model.addAttribute("photoData", athlete.getPhotoDataAsBase64());
        model.addAttribute("photoContentType", athlete.getPhotoContentType());

        //TODO change this string to the actual link for the athlete profile page.
        return "link_to_athlete_page";
    }
}