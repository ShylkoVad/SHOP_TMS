package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.User;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    User getUserById(int id);

    User getUserByEmailAndPassword(String email, String password);

    User getUserByEmail(String email);
    ModelAndView authenticate(User user);

    ModelAndView createUser(User user);

    ModelAndView generateAccountPage(User user);
}