package model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cruises")
//@SecondaryTable(name = "ports")
public class Cruise {

    @Id
    @GeneratedValue
    @Column(name = "id")
    int id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    Ship ship;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "route_points",
            joinColumns = @JoinColumn(name = "route_id"), // References parent
            inverseJoinColumns = @JoinColumn(name = "port_id")
    )
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "ports")
    //@Column(name = "name", table = "ports")
    List<Port> ports;

    @Transient
    List<String> route;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "end_date")
    Date endDate;

    @Column(name = "base_price")
    int price;


    public Cruise() {
    }

    public Cruise(int id, Ship ship, List<String> route, Date start_date, Date endDate, int price) {
        this.id = id;
        this.ship = ship;
        this.route = route;
        this.startDate = start_date;
        this.endDate = endDate;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public Ship getShip() {
        return ship;
    }


    public List<String> getRoute() {
        return route;
    }

    public String getRouteString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < route.size(); i++) {
            str.append(route.get(i));
            if (i != route.size() - 1) {
                str.append(" - ");
            }
        }
        return str.toString();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", ship=" + ship +
                ", ports=" + ports +
                ", route=" + route +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                '}';
    }
}
