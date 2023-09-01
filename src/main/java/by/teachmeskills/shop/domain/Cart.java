package by.teachmeskills.shop.domain;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.teachmeskills.shop.enums.RequestParamsEnum.SHOPPING_CART_PRODUCTS;

public class Cart {
    private Map<Integer, Product> products;
    @Getter
    private double totalPrice = 0;

    public Cart() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
        totalPrice += product.getPrice();
    }

    public void removeProduct(int productId) {
        Product product = products.get(productId);
        products.remove(productId);
        totalPrice -= product.getPrice();
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public void clear() {
        products.clear();
    }
    public void shoppingCartProducts(HttpServletRequest request, List <Product> products) {
        request.setAttribute(SHOPPING_CART_PRODUCTS.getValue(), products);
    }
}
