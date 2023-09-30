package by.teachmeskills.shop.enums;

import lombok.Getter;

@Getter
public enum InfoEnum {
    WELCOME_INFO("Добро пожаловать, "),
    CATEGORIES_SUCCESSFUL_ADDED_INFO("Новые категории загружены."),
    PRODUCTS_SUCCESSFUL_ADDED_INFO("Новые продукты загружены.");

    private final String info;

    InfoEnum(String info) {
        this.info = info;
    }
}
