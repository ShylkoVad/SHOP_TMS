package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.EntityNotFoundException;
import by.teachmeskills.shop.exceptions.LoginException;
import by.teachmeskills.shop.exceptions.RegistrationException;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    User getUserById(int id);

    User getUserByEmailAndPassword(String email, String password) throws EntityNotFoundException;

    User getUserByEmail(String email) throws EntityNotFoundException;
    ModelAndView authenticate(User user) throws LoginException, EntityNotFoundException;

    ModelAndView createUser(User user) throws RegistrationException, EntityNotFoundException;

    ModelAndView generateAccountPage(User user);
}