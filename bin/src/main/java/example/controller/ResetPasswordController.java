package example.controller;

import example.model.*;
import example.service.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

@Controller
public class ResetPasswordController
{
  @Autowired
  private SecurityService securityService;

  @Autowired
  private UserService userService;

  @Autowired
  private ChallengeService challengeService;

	@GetMapping("/reset")
	public String reset(Model model)
	{
    if (securityService.isAuthenticated()) {
        return "redirect:/";
    }
		model.addAttribute("resetForm", new Userform());
		model.addAttribute("challengeQuestion", new Challenge());
    model.addAttribute("challengeQuestionList", challengeService.findAllQuestions());
		return "reset";
	}

  @PostMapping("/reset")
  public String reset(@ModelAttribute("resetForm") Userform userForm
  , @ModelAttribute("challengeQuestion") Challenge challengeQuestion) {

      UserDetails userdetails = userService.findByName(userForm.getUsername());

      if (userdetails == null) {
        System.out.println("[LOG] User not found");
        return "redirect:/register";
      }
      User user = (User)userdetails;
      Challenge c = user.getChallenge();

      boolean challengeMatch = challengeQuestion.equals(user.getChallenge());

      if (challengeMatch == false) {
        System.out.println("[LOG] Challenge failed");
        System.out.printf("[LOG] Challenge form %s\n", challengeQuestion.toString());
        System.out.printf("[LOG] Challenge user %s\n", user.getChallenge().toString());
        return "redirect:/redirect";
      }

      user = userService.resetPassword(userForm.getUsername(), userForm.getPassword());

      securityService.autoLogin(user.getUsername(), user.getPassword());

      return "redirect:/user";
  }
}
