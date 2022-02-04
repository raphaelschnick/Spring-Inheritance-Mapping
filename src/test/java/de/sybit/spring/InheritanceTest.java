package de.sybit.spring;

import de.sybit.spring.products.Bike;
import de.sybit.spring.products.Car;
import de.sybit.spring.products.Product;
import de.sybit.spring.products.ProductRepository;
import de.sybit.spring.products.ProductService;
import de.sybit.spring.products.Truck;
import de.sybit.spring.products.Wheel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class InheritanceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public static Car car;

    public static Bike bike;

    public static Wheel wheel;

    public static Truck truck;

    @BeforeEach
    void setUp() {
        car = new Car("#0000F", 20.000, ManufacturerServiceTest.manufacturer);
        truck = new Truck("#0000F", 1000, ManufacturerServiceTest.manufacturer, 2000);
        wheel = new Wheel("#0000F", 20.000, ManufacturerServiceTest.manufacturer, 27);
        bike = new Bike("#0000F", 20.000, ManufacturerServiceTest.manufacturer, 40);
    }

    @Test
    void add_Car() {
        when(productRepository.save(ArgumentMatchers.any(Car.class))).thenReturn(car);

        car.getWheels().add(wheel);
        Car created = (Car) productService.add(car);

        assertThat(created.getColor()).isSameAs(car.getColor());
        assertThat(created.getWheels().size()).isSameAs(car.getWheels().size());
        verify(productRepository).save(car);
    }

    @Test
    void add_Wheel() {
        when(productRepository.save(ArgumentMatchers.any(Wheel.class))).thenReturn(wheel);

        Wheel created = (Wheel) productService.add(wheel);

        assertThat(created.getSize()).isSameAs(wheel.getSize());
        verify(productRepository).save(wheel);

    }

    @Test
    void add_Truck() {
        when(productRepository.save(ArgumentMatchers.any(Truck.class))).thenReturn(truck);

        Truck created = (Truck) productService.add(truck);

        assertThat(created.getPrice()).isSameAs(truck.getPrice());
        verify(productRepository).save(truck);
    }

    @Test
    void add_Bike() {
        when(productRepository.save(ArgumentMatchers.any(Bike.class))).thenReturn(bike);

        Bike created = (Bike) productService.add(bike);

        assertThat(created.getId()).isSameAs(bike.getId());
        verify(productRepository).save(bike);
    }

    @Test
    void getList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Wheel());
        products.add(bike);
        products.add(car);
        products.add(truck);

        given(productRepository.findAll()).willReturn(products);
        List<Product> expected = productService.getList();
        Assertions.assertEquals(expected, products);
        verify(productRepository).findAll();
        System.out.println(expected);
    }
}
