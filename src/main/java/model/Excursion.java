package model;

import javax.persistence.*;

@Entity
@Table(name = "Excursions")
public class Excursion {
    @Id
    @GeneratedValue
    @Column(name = "id")
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "port_id")
    int portId;

    @Transient
    String portName;

    @Column(name = "price")
    int price;

    @Column(name = "description")
    String description;

    @Column(name = "isActive")
    boolean isActive;

    public Excursion() {
    }

    public Excursion(int id, String name, String portName, int price, String description, boolean isActive) {
        this.id = id;
        this.name = name;
        this.portName = portName;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
    }

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPortName() {
        return portName;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", portName='" + portName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
