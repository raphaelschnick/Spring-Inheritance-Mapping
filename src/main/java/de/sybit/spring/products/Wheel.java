package de.sybit.spring.products;

import de.sybit.spring.manufecturer.Manufacturer;

import javax.persistence.Entity;

@Entity
public class Wheel extends Product {

    private int size;

    public Wheel() {}

    public Wheel(String color, double price, Manufacturer manufacturer, int size) {
        super(color, price, manufacturer);
        this.setSize(size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Wheel{" +
                "id=" + this.getId() +
                ", color=" + this.getColor() +
                ", price=" + this.getPrice() +
                ", manufacturer=" + this.getManufacturer() +
                "size=" + size +
                '}';
    }
}
