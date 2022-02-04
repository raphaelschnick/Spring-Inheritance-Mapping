package de.sybit.spring;


import de.sybit.spring.exceptions.EntityAlreadyExistsException;
import de.sybit.spring.exceptions.EntityNotExistsException;
import de.sybit.spring.exceptions.EntityNotFoundException;
import de.sybit.spring.products.Product;
import de.sybit.spring.products.ProductRepository;
import de.sybit.spring.products.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public static Product product = new Product("#0000F", 1L, ManufacturerServiceTest.manufacturer);

    @BeforeEach
    void setUp() {
        product = new Product("#0000F", 1L, ManufacturerServiceTest.manufacturer);
    }

    @Test
    void add() {
        when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);

        Product created = productService.add(product);

        assertThat(created.getColor()).isSameAs(product.getColor());
        verify(productRepository).save(product);
    }

    @Test
    void add_WithId() {
        product.setId(100L);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> productService.add(product));
    }

    @Test
    void getById() {
        product.setId(89L);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product expected = productService.getById(product.getId());

        assertThat(expected).isSameAs(product);
        Assertions.assertEquals(expected.getPrice(), product.getPrice());
        Assertions.assertEquals(expected.getManufacturer().getId(), product.getManufacturer().getId());
        Assertions.assertEquals(expected.toString(), product.toString());
        verify(productRepository).findById(product.getId());
    }

    @Test
    void getById_WithNotExists() {
        product.setId(89L);
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> productService.getById(product.getId()));
    }

    @Test
    void getList() {
        List<Product> cars = new ArrayList<>();
        cars.add(new Product());
        given(productRepository.findAll()).willReturn(cars);
        List<Product> expected = productService.getList();
        Assertions.assertEquals(expected, cars);
        verify(productRepository).findAll();
    }

    @Test
    public void update() {
        product.setId(12L);
        productService.update(product);
        verify(productRepository).saveAndFlush(product);
    }

    @Test
    public void update_WithNullId() {
        product.setId(null);
        Assertions.assertThrows(EntityNotExistsException.class, () -> productService.update(product));
    }

    @Test
    void delete() {
        product.setId(10L);
        productService.delete(product);
        verify(productRepository).delete(product);
    }
}
