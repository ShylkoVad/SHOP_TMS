package by.teachmeskills.shop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.shop.enums.PagesPathEnum.CONTACT_PAGE;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @GetMapping
    public ModelAndView openContactPage() {
        return new ModelAndView(CONTACT_PAGE.getPath());
    }
}
