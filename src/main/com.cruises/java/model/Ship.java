package model;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Component
@Entity
@Table(name="ships")
public class Ship {
    @Id
    int id;

    @Column(name="capacity")
    int capacity;

    @Column(name="model")
    String model;

    public Ship() {
    }

    public Ship(int id, int capacity, String model) {
        this.id = id;
        this.capacity = capacity;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getModel() {
        return model;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", model='" + model + '\'' +
                '}';
    }
}
