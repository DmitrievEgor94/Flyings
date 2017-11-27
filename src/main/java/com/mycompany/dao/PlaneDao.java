package com.mycompany.dao;

import com.mycompany.models.Flight;
import com.mycompany.models.Passenger;
import com.mycompany.models.Plane;
import com.mycompany.models.Ticket;

import javax.persistence.Query;
import java.util.List;

public class PlaneDao extends AbstractDao<Plane> {

    private final static String NAMED_QUERY_PLANES_FOR_PASSENGERS = "findPlanesForPassenger";
    private final static String LAST_NAME_FIELD = "lastName";
    private final static String FIRST_NAME_FIELD = "firstName";

    public PlaneDao() {
        super(Plane.class);
    }

    @Override
    public void delete(Plane plane) {
        for (Ticket ticket : plane.getTickets()) {
            ticket.getPassenger().getTickets().remove(ticket);
            ticket.getFlight().getTickets().remove(ticket);
        }

        FlightDao flightDao = new FlightDao();

        for (Flight flight : plane.getFlights()) {
            flight.getPlanes().remove(plane);

            if (flight.getPlanes().size() == 0) {
                flightDao.delete(flight);
            } else {
                flightDao.update(flight);
            }
        }

        super.delete(plane);
    }

    //2 part

    //NamedQuery
    public List<Plane> getPlanesForPassenger(Passenger passenger) {
        Query query = super.entityManager.createNamedQuery(NAMED_QUERY_PLANES_FOR_PASSENGERS);

        query.setParameter(FIRST_NAME_FIELD, passenger.getFirstName());
        query.setParameter(LAST_NAME_FIELD, passenger.getLastName());

        return query.getResultList();
    }
}
