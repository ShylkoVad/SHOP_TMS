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
    @ExceptionHandler(RegistrationException.class)
    public ModelAndView handleRegistrationException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ERROR_PARAM.getValue(), ex.getMessage());
        return new ModelAndView(ERROR_PAGE.getPath(), modelMap);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExportToFIleException.class)
    public ModelAndView handlerWritingToFileException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ERROR_PARAM.getValue(), ex.getMessage());
        return new ModelAndView(ERROR_PAGE.getPath(), modelMap);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParsingException.class)
    public ModelAndView handlerParsingException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ERROR_PARAM.getValue(), ex.getMessage());
        return new ModelAndView(ERROR_PAGE.getPath(), modelMap);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IncorrectUserDataException.class)
    public ModelAndView handlerIncorrectDataException(Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ERROR_PARAM.getValue(),
                String.format("Введены неверные данные: %s.",
                        ex.getMessage()));
        return new ModelAndView(ERROR_PAGE.getPath(), modelMap);
    }
}
