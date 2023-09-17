package by.teachmeskills.shop.controllers;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.EntityOperationException;
import by.teachmeskills.shop.exceptions.RegistrationException;
import by.teachmeskills.shop.services.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

import static by.teachmeskills.shop.enums.PagesPathEnum.REGISTRATION_PAGE;
import static by.teachmeskills.shop.enums.ShopConstants.USER;

@RestController
@SessionAttributes({USER})
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openRegistrationPage() {
        return new ModelAndView(REGISTRATION_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView registration(@ModelAttribute(USER) @Validated User user, BindingResult bindingResult, ModelAndView modelAndView) throws RegistrationException, EntityOperationException {
        if (bindingResult.hasErrors()) {
            populateError("name", modelAndView, bindingResult);
            populateError("surname", modelAndView, bindingResult);
            populateError("birthday", modelAndView, bindingResult);
            populateError("email", modelAndView, bindingResult);
            populateError("password", modelAndView, bindingResult);
            modelAndView.setViewName(REGISTRATION_PAGE.getPath());
            return modelAndView;
        }
        return userService.createUser(user);
    }

    @ModelAttribute(USER)
    public User setUpUserForm() {
        return new User();
    }

    private void populateError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }
}