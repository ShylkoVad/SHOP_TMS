package by.teachmeskills.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.shop.enums.PagesPathEnum.ERROR_PAGE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.ERROR_PARAM;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({RegistrationException.class})
    public ModelAndView handleRegistrationException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ERROR_PARAM.getValue(), ex.getMessage());
        return new ModelAndView(ERROR_PAGE.getPath(), modelMap);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({LoginException.class})
    public ModelAndView handleLoginException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ERROR_PARAM.getValue(), ex.getMessage());
        return new ModelAndView(ERROR_PAGE.getPath(), modelMap);
    }
}
