package de.sybit.spring.products;

import de.sybit.spring.manufecturer.Manufacturer;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Car extends Product {

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Wheel> wheels = new HashSet<>();

    public Car() {}

    public Car(String color, double price, Manufacturer manufacturer) {
        super(color, price, manufacturer);
    }

    public Car(String color, double price, Manufacturer manufacturer, Set<Wheel> wheels) {
        super(color, price, manufacturer);
        this.setWheels(wheels);
    }

    public Set<Wheel> getWheels() {
        return wheels;
    }

    public void setWheels(Set<Wheel> wheels) {
        this.wheels = wheels;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + this.getId() +
                ", color=" + this.getColor() +
                ", price=" + this.getPrice() +
                ", manufacturer=" + this.getManufacturer() +
                "wheels=" + wheels +
                '}';
    }
}
