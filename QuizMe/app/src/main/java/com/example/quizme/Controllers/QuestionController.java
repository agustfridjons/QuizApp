package com.example.quizme.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@RestController
public class QuestionController {

    // Instance variables
    private QuestionService questionService;

    // Dependency injection
    @Autowired
    public QuestionController(QuestionService questionService) { this.questionService = questionService; }

    /**
     * Handle get request for path /questions
     * @param model
     * @param user
     * @param session
     * @return next view
     */
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String questionViewGet(Model model, User user, HttpSession session) {

        User isLoggedIn = (User) session.getAttribute("loggedIn");
        if (isLoggedIn != null) {
            model.addAttribute("name", isLoggedIn.getName());

            model.addAttribute("newQuestion", new Question());

            return "Questions";

        }
        session.setAttribute("error", "You must log in to access this site");
        return "redirect:/login";

    }

    int value = 60;

    /**
     * Handle post request for path /questions
     * @param newQuestion
     * @param model
     * @param session
     * @return next view
     */
    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public String questionViewPost(@ModelAttribute("newQuestion") Question newQuestion, Model model, HttpSession session) {

        User isLoggedIn = (User) session.getAttribute("loggedIn");
        model.addAttribute("name", isLoggedIn.getName());

        questionService.save(newQuestion);
        value++;

        model.addAttribute("newQuestion", new Question());

        model.addAttribute("questions", questionService.findAll());

        model.addAttribute("value", value);

        return "Questions";
    }
}