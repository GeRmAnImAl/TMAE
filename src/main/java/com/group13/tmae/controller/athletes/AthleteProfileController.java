package com.group13.tmae.controller.athletes;

import com.group13.tmae.model.Athlete;
import com.group13.tmae.repository.AthleteRepository;
import com.group13.tmae.service.AthleteService;
import com.group13.tmae.service.Impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

/**
 * Controller for managing athlete profiles, including viewing, creating, and updating athlete information.
 */
@Controller
@RequestMapping("/athlete_profile")
public class AthleteProfileController {
    /**
     *
     */
    @Autowired
    private AthleteService athleteService;

    /**
     *
     */
    @Autowired
    private AthleteRepository athleteRepository;

    /**
     *
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final long MAX_FILE_SIZE = 3145728; // 3MB


    /**
     * Processes the form submission for creating or updating an athlete's information.
     *
     * @param athlete The athlete object populated with data from the form.
     * @return A string indicating the view to redirect to after processing the form submission.
     */
    @PostMapping("/saveAthlete")
    public String saveAthlete(@ModelAttribute("athlete") Athlete athlete){
        athleteService.createAthlete(athlete);
        return "redirect:/athlete_profile/userInfo";
    }

    @PostMapping("/updateAthlete")
    public String updateAthlete(@ModelAttribute("athlete") Athlete athlete){
        Athlete athleteToUpdate = athleteService.getAthleteById(athlete.getAthleteID());

        Class<?> clazz = Athlete.class;
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true); // to access private fields
            try {
                Object value1 = field.get(athlete);
                Object value2 = field.get(athleteToUpdate);

                // Checking if the value in 'athlete' is different from 'athleteToUpdate'
                if (value1 != null && !value1.equals(value2)) {
                    // Updating 'athleteToUpdate' with the value from 'athlete'
                    field.set(athleteToUpdate, value1);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        athleteService.updateAthlete(athleteToUpdate);
        return "redirect:/athlete_profile/userInfo";
    }

    /**
     * Handles the submission of a form to update an athlete's profile photo.
     *
     * @param photoFile The photo file uploaded by the athlete.
     * @param athleteID The unique identifier of the athlete whose profile photo is being updated.
     * @return A string indicating the view to redirect to after processing the form submission.
     */
    @PostMapping("/updatePhoto")
    public String updateAthletePhoto(@RequestParam("photoFile") MultipartFile photoFile,
                              @RequestParam("athleteID") Long athleteID, RedirectAttributes redirectAttributes) {
        try {

            Athlete athlete = athleteService.getAthleteById(athleteID);
            if (photoFile != null && !photoFile.isEmpty()) {
                if (photoFile.getSize() > MAX_FILE_SIZE) {
                    redirectAttributes.addFlashAttribute("error", "Image file is too large. Max size is 3MB.");
                    return "redirect:/athlete_profile/userInfo";
                }
                athlete.setPhotoData(photoFile.getBytes());
                athlete.setPhotoContentType(photoFile.getContentType());
            }
            athleteService.updateAthlete(athlete);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "An error occurred while saving the athlete.");
        }
        return "redirect:/athlete_profile/userInfo";
    }

    /**
     * Handles a request to view an athlete's profile.
     *
     * @param id    The ID of the athlete to be retrieved.
     * @param model The model to which the athlete object is to be added.
     * @return The name of the view to be rendered.
     */
    @GetMapping("/athlete/{id}")
    public String showAthleteProfile(@PathVariable(value = "id") Long id, Model model) {
        Athlete athlete = athleteService.getAthleteById(id);

        String welcome = athlete.getFirstName() + " " + athlete.getLastName() + "'s Profile";

        model.addAttribute("customWelcome", welcome);
        model.addAttribute("athleteID", athlete.getAthleteID());
        model.addAttribute("wins", athlete.getWins());
        model.addAttribute("losses", athlete.getLosses());
        model.addAttribute("ties", athlete.getTies());
        model.addAttribute("weight", athlete.getWeight());

        long age = ChronoUnit.YEARS.between(athlete.getBirthDate(), LocalDate.now());

        model.addAttribute("age", age);
        model.addAttribute("affiliation", athlete.getAffiliation());
        model.addAttribute("listEvents", athlete.getTournaments());

        if (athlete.getPhotoData() != null && athlete.getPhotoData().length > 0) {
            String photoDataAsBase64 = athlete.getPhotoDataAsBase64();
            model.addAttribute("photoData", athlete.getPhotoData()); // raw photo data
            model.addAttribute("photoDataAsBase64", photoDataAsBase64); // Base64 encoded photo data
            model.addAttribute("photoContentType", athlete.getPhotoContentType()); // Content type of the photo
        } else {
            model.addAttribute("photo", "/backgrounds/no-photo-icon.png");
        }


        return "athlete_view";
    }

    /**
     * Handles the request to view the profile page of the currently logged-in user. Populates the model with user details
     * and prepares attributes for display on the profile page.
     *
     * @param model The {@link Model} object that holds attributes to be displayed by the view.
     * @return The name of the user profile view template to render.
     */
    @GetMapping("/userInfo")
    public String userProfile(Model model) {
        try {
            Athlete user = this.customUserDetailsService.getLoggedInUser();

            String welcome = "Welcome " + user.getFirstName() + " " + user.getLastName() + "!";

            model.addAttribute("customWelcome", welcome);
            model.addAttribute("athleteID", user.getAthleteID());
            model.addAttribute("wins", user.getWins());
            model.addAttribute("losses", user.getLosses());
            model.addAttribute("ties", user.getTies());
            model.addAttribute("weight", user.getWeight());

            long age = ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now());

            model.addAttribute("age", age);
            model.addAttribute("affiliation", user.getAffiliation());
            model.addAttribute("listEvents", user.getTournaments());

            if (user.getPhotoData() != null && user.getPhotoData().length > 0) {
                String photoDataAsBase64 = user.getPhotoDataAsBase64();
                model.addAttribute("photoData", user.getPhotoData()); // raw photo data
                model.addAttribute("photoDataAsBase64", photoDataAsBase64); // Base64 encoded photo data
                model.addAttribute("photoContentType", user.getPhotoContentType()); // Content type of the photo
            } else {
                model.addAttribute("photo", "/backgrounds/no-photo-icon.png");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "user-profile-page";
    }

    @GetMapping("/updateUserInfo/{id}")
    public String showUpdateForm(@PathVariable(value = "id") Long id, Model model, RedirectAttributes redirectAttributes){
        Athlete user = this.customUserDetailsService.getLoggedInUser();
        System.out.println("*HERE* USER-ID: " + user.getAthleteID());
        System.out.println("*HERE* OTHER-ID: " + id);
        if(!user.getAthleteID().equals(id)){
            redirectAttributes.addFlashAttribute("warning", "Unauthorized attempt to alter user information.");
            return "redirect:/athlete_profile/athlete/" + id;
        }
        Athlete athlete = athleteService.getAthleteById(id);
        model.addAttribute("athlete", athlete);

        return "update_user_info";
    }
}
