package by.teachmeskills.shop.enums;

public enum CommandsEnum {
    LOGIN_COMMAND("login"), // login.jsp
    REDIRECT_TO_CATEGORY_COMMAND("redirect_category_page"), // home.jsp
    REDIRECT_TO_PRODUCT_COMMAND("redirect_to_product_page"), // category.jsp
    REGISTRATION_COMMAND("registration_user"), // registration.jsp
    ADD_PRODUCT_TO_SHOPPING_CART("add_product_to_shopping_cart"), // product.jsp
    REDIRECT_TO_CONTACT_COMMAND("redirect_to_contact_page"), // header.jsp
    REDIRECT_TO_HOME_PAGE_COMMAND("redirect_to_home_page"), // header.jsp
    SHOW_LOGIN_COMMAND("show_login_page"), // registration.jsp
    REDIRECT_TO_REGISTRATION_COMMAND("redirect_register_page"), // login
    REDIRECT_TO_CART_COMMAND("redirect_to_cart"),
    DELETE_PRODUCT_FROM_CART("remove_product_from_shopping_cart"), // shoppingCart.jsp
    REDIRECT_TO_USER_ACCOUNT_PAGE_COMMAND("redirect_to_user_account_page"),
    UPDATE_USER_DATA_COMMAND("update_user_data"),
    CHECKOUT_COMMAND("checkout"), // shoppingCart.jsp
    REDIRECT_TO_SEARCH_PAGE_COMMAND("redirect_to_search_page"), //search.jsp
    SEARCH_COMMAND("search"); //header.jsp

    private final String command;

    CommandsEnum(String command) {
        this.command = command;
    }

}
