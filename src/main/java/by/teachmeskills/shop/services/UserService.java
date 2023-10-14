package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.EntityNotFoundException;
import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.LoginException;
import by.teachmeskills.shop.exceptions.RegistrationException;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {

    ModelAndView authenticate(User user) throws EntityNotFoundException, LoginException, IncorrectUserDataException;

    ModelAndView createUser(User user) throws RegistrationException, EntityNotFoundException, IncorrectUserDataException;

    ModelAndView generateAccountPage(String userEmail);

    User getUserByEmail(String email);

}