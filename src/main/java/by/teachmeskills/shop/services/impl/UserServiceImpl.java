package by.teachmeskills.shop.services.impl;

import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.RegistrationException;
import by.teachmeskills.shop.repositories.CategoryRepository;
import by.teachmeskills.shop.repositories.UserRepository;
import by.teachmeskills.shop.services.CategoryService;
import by.teachmeskills.shop.services.ImageService;
import by.teachmeskills.shop.services.OrderService;
import by.teachmeskills.shop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static by.teachmeskills.shop.enums.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.shop.enums.PagesPathEnum.LOGIN_PAGE;
import static by.teachmeskills.shop.enums.PagesPathEnum.USER_ACCOUNT_PAGE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.BIRTHDAY;
import static by.teachmeskills.shop.enums.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.EMAIL;
import static by.teachmeskills.shop.enums.RequestParamsEnum.IMAGES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.NAME;
import static by.teachmeskills.shop.enums.RequestParamsEnum.ORDERS;
import static by.teachmeskills.shop.enums.RequestParamsEnum.SURNAME;
import static by.teachmeskills.shop.enums.RequestParamsEnum.USER;
import static by.teachmeskills.shop.enums.ShopConstants.PAGE_SIZE;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final OrderService orderService;
    private final CategoryRepository categoryRepository;


    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService,
                           ImageService imageService, OrderService orderService, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.imageService = imageService;
        this.orderService = orderService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public User create(User entity) {
        return userRepository.create(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.read();
    }

    @Override
    public User update(User entity) {
        return userRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ModelAndView authenticate(String email, String password) {
        ModelMap model = new ModelMap();
        if (email != null && password != null) {
            User loggedUser = userRepository.findByEmailAndPassword(email, password);

            if (loggedUser != null) {
                List<Category> categories = categoryRepository.findPaginatedCategories(0, PAGE_SIZE);
                List<Image> images = new ArrayList<>();

                for (Category category : categories) {
                    images.add(imageService.getImageByCategoryId(category.getId()));
                }
                Long totalItems = categoryRepository.getTotalItems();
                int totalPages = (int) (Math.ceil(totalItems / PAGE_SIZE));
                model.addAttribute("currentPage", 1);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute(CATEGORIES.getValue(), categories);
                model.addAttribute(IMAGES.getValue(), images);
                model.addAttribute(USER.getValue(), loggedUser);

                return new ModelAndView(HOME_PAGE.getPath(), model);
            } else {
                return new ModelAndView(LOGIN_PAGE.getPath(), model);
            }
        }
        return new ModelAndView(LOGIN_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView createUser(User user) throws RegistrationException {

        User createdUser = create(user);
        if (createdUser != null) {
            ModelMap model = new ModelMap();
            List<Category> categories = categoryService.read();
            List<Image> images = new ArrayList<>();

            for (Category category : categories) {
                images.add(imageService.getImageByCategoryId(category.getId()));
            }
            model.addAttribute(CATEGORIES.getValue(), categories);
            model.addAttribute(IMAGES.getValue(), images);
            return new ModelAndView(HOME_PAGE.getPath(), model);
        } else {
            throw new RegistrationException("При регистрации произошла ошибка.");
        }
    }

    @Override
    public ModelAndView generateAccountPage(User user) {
        ModelMap model = new ModelMap();

        model.addAttribute(NAME.getValue(), user.getName());
        model.addAttribute(SURNAME.getValue(), user.getSurname());
        model.addAttribute(BIRTHDAY.getValue(), user.getBirthday().toString());
        model.addAttribute(EMAIL.getValue(), user.getEmail());
        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        model.addAttribute(ORDERS.getValue(), orders);

        return new ModelAndView(USER_ACCOUNT_PAGE.getPath(), model);
    }
}
