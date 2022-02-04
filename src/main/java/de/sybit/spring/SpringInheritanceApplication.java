package de.sybit.spring;

import de.sybit.spring.customer.Customer;
import de.sybit.spring.customer.CustomerService;
import de.sybit.spring.manufecturer.Manufacturer;
import de.sybit.spring.manufecturer.ManufacturerService;
import de.sybit.spring.order.Order;
import de.sybit.spring.order.OrderService;
import de.sybit.spring.products.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class SpringInheritanceApplication {

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductRepository productRepository;


	public static void main(String[] args) {
		SpringApplication.run(SpringInheritanceApplication.class, args);
	}

	@PostConstruct
	public void test() {
		System.out.println("Start");

		Manufacturer manufacturer = this.manufacturerService.add(new Manufacturer("Mercedes"));

		Bike bike = new Bike("#0000F", 20.000, manufacturer, 30);
		this.productService.add(bike);

		Car car = new Car("#0000F", 20.000, manufacturer);
		this.productService.add(car);

		Truck truck = new Truck("#0000F", 20.000, manufacturer, 20);
		this.productService.add(truck);

		Wheel wheel = new Wheel("#0000F", 20.000, manufacturer, 27);
		this.productService.add(wheel);

		Car newCar = (Car) this.productService.getById(2L);
		newCar.getWheels().add((Wheel) this.productService.getById(4L));
		this.productService.update(newCar);

		List<Product> products = this.productService.getList();

		Customer customer = this.customerService.add(new Customer("Max", "Mustermann", "max.mustermann@muster.de", "012743684", 2));

		Order order = new Order(customer, products);
		this.orderService.add(order);
		List<Order> orderList = this.orderService.getList();
		System.out.println("End");

		System.out.println(this.productRepository.findAllCars());
	}

}
