package by.teachmeskills.shop.utils;

import by.teachmeskills.shop.exceptions.RequestParamNullException;

import java.util.Optional;

public class HttpRequestParamValidator {
    private static final String REQUEST_PARAMETER_IS_NULL_ERROR = "Параметр запроса не инициализирован!";

    public static void validateParamNotNull(String param) throws RequestParamNullException {
        if (!Optional.ofNullable(param).isPresent()) {
            throw new RequestParamNullException(REQUEST_PARAMETER_IS_NULL_ERROR);
        }
    }
}
