package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.EntityOperationException;
import by.teachmeskills.shop.exceptions.RegistrationException;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    User getUserById(int id);

    User getUserByEmailAndPassword(String email, String password) throws EntityOperationException;

    User getUserByEmail(String email) throws EntityOperationException;
    ModelAndView authenticate(User user);

    ModelAndView createUser(User user) throws RegistrationException, EntityOperationException;

    ModelAndView generateAccountPage(User user);
}