package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.LoginException;
import by.teachmeskills.shop.exceptions.RegistrationException;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    User getUserById(int id);

    User getUserByEmailAndPassword(String email, String password);

    User getUserByEmail(String email);
    ModelAndView authenticate(User user) throws LoginException;

    ModelAndView createUser(User user) throws RegistrationException;

    ModelAndView generateAccountPage(User user);
}