package by.teachmeskills.shop.utils.actuator.endpoints;

import by.teachmeskills.shop.domain.Statistic;
import by.teachmeskills.shop.enums.PagesPathEnum;
import by.teachmeskills.shop.enums.RequestParamsEnum;
import by.teachmeskills.shop.exceptions.EntityNotFoundException;
import by.teachmeskills.shop.repositories.StatisticRepository;
import by.teachmeskills.shop.services.ProductService;
import by.teachmeskills.shop.services.UserService;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static by.teachmeskills.shop.enums.PagesPathEnum.STATISTIC_PAGE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.TIME_IN_MILLIS_PARAM;

@Component
@Endpoint(id = "statistic")
public class ShopStatisticEndpoint {
    private final ProductService productService;
    private final UserService userService;
    private final StatisticRepository statisticRepository;


    public ShopStatisticEndpoint(ProductService productService, UserService userService, StatisticRepository statisticRepository) {
        this.productService = productService;
        this.userService = userService;
        this.statisticRepository = statisticRepository;
    }
    @ReadOperation
    public ModelAndView getProductStatistic() {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();

        StopWatch stopWatch = new StopWatch("App");
        stopWatch.start("App Startup");
        userService.read();
        stopWatch.stop();

        modelMap.addAttribute(TIME_IN_MILLIS_PARAM.getValue(), stopWatch.getTotalTimeSeconds());
        modelAndView.setViewName(STATISTIC_PAGE.getPath());
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
    @WriteOperation
    public void writeOperation(@Selector int categoryId) throws EntityNotFoundException {
        StopWatch stopWatch = new StopWatch("App");
        stopWatch.start("App Startup");
        productService.getProductsByCategoryId(categoryId);
        stopWatch.stop();

        statisticRepository.save(Statistic.builder()
                .description("To get all category products spent time in seconds " + stopWatch.getTotalTimeSeconds()).build());
    }

    @DeleteOperation
    public void deleteOperation(@Selector int id) throws EntityNotFoundException {
        Optional<Statistic> statistic = statisticRepository.findById(id);
        statistic.ifPresent(statisticRepository::delete);
    }
}
