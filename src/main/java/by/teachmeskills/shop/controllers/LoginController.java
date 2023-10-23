package by.teachmeskills.shop.controllers;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.EntityNotFoundException;
import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.LoginException;
import by.teachmeskills.shop.services.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

import static by.teachmeskills.shop.enums.PagesPathEnum.LOGIN_PAGE;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openLoginPage() {
        return new ModelAndView(LOGIN_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView login(@Validated User user, BindingResult bindingResult, ModelAndView modelAndView)
            throws LoginException, IncorrectUserDataException, EntityNotFoundException {
        if (bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("password")) {
            populateError("email", modelAndView, bindingResult);
            populateError("password", modelAndView, bindingResult);
            return new ModelAndView(LOGIN_PAGE.getPath());
        }
        return userService.authenticate(user.getEmail(), user.getPassword());
    }

    private void populateError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }
}
