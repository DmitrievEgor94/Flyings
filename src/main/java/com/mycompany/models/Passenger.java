package com.mycompany.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "passengers")
@NamedQuery(
        name = "findPassengersWithSeveralTickets",
        query = "select p,count(t.id) from Passenger p join p.tickets t group by p having count(t.id) > 1"
)
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.REMOVE)
    private Set<Ticket> tickets = new HashSet<>();

    public Passenger() {
    }

    public Passenger(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (o.getClass() != this.getClass()) return false;

        Passenger passenger = (Passenger) o;

        return Objects.equals(passenger.id, this.id)
                && Objects.equals(passenger.firstName, this.firstName)
                && Objects.equals(passenger.lastName, this.lastName)
                && Objects.equals(passenger.tickets, this.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, tickets);
    }
}
