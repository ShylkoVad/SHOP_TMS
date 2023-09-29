package by.teachmeskills.shop.services;

import by.teachmeskills.shop.domain.Cart;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.exceptions.EntityNotFoundException;
import by.teachmeskills.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static by.teachmeskills.shop.enums.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.shop.enums.PagesPathEnum.SHOPPING_CART_PAGE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.IMAGES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.PRODUCT;
import static by.teachmeskills.shop.enums.RequestParamsEnum.SHOPPING_CART_PRODUCTS;
@Service
public class CartService {
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public CartService(ProductRepository productRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    public ModelAndView addProductToCart(String id, Cart shopCart) {
        ModelMap model = new ModelMap();

        int productId = Integer.parseInt(id);
        Product product = productRepository.findById(productId);
        shopCart.addProduct(product);

        model.addAttribute(PRODUCT.getValue(), product);
        model.addAttribute(IMAGES.getValue(), product.getImages());

        return new ModelAndView(PRODUCT_PAGE.getPath(), model);
    }

    public ModelAndView removeProductFromCart(String productId, Cart shopCart) throws EntityNotFoundException {
        ModelMap model = new ModelMap();

        shopCart.removeProduct(Integer.parseInt(productId));

        List<Product> products = shopCart.getProducts();
        List<List<Image>> images = new ArrayList<>();

        for (Product product : products) {
            images.add(product.getImages());
        }

        model.addAttribute(SHOPPING_CART_PRODUCTS.getValue(), products);
        model.addAttribute(IMAGES.getValue(), images.stream().flatMap(Collection::stream).collect(Collectors.toList()));

        return new ModelAndView(SHOPPING_CART_PAGE.getPath(), model);
    }

    public ModelAndView showCartProductList(Cart shopCart) {
        ModelMap model = new ModelMap();

        List<Product> products = shopCart.getProducts();
        List<List<Image>> images = new ArrayList<>();

        for (Product product : products) {
            images.add(product.getImages());
        }

        model.addAttribute(SHOPPING_CART_PRODUCTS.getValue(), products);
        model.addAttribute(IMAGES.getValue(), images.stream().flatMap(Collection::stream).collect(Collectors.toList()));

        return new ModelAndView(SHOPPING_CART_PAGE.getPath(), model);
    }

    public ModelAndView checkout(Cart shopCart, User user) {
        ModelMap model = new ModelMap();

        List<Product> productList = shopCart.getProducts();

        if (productList.isEmpty()) {
            return new ModelAndView(SHOPPING_CART_PAGE.getPath(), model);
        }

        Order order = Order.builder()
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .price(shopCart.getTotalPrice())
                .products(productList)
                .user(user)
                .build();

        orderService.create(order);

        shopCart.clear();
        return new ModelAndView(SHOPPING_CART_PAGE.getPath());
    }
}
