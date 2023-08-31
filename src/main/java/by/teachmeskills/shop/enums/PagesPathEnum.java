package by.teachmeskills.shop.enums;

public enum PagesPathEnum {
    REGISTRATION_PAGE("registration"),
    LOGIN_PAGE("login"),
    HOME_PAGE("home"),
    CATEGORY_PAGE("category"),
    PRODUCT_PAGE("product"),
    SHOPPING_CART_PAGE("shoppingCart"),
    CONTACT_PAGE("contact"),
    USER_ACCOUNT_PAGE("user_account"),
    SEARCH_PAGE("search");

    private final String path;

    PagesPathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
