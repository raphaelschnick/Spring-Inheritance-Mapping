package de.sybit.spring.products;

import de.sybit.spring.manufecturer.Manufacturer;

import javax.persistence.Entity;

@Entity
public class Truck extends Product {

    private double maxWeight;

    public Truck() {}

    public Truck(String color, double price, Manufacturer manufacturer, double maxWeight) {
        super(color, price, manufacturer);
        this.setMaxWeight(maxWeight);
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + this.getId() +
                ", color=" + this.getColor() +
                ", price=" + this.getPrice() +
                ", manufacturer=" + this.getManufacturer() +
                "maxWeight=" + maxWeight +
                '}';
    }
}
