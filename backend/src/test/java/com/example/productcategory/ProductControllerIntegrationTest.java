package com.example.productcategory;

import com.example.productcategory.entity.Category;
import com.example.productcategory.repository.CategoryRepository;
import com.example.productcategory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        category = categoryRepository.save(Category.builder()
                .title("Computers")
                .description("Computer products")
                .build());
    }

    @Test
    void createsProduct() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Laptop",
                                  "description": "Professional laptop",
                                  "price": 899.99,
                                  "image": "https://example.com/laptop.jpg",
                                  "categoryId": %d
                                }
                                """.formatted(category.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Laptop"))
                .andExpect(jsonPath("$.category.id").value(category.getId()));
    }

    @Test
    void validatesProductPayload() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "",
                                  "description": "",
                                  "price": -1,
                                  "categoryId": null
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.title").isArray())
                .andExpect(jsonPath("$.errors.description").isArray())
                .andExpect(jsonPath("$.errors.price").isArray())
                .andExpect(jsonPath("$.errors.categoryId").isArray());
    }

    @Test
    void returns404ForUnknownProduct() throws Exception {
        mockMvc.perform(get("/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found with id: 999"));
    }

    @Test
    void paginatesAndFiltersProductsByCategory() throws Exception {
        createsProduct();

        mockMvc.perform(get("/products")
                        .param("page", "0")
                        .param("size", "5")
                        .param("categoryId", category.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].category.id").value(category.getId()))
                .andExpect(jsonPath("$.size").value(5));
    }
}
