package de.sybit.spring.products;

import de.sybit.spring.manufecturer.Manufacturer;

import javax.persistence.Entity;

@Entity
public class Bike extends Product {

    private int ps;

    public Bike() {}

    public Bike(String color, double price, Manufacturer manufacturer, int ps) {
        super(color, price, manufacturer);
        this.setPs(ps);
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + this.getId() +
                ", color=" + this.getColor() +
                ", price=" + this.getPrice() +
                ", manufacturer=" + this.getManufacturer() +
                ", ps=" + ps +
                '}';
    }
}
