package com.mycompany.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "cost", nullable = false)
    private long cost;

    @Column(name = "seat", nullable = false)
    private int seat;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    public Ticket() {
    }

    public Ticket(Date date, int seat, long cost) {
        this.date = date;
        this.seat = seat;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public long getSeat() {
        return seat;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public Plane getPlane() {
        return plane;
    }

    public long getCost() {
        return cost;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
        passenger.getTickets().add(this);
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        flight.getTickets().add(this);

    }

    public void setPlane(Plane plane) {
        this.plane = plane;
        plane.getTickets().add(this);
    }

    public void setCost(long cost) {
        this.cost = cost;
    }
}
