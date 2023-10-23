package by.teachmeskills.shop.services.impl;

import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.Role;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.enums.ShopConstants;
import by.teachmeskills.shop.exceptions.IncorrectUserDataException;
import by.teachmeskills.shop.exceptions.LoginException;
import by.teachmeskills.shop.exceptions.RegistrationException;
import by.teachmeskills.shop.exceptions.UserExistsException;
import by.teachmeskills.shop.repositories.CategoryRepository;
import by.teachmeskills.shop.repositories.RoleRepository;
import by.teachmeskills.shop.repositories.UserRepository;
import by.teachmeskills.shop.services.CustomUserDetailsService;
import by.teachmeskills.shop.services.OrderService;
import by.teachmeskills.shop.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static by.teachmeskills.shop.enums.InfoEnum.INCORRECT_DATA_INFO;
import static by.teachmeskills.shop.enums.InfoEnum.USER_NOT_FOUND_INFO;
import static by.teachmeskills.shop.enums.InfoEnum.WELCOME_INFO;
import static by.teachmeskills.shop.enums.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.shop.enums.PagesPathEnum.USER_ACCOUNT_PAGE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.BALANCE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.BIRTHDAY;
import static by.teachmeskills.shop.enums.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.EMAIL;
import static by.teachmeskills.shop.enums.RequestParamsEnum.INFO;
import static by.teachmeskills.shop.enums.RequestParamsEnum.NAME;
import static by.teachmeskills.shop.enums.RequestParamsEnum.ORDERS;
import static by.teachmeskills.shop.enums.RequestParamsEnum.PAGE_NUMBER;
import static by.teachmeskills.shop.enums.RequestParamsEnum.PAGE_SIZE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.SELECTED_PAGE_SIZE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.SURNAME;
import static by.teachmeskills.shop.enums.RequestParamsEnum.TOTAL_PAGES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.USER;
import static by.teachmeskills.shop.enums.RequestParamsEnum.USER_ID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;


    public UserServiceImpl(UserRepository userRepository, OrderService orderService, CategoryRepository categoryRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public User create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userRepository.save(entity);
    }

    @Override
    public void read() {
        userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователя с id %d не найдено.", id)));
        userRepository.delete(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователя с почтой %s не найдено.", email)));
    }

    @Override
    public ModelAndView authenticate(String email, String password) throws LoginException, IncorrectUserDataException {
User user = new User();
        if (user != null && user.getEmail() != null && user.getPassword() != null) {
            User loggedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

            if (loggedUser != null) {
                ModelMap model = new ModelMap();

                Pageable paging = PageRequest.of(0, ShopConstants.PAGE_SIZE, Sort.by("name").ascending());
                List<Category> categories = categoryRepository.findAll(paging).getContent();
                long totalItems = categoryRepository.count();
                int totalPages = (int) (Math.ceil((double) totalItems / ShopConstants.PAGE_SIZE));
                model.addAttribute(PAGE_NUMBER.getValue(), 1);
                model.addAttribute(PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
                model.addAttribute(SELECTED_PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
                model.addAttribute(TOTAL_PAGES.getValue(), totalPages);
                model.addAttribute(CATEGORIES.getValue(), categories);
                model.addAttribute(INFO.getValue(), WELCOME_INFO.getInfo() + loggedUser.getName() + ".");
                model.addAttribute(USER.getValue(), loggedUser);

                return new ModelAndView(HOME_PAGE.getPath(), model);
            } else {
                throw new LoginException(USER_NOT_FOUND_INFO.getInfo());
            }
        }
        throw new IncorrectUserDataException(INCORRECT_DATA_INFO.getInfo());
    }

    @Override
    public ModelAndView createUser(User user) throws RegistrationException {
        if (checkUserAlreadyExists(user.getEmail())) {
            Role role = roleRepository.findByName("user");
            if (role == null) {
                throw new RegistrationException("При регистрации пользователя произошла ошибка.");
            }
            user.setRoles(List.of(role));

            User createdUser = create(user);

            if (createdUser != null) {
                ModelMap model = new ModelMap();

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(createdUser.getEmail());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

                Pageable paging = PageRequest.of(0, ShopConstants.PAGE_SIZE, Sort.by("name").ascending());
                List<Category> categories = categoryRepository.findAll(paging).getContent();
                long totalItems = categoryRepository.count();

                int totalPages = (int) (Math.ceil((double) totalItems / ShopConstants.PAGE_SIZE));

                model.addAttribute(PAGE_NUMBER.getValue(), 1);
                model.addAttribute(PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
                model.addAttribute(SELECTED_PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
                model.addAttribute(TOTAL_PAGES.getValue(), totalPages);
                model.addAttribute(CATEGORIES.getValue(), categories);
                model.addAttribute(INFO.getValue(), WELCOME_INFO.getInfo() + createdUser.getName() + ".");
                return new ModelAndView(HOME_PAGE.getPath(), model);

            }
            throw new RegistrationException("При регистрации произошла ошибка.");
        }

        throw new UserExistsException("Пользователь с таким логином уже существует. " +
                "Перейдите на страницу входа.");
    }

    private boolean checkUserAlreadyExists(String email) {
        try {
            User existUser = getUserByEmail(email);
            return existUser == null;
        } catch (EntityNotFoundException e) {
            return true;
        }
    }

    @Override
    public ModelAndView generateAccountPage(String userEmail) {
        ModelMap model = new ModelMap();
        User user = getUserByEmail(userEmail);
        model.addAttribute(USER_ID.getValue(), user.getId());
        model.addAttribute(NAME.getValue(), user.getName());
        model.addAttribute(SURNAME.getValue(), user.getSurname());
        model.addAttribute(BIRTHDAY.getValue(), user.getBirthday().toString());
        model.addAttribute(EMAIL.getValue(), user.getEmail());
        model.addAttribute(BALANCE.getValue(), user.getBalance());

        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        model.addAttribute(ORDERS.getValue(), orders);

        return new ModelAndView(USER_ACCOUNT_PAGE.getPath(), model);
    }
}
