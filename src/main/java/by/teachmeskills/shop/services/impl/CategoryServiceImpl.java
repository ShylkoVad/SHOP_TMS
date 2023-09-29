package by.teachmeskills.shop.services.impl;

import by.teachmeskills.shop.csv.converters.CategoryCsvConverter;
import by.teachmeskills.shop.csv.dto.CategoryCsv;
import by.teachmeskills.shop.domain.Category;
import by.teachmeskills.shop.domain.Image;
import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.enums.InfoEnum;
import by.teachmeskills.shop.exceptions.EntityNotFoundException;
import by.teachmeskills.shop.exceptions.ExportToFIleException;
import by.teachmeskills.shop.exceptions.ParsingException;
import by.teachmeskills.shop.repositories.CategoryRepository;
import by.teachmeskills.shop.services.CategoryService;
import by.teachmeskills.shop.services.ProductService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.shop.enums.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.shop.enums.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.shop.enums.RequestParamsEnum.CATEGORIES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.CATEGORY_ID;
import static by.teachmeskills.shop.enums.RequestParamsEnum.IMAGES;
import static by.teachmeskills.shop.enums.RequestParamsEnum.INFO;
import static by.teachmeskills.shop.enums.RequestParamsEnum.PRODUCTS;
import static by.teachmeskills.shop.enums.ShopConstants.PAGE_SIZE;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final CategoryCsvConverter categoryConverter;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService, CategoryCsvConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public Category create(Category entity) {
        return categoryRepository.create(entity);
    }

    @Override
    public List<Category> read() {
        return categoryRepository.read();
    }

    @Override
    public Category update(Category entity) {
        return categoryRepository.update(entity);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        categoryRepository.delete(id);
    }

    @Override
    public ModelAndView getCategoryById(int id) throws EntityNotFoundException {
        ModelMap model = new ModelMap();
        Category category = categoryRepository.findById(id);

        if (category != null) {

            List<Product> products = productService.getProductsByCategoryId(category.getId());

            model.addAttribute(PRODUCTS.getValue(), products);
            model.addAttribute(CATEGORY_ID.getValue(), id);
        } else {
            throw new EntityNotFoundException("Категории не найдены.");
        }
        return new ModelAndView(CATEGORY_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getCategories() {
        ModelMap model = new ModelMap();
        List<Category> categories = categoryRepository.read();
        List<Image> images = new ArrayList<>();

        for (Category category : categories) {
            images.add(category.getImage());
        }

        model.addAttribute(CATEGORIES.getValue(), categories);
        model.addAttribute(IMAGES.getValue(), images);

        return new ModelAndView(HOME_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getPaginatedCategories(int currentPage) {
        ModelMap model = new ModelMap();
        List<Category> categories = categoryRepository.findPaginatedCategories(currentPage - 1, PAGE_SIZE);

        List<Image> images = new ArrayList<>();

        for (Category category : categories) {
            images.add(category.getImage());
        }

        Long totalItems = categoryRepository.getTotalItems();
        int totalPages = (int) (Math.ceil((double) totalItems / PAGE_SIZE));

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute(CATEGORIES.getValue(), categories);
        model.addAttribute(IMAGES.getValue(), images);

        return new ModelAndView(HOME_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView importCategoriesFromCsv(MultipartFile file) {
        ModelMap model = new ModelMap();

        List<CategoryCsv> csvCategories = parseCsv(file);
        List<Category> newCategories = Optional.ofNullable(csvCategories)
                .map(list -> list.stream()
                        .map(categoryConverter::fromCsv)
                        .toList())
                .orElse(null);

        if (Optional.ofNullable(newCategories).isPresent()) {
            newCategories.forEach(categoryRepository::create);
            model.addAttribute(INFO.getValue(), InfoEnum.CATEGORIES_SUCCESSFUL_ADDED_INFO.getInfo());
        }

        List<Category> categories = categoryRepository.findPaginatedCategories(0, PAGE_SIZE);

        Long totalItems = categoryRepository.getTotalItems();
        int totalPages = (int) (Math.ceil((double) totalItems / PAGE_SIZE));

        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute(CATEGORIES.getValue(), categories);
        return new ModelAndView(HOME_PAGE.getPath(), model);
    }

    @Override
    public void exportCategoriesToCsv(HttpServletResponse response) throws ExportToFIleException {
        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=categories.csv";
        response.setHeader(headerKey, headerValue);
        response.setCharacterEncoding("UTF-8");

        List<CategoryCsv> csvCategories = categoryRepository.read().stream().map(categoryConverter::toCsv).toList();

        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"Category ID", "Name", "Image path"};
            String[] nameMapping = {"id", "name", "imagePath"};

            csvWriter.writeHeader(csvHeader);

            for (CategoryCsv categoryCsv : csvCategories) {
                csvWriter.write(categoryCsv, nameMapping);
            }
        } catch (IOException e) {
            throw new ExportToFIleException("Во время записи произошла ошибка.");
        }
    }

    private List<CategoryCsv> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<CategoryCsv> csvToBean = new CsvToBeanBuilder<CategoryCsv>(reader)
                        .withType(CategoryCsv.class)
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
