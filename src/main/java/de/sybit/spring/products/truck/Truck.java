package de.sybit.spring.products.truck;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String color;

    private Long manufacturerId;

    private double maxWeight;

    private double price;

    public Truck() {}

    public Truck(String color, Long manufacturerId, int maxWeight, double price) {
        this.setColor(color);
        this.setManufacturerId(manufacturerId);
        this.setMaxWeight(maxWeight);
        this.setPrice(price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", manufacturerId=" + manufacturerId +
                ", maxWeight=" + maxWeight +
                ", price=" + price +
                '}';
    }
}
