package by.teachmeskills.shop.repositories;


import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.domain.SearchDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ProductSearchSpecification implements Specification<Product> {
    private final SearchDto searchDto;

    public ProductSearchSpecification(SearchDto searchDto) {
        this.searchDto = searchDto;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Product> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Optional.ofNullable(searchDto).isPresent() && Optional.ofNullable(searchDto.getSearchKey()).isPresent() && !searchDto.getSearchKey().isBlank()) {
            predicates.add(criteriaBuilder
                    .or(criteriaBuilder.like(root.get("name"), "%" + searchDto.getSearchKey() + "%"),
                            criteriaBuilder.like(root.get("description"), "%" + searchDto.getSearchKey() + "%")));
        }

        if (Optional.ofNullable(searchDto).isPresent() && Optional.ofNullable(searchDto.getPriceFrom()).isPresent() && searchDto.getPriceFrom() > 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchDto.getPriceFrom()));
        }

        if (Optional.ofNullable(searchDto).isPresent() && Optional.ofNullable(searchDto.getPriceTo()).isPresent() && searchDto.getPriceTo() > 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchDto.getPriceTo()));
        }

        if (Optional.ofNullable(searchDto).isPresent() && Optional.ofNullable(searchDto.getCategoryName()).isPresent()
                && !searchDto.getCategoryName().isBlank()) {
            Join<Product, Category> productCategoryJoin = root.join("category");
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(productCategoryJoin.get("name"),
                    "%" + searchDto.getCategoryName() + "%")));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
