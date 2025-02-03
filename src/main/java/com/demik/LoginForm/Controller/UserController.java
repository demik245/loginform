package com.demik.LoginForm.Controller;

import com.demik.LoginForm.Entity.User;
import com.demik.LoginForm.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/users")
    @ResponseBody
    public List<User> getUsers() {
        return userService.getUsers();
    }


    @GetMapping("/signup")
    public String signupForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "signupForm";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "loginForm";
    }

    @PostMapping("/addUser")
    public String processForm(
            @ModelAttribute("user") User user,
            BindingResult theBindingResult
    ) {


        System.out.println("Last name:|" + user.getUsername() + "|");

        System.out.println("Binding results: " + theBindingResult.toString());

        System.out.println("\n\n\n\n");


        userService.AddUser(user);
        return "redirect:/";

    }

    @GetMapping("/loginUser")
    public String loginUser(@ModelAttribute("user") User user) {

        try {
            User user1 = userService.findUserByUsername(user.getUsername());
            if (user1.getPassword().equals(user.getPassword())) {
                return "redirect:/hello";
            }
        } catch (Exception e) {
            return "redirect:/login";
        }

        return "redirect:/login";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(User.class, stringTrimmerEditor);
    }

}
