package by.teachmeskills.shop.services.impl;

import by.teachmeskills.shop.csv.converters.ProductCsvConverter;
import by.teachmeskills.shop.csv.dto.ProductCsv;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.domain.SearchDto;
import by.teachmeskills.shop.enums.ShopConstants;
import by.teachmeskills.shop.exceptions.ExportToFIleException;
import by.teachmeskills.shop.exceptions.ParsingException;
import by.teachmeskills.shop.repositories.ProductRepository;
import by.teachmeskills.shop.repositories.ProductSearchSpecification;
import by.teachmeskills.shop.services.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.shop.enums.InfoEnum.PRODUCTS_NOT_FOUND_INFO;
import static by.teachmeskills.shop.enums.InfoEnum.PRODUCTS_SUCCESSFUL_ADDED_INFO;
import static by.teachmeskills.shop.enums.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.shop.enums.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.shop.enums.PagesPathEnum.SEARCH_PAGE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.IMAGES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.INFO;
import static by.teachmeskills.shop.enums.RequestParamsEnum.PAGE_NUMBER;
import static by.teachmeskills.shop.enums.RequestParamsEnum.PAGE_SIZE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.PRODUCT;
import static by.teachmeskills.shop.enums.RequestParamsEnum.PRODUCTS;
import static by.teachmeskills.shop.enums.RequestParamsEnum.SELECTED_PAGE_SIZE;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCsvConverter productConverter;

    public ProductServiceImpl(ProductRepository productRepository, ProductCsvConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    @Override
    public Product create(Product entity) {
        return productRepository.save(entity);
    }

    public void getProductsByCategoryId(int categoryId) {
        productRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryId(categoryId, pageable).getContent();
    }

    @Override
    public ModelAndView getProductsBySearchParameters(SearchDto searchDto, int pageNumber, int pageSize) {
        ModelMap model = new ModelMap();

        if (searchDto != null) {

            Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
            ProductSearchSpecification productSearchSpecification = new ProductSearchSpecification(searchDto);
            List<Product> products = productRepository.findAll(productSearchSpecification, paging).getContent();

            if (!products.isEmpty()) {
                long totalItems = productRepository.count(productSearchSpecification);
                int totalPages = (int) (Math.ceil((double) totalItems / pageSize));

                model.addAttribute(PRODUCTS.getValue(), products);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute(PAGE_NUMBER.getValue(), pageNumber + 1);
                model.addAttribute(PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
            } else {
                model.addAttribute(INFO.getValue(), PRODUCTS_NOT_FOUND_INFO.getInfo());
            }
        }
        model.addAttribute(SELECTED_PAGE_SIZE.getValue(), pageSize);

        return new ModelAndView(SEARCH_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getProductData(int id) {
        ModelMap model = new ModelMap();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Продукта с id %d не найдено.", id)));
        model.addAttribute(PRODUCT.getValue(), product);
        model.addAttribute(IMAGES.getValue(), product.getImages());
        return new ModelAndView(PRODUCT_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView importProductsFromCsv(int pageNumber, int pageSize, MultipartFile file) {
        ModelMap model = new ModelMap();

        List<ProductCsv> csvProducts = parseCsv(file);
        List<Product> newProducts = Optional.ofNullable(csvProducts)
                .map(list -> list.stream()
                        .map(productConverter::fromCsv)
                        .toList())
                .orElse(null);
        if (Optional.ofNullable(newProducts).isPresent()) {
            newProducts.forEach(productRepository::save);
            model.addAttribute(INFO.getValue(), PRODUCTS_SUCCESSFUL_ADDED_INFO.getInfo());
            int categoryId = newProducts.get(0).getCategory().getId();

            Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
            List<Product> products = productRepository.findAllByCategoryId(categoryId, paging).getContent();
            model.addAttribute(PRODUCTS.getValue(), products);
        }

        return new ModelAndView(CATEGORY_PAGE.getPath(), model);
    }


    @Override
    public void exportProductsToCsv(HttpServletResponse response, int categoryId) throws ExportToFIleException {
        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=productsOfCategoryWithId" + categoryId + ".csv";
        response.setHeader(headerKey, headerValue);
        response.setCharacterEncoding("UTF-8");

        List<ProductCsv> csvProducts = productRepository.findAllByCategoryId(categoryId).stream().map(productConverter::toCsv).toList();

        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"Product ID", "Name", "Descriptions", "Price", "Category ID"};
            String[] nameMapping = {"id", "name", "description", "price", "categoryId"};

            csvWriter.writeHeader(csvHeader);

            for (ProductCsv productCsv : csvProducts) {
                csvWriter.write(productCsv, nameMapping);
            }
        } catch (IOException e) {
            throw new ExportToFIleException("Во время записи произошла ошибка.");
        }
    }

    private List<ProductCsv> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductCsv> csvToBean = new CsvToBeanBuilder<ProductCsv>(reader)
                        .withType(ProductCsv.class)
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
