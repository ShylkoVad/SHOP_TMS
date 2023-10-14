package by.teachmeskills.shop.controllers;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.ExportToFIleException;
import by.teachmeskills.shop.services.OrderService;
import by.teachmeskills.shop.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/account")
public class UserAccountController {
    private final UserService userService;
    private final OrderService orderService;

    public UserAccountController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public ModelAndView openAccountPage() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.generateAccountPage(userEmail);
    }

    @PostMapping("/csv/import")
    public ModelAndView importOrdersFromCsv(@RequestParam("file") MultipartFile file, User user) {
        return orderService.exportSmthFromCsv(file, user);
    }

    @GetMapping("/csv/export/{userId}")
    public void exportOrdersToCsv(HttpServletResponse response, @PathVariable int userId) throws ExportToFIleException {
        orderService.importSmthToCsv(response, userId);
    }
}