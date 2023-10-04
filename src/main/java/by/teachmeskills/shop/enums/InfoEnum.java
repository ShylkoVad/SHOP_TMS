package by.teachmeskills.shop.enums;

import lombok.Getter;

@Getter
public enum InfoEnum {
    PRODUCTS_NOT_FOUND_INFO("Продукты не найдены."),
    WELCOME_INFO("Добро пожаловать, "),
    USER_NOT_FOUND_INFO("Пользователя с такими данными не найдено."),
    INCORRECT_DATA_INFO("Введены некорректные  данные."),
    CATEGORIES_SUCCESSFUL_ADDED_INFO("Новые категории загружены."),
    PRODUCTS_SUCCESSFUL_ADDED_INFO("Новые продукты загружены.");

    private final String info;

    InfoEnum(String info) {
        this.info = info;
    }
}
