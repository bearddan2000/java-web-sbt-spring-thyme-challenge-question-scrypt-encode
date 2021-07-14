package example.controller;

import example.model.*;
import example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

@Controller
public class RegisterController
{
  @Autowired
  private SecurityService securityService;

  @Autowired
  private UserService userService;

  @Autowired
  private ChallengeService challengeService;

	@GetMapping("/register")
	public String register(Model model)
	{
    if (securityService.isAuthenticated()) {
        return "redirect:/";
    }
		model.addAttribute("userForm", new Userform());
		model.addAttribute("challengeQuestion", new Challenge());
    model.addAttribute("challengeQuestionList", challengeService.findAllQuestions());
		return "register";
	}

  @PostMapping("/register")
  public String register(@ModelAttribute("userForm") Userform userForm
  , @ModelAttribute("challengeQuestion") Challenge challengeQuestion) {

      System.out.printf("[LOG Controller] Question: %s\n", challengeQuestion.toString());

      User user = new User(userForm.getUsername(), userForm.getPassword()
      , userService.createAuthorities("USER"), challengeQuestion);

      userService.save(user);

      securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

      return "redirect:/user";
  }
}
