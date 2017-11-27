package com.mycompany.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "flight_number")
    private String flightNumber;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.REMOVE)
    private Set<Ticket> tickets = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "flights_planes",
            joinColumns = @JoinColumn(name = "flight_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "plane_id", referencedColumnName = "id")
    )
    private Set<Plane> planes = new HashSet<>();

    public Flight() {
    }

    public Flight(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public long getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public Set<Plane> getPlanes() {
        return planes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setTickets(Set<Ticket> ticket) {
        this.tickets = ticket;
    }

    public void setPlanes(Set<Plane> planes) {
        this.planes = planes;
        planes.forEach(plane -> plane.getFlights().add(this));
    }
}
