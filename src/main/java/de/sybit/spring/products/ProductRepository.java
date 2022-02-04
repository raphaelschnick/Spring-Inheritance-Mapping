package de.sybit.spring.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from Car", nativeQuery = true)
    List<Car> findAllCars();

    @Query(value = "select * from Truck", nativeQuery = true)
    List<Truck> findAllTrucks();

    @Query(value = "select * from Bike", nativeQuery = true)
    List<Bike> findAllBikes();

    @Query(value = "select * from Wheel", nativeQuery = true)
    List<Wheel> findAllWheels();

}
