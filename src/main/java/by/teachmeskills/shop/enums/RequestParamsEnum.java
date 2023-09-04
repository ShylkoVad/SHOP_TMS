package by.teachmeskills.shop.enums;

public enum RequestParamsEnum {
    COMMAND("command"),
    NAME("name"),
    SURNAME("surname"),
    BIRTHDAY("birthday"),
    EMAIL("email"),
    LOGIN("email"),
    PASSWORD("password"),
    USER("user"),
    INFO("info"),
    IMAGES("images"),
    CATEGORIES("categories"),
    CATEGORY_ID("category_id"),
    PRODUCTS("products"),
    PRODUCT("product"),
    PRODUCT_ID("product_id"),
    SHOPPING_CART("shopping_cart"),
    SHOPPING_CART_PRODUCTS("cartProductsList"),
    ORDERS("orders"),
    SEARCH_PARAM("search_param"),
    TIME_IN_MILLIS_PARAM("timeInMillis"),
    ERROR_PARAM("error");




    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}