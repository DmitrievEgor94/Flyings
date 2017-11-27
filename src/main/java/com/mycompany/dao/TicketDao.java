package com.mycompany.dao;

import com.mycompany.models.Flight;
import com.mycompany.models.Passenger;
import com.mycompany.models.Ticket;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class TicketDao extends AbstractDao<Ticket> {

    private final static String PASSENGER_ENTITY_FIELD = "passenger";
    private final static String ID_FIELD_NAME = "id";
    private final static String DATE_FIELD_NAME = "date";

    private final static String NATIVE_QUERY_GET_TICKETS_OF_FLIGHT = "select t.* from tickets t join flights  f " +
            "on t.flight_id = f.id where t.flight_id = ? order by t.date";

    public TicketDao() {
        super(Ticket.class);
    }

    @Override
    public void delete(Ticket ticket) {
        ticket.getPassenger().getTickets().remove(ticket);
        ticket.getFlight().getTickets().remove(ticket);
        ticket.getPlane().getTickets().remove(ticket);

        super.delete(ticket);
    }

    //2 part

    //NativeQuery
    public List<Ticket> getTicketsOfFlight(Flight flight) {
        Query nativeQuery = super.entityManager.createNativeQuery(NATIVE_QUERY_GET_TICKETS_OF_FLIGHT, Ticket.class);

        nativeQuery.setParameter(1, flight.getId());

        return nativeQuery.getResultList();
    }

    //CriteriaApi
    public List<Ticket> getTicketsOfPassenger(Passenger passenger) {
        CriteriaBuilder criteriaBuilder = super.entityManager.getCriteriaBuilder();

        CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> root = query.from(Ticket.class);

        Join<Ticket, Passenger> ticketPassengerJoin = root.join(PASSENGER_ENTITY_FIELD);

        query.where(criteriaBuilder.equal(ticketPassengerJoin.get(ID_FIELD_NAME), passenger.getId()));

        query.orderBy(criteriaBuilder.asc(root.get(DATE_FIELD_NAME)));

        return super.entityManager.createQuery(query).getResultList();
    }


}
