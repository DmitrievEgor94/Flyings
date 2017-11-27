package com.mycompany.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "planes")
@NamedQuery(
        name = "findPlanesForPassenger",
        query = "select p from Plane p join p.tickets t join t.passenger pas " +
                "where pas.firstName = :firstName and pas.lastName = :lastName order by p.planeNumber"
)
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "plane_number")
    private String planeNumber;

    @ManyToMany(mappedBy = "planes")
    private Set<Flight> flights = new HashSet<>();

    @OneToMany(mappedBy = "plane", cascade = CascadeType.REMOVE)
    private Set<Ticket> tickets = new HashSet<>();

    public Plane() {
    }

    public Plane(String planeNumber) {
        this.planeNumber = planeNumber;
    }

    public long getId() {
        return id;
    }

    public String getPlaneNumber() {
        return planeNumber;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPlaneNumber(String planeNumber) {
        this.planeNumber = planeNumber;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
