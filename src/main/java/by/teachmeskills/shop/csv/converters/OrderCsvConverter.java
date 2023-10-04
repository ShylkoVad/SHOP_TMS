package by.teachmeskills.shop.csv.converters;


import by.teachmeskills.shop.csv.dto.OrderCsv;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class OrderCsvConverter {
    private final ProductCsvConverter productConverter;
    private final UserRepository userRepository;

    public OrderCsvConverter(ProductCsvConverter productCsvConverter, UserRepository userRepository) {
        this.productConverter = productCsvConverter;
        this.userRepository = userRepository;
    }

    public OrderCsv toCsv(Order order) {
        return Optional.ofNullable(order).map(o -> OrderCsv.builder()
                        .id(o.getId())
                        .createdAt(o.getCreatedAt().toString())
                        .products(Optional.ofNullable(o.getProducts()).map(products -> products.stream()
                                .map(productConverter::toCsv).toList()).orElse(List.of()))
                        .price(o.getPrice())
                        .userId(o.getUser().getId())
                        .build())
                .orElse(null);
    }

    public Order fromCsv(OrderCsv orderCsv) {
        return Order.builder()
                .user(userRepository.findById(orderCsv.getUserId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователя с id %d не найдено.", orderCsv.getUserId()))))
                .price(orderCsv.getPrice())
                .createdAt(Timestamp.valueOf(LocalDateTime.parse(orderCsv.getCreatedAt())))
                .build();
    }
}
