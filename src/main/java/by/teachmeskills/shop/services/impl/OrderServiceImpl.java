package by.teachmeskills.shop.services.impl;

import by.teachmeskills.shop.csv.converters.OrderCsvConverter;
import by.teachmeskills.shop.csv.dto.OrderCsv;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.enums.RequestParamsEnum;
import by.teachmeskills.shop.exceptions.EntityNotFoundException;
import by.teachmeskills.shop.exceptions.ExportToFIleException;
import by.teachmeskills.shop.exceptions.ParsingException;
import by.teachmeskills.shop.repositories.OrderRepository;
import by.teachmeskills.shop.services.OrderService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.teachmeskills.shop.enums.PagesPathEnum.USER_ACCOUNT_PAGE;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderCsvConverter orderConverter;

    public OrderServiceImpl(OrderRepository orderRepository, OrderCsvConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    public Order create(Order entity) {
        return orderRepository.create(entity);
    }

    @Override
    public List<Order> read() {
        return orderRepository.read();
    }

    @Override
    public Order update(Order entity) {
        return orderRepository.update(entity);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        orderRepository.delete(id);
    }

    @Override
    public Order getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrderByDate(LocalDateTime date) {
        return orderRepository.findByDate(date);
    }

    @Override
    public List<Order> getOrdersByUserId(int id) {
        return orderRepository.findByUserId(id);
    }
    @Override
    public ModelAndView exportSmthFromCsv(MultipartFile file, User user) {
        ModelMap model = new ModelMap();

        model.addAttribute(RequestParamsEnum.USER_ID.getValue(), user.getId());
        model.addAttribute(RequestParamsEnum.NAME.getValue(), user.getName());
        model.addAttribute(RequestParamsEnum.SURNAME.getValue(), user.getSurname());
        model.addAttribute(RequestParamsEnum.BIRTHDAY.getValue(), user.getBirthday().toString());
        model.addAttribute(RequestParamsEnum.EMAIL.getValue(), user.getEmail());

        List<OrderCsv> csvOrders = parseCsv(file);
        List<Order> newOrders = Optional.ofNullable(csvOrders)
                .map(list -> list.stream()
                        .map(orderConverter::fromCsv)
                        .toList())
                .orElse(null);
        if (Optional.ofNullable(newOrders).isPresent()) {
            newOrders.forEach(orderRepository::create);
        }

        List<Order> orders = getOrdersByUserId(user.getId());
        model.addAttribute(orders.stream().collect(Collectors.toList()));

        return new ModelAndView(USER_ACCOUNT_PAGE.getPath(), model);
    }

    @Override
    public void importSmthToCsv(HttpServletResponse response, int userId) throws ExportToFIleException {
        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ordersOfUserWithId" + userId + ".csv";
        response.setHeader(headerKey, headerValue);
        response.setCharacterEncoding("UTF-8");

        List<OrderCsv> csvOrders = orderRepository.findByUserId(userId).stream().map(orderConverter::toCsv).toList();

        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"Order ID", "Created at", "Products", "Total price", "User ID"};
            String[] nameMapping = {"id", "createdAt", "products", "price", "userId"};

            csvWriter.writeHeader(csvHeader);

            for (OrderCsv orderCsv : csvOrders) {
                csvWriter.write(orderCsv, nameMapping);
            }
        } catch (IOException e) {
            throw new ExportToFIleException("Во время записи произошла ошибка.");
        }
    }

    private List<OrderCsv> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<OrderCsv> csvToBean = new CsvToBeanBuilder<OrderCsv>(reader)
                        .withType(OrderCsv.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();

                return csvToBean.parse();
            } catch (Exception ex) {
                throw new ParsingException(String.format("Ошибка во время преобразования данных: %s", ex.getMessage()));
            }
        }
        return Collections.emptyList();
    }
}
