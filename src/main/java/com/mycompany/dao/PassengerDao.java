package com.mycompany.dao;

import com.mycompany.models.Passenger;
import com.mycompany.models.Ticket;

import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class PassengerDao extends AbstractDao<Passenger> {

    private final static String NAMED_QUERY_FIND_PASSENGER_WITH_SEVERAL_TICKETS = "findPassengersWithSeveralTickets";

    private final static String PASSENGER_ENTITY_FIELD = "passenger";

    private final static String COST_FIELD = "cost";

    private final static int COST_OF_EXPENSIVE_TICKET = 250;


    public PassengerDao() {
        super(Passenger.class);
    }

    @Override
    public void delete(Passenger passenger) {
        for (Ticket ticket : passenger.getTickets()) {
            ticket.getFlight().getTickets().remove(ticket);
            ticket.getPlane().getTickets().remove(ticket);
        }

        super.delete(passenger);
    }

    //2 part

    //NamedQuery
    public void printPassengersWithSeveralTickets() {
        Query query = super.entityManager.createNamedQuery(NAMED_QUERY_FIND_PASSENGER_WITH_SEVERAL_TICKETS);

        List resultList = query.getResultList();

        for (Object result : resultList) {

            Object[] arr = (Object[]) result;

            Passenger passenger = (Passenger) arr[0];
            long numberOfTickets = (long) arr[1];

            System.out.println(passenger.getFirstName() + " " + passenger.getLastName() + " has " + numberOfTickets + " tickets.");

            System.out.println();
        }
    }

    //CriteriaApi
    public List<Tuple> getPassengersWithCostOfTickets() {
        CriteriaBuilder criteriaBuilder = super.entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);

        Root<Ticket> ticket = query.from(Ticket.class);
        Join<Ticket, Passenger> passenger = ticket.join(PASSENGER_ENTITY_FIELD);

        query.multiselect(passenger, criteriaBuilder.sum(ticket.get(COST_FIELD))).groupBy(passenger).
                having(criteriaBuilder.gt(criteriaBuilder.sum(ticket.get(COST_FIELD)), COST_OF_EXPENSIVE_TICKET));


        return super.entityManager.createQuery(query).getResultList();

    }
}
